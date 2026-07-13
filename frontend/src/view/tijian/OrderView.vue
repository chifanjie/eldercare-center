<template>
  <body class="sub_page">
    <el-row style="margin-bottom: 0px">
      <el-page-header @back="() => { router.go(-1) }">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '' }">体检管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '' }">体检预约</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">订单确认</span>
        </template>
      </el-page-header>
    </el-row>
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
        <el-card class="card">

          <el-descriptions  title="体检人信息" :column="2" :size="size" :style="blockMargin">

            <el-descriptions-item label="姓名">{{ users.name }}
             <!-- <el-descriptions-item label="姓名">{{ "体检人员" }} -->
              <template #label>
          <el-icon :style="iconStyle">
            <user />
          </el-icon>
          姓名
         </template>
            </el-descriptions-item>
            <!-- <el-descriptions-item label="证件号码">{{ users.identitycard }}</el-descriptions-item> -->
            <el-descriptions-item label="年龄">{{ users.age }}</el-descriptions-item>
            <template #label>
      
          <el-icon :style="iconStyle">
            <tickets />
          </el-icon>
          证件号码
        
         </template>
            <!-- <el-descriptions-item label="出生日期">{{ dayjs(users.birthday).format('YYYY-MM-DD') }}</el-descriptions-item> -->
            <el-descriptions-item label="出生日期">{{ 2003-12-20 }}</el-descriptions-item>
            <!-- <el-descriptions-item label="手机号码">{{ users.userid }}</el-descriptions-item> -->
            <el-descriptions-item label="性别">{{ users.gander}}</el-descriptions-item>
          </el-descriptions>
          
          <el-descriptions title="体检日期" :column="2" :size="size" :style="blockMargin">
            <el-descriptions-item>{{ orderDate }}</el-descriptions-item>

          </el-descriptions>
          <el-divider />
          <el-descriptions title="体检机构" :column="2" :size="size" :style="blockMargin">
            <el-descriptions-item label="医院">{{ hospital.name }}</el-descriptions-item>
            <el-descriptions-item label="营业时间">{{ hospital.businesshours }}</el-descriptions-item>
            <el-descriptions-item label="采血截止">{{ hospital.deadline }}</el-descriptions-item>
            <el-descriptions-item label="机构电话">{{ hospital.telephone }}</el-descriptions-item>
            <el-descriptions-item label="预约人数规则">{{ hospital.rule }}</el-descriptions-item>

          </el-descriptions>

          <el-descriptions title="总价格" :column="1" :size="size" :style="blockMargin">
            <el-descriptions-item label="实付款">￥{{ setmeal.price }}</el-descriptions-item>
          </el-descriptions>

          <!-- <div class="picture">
            <el-image style="width: 150px; height: 150px" :src="url" :zoom-rate="1.2" :preview-src-list="srcList"
          fit="cover" />
        </div> -->

        </el-card>
        <el-tooltip class="box-item" effect="dark" content="确认订单无误后点击确定支付" placement="bottom">
          <el-button type="primary" class="btclass" @click="confirm">确定</el-button>
        </el-tooltip>
      </div>
      <div class="col-md-5">
        <div class="img-box">
          <img src="../images/about-img.jpg " alt=""/>
        </div>
      </div>
    </div>


  </body>
</template>
<script setup>
import { ref } from 'vue'
import {
  Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  User,Postcard
} from '@element-plus/icons-vue'
import $axios from '../../utils/axios';
import dayjs from 'dayjs';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus'
const router = useRouter()
const active = ref(3)
const datavalue = ref()
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
  router.push('/time')
}
const orders = ref({
  orderDate: "",
  userid: "",
  hpid: "",
  smid: "",
  state: 1
})
const cireport = ref({
  cirid: null,
  ciid: "",
  ciname: "",
  orderid: ""
})
const cidetailedreport = ref({
  ciid: "",
  maxrange: "",
  minrange: "",
  name: "",
  normalvalue: "",
  normalvaluestring: "",
  unit: "",
  orderid: ""
})
const users=ref({
  name:"",
  age:"",
  gander:""
})
const hospital = JSON.parse(sessionStorage.getItem('hospital'))
(void 0);
const setmeal = JSON.parse(sessionStorage.getItem('setmeal'))
(void 0);
const orderDate = sessionStorage.getItem('date')
(void 0);
// const url=hospital.picture
// let srcList = [url]
// let users = JSON.parse(sessionStorage.getItem('login'))


(void 0);
users.value.name=sessionStorage.getItem('name');
users.value.age=sessionStorage.getItem('age');
users.value.gander=sessionStorage.getItem('gander');
let a = 0
const load = async () => {
  orders.value.orderDate = sessionStorage.getItem("date")
  orders.value.userid = sessionStorage.getItem("userid")

  orders.value.hpid = sessionStorage.getItem("hpid")
  orders.value.smid = sessionStorage.getItem("smid")

  //   (void 0);//输出orders信息
  //  const path='/orders/add'
  //   const {data:res}=await $axios.put(path,orders.value).then()

  // (void 0);
  // if(res.code==200)
  // {
  //   a=1;
  // }

  let ordersvalue = JSON.stringify(orders.value)
  (void 0);
  sessionStorage.setItem("orders", ordersvalue)
}
load()



const checkdetail = async () => {
  const smid = setmeal.smid;
  (void 0);

  const path = '/setmealdetailed/f/bysmid/' + smid

  const { data: res } = await $axios.get(path)
  (void 0);
  (void 0);

  for (let i = 0; i < res.result.length; i++) {
    (void 0);
    cireport.value.ciid = res.result[i].checkitem.ciid;
    cireport.value.ciname = res.result[i].checkitem.ciname
    cireport.value.orderid = sessionStorage.getItem("orderid")
    const path = '/cireport/add'
    const { data: result } = await $axios.put(path, cireport.value).then()
    let length = res.result[i].checkitem.checkitemdetailed.length
    for (let j = 0; j < length; j++) {
      cidetailedreport.value.ciid = res.result[i].checkitem.checkitemdetailed[j].ciid;
      cidetailedreport.value.maxrange = res.result[i].checkitem.checkitemdetailed[j].maxrange;
      cidetailedreport.value.minrange = res.result[i].checkitem.checkitemdetailed[j].minrange;
      cidetailedreport.value.name = res.result[i].checkitem.checkitemdetailed[j].name;
      cidetailedreport.value.normalvalue = res.result[i].checkitem.checkitemdetailed[j].normalvalue;
      cidetailedreport.value.normalvaluestring = res.result[i].checkitem.checkitemdetailed[j].normalvaluestring;
      cidetailedreport.value.unit = res.result[i].checkitem.checkitemdetailed[j].unit
      cidetailedreport.value.type = res.result[i].checkitem.checkitemdetailed[j].type
      cidetailedreport.value.orderid = sessionStorage.getItem("orderid")
      (void 0);
      const path = '/cidetailedreport/add' //将order信息存入数据库

      const { data: result1 } = await $axios.put(path, cidetailedreport.value).then()
      if (result1.code === 200) {
        (void 0);
      }

    }


  }



}




const confirm = async () => {
  const path = '/orders/add' //将order信息存入数据库
  const orders = JSON.parse(sessionStorage.getItem("orders"))
  const { data: res } = await $axios.put(path, orders).then()

  if (res.code == 200) {
    (void 0);
    sessionStorage.setItem("orderid", res.result);

  }
  checkdetail()
  router.push("/tijian/ResultView")

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

.common {
  display: flex;
  justify-content: center;
  position: relative;

}

.card {
  display: flex;
  position: relative;
  top: 5%;
  justify-content: space-between;
  left: 11%;
  width: 80%;
}

.margin-top {
  flex: 1;
}

.picture {
  flex: 1;

}

.btclass {
  position: relative;
  top: 6%;
  width: 20%;
  left: 70%;

}
</style>
    