<script setup>

import Page_container from "@/view/components/page_container.vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {ElButton, ElMessageBox, ElNotification} from "element-plus";
import {onActivated, ref} from "vue";
import {useStore} from '@/stores/temp/service/service_subject'
import router from "@/router/index.js";
import {getFollowCustomerPaged} from "@/api/service/follow.js";
import {deleteServiceSet, getServiceSetPaged} from "@/api/service/set.js";
import Set_change from "@/view/service/drawer/set_change.vue";

const currentPage=ref(1)
const pageSize=ref(5)
const total=ref(0)
const keyword=ref('')
const data=ref([])
const change=ref()

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 设置成功
const onChangeSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 打开设置对话框
const openSetChangeDrawer=(data)=>{
  change.value.openDrawer(data)
}

// 删除服务对象
const deleteAllService=async (data)=>{
  await ElMessageBox.confirm('您确定要删除该健康管家所有服务对象吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  let service={sid:data}
  deleteServiceSet(service).then(resp=>{
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

// 获取表格数据
const getTableData = async () => {
  getServiceSetPaged(keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 生命周期钩子
onActivated(()=>{
  getTableData()
})
</script>

<template>
  <page_container title="设置服务对象">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/service'}">服务管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/service/set' }">设置服务对象</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">设置服务对象</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input placeholder="管家姓名" clearable v-model="keyword">
              <template #append>
                <el-button :icon="Search" @click="getTableData" />
              </template>
            </el-input>
          </el-form-item>
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
      <el-table-column label="管家姓名" prop="name"></el-table-column>
      <el-table-column label="联系电话" prop="tel"></el-table-column>
      <el-table-column label="服务对象">
        <template v-slot="{ row: { serviceName } }">
          <el-tag type="primary" v-for="s in serviceName" :key="s.value">{{ s }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="success" @click="openSetChangeDrawer(scope.row.sid)">设置</el-button>
          <el-button size="small" type="danger" @click="deleteAllService(scope.row.sid)">取消</el-button>
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

    <!--  设置服务对象Drawer  -->
    <set_change ref="change" @success="onChangeSuccess"></set_change>

  </page_container>
</template>

<style scoped lang="less">

</style>