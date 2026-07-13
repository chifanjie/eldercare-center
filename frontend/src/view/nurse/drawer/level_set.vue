<script setup>
import {ref} from "vue";
import {Plus} from "@element-plus/icons-vue";
import {ElButton, ElMessage, ElMessageBox} from "element-plus";
import Level_set_add from "@/view/nurse/dialog/level_set_add.vue";
import {deleteNurseContentSet, getNurseContentSetPaged} from "@/api/nurse/nurse.js";
import Level_set_set from "@/view/nurse/dialog/level_set_set.vue";

const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const data=ref()
const drawerVisible=ref(false)
const deleteId=ref({nid:'',cid:''})
const nid=ref()
const add=ref()
const set=ref()

// 打开添加Dialog
const openAddLevelDialog=()=>{
  add.value.openDialog(nid.value)
}

// 打开设置Dialog
const openSetLevelDialog=(data)=>{
  set.value.openDialog(nid.value,data)
}

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 添加成功
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 设置成功
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 打开界面时
const openDrawer=(value)=>{
  nid.value=value
  getTableData()
  drawerVisible.value=true
}

// 获取表格数据
const getTableData = async () => {
  getNurseContentSetPaged(nid.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 删除护理配置
const deleteLevelSet=async (id)=>{
  await ElMessageBox.confirm('您确定要删除该条护理内容配置吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  deleteId.value.nid=nid.value
  deleteId.value.cid=id
  deleteNurseContentSet(deleteId.value).then(resp=>{
    if (resp.code===200){
      ElMessage.success(resp.msg)
    }else {
      ElMessage.error(resp.msg)
    }
    getTableData()
  })
}

// 关闭界面时
const closeDrawer=()=>{
  drawerVisible.value=false
}

// 暴露给父组件方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="护理内容配置" v-model="drawerVisible" size="40%" @close="closeDrawer">

    <el-row>
      <el-col :span="4">
        <el-button type="primary" :icon="Plus" @click="openAddLevelDialog">添加护理内容</el-button>
      </el-col>
    </el-row>

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
      <el-table-column label="护理内容" prop="content"></el-table-column>
      <el-table-column label="执行周期">
        <template v-slot="{ row: { cycle } }">
          <el-tag type="primary" v-if="cycle===1">每天</el-tag>
          <el-tag type="primary" v-if="cycle===2">每周</el-tag>
          <el-tag type="primary" v-if="cycle===3">每月</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="执行次数" prop="frequency"></el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="排序号" prop="sort"></el-table-column>
      <el-table-column label="操作" width="140" fixed="right">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openSetLevelDialog(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="deleteLevelSet(scope.row.cid)">删除</el-button>
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
        :small="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
    />
    <!-- 添加服务等级Dialog -->
    <level_set_add ref="add" @success="onAddSuccess"></level_set_add>
    <!-- 修改服务等级Dialog -->
    <level_set_set ref="set" @success="onSetSuccess"></level_set_set>

  </el-drawer>
</template>

<style scoped lang="less">

</style>