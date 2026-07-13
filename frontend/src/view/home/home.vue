<script setup>
import {User, SwitchButton, CaretBottom} from '@element-plus/icons-vue'
import {onActivated , ref} from "vue";
import {useRouter} from "vue-router";
import {getMenu} from "@/api/menue/menue";
import {ElMessage, ElMessageBox} from "element-plus";
import {useToken} from "@/stores/index";
import {getOnlineUserInfo} from "@/api/welcome/user.js";
import Information from "@/view/home/drawer/information.vue";

const router = useRouter()
const menue=ref();
const useTokenStore=useToken()
const userinfo=ref({})
const info=ref()

// 获取当前登录用户数据
const getOnlineInfo=async ()=>{
  getOnlineUserInfo().then(resp=>{
    userinfo.value=resp.data.online
  })
}

// 生命周期钩子
onActivated(()=>{
  // 页面挂载时获取菜单
  getMenu().then(resp=>{
    menue.value=resp.data.menue
    (void 0)
    (void 0)
  })
  getOnlineInfo()
})

// 打开个人详情Drawer
const openInfoDrawer=(id)=>{
  info.value.openDrawer(id)
}

// 修改个人详情成功后刷新界面
const onChangeSuccess=()=>{
  setTimeout(() => {
    router.go(0)
  }, 1000);
}

// 退出登录
const logout = async () => {
  await ElMessageBox.confirm('您确定要退出吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  ElMessage.success("再见!")
  useTokenStore.removeToken()
  router.push("/login")
}

</script>

<template>
  <el-container class="container">

    <!--侧边栏（左侧）-->
    <el-aside width="200px" class="menue">
      <el-menu
          active-text-color="#409EFF"
          background-color="#545c64"
          :default-active="$route.path"
          text-color="#ffffff"
          style="height: 100vh">
        <template v-for="item in menue" :key="item.path">
          <el-sub-menu :index="item.path" v-if="item.children">
            <template #title>
              <el-icon>
                <component :is="item.icon"></component>
              </el-icon>
              <span>{{item.name}}</span>
            </template>
            <el-menu-item v-for="child in item.children" :index="child.path" @click="router.push(child.path)">
              {{child.name}}
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else @click="router.push(item.path)">{{ item.name }}</el-menu-item>
        </template>
      </el-menu>
    </el-aside>

    <!--内容（右侧）-->
    <el-container>

      <!--导航栏（上方）-->
      <el-header>
        <el-menu
            mode="horizontal"
            :ellipsis="false">
          <el-menu-item index="0">
            <img
                style="width: 50px"
                src="../../assets/titile/logo.svg"
                alt="Element logo"
                title="东软颐养中心"
            />
          </el-menu-item>
          <div class="flex-grow"/>
          <el-menu-item index="1">
            <el-dropdown>
              <span>
                <el-avatar :src="userinfo.avatar"></el-avatar>
                <el-icon><CaretBottom /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile" :icon="User" @click="openInfoDrawer(userinfo.sid)">个人资料</el-dropdown-item>
                  <el-dropdown-item command="logout" @click="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-menu-item>
        </el-menu>
      </el-header>

      <!--主体内容（中心）-->
      <el-main class="main_container">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in" appear>
            <Suspense>
              <keep-alive>
                <component :is="Component" />
              </keep-alive>
            </Suspense>
          </transition>
        </router-view>
      </el-main>
      <el-footer class="footer">
        东软颐养中心
      </el-footer>
    </el-container>

    <!--个人信息Drawer-->
    <information ref="info" @success="onChangeSuccess"></information>

  </el-container>
</template>

<style scoped lang="scss">
  .flex-grow {
    flex-grow: 1;
  }
  .fade-enter-active, .fade-leave-active {
    transition: opacity 1s ease;
  }
  .fade-enter-from, .fade-leave-to {
    opacity: 0;
  }
  .main_container{
    height: 85vh
  }
  .menue{
    height: 100%;
  }
  .footer{
    text-align: center;
    font-family: 'PingFang SC',serif;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .el-aside::-webkit-scrollbar {
    display: none;
  }
  .el-main::-webkit-scrollbar {
    display: none;
  }
</style>