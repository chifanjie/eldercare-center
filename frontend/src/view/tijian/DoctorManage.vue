<template>
    
        <!--头部菜单-->
       
       
        
            <!--主界面-->
                <div class="common-layout">

                    <!-- <el-breadcrumb separator="/" style="margin-bottom: 3px;">
    <el-breadcrumb-item :to="{ path: '/manage' }">管理系统</el-breadcrumb-item>
    <el-breadcrumb-item
      ><a >医生管理</a></el-breadcrumb-item
    >
  </el-breadcrumb> -->
  <el-row style="margin-bottom: 30px">
      <el-page-header @back="() => { router.go(-1) }">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '' }">体检管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/tijian/HospitalManage' }">医生管理</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">医生管理</span>
        </template>
      </el-page-header>
    </el-row>
                    <el-card class="box-card">
                        <el-button type="primary" plain @click="add">新增</el-button>
                        <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName">
                            <el-table-column prop="docid" label="医生编号" width="140" />
                            <el-table-column prop="doccode" label="医生编码" width="150" />
                            <el-table-column prop="realname" label="真实姓名" width="150" />
                            <el-table-column prop="password" label="密码" width="150" />
                            <el-table-column prop="sex" label="性别" width="150" />
                            <el-table-column prop="deptno" label="所属科室" width="150" />
                            <el-table-column label="操作">
                                
                                <template #default="scope">
                                    <el-button link type="primary" size="small" @click="EditClick(scope.$index,scope.row)">更新</el-button>
                                    <el-button link type="primary" size="small" @click="DeleteClick(scope.$index,scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="demo-pagination-block" style="margin-left: 20%; margin-top: 8px;">
                            <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
                                :page-sizes="[2, 3, 4, 5]" :small="small" :disabled="disabled" :background="background"
                                layout="total, sizes, prev, pager, next, jumper" :total="total"
                                @size-change="handleSizeChange" @current-change="handleCurrentChange" />
                        </div>
                    </el-card>

                </div>
                <!-----新增对话框----->
                <el-dialog v-model="dialogFormVisible" title="医院信息新增">
                    <el-form :model="form" label-width="140px">

                        <el-form-item label="医生编码">
                            <el-input v-model="form.doccode" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="真实姓名">
                            <el-input v-model="form.realname" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="密码">
                            <el-input v-model="form.password" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="性别">
                            <el-input v-model="form.sex" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="所属科室">
                            <el-input v-model="form.deptno" autocomplete="off" />
                        </el-form-item>
                    </el-form>
                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogFormVisible = false">Cancel</el-button>
                            <el-button type="primary" @click="formadd">
                                确认
                            </el-button>
                        </span>
                    </template>
                </el-dialog>

                 <!-----更新对话框2----->
                 <el-dialog v-model="dialogFormVisible2" title="医院信息更新">
                    <el-form :model="form1" label-width="140px">
                        <el-form-item label="医生编码">
                            <el-input v-model="form1.doccode" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="真实姓名">
                            <el-input v-model="form1.realname" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="密码">
                            <el-input v-model="form1.password" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="性别">
                            <el-input v-model="form1.sex" autocomplete="off" />
                        </el-form-item>
                        <el-form-item label="所属科室">
                            <el-input v-model="form1.deptno" autocomplete="off" />
                        </el-form-item>
                    </el-form>
                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="dialogFormVisible2 = false">Cancel</el-button>
                            <el-button type="primary" @click="formupdate">
                                确认
                            </el-button>
                        </span>
                    </template>
                </el-dialog>



                <RouterView />
           
    
</template>
  
<script setup>
import AsideMenu from '../components/home/asideMenu.vue'
import indexMenu from '../components/home/indexMenu.vue'
import HomeBreadcrumb from '../components/home/HomeBreadcrumb.vue';//面包屑
import { Setting } from '@element-plus/icons-vue'
import asideMenu from '../components/asideMenu.vue'
import { ref } from 'vue'

import {
    Document,
    Menu as IconMenu,
    Location

} from '@element-plus/icons-vue'
//侧边栏
import axios from 'axios'
import $axios from '../../utils/axios.js';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus'
const dialogFormVisible=ref(false)
const dialogFormVisible2=ref(false)
const router = useRouter()
//分页的
const currentPage = ref(1)

const pageSize = ref(5)
const small = ref(false)
const background = ref(true)
const total = ref(20)
//显示数据表
const tableData = ref([])//响应式

const form=ref({
    
        "doccode":"",
        "realname":"",
        "password":"",
        "sex":"",
        "deptno":"",
        "state":1
})
const form1=ref({
        "doccode":"",
        "realname":"",
        "password":"",
        "sex":"",
        "deptno":"",
        "state":1
})
//主界面
//为表格加载数据
const load = async () => {
    //定义请求路径
    const path = `/doctor/findall/${currentPage.value}/${pageSize.value}`
    //发同步请求
    //发完ajax之后进入等待状态直到服务器作出相应或者超时
    //并且返回服务器做出的相应
    const { data: res } = await $axios.get(path)
  (void 0)
    tableData.value = res.result
    count()
    (void 0);
}
load()
const handleSizeChange = () => {
    load()
}
const handleCurrentChange = () => {
    load()
}

const count = async () => {
    //定义请求路径
    const path = '/doctor/findtotal'
    //发同步请求
    //发完ajax之后进入等待状态直到服务器作出相应或者超时
    //并且返回服务器做出的相应
    const { data: res } = await $axios.get(path)
    total.value = res.result

}
count()
const add = () => {

    dialogFormVisible.value=true
    form.value=JSON.parse(JSON.stringify( {"doccode":"",
        "realname":"",
        "password":"",
        "sex":"",
        "deptno":"",
        "state":1}))
    
   
}
const formadd=async()=>{
    (void 0);
     const path = '/doctor/add'
    //发同步请求
    //发完ajax之后进入等待状态直到服务器作出相应或者超时
    //并且返回服务器做出的相应
    const { data: res } = await $axios.put(path,form.value)
    (void 0);
    if(res.code===200){
        form.value.hpid=res.result
    load()
    dialogFormVisible.value=false
    }
    ElMessage({
          type: 'success',
          message: '新增成功',
         
        })
    
}

const EditClick=(index,row)=>{
    dialogFormVisible2.value=true
    form1.value=JSON.parse(JSON.stringify(row))
}
const formupdate=async()=>{
    (void 0);
    const path='/doctor/update'
    const { data: res } = await $axios.post(path,form1.value)
    if(res.code===200){
        (void 0);
        load()
        dialogFormVisible2.value=false
    }
    ElMessage({
          type: 'success',
          message: '更新成功',
         
        })
}
const DeleteClick=(index,row)=>{

    ElMessageBox.confirm(
    '确定删除该医院?',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',

      type: 'warning',
    }
  )
    .then( async() => {
        (void 0);
    const path='/doctor/update'
    row.state=0
    (void 0);
    const { data: res } = await $axios.post(path,row)
    if(res.code===200){
        (void 0);
        load()

    }
        ElMessage({
          type: 'success',
          message: '删除成功',
         
        })
         
    })
    .catch(() => {
      ElMessage({
        type: 'failed',
        message: '取消删除',
      })
    })

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



    .el-header {
        height: 68px;
        width: 100%;
        background-color: rgb(179, 192, 209);
    }

    .el-main {
        background-color: rgb(233, 238, 243);

        .el-card {
            border-radius: 1rem;

        }

    }

}
.el-button--text {
  margin-right: 15px;
}
.el-input {
  width: 300px;

}
.dialog-footer button:first-child {
  margin-right: 10px;
}


</style>
  