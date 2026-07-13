<script setup>
import Page_container from "@/view/components/page_container.vue";
import {onActivated, ref} from "vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {ElButton, ElMessageBox, ElNotification} from "element-plus";
import Role_add from "@/view/system/dialog/role_add.vue";
import Role_set from "@/view/system/drawer/role_set.vue";
import {deleteRole, getRolePaged} from "@/api/system/role";
import router from "@/router/index.js";

const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const keyword=ref('')
const data=ref()
const add=ref()
const set=ref()

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 打开添加角色对话框
const openRoleAddDialog=()=>{
  add.value.openDialog()
}

// 打开修改角色信息对话框
const openRoleSetDrawer=(row)=>{
  set.value.openDrawer(row)
}

// 获取表格数据
const getTableData = async () => {
  getRolePaged(keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 添加角色成功
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 修改角色信息成功
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 禁用角色
const disableRole=async (role)=>{
  await ElMessageBox.confirm('您确定要禁用该角色吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  const del={rid:role}
  deleteRole(del).then(resp=>{
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

// 生命周期钩子
onActivated(()=>{
  getTableData()
})
</script>

<template>
  <page_container title="权限管理">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/system'}">系统管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/system/role' }">权限管理</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">权限管理</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input v-model="keyword" clearable placeholder="角色名称">
              <template #append>
                <el-button :icon="Search" @click="getTableData" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" :icon="Plus" @click="openRoleAddDialog">添加角色</el-button>
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
        table-layout="auto">
      <el-table-column label="序号" type="index" width="60px"></el-table-column>
      <el-table-column label="角色名" prop="role"></el-table-column>
      <el-table-column label="权限">
        <template v-slot="{ row: { auth } }">
          <el-tag type="primary" v-for="a in auth" :key="auth.index">{{ a }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="启用状态">
        <template v-slot="{ row: { del } }">
          <el-tag type="success" v-if="del===0">启用</el-tag>
          <el-tag type="danger" v-if="del===1">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openRoleSetDrawer(scope.row.rid)">修改</el-button>
          <el-button size="small" type="danger" @click="disableRole(scope.row.rid)">禁用</el-button>
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

    <!-- 添加角色Dialog   -->
    <role_add ref="add" @success="onAddSuccess"></role_add>

    <!-- 修改角色Drawer   -->
    <role_set ref="set" @success="onSetSuccess"></role_set>

  </page_container>
</template>

<style scoped lang="less">

</style>