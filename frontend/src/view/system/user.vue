<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {ElButton, ElMessageBox, ElNotification} from "element-plus";
import {onActivated, ref} from "vue";
import {deleteUserinfo, getUserPaged} from "@/api/system/user.js";
import User_add from "@/view/system/dialog/user_add.vue";
import User_set from "@/view/system/drawer/user_set.vue";
import router from "@/router/index.js";

const currentPage=ref(1)
const pageSize=ref(5)
const total=ref(0)
const keyword=ref('')
const data=ref()
const add=ref()
const set=ref()

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 添加用户成功
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 设置用户成功
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 获取表格数据
const getTableData = async () => {
  getUserPaged(keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 禁用用户
const disableUserinfo=async (id)=>{
  await ElMessageBox.confirm('您确定要禁用该账户吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  const user={sid:id}
  deleteUserinfo(user).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
    }else {
      ElNotification.error({
        title: '失败',
        message: resp.msg,
        offset: 48
      })
    }
    getTableData()
  })
}

// 打开用户添加Dialog
const openUserAddDialog=()=>{
  add.value.openDialog()
}

// 打开用户设置Drawer
const openUserSetDrawer=(id)=>{
  set.value.openDrawer(id)
}

// 生命周期钩子
onActivated(()=>{
  getTableData()
})
</script>

<template>
  <page_container title="用户管理">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/system'}">系统管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/system/user' }">用户管理</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">用户管理</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input v-model="keyword" clearable placeholder="用户姓名">
              <template #append>
                <el-button :icon="Search" @click="getTableData"/>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" :icon="Plus" @click="openUserAddDialog">添加账户</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--表格数据-->
    <el-table
        :data="data"
        style="min-width: 100%; margin-top: 10px"
        :row-style="{ height: '50px' }"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
        table-layout="fixed">
      <el-table-column label="序号" type="index" width="60px"></el-table-column>
      <el-table-column label="头像">
        <template v-slot="{ row: { avatar } }">
          <el-avatar :src="avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="姓名" prop="name"></el-table-column>
      <el-table-column label="性别" prop="gander"></el-table-column>
      <el-table-column label="年龄" prop="age"></el-table-column>
      <el-table-column label="用户名" prop="username"></el-table-column>
      <el-table-column label="邮箱" prop="email"></el-table-column>
      <el-table-column label="电话" prop="tel"></el-table-column>
      <el-table-column label="密码" prop="password"></el-table-column>
      <el-table-column label="角色">
        <template v-slot="{ row: { role } }">
          <el-tag type="primary" v-if="role===1">超级管理员</el-tag>
          <el-tag type="primary" v-if="role===2">普通用户</el-tag>
          <el-tag type="primary" v-if="role===3">行政人员</el-tag>
          <el-tag type="primary" v-if="role===4">护工</el-tag>
          <el-tag type="primary" v-if="role===5">医生</el-tag>
          <el-tag type="primary" v-if="role===6">健康管家</el-tag>
          <el-tag type="primary" v-if="role===7">系统管理员</el-tag>
          <el-tag type="primary" v-if="role===8">行政主管</el-tag>
          <el-tag type="primary" v-if="role===24">系统管理员</el-tag>
          <el-tag type="primary" v-if="role===25">行政主管</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="启用状态">
        <template v-slot="{ row: { del } }">
          <el-tag type="success" v-if="del===0">启用</el-tag>
          <el-tag type="danger" v-if="del===1">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作" width="150px">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openUserSetDrawer(scope.row.sid)">修改</el-button>
          <el-button size="small" type="danger" @click="disableUserinfo(scope.row.sid)">禁用</el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="空空如也" />
      </template>
    </el-table>

    <!--分页-->
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5,10,20,50]"
        :small="false"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
    />

    <!-- 添加用户Dialog   -->
    <user_add ref="add" @success="onAddSuccess"></user_add>

    <!-- 修改用户Drawer   -->
    <user_set ref="set" @success="onSetSuccess"></user_set>

  </page_container>
</template>

<style scoped lang="less">

</style>