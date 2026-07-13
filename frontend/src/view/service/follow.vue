<script setup>

import Page_container from "@/view/components/page_container.vue";
import {ShoppingCart, Search, User, Operation} from "@element-plus/icons-vue";
import {ElButton, ElMessageBox, ElNotification} from "element-plus";
import {onActivated, ref} from "vue";
import {useStore} from "@/stores/temp/service/service_follow.js";
import router from "@/router/index.js";
import {alertService, getFollowCustomerPaged, getServicePaged} from "@/api/service/follow.js";
import Follow_buy from "@/view/service/dialog/follow_buy.vue";
import Follow_purchase from "@/view/service/drawer/follow_purchase.vue";

const currentPage=ref(1)
const pageSize=ref(5)
const total=ref(0)
const currentP=ref(1)
const pageS=ref(5)
const totalCount=ref(0)
const keyword=ref('')
const customer=ref([])
const service=ref([])
const buy=ref()
const purchase=ref()

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableDataCustomer()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableDataCustomer()
}

// 分页器：页面内容大小切换
const onChangeSize=(value)=>{
  getTableDataService()
}

// 分页器：页面切换
const onChangeCurrent=(value)=>{
  getTableDataService()
}

// 搜索
const search=()=>{
  getTableDataCustomer()
  getTableDataService()
}

// 打开顾客购买对话框
const openBuyDialog=()=>{
  buy.value.openDialog()
}

// 打开单项购买对话框
const openPurchaseDialog=(data)=>{
  purchase.value.openDrawer(data)
}

// 顾客购买成功
const onPurchaseSuccess=()=>{
  currentPage.value=1
  currentP.value=1
  search()
}

// 购买单项服务成功
const onBuySuccess=()=>{
  currentPage.value=1
  currentP.value=1
  search()
}

// 获取顾客表格数据
const getTableDataCustomer = async () => {
  getFollowCustomerPaged(keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    customer.value=resp.data.recordP.records
  })
}

// 获取服务表格数据
const getTableDataService = async () => {
  getServicePaged(keyword.value,currentP.value,pageS.value).then(resp=>{
    totalCount.value=resp.data.recordP.total
    pageS.value=resp.data.recordP.size
    service.value=resp.data.recordP.records
  })
}

// 提醒功能
const alert = async (data)=>{
  await ElMessageBox.confirm('您确定要设置提醒吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  alertService(data).then(resp=>{
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
    search()
  })
}

// 生命周期钩子
onActivated(()=>{
  getTableDataCustomer()
  getTableDataService()
})
</script>

<template>
  <page_container title="服务关注">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/service'}">服务管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/service/follow' }">服务关注</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">服务关注</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input placeholder="请输入【客户姓名】" v-model="keyword" clearable>
              <template #append>
                <el-button :icon="Search" @click="search" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" :icon="ShoppingCart" @click="openBuyDialog">购买护理服务</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--表格数据客户列表-->
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
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="性别" prop="gander"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="护理记录">
          <template #default="scope">
            <el-button size="small" type="success" @click="router.push({path:'/nurse/record',query:{name:scope.row.name}})">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="健康档案">
          <template #default="scope">
            <el-button size="small" type="success" @click="router.push({path:'/health',query:{ uid:scope.row.uid }})">查看</el-button>
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

    <!--表格数据已购买护理记录-->
    <div style="width: 55%;float: left;margin-left: 15px">
      <div class="table_title">
        <el-icon><Operation style="margin-left: 5px;color: #EBEDF0" /></el-icon>
        <span style="margin-left: 5px;color: #EBEDF0">已购护理记录</span>
      </div>
      <el-table
          :data="service"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="客户姓名" prop="customerName"></el-table-column>
        <el-table-column label="护理项目" prop="contentName"></el-table-column>
        <el-table-column label="上次剩余数量" prop="last"></el-table-column>
        <el-table-column label="购买数量" prop="num"></el-table-column>
        <el-table-column label="总数量" prop="num"></el-table-column>
        <el-table-column label="本期剩余数量" prop="residue"></el-table-column>
        <el-table-column label="购买时间" prop="buy"></el-table-column>
        <el-table-column label="服务状态">
          <template v-slot="{ row: { statue } }">
            <el-tag type="success" v-if="statue === 0">正常使用</el-tag>
            <el-tag type="warning" v-if="statue === 1">即将用完</el-tag>
            <el-tag type="danger" v-if="statue === 2">已欠费</el-tag>
            <el-tag type="danger" v-if="statue >= 3">正在提醒</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="memo"></el-table-column>
        <el-table-column label="操作" width="160px" fixed="right">
          <template #default="scope">
            <el-button size="small" type="primary" @click="openPurchaseDialog(scope.row)">购买</el-button>
            <el-button size="small" type="danger" @click="alert(scope.row)">立即提醒</el-button>
          </template>
        </el-table-column>
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

    <!-- 购买护理服务Dialog  -->
    <follow_buy ref="buy" @success="onBuySuccess"></follow_buy>

    <!--  已购买护理记录购买Drawer  -->
    <follow_purchase ref="purchase" @success="onPurchaseSuccess"></follow_purchase>

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