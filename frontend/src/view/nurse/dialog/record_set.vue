<script setup>
import {ref} from "vue";
import {deleteCustomerBuyService, getUserBuyServicePaged} from "@/api/nurse/nurse.js";
import {ElButton, ElMessage, ElMessageBox, ElNotification} from "element-plus";
import Record_change from "@/view/nurse/drawer/record_change.vue";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const currentPage=ref(1)
const pageSize=ref(5)
const total=ref(0)
const uid=ref()
const record=ref([])
const change=ref()

// 打开界面时
const openDialog=(data)=>{
  uid.value=data
  getTableData()
  dialogVisible.value=true
}

// 关闭界面时
const closeDialog=()=>{
  dialogVisible.value=false
}

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 打开修改对话框
const openChangeDrawer=(data)=>{
  change.value.openDrawer(data)
}

// 修改成功时
const onChangeSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 获取表格数据
const getTableData = async ()=>{
  getUserBuyServicePaged(uid.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    record.value=resp.data.recordP.records
  })
}

// 删除服务记录
const deleteContent=async (id)=>{
  await ElMessageBox.confirm('您确定要删除该服务吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  let deleteForm={sid:id}
  deleteCustomerBuyService(deleteForm).then(resp=>{
    if (resp.code===200){
      ElMessage.success(resp.msg)
    }else {
      ElMessage.error(resp.msg)
    }
    getTableData()
  })
}

// 暴露给父组件方法
defineExpose({
  openDialog
})
</script>

<template>
  <el-dialog title="设置护理内容" width="35%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-table
        :data="record"
        style="min-width: 100%; margin-top: 10px"
        :row-style="{ height: '50px' }"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
        table-layout="fixed">
      <el-table-column label="序号" type="index" width="60px"></el-table-column>
      <el-table-column label="护理内容" prop="contentName"></el-table-column>
      <el-table-column label="服务状态">
        <template v-slot="{ row: { statue } }">
          <el-tag type="success" v-if="statue === 0">正常使用</el-tag>
          <el-tag type="warning" v-if="statue === 1">即将用完</el-tag>
          <el-tag type="danger" v-if="statue === 2">已欠费</el-tag>
          <el-tag type="danger" v-if="statue >= 3">正在提醒</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="执行次数" prop="frequency"></el-table-column>
      <el-table-column label="剩余数量" prop="residue"></el-table-column>
      <el-table-column label="操作" width="160px">
        <template #default="scope">
          <el-button size="small" type="success" @click="openChangeDrawer(scope.row)">修改</el-button>
          <el-button size="small" type="danger" @click="deleteContent(scope.row.sid)">删除</el-button>
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
    <el-row style="margin-top: 15px">
      <el-col :span="1" :offset="20">
        <el-button type="primary" @click="closeDialog">关闭</el-button>
      </el-col>
    </el-row>

    <!-- 护理记录修改Drawer  -->
    <record_change ref="change" @success="onChangeSuccess"></record_change>

  </el-dialog>
</template>

<style scoped lang="less">

</style>