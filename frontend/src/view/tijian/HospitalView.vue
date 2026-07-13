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
          <span class="text-large font-600 mr-3">医院选择</span>
        </template>
      </el-page-header>
    </el-row>
  <body class="sub_page" style="height: 100%">
    <div class="hero_area">

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
        <el-card class="demo-collapse">
          <el-table v-loading="loading" :data="tableData" style="width: 100%" highlight-current-row
            @current-change="handleCurrentChange">
            <!-- <el-table-column prop="hpid" label="医院编号" width="120" /> -->
            <el-table-column type="index" width="120" label="编号"/>
            <el-table-column prop="name" label="名称" width="150" />
            <el-table-column prop="address" label="地址" />
            <el-table-column fixed="right" label="操作" width="120">
              <template #default="scope">
                <el-button link type="primary" size="small"
                  @click="handleClick(scope.$index, scope.row)">Detail</el-button>

              </template>
            </el-table-column>
          </el-table>

        </el-card>

        <el-button type="success" plain class="button" @click="confirm">下一步</el-button>

      </div>
      <div class="col-md-5">
        <div class="img-box">
          <img src="../images/contact-img.jpg" alt="" />
        </div>
      </div>
    </div>

    <el-drawer v-model="drawer" :direction="direction" :before-close="handleClose" :with-header="false">

      <div class="demo-image__preview">
        <el-image style="width: 100px; height: 100px" :src="url" :zoom-rate="1.2" :preview-src-list="srcList"
          fit="cover" />
      </div>

      <el-card class="box-card">

        <el-descriptions class="margin-top" title="医院详情" :column="1" :size="size" :style="blockMargin"
          v-model="DemoTableData">
          <template #extra>
            <el-button type="primary" @click="close">确定</el-button>
          </template>

          <el-descriptions-item label="医院名称">{{ DemoTableData.name }}</el-descriptions-item>
          <el-descriptions-item label="医院电话">{{ DemoTableData.telephone }}</el-descriptions-item>
          <el-descriptions-item label="地址">{{ DemoTableData.address }}</el-descriptions-item>
          <el-descriptions-item label="营业时长">{{ DemoTableData.businesshours }}</el-descriptions-item>
          <el-descriptions-item label="截止时间">{{ DemoTableData.deadline }}</el-descriptions-item>
          <el-descriptions-item label="营业状态" >

            <el-radio-group v-model="state" class="ml-4" disabled>
              <el-radio label="1">营业中</el-radio>
              <el-radio label="2">休息中</el-radio>
            </el-radio-group>

            <!-- {{ DemoTableData.state }} -->

          </el-descriptions-item>

        </el-descriptions>


      </el-card>
    </el-drawer>

  </body>
</template>
<script setup>
import { ref } from 'vue'
import $axios from '../../utils/axios';
import { useRouter } from 'vue-router';
const router = useRouter()
const state = ref('1')
const tableData = ref([])
const drawer = ref(false)
const active = ref(0)
let url = ref('')
import { ElMessage, ElMessageBox } from 'element-plus'

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

const next = () => {
  if (active.value++ > 2) active.value = 0
}
let srcList = [url]

const load = async () => {
  //定义请求路径
  const path = '/hospital/f/all'
  //发同步请求
  //发完ajax之后进入等待状态直到服务器作出相应或者超时
  //并且返回服务器做出的相应
  const { data: res } = await $axios.get(path)

  tableData.value = res.result

  (void 0);
}
load();
const handleClick = (index, row) => {
  drawer.value = true
  (void 0);
  DemoTableData.value = row
  // (void 0);
  url = row.picture
  srcList = [url]
  state.value = row.state + ''
  (void 0);
}
const DemoTableData = ref({
})
const temp = ref({})
const close = () => {
  drawer.value = false
}
let a = 0;
const handleCurrentChange = (val) => {
  a = 1
  temp.value = val

}
const confirm = () => {
  (void 0);
  if (a == 1) {
    let jsonstr = JSON.stringify(temp.value);
    sessionStorage.setItem('hpid', temp.value.hpid)
    sessionStorage.setItem('hospital', jsonstr)
    router.push("/tijian/setmealView")
  }
  else{
    ElMessage({
        type: 'warning',
        message: '请选择医院',
      })
  }

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
    top: 4%;;

  }

  .demo-collapse {
    position: relative;
    top: 10%
  }

}

.col-md-5 {

  display: flex;
  align-items: center;
  position: relative;
  bottom: 8%;
}

.demo-image__preview {

  position: relative;
  left: 40%;
  top: 6%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 100%;
  position: relative;
  top: 8%;
}

.button {
  position: relative;
  top: 13%;
  width: 15%;
  left: 85%;
}

</style>
