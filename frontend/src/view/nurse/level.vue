<script setup>
import {ElButton, ElMessageBox, ElNotification} from "element-plus";
import Page_container from "@/view/components/page_container.vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {onActivated, ref} from "vue";
import Level_add from "@/view/nurse/dialog/level_add.vue";
import Level_set from "@/view/nurse/drawer/level_set.vue";
import {deleteNurseLevel, getNurseLevelPaged} from "@/api/nurse/nurse.js";
import Level_change from "@/view/nurse/drawer/level_change.vue";
import router from "@/router/index.js";

const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const keyword=ref('')
const data=ref()
const add=ref()
const set=ref()
const change=ref()

// 分页组件：页面内容大小改变时
const onSizeChange=(value)=>{
  getTableData()
}

// 分页组件：当前页改变时
const onCurrentChange=(value)=>{
  getTableData()
}

// 添加成功时
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 修改成功时
const onChangeSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 获取表格数据
const getTableData = async () => {
  getNurseLevelPaged(keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 删除护理等级
const deleteNurseLevelByNid=async (id)=>{
  await ElMessageBox.confirm('您确定要删除该护理级别吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  const level=ref({})
  level.value={...level.value,nid:id}
  deleteNurseLevel(level.value).then(resp=>{
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

// 打开添加Dialog
const openAddLevelDialog=()=>{
  add.value.openDialog({})
}

// 打开配置Drawer
const openSetDrawer=(value)=>{
  set.value.openDrawer(value)
}

// 打开修改Drawer
const openChangeDrawer=(row)=>{
  change.value.openDrawer(row)
}

// 生命周期钩子
onActivated(()=>{
  getTableData()
})
</script>

<template>
  <page_container title="护理级别">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/nurse'}">护理管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/nurse/level' }">护理级别</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">护理级别</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input v-model="keyword" clearable placeholder="护理级别">
              <template #append>
                <el-button :icon="Search" @click="getTableData" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="openAddLevelDialog" :icon="Plus" >添加</el-button>
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
      <el-table-column label="护理级别" prop="level">
        <template v-slot="{ row: { level } }">
          <el-tag type="primary">{{level}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="statue">
        <template v-slot="{ row: { statue } }">
          <el-tag type="success" v-if="statue === 1">启用</el-tag>
          <el-tag type="danger" v-if="statue === 0">未启用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openChangeDrawer(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="deleteNurseLevelByNid(scope.row.nid)">删除</el-button>
          <el-button size="small" type="success" @click="openSetDrawer(scope.row.nid)">护理内容配置</el-button>
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

    <!-- 添加护理级别Dialog   -->
    <level_add ref="add" @success="onAddSuccess"></level_add>

    <!-- 修改护理级别Drawer  -->
    <level_change ref="change" @success="onChangeSuccess"></level_change>

    <!-- 设置详情护理级别Drawer  -->
    <level_set ref="set"></level_set>

  </page_container>
</template>

<style scoped lang="less">

</style>