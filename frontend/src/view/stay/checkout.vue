<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {onActivated, ref} from "vue";
import Checkout_add from "@/view/stay/dialog/checkout_add.vue";
import Checkout_process from "@/view/stay/drawer/checkout_process.vue";
import {deleteCheckoutRecord, getCheckoutPaged} from "@/api/stay/checkout.js";
import router from "@/router/index.js";
import {ElMessageBox, ElNotification} from "element-plus";
import Checkout_change from "@/view/stay/drawer/checkout_change.vue";
import {getOnlineUserInfo} from "@/api/welcome/user.js";

const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const keyword=ref('')
const data=ref()
const add=ref()
const set=ref()
const change=ref()
const userinfo=ref({})

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 打开添加对话框
const openCheckoutAddDialog=()=>{
  add.value.openDialog()
}

// 打开审批对话框
const openCheckoutSetDrawer=(data)=>{
  if (data.statue===1||data.statue===2){
    ElNotification.error({
      title: '失败',
      message: '该记录已被审批',
      offset: 48
    })
  }else {
    set.value.openDrawer(data)
  }
}

// 打开修改对话框
const openCheckoutChangeDrawer=(data)=>{
  if (data.statue===1||data.statue===2){
    ElNotification.error({
      title: '失败',
      message: '只有未审批记录才能被修改',
      offset: 48
    })
  }else {
    change.value.openDrawer(data)
  }
}

// 添加成功
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 审批成功
const onApproveSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 修改成功
const onChangeSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 获取表格数据
const getTableData = async () => {
  getCheckoutPaged (keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 获取当前登录用户数据
const getUserInfo=async ()=>{
  getOnlineUserInfo().then(resp=>{
    userinfo.value=resp.data.online
  })
}

// 删除退住信息
const deleteRecord = async (id)=>{
  let checkout={}
  checkout.cid=id
  await ElMessageBox.confirm('您确定要删除该退住登记记录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  deleteCheckoutRecord(checkout).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
      getTableData()
    }else {
      if (resp.data.code===500){
        ElNotification.error({
          title: '失败',
          message: resp.msg,
          offset: 48
        })
      }else {
        ElNotification.error({
          title: '失败',
          message: resp.msg,
          offset: 48
        })
        getTableData()
      }
    }
  })
}

// 生命周期钩子
onActivated(()=>{
  getTableData()
  getUserInfo()
})
</script>

<template>
  <page_container title="退住登记">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/stay'}">住宿变更</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/stay/checkout' }">退住登记</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">退住登记</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input v-model="keyword" placeholder="客户姓名" clearable>
              <template #append>
                <el-button :icon="Search" @click="getTableData" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button
              type="primary"
              :icon="Plus"
              @click="openCheckoutAddDialog"
              v-if="userinfo.role===1||userinfo.role===3">退住办理</el-button>
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
      <el-table-column label="审批状态">
        <template v-slot="{ row: { statue } }">
          <el-tag type="warning" v-if="statue===0">待审核</el-tag>
          <el-tag type="success" v-if="statue===1">已通过</el-tag>
          <el-tag type="danger" v-if="statue===2">未通过</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="姓名" prop="name"></el-table-column>
      <el-table-column label="性别" prop="gander"></el-table-column>
      <el-table-column label="年龄" prop="age"></el-table-column>
      <el-table-column label="档案号" prop="file"></el-table-column>
      <el-table-column label="入住时间" prop="checkin" width="130px"></el-table-column>
      <el-table-column label="退住时间" prop="checkout" width="130px"></el-table-column>
      <el-table-column label="退住类型">
        <template v-slot="{ row: { type } }">
          <el-tag type="primary" v-if="type===3">保留床位</el-tag>
          <el-tag type="success" v-if="type===1">正常退住</el-tag>
          <el-tag type="danger" v-if="type===2">死亡退住</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="退住原因" prop="reason"></el-table-column>
      <el-table-column label="申请时间" prop="apply" width="130px"></el-table-column>
      <el-table-column label="审核意见" prop="comment"></el-table-column>
      <el-table-column label="审核人" prop="reviewerName"></el-table-column>
      <el-table-column label="审核时间" prop="review" width="130px"></el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作" width="195px" fixed="right">
        <template #default="scope">
          <el-button
              size="small"
              type="primary"
              @click="openCheckoutChangeDrawer(scope.row)"
              v-if="userinfo.role===1||userinfo.role===3">修改</el-button>
          <el-button
              size="small"
              type="danger"
              @click="deleteRecord(scope.row.cid)"
              v-if="userinfo.role===1||userinfo.role===3">删除</el-button>
          <el-button
              size="small"
              type="success"
              @click="openCheckoutSetDrawer(scope.row)"
              v-if="userinfo.role===1||userinfo.role===25">审核</el-button>
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

    <!-- 添加退住登记Dialog   -->
    <checkout_add ref="add" @success="onAddSuccess"></checkout_add>

    <!-- 审核退住登记Drawer   -->
    <checkout_process ref="set" @success="onApproveSuccess"></checkout_process>

    <!-- 修改退住登记Drawer   -->
    <checkout_change ref="change" @success="onChangeSuccess"></checkout_change>

  </page_container>
</template>

<style scoped lang="less">

</style>