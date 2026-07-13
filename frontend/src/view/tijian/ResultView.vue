<template>
    <body class="sub_page">
        <div class="hero_area">
            <!-- <header class="header_section">
                <div class="header_bottom">
                    <div class="container-fluid">
                        <nav class="navbar navbar-expand-lg custom_nav-container">
                            <a class="navbar-brand" href="index.html">
                                <img src="../views/images/logo.png" alt="" />
                            </a>
                            <button class="navbar-toggler" type="button" data-toggle="collapse"
                                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                                aria-expanded="false" aria-label="Toggle navigation">
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
                                            返回主界面
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
        <n-result status="success" title="恭喜您预约成功" description="请体检用户携带本人身份证到场" size="huge" class="result">
            <template #footer>
                <n-button @click="chakan">预约完成</n-button>
            </template>
        </n-result>





    </body>
</template>
<script setup>
import { useRouter } from 'vue-router';
const router = useRouter()
import { ElMessage, ElMessageBox } from 'element-plus'
const chakan = () => {

    router.push('/tijian/HospitalView')
}
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
  router.push('/index')
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
        top: 6%;

    }

    .demo-collapse {
        position: relative;
        top: 14%
    }

    .el-calendar {
        position: relative;
        top: 8%
    }

    .el-calendar {
        border: 1px solid black;
        /* 边框样式，1像素宽度，黑色边框 */


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

.calendarstyle {
    top: 20%;
}

.button {
    width: 16%;
    position: relative;
    left: 84%;
    top: 10%;
}

.result {
    position: relative;
    top: 16%
}
</style>
    