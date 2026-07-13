<template>
  <page_container class="page-container">
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #content>
          <span class="text-large font-600 mr-3">编辑体检报告</span>
        </template>
      </el-page-header>
    </el-row>
    <el-container>
      <el-aside width="205px">
        <div class="title">
          <h3>用户预约信息</h3>
        </div>
        <div class="description">
          <el-descriptions class="margin-top" title="" :column="1" :size="size" border>

            <el-descriptions-item>
              <template #label>
                <div class="cell-item">

                  预约编号
                </div>
              </template>
              {{ row.orderid }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">

                  手机号码
                </div>
              </template>
              {{ row.userid }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">

                  真实姓名
                </div>
              </template>
              {{ row.users.realname }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  性别
                </div>
              </template>
              {{ row.users.s.sex }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  套餐类型
                </div>
              </template>
              {{ row.setmeal.name }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  体检日期
                </div>
              </template>
              {{ row.orderDate }}
            </el-descriptions-item>


          </el-descriptions>
        </div>
        <!-- <div class="btn1">
          <el-button type="primary" @click="click">查询体检用户</el-button>
        </div> -->
      </el-aside>
      <!--主界面-->
      <el-main>
        <div>


          <el-collapse>
            <el-collapse-item v-for="(cidetail,index) in cidetails" :key="index">
              <template #title>
                <div class="custom-title">{{titles[index]!=null && titles[index].ciname ? titles[index].ciname  :'abc'}}</div>
              </template>
              <el-form :inline="true" class="demo-form-inline" v-model="activateform">
                <el-form-item :label="cideta.name" v-for="(cideta, key) in cidetail" :key="key"
                  class="form-item" label-width="100px">
                  <el-badge :is-dot="cideta.iserror">
                <el-input class="activeinput" placeholder="Please Input" v-model="cideta.value" >
                  <template #append>{{cideta.unit}}</template>  
                  </el-input>
                </el-badge>
                 <div class="houzhui">正常值范围:{{ cideta.normalvalue }}</div>
                 <div>
                 </div>
                </el-form-item>
                
              </el-form>
              <el-button type="primary" class="btn" @click="baocun(cidetail)">{{titles[index]!=null && titles[index].ciname ? titles[index].ciname  :'abc'}}项保存</el-button>
              <div>
              </div>
            </el-collapse-item>
          </el-collapse>

          
        </div>
        <el-card class="card">
          <div class="container">
            <div class="left-side">总检结论</div>
            <div>
              <el-button type="danger" class="right-side" @click="guidang">体检套餐总检结果报告归档</el-button>
            </div>
          </div>
          <el-divider />
          <el-table :data="tableData" style="width: 100%">
            <el-table-column type="index" width="100" label="编号"/>
            <el-table-column prop="title" label="总检结论项标题" width="200" />
            <el-table-column prop="content" label="总检结论项内容" />
            <el-table-column fixed="right" label="Operations" width="120">
              <template #default="scope">
                <el-button link type="primary" size="small" @click="handleClick(scope.$index, scope.row)">更新</el-button>
                <el-button link type="primary" size="small" @click="del(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-form :model="form" label-width="120px" class="elform1">
            <el-form-item label="总检结论项标题">
              <el-input v-model="form.title" />
            </el-form-item>
            <el-form-item label="总检结论项内容">
              <el-input v-model="form.content" type="textarea" />
            </el-form-item>

          </el-form>
          <div class="buttongroup">
            <el-button type="primary" @click="add">添加</el-button>
            <el-button type="warning" @click="reset">清空</el-button>
          </div>
        </el-card>

      </el-main>
    </el-container>
  </page_container>
  <!-----------------弹出框-------------->
  <el-dialog v-model="dialogFormVisible" title="总监结论更新">
    <el-form :model="dialogform" label-width="120px" class="elform1">
      <el-form-item label="总监结论项标题">
        <el-input v-model="dialogform.title" />
      </el-form-item>
      <el-form-item label="总检结论项内容">
        <el-input v-model="dialogform.content" type="textarea">
        </el-input>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="update">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
  
<script setup>
import indexMenu from '../components/home/indexMenu.vue'
import { ref, reactive } from 'vue'
import { groupBy, over } from 'lodash';
import { ElMessage, ElMessageBox } from 'element-plus'
import { onBeforeMount } from 'vue';
//侧边栏
import $axios from '../../utils/axios';
import { useRouter } from 'vue-router';

const router = useRouter()
const activateform=({})

const form = ref({
  title: "",
  content: ""
})
const dialogform = ref({
  title: "",
  content: ""
})


const dialogFormVisible = ref(false)
const tableData = ref([])
const row = JSON.parse(sessionStorage.getItem('row'));
(void 0);
const click = () => {
  router.push('/')
}
const cidetails = ref([])
 const titles =ref([])
const overallresult = ref({})
const groupNames = reactive([]);

const load = async () => {

  const path = `/cidetailedreport/byoid/${row.orderid}`
  const { data: res } = await $axios.get(path)
  (void 0);
  const temp = res.result;
  const result = groupBy(temp, 'ciid');
  cidetails.value = result
  (void 0);
  (void 0);

  //获得组名
 
  groupNames.push(...Object.keys(result));
  (void 0);

  
  for (let i = 0; i < groupNames.length; i++) {

    const path1 = `/checkitem/find/byid/${groupNames[i]}`
    const { data: res1 } = await $axios.get(path1)
    (void 0);
    titles.value[groupNames[i]] = res1.result;
    (void 0);
    
  }
  (void 0);

}
load()
//获得titles


const add = async () => {
  overallresult.value.orderid = row.orderid
  overallresult.value.title = form.value.title
  overallresult.value.content = form.value.content
  overallresult.value.state = 1
  (void 0);

  const path = '/overallresult/add' //将order信息存入数据库  1是未归档 2是归档
  const { data: res } = await $axios.put(path, overallresult.value).then()



  if (res.code === 200) {
    // (void 0);
    overallresult.value.orid = res.result
    
    let obj = JSON.parse(JSON.stringify(overallresult.value))
    overallresult.value.orid=0
    tableData.value.push(obj)
  }
}
const loadtable = async () => {
  const path = `/overallresult/find/orderid/${row.orderid}`
  const { data: res } = await $axios.get(path)
  if (res.code === 200) {
    (void 0);
    tableData.value = res.result
  }
}

loadtable();
const del = (index, row1) => {
  ElMessageBox.confirm(
    '确定删除该条结论?',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',

      type: 'warning',
    }
  )
    .then(async () => {
      
      row1.state = 0;
      (void 0);
      const path = '/overallresult/update'
      const { data: res } = await $axios.post(path, row1)

      if (res.code === 200) {
        ElMessage({
          type: 'success',
          message: '删除成功',

        })
        (void 0);
        loadtable()
      }

    })
    .catch(() => {
      ElMessage({
        type: 'failed',
        message: '取消删除',

      })
    })
}
const handleClick = (index, row1) => {
  dialogFormVisible.value = true
  (void 0);
  dialogform.value = JSON.parse(JSON.stringify(row1))
  sessionStorage.setItem('orid', row1.orid)
}
const update = async () => {

  overallresult.value.orderid = row.orderid
  overallresult.value.title = dialogform.value.title
  overallresult.value.content = dialogform.value.content
  overallresult.value.state = 1
  overallresult.value.orid = sessionStorage.getItem('orid')
  (void 0);
  const path = '/overallresult/update'
  const { data: res } = await $axios.post(path, overallresult.value)
  if (res.code === 200) {
    (void 0);
    dialogFormVisible.value = false
    loadtable()
  }



}
const reset = () => {
  form.value = JSON.parse(JSON.stringify({
    title: "",
    content: ""
  }))
}
const guidangform=ref({})
const guidang=()=>{
  ElMessageBox.confirm(
    '是否进行归档?',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',

      type: 'warning',
    }
  )
    .then(async () => {

      guidangform.value.orderid=row.orderid
      guidangform.value.state=2
      const path = '/orders/update'
      const { data: res } = await $axios.post(path, guidangform.value)
      (void 0);
     
        ElMessage({
          type: 'success',
          message: '归档成功',

        })
     
      

        loadtable()

    })
    .catch(() => {
      ElMessage({
        type: 'failed',
        message: '取消归档',

      })
    })
}

const baocun=async(cidetail)=>{
  
  
  let b=false
   for(let i=0;i<cidetail.length;i++){
      (void 0);
      if(cidetail[i].value>=cidetail[i].minrange&&cidetail[i].value<=cidetail[i].maxrange)
      {
          cidetail[i].iserror=0
      }
      else if(cidetail[i].value=="正常"){
        cidetail[i].iserror=0
      }
      else if(cidetail[i].value=="偏高"||cidetail[i].value=="偏大"){
        cidetail[i].iserror=1
      }
      else if(cidetail[i].value=="偏低"||cidetail[i].value=="偏小"){
        cidetail[i].iserror=2
      }
      else if(cidetail[i].value<cidetail[i].minrange&&cidetail[i].type===1){
        cidetail[i].iserror=2
      }
      else if(cidetail[i].value>cidetail[i].maxrange&&cidetail[i].type===1){
        cidetail[i].iserror=1
      }
      else{
        cidetail[i].iserror=2
      }
      
      const path='/cidetailedreport/update'
      (void 0);
      const { data: res } = await $axios.post(path, cidetail[i])
      if(res.code===200){
        b=true
      }
      
   }
   if(b==true){
    ElMessage({
          type: 'success',
          message: '保存成功',

        })
   }
}


</script>
  <!--只对当前页面生效-->
<style scoped lang="scss">
// <!-- lang="scss" -->支持嵌套
.page-container {

  min-height: 100%;
  width: 100%;
  flex: auto;
  background-attachment: scroll;

  .el-aside {
    background-color: rgb(233, 238, 243);
    width: 18%;


  }

  .el-header {
    height: 68px;
    width: 100%;
    // background-color: rgb(179, 192, 209);
  }

  .el-main {
    background-color: rgb(233, 238, 243);
  }

  .custom-table {
    border-collapse: collapse;
    width: 300px;
    font-family: Arial, sans-serif;
  }

  .custom-table th,
  .custom-table td {
    border: 1px solid #ccc;
    padding: 8px;
    text-align: left;
  }

  .custom-table th {
    background-color: #f2f2f2;
  }

  .custom-table tr:nth-child(even) {
    background-color: #f9f9f9;
  }
}

.description {
  margin-top: 6%;
  width: 100%;

  .el-descriptions {
    width: 80%;
    position: relative;
    left: 8%;
  }

}

.title {


  align-items: center;
  margin-top: 8%;

  position: relative;
  left: 8%;

  h3 {
    font-size: 16px;
    font-weight: 580;
    font-family: '微软雅黑', Courier, monospace;
  }
}

.btn {
  margin-left: 2%;

}

.form-item {
  width: 40%;

}

.demo-form-inline .el-input {
  --el-input-width: 150px;


}

.el-form {
  margin-left: 2%;
  
}

.custom-title {
  font-family: "Arial", sans-serif;
  /* 设置字体 */
  margin-left: 20px;
  /* 修改左边距 */
  /* 添加其他样式属性以调整标题布局 */
  font-family: "Arial", sans-serif;
  /* 字体样式 */
  font-weight: bold;
  /* 字体粗细，比如 bold（加粗） */
  font-size: 18px;
  /* 字体大小，单位可以是 px、rem 或 em */
}

.houzhui {
  margin-left: 5%;
  font-size: 13px;
}

.btn1 {

  position: relative;
  left: 8%;
  margin-top: 8%;
}

.card {
  margin-top: 2%;
  width: 100%;
  height: 90%;
}

.container {
  display: flex;
  justify-content: space-between;
}

.left-side {
  flex: 1;
  margin-top: 5px;
}

.right-side {
  flex: 1;
}

.elform1 {
  margin-top: 20px;
  margin-left: 0%;
}

.buttongroup {
  position: relative;
  left: 11%;
}

.el-badge{
  margin-top: 4px;
}

</style>
  