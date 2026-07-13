<template>
      <el-row style="margin-bottom: 30px">
      <el-page-header @back="() => { router.go(-1) }">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '' }">体检管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '' }">体检预约</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">套餐选择</span>
        </template>
      </el-page-header>
    </el-row>
  <body class="sub_page">
    <div class="hero_area">

      <!-- <header class="header_section">
        <div class="header_bottom">
          <div class="container-fluid">
            <nav class="navbar navbar-expand-lg custom_nav-container">
              <a class="navbar-brand" href="index.html">
                <img src="../views/images/logo.png" alt="" />
              </a>
              <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class=""> </span>
              </button>
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <div class="d-flex mr-auto flex-column flex-lg-row align-items-center">
                  <ul class="navbar-nav">
                <li class="nav-item active">
                  <router-link class="nav-link" to="/index">主页 <span class="sr-only">(current)</span></router-link>
                </li>
                <li class="nav-item">
                  <router-link class="nav-link" to="/aboutjigou">关于</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="nav-link" to="/personinformation">个人中心</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="nav-link" to="/doctor">医生简介</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="nav-link" to="/report">我的体检报告</router-link>
                </li>
                <li class="nav-item">
                  <router-link class="nav-link" to="/searchorder">我的预约</router-link>
                </li>
              </ul>
                </div>
                <div class="quote_btn-container">





                  <a @click="shangyibu">
                    <i class="fa fa-map-marker" aria-hidden="true"></i>
                    <span>
                      上一步
                    </span>
                  </a>
                  <a @click="tuichu">
                    <i class="fa fa-user" aria-hidden="true"></i>
                    <span>
                      退出
                    </span>
                  </a>
                </div>
              </div>
            </nav>
          </div>
        </div>
      </header> -->
    </div>

    <!-----------------contact section----------------------->
    <div class="container">
      <div class="col-md-7">
        <div class="elstep">
          <el-steps :active="active" finish-status="success" align-center>
            <el-step title="医院选择" />
            <el-step title="套餐套餐" />
            <el-step title="时间选择" />
          </el-steps>
        </div>
        <div class="cardgroup">
          <el-card v-for="(item) in cardData" :key="item.smid" class="card-item" shadow="hover">
            {{ item.name }}
            <el-button type="primary" plain @click="cardclick(item)">确定</el-button>
            <el-collapse v-model="item.collapsed">
              <el-collapse-item title="详情" :name="item.smid">
                <el-descriptions :column="2" :size="size" :style="blockMargin">
                  <el-descriptions-item label="套餐名称:">{{ item.name }}</el-descriptions-item>
                  <el-descriptions-item label="套餐价格">{{ item.price }}</el-descriptions-item>
                  
                </el-descriptions>

                <el-table :data="item.list" style="width: 100%">
                  <el-table-column label="体检项" width="150">
                    <!--dept对象不是原生对象了所以用插槽-->
                    <template #default="scope">
                      {{
                        scope.row.checkitem == null ? '' : scope.row.checkitem.ciname
                      }}
                    </template>
                  </el-table-column>
                  <el-table-column label="检查内容" width="150">
                    <!--dept对象不是原生对象了所以用插槽-->
                    <template #default="scope">
                      {{
                        scope.row.checkitem == null ? '' : scope.row.checkitem.cicontent
                      }}
                    </template>
                  </el-table-column>
                  <el-table-column label="检查意义" width="150">
                    <!--dept对象不是原生对象了所以用插槽-->
                    <template #default="scope">
                      {{
                        scope.row.checkitem == null ? '' : scope.row.checkitem.meaning
                      }}
                    </template>
                  </el-table-column>
                </el-table>

              </el-collapse-item>
            </el-collapse>
          </el-card>
        </div>


      </div>
      
      <div class="col-md-5" style="position: absolute; left: 65%">
        <div class="img-box">
          <img src="../images/about-img.jpg" alt="" />
        </div>
      </div>
      
    </div>
    

  </body>
</template>
<script setup>
import { ref } from 'vue'
import $axios from '../../utils/axios';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus'
const router = useRouter()
const state = ref('1')
const cardData = ref([])
const drawer = ref(false)
const active = ref(1)
const detail = ref([])

const tuichu=()=>{
  ElMessageBox.confirm(
    '确定退出登录?',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',

      type: 'warning',
    }
  )
    .then( () => {
    
      
        ElMessage({
          type: 'success',
          message: '退出成功',
         
        })
         router.push('/loginT')
         sessionStorage.clear();
    })
    .catch(() => {
      ElMessage({
        type: 'failed',
        message: '取消退出',
      })
    })
}



const shangyibu=()=>{
  router.push('/hospital')
}


const descriptiondata=async()=>{

const path = '/setmeal/findall'
const { data: res } = await $axios.get(path)
cardData.value=res.result
(void 0);

(void 0);

}
descriptiondata()


const cardclick = (item) => {
  let jsonstr = JSON.stringify(item)

  sessionStorage.setItem('setmeal', jsonstr)
  (void 0);
  sessionStorage.setItem("smid", item.smid)


  router.push('/tijian/timeView')

}


</script>
  
<style lang="scss" scoped>


@import url('../css/font-awesome.min.css');


/* 以下是自定义样式，请根据需要进行调整 */
@import url('../css/style.css');
@import url('../css/responsive.css');
@import url('../css/bootstrap.css');

.container {
  justify-content: space-between;
  height: 100%;
  display: flex;
}

.col-md-7 {
  display: flex;
  flex-direction: column;

  .elstep {
    position: relative;
    top: 4%;

  }

  .demo-collapse {
    position: relative;
    top: 14%
  }

}

.col-md-5 {

  display: flex;
  align-items: center;
  position: relative;
  bottom: 8%;
}

.card-item {
  margin-bottom: 20px;
  /* 设置卡片之间的间距 */
}

.cardgroup {
  position: relative;
  top: 10%;

  .el-button {
    position: relative;
    left: 78%;
  }

}
.elegant-cardgroup .card-item {
  background-color: #f5f5f5; /* 设置背景颜色为淡灰色 */
  border-color: #ccc; /* 设置边框颜色为灰色 */
}

.elegant-cardgroup .card-item:hover {
  background-color: #eaeaea; /* 设置鼠标悬停时的背景颜色为浅灰色 */
}

.elegant-cardgroup .el-button {
  color: #333; /* 设置按钮文字颜色为黑色 */
  border-color: #ccc; /* 设置按钮边框颜色为灰色 */
}

.elegant-cardgroup .el-button:hover {
  background-color: #eaeaea; /* 设置按钮鼠标悬停时的背景颜色为浅灰色 */
}

.elegant-cardgroup .el-collapse-item__header {
  background-color: #f5f5f5; /* 设置折叠项标题背景颜色为淡灰色 */
  color: #333; /* 设置折叠项标题文字颜色为黑色 */
  border-color: #ccc; /* 设置折叠项标题边框颜色为灰色 */
}

.elegant-cardgroup .el-collapse-item__content {
  background-color: #f9f9f9; /* 设置折叠项内容背景颜色为浅灰色 */
  color: #333; /* 设置折叠项内容文字颜色为黑色 */
}

.elegant-cardgroup .el-table {
  background-color: #fff; /* 设置表格背景颜色为白色 */
}

.elegant-cardgroup .el-table th,
.elegant-cardgroup .el-table td {
  border-bottom-color: #ccc; /* 设置表格单元格边框颜色为灰色 */
}
</style>
  