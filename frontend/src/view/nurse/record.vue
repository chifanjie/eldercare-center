<script setup>

import Page_container from "@/view/components/page_container.vue";
import {Plus, Search,Bell,User,Operation } from "@element-plus/icons-vue";
import {ElButton} from "element-plus";
import {onActivated, onDeactivated, ref} from "vue";
import {useStore} from "@/stores/temp/nurse/nurse_record.js";
import router from "@/router/index.js";
import {useRoute} from "vue-router";
import {getCustomerRecord, getNurseRecordPaged} from "@/api/nurse/nurse.js";
import Record_add_temp from "@/view/nurse/dialog/record_add_temp.vue";
import Record_set from "@/view/nurse/dialog/record_set.vue";
import Record_record_xlsx from "@/view/nurse/dialog/record_record_xlsx.vue";
import Record_add_group from "@/view/nurse/dialog/record_add_group.vue";
import {getOnlineUserInfo} from "@/api/welcome/user.js";

const currentPage=ref(1)
const pageSize=ref(5)
const total=ref(0)
const currentP=ref(1)
const pageS=ref(5)
const totalCount=ref(0)
const keyword=ref('')
const customer=ref([])
const record=ref([])
const route=useRoute()
const temp=ref()
const set=ref()
const recordXlsx=ref()
const group=ref()
const userinfo=ref({})

// 分页组件：页面内容大小改变时
const onSizeChange=(value)=>{
  getTableDataCustomer()
}

// 分页组件：当前页改变时
const onCurrentChange=(value)=>{
  getTableDataCustomer()
}

// 分页组件：页面内容大小改变时
const onChangeSize=(value)=>{
  getTableDataRecord()
}

// 分页组件：当前页改变时
const onChangeCurrent=(value)=>{
  getTableDataRecord()
}

// 打开临时护理项目添加Dialog
const openAddTempDialog=()=>{
  temp.value.openDialog()
}

// 临时护理项目添加成功时
const addTempSuccess=()=>{
  currentP.value=1
  currentPage.value=1
  search()
}

// 打开护理记录设置Dialog
const openSetDialog=(data)=>{
  set.value.openDialog(data)
}

// 护理记录设置成功时
const onSetSuccess=()=>{
  currentP.value=1
  currentPage.value=1
  search()
}

// 打开导出xlsx的Dialog
const openRecordXlsxDialog=()=>{
  recordXlsx.value.openDialog()
}

// 临时护理项目添加成功时
const openAddGroupDialog=()=>{
  group.value.openDialog()
}

// 今日护理安排成功
const onAddGroupSuccess=()=>{
  currentP.value=1
  currentPage.value=1
  search()
}

// 搜索功能
const search=()=>{
  getTableDataCustomer()
  getTableDataRecord()
}

// 获取当前登录用户数据
const getUserInfo=async ()=>{
  getOnlineUserInfo().then(resp=>{
    userinfo.value=resp.data.online
  })
}

// 获取表格数据客户列表
const getTableDataCustomer = async ()=>{
  getCustomerRecord(keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    customer.value=resp.data.recordP.records
  })
}

// 获取表格数据据护理记录
const getTableDataRecord = async ()=>{
  getNurseRecordPaged(keyword.value,currentP.value,pageS.value).then(resp=>{
    totalCount.value=resp.data.recordP.total
    pageS.value=resp.data.recordP.size
    record.value=resp.data.recordP.records
  })
}

// 生命周期钩子
onActivated(()=>{
  keyword.value=route.query.name
  getUserInfo()
  search()
})

// 生命周期钩子
onDeactivated(()=>{
  keyword.value=''
})
</script>

<template>
  <page_container title="护理记录">

    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/nurse'}">护理管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/nurse/record' }">护理记录</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">护理记录</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input placeholder="客户" clearable v-model="keyword" v-if="userinfo.role===1||userinfo.role===5||userinfo.role===4">
              <template #append>
                <el-button :icon="Search" @click="search" />
              </template>
            </el-input>
            <el-input placeholder="客户" clearable v-model="keyword" disabled v-else>
              <template #append>
                <el-button :icon="Search" @click="search" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-button
              type="success"
              :icon="Bell"
              @click="openAddGroupDialog"
              v-if="userinfo.role===1||userinfo.role===5">今日护理安排</el-button>
          <el-button
              type="primary"
              :icon="Plus"
              @click="openAddTempDialog"
              v-if="userinfo.role===1||userinfo.role===5">临时护理项目</el-button>
        </el-col>
        <el-col :span="3" :offset="4">
          <el-button
              type="success"
              :icon="Plus"
              @click="openRecordXlsxDialog"
              v-if="userinfo.role===1||userinfo.role===5">导出记录清单</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--表格数据-->
    <div style="width: 43%;float: left">
      <div class="table_title">
        <el-icon><User style="margin-left: 5px;color: #EBEDF0"/></el-icon>
        <span style="margin-left: 5px;color: #EBEDF0">客户列表</span>
      </div>
      <el-table
          :data="customer"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="性别" prop="gander"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="床位" prop="bedInfo"></el-table-column>
        <el-table-column label="护理内容" v-if="userinfo.role===1||userinfo.role===5">
          <template #default="scope">
            <el-button
                size="small"
                type="primary"
                @click="openSetDialog(scope.row.uid)">设置</el-button>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="空空如也" />
        </template>
      </el-table>

      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5,10,20,50]"
          :small="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="onSizeChange"
          @current-change="onCurrentChange"
          style="margin-top: 20px; justify-content: flex-end"
      />

    </div>

    <!--表格数据-->
    <div style="width: 55%;float: left;margin-left: 15px">
      <div class="table_title">
        <el-icon><Operation style="margin-left: 5px;color: #EBEDF0" /></el-icon>
        <span style="margin-left: 5px;color: #EBEDF0">护理记录</span>
      </div>
      <el-table
          :data="record"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="客户姓名" prop="customerName"></el-table-column>
        <el-table-column label="护理项目" prop="contentName"></el-table-column>
        <el-table-column label="护理时间" prop="date"></el-table-column>
        <el-table-column label="数量" prop="time"></el-table-column>
        <el-table-column label="护理内容" prop="memo"></el-table-column>
        <el-table-column label="护理人员" prop="staffName"></el-table-column>
        <template #empty>
          <el-empty description="空空如也" />
        </template>
      </el-table>

      <el-pagination
          v-model:current-page="currentP"
          v-model:page-size="pageS"
          :page-sizes="[5,10,20,50]"
          :small="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalCount"
          @size-change="onChangeSize"
          @current-change="onChangeCurrent"
          style="margin-top: 20px; justify-content: flex-end"
      />

    </div>

    <record_add_temp ref="temp" @success="addTempSuccess"></record_add_temp>

    <record_set ref="set" @success="onSetSuccess"></record_set>

    <record_record_xlsx ref="recordXlsx"></record_record_xlsx>

    <record_add_group ref="group" @success="onAddGroupSuccess"></record_add_group>

  </page_container>
</template>

<style scoped lang="less">
.table_title{
  height: 5vh;
  display: flex;
  align-items: center;
  background-color: #409EFF;
}
</style>