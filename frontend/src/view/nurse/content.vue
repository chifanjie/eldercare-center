<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {ElButton, ElMessageBox, ElNotification} from "element-plus";
import {onActivated, ref} from "vue";
import Content_add from "@/view/nurse/dialog/content_add.vue";
import {deleteNurseContent, getNurseContentPaged} from "@/api/nurse/nurse.js";
import Content_set from "@/view/nurse/drawer/content_set.vue";
import router from "@/router/index.js";

const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const keyword=ref('')
const data=ref()
const add=ref()
const set=ref()

// 分页组件：页面内容大小改变时
const onSizeChange=(value)=>{
  getTableData()
}

// 分页组件：当前页改变时
const onCurrentChange=(value)=>{
  getTableData()
}

// 打开添加Dialog
const openAddContentDialog=()=>{
  add.value.openDialog({})
}

// 打开修改护理内容Drawer
const openSetContentDrawer=(row)=>{
  set.value.openDrawer(row)
}

// 添加成功时
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 修改成功时
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 获取表格数据
const getTableData = async () => {
  getNurseContentPaged(keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 删除护理内容
const deleteContent=async (id)=>{
  await ElMessageBox.confirm('您确定要删除该护理内容吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  const content=ref({})
  content.value={...content.value,cid:id}
  deleteNurseContent(content.value).then(resp=>{
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
  <page_container title="护理内容">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/nurse'}">护理管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/nurse/content' }">护理内容</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">护理内容</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input v-model="keyword" clearable placeholder="护理名称或编号">
              <template #append>
                <el-button :icon="Search" @click="getTableData" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" :icon="Plus" @click="openAddContentDialog">添加</el-button>
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
      <el-table-column label="编号" prop="no"></el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
      <el-table-column label="价格" prop="price"></el-table-column>
      <el-table-column label="描述" prop="description"></el-table-column>
      <el-table-column label="是否增值服务">
        <template v-slot="{ row: { value } }">
          <el-tag type="success" v-if="value === 1">启用</el-tag>
          <el-tag type="danger" v-if="value === 0">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template v-slot="{ row: { statue } }">
          <el-tag type="success" v-if="statue === 1">启用</el-tag>
          <el-tag type="danger" v-if="statue === 0">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openSetContentDrawer(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="deleteContent(scope.row.cid)">删除</el-button>
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

    <!-- 添加服务内容Dialog   -->
    <content_add ref="add" @success="onAddSuccess"></content_add>

    <!-- 修改服务内容Drawer   -->
    <content_set ref="set" @success="onSetSuccess"></content_set>

  </page_container>
</template>

<style scoped lang="less">

</style>