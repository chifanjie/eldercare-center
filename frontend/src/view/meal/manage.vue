<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Search} from "@element-plus/icons-vue";
import {onActivated, ref} from "vue";
import router from "@/router/index.js";
import {getMealManagePaged} from "@/api/meal/manage.js";
import Manage_change from "@/view/meal/drawer/manage_change.vue";
import Manage_set from "@/view/meal/drawer/manage_set.vue";

const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const keyword=ref('')
const data=ref()
const change=ref()
const set=ref()

// 分页组件：页面内容大小改变时
const onSizeChange=(value)=>{
  getTableData()
}

// 分页组件：当前页改变时
const onCurrentChange=(value)=>{
  getTableData()
}

// 编辑成功时
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 设置成功时
const onChangeSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 打开编辑Drawer
const openSetDrawer=(value)=>{
  set.value.openDrawer(value)
}

// 打开设置Drawer
const openChangeDrawer=(value)=>{
  change.value.openDrawer(value)
}

// 获取表格数据
const getTableData = async () => {
  getMealManagePaged(keyword.value,currentPage.value,pageSize.value).then(resp=>{
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
  <page_container title="膳食管理">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/meals'}">膳食</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/meals/manage' }">膳食管理</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">膳食管理</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input placeholder="输入健康管家姓名" clearable v-model="keyword">
              <template #append>
                <el-button :icon="Search" @click="getTableData"/>
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
      <el-table-column label="姓名" prop="name"></el-table-column>
      <el-table-column label="性别" prop="gander"></el-table-column>
      <el-table-column label="年龄" prop="age"></el-table-column>
      <el-table-column label="平时喜好" prop="habit"></el-table-column>
      <el-table-column label="注意事项" prop="warn"></el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openChangeDrawer(scope.row)">编辑</el-button>
          <el-button size="small" type="success" @click="openSetDrawer(scope.row)">设置</el-button>
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

    <!-- 添加膳食管理Dialog   -->
    <manage_change ref="change" @success="onChangeSuccess"></manage_change>

    <!-- 修改膳食管理Drawer   -->
    <manage_set ref="set" @success="onSetSuccess"></manage_set>

  </page_container>
</template>

<style scoped lang="less">

</style>