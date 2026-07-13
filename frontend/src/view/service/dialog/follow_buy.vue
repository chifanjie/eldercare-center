<script setup>
import {ref} from "vue";
import {getCustomCustomerServiceMenue, getCustomerMenue, getLevelMenue} from "@/api/menue/menue.js";
import {getNurseContentSetPaged} from "@/api/nurse/nurse.js";
import {ElButton, ElNotification} from "element-plus";
import {addServiceFollow} from "@/api/service/follow.js";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const customer=ref([])
const level=ref([])
const currentPage=ref(1)
const pageSize=ref(5)
const total=ref(0)
const data=ref([])
const keyword=ref('')
const rules={
  uid:[{ required: true, message: '请选择客户', trigger: 'blur'}],
  cid:[{required: true, message: '请选择购买的护理级别', trigger: 'blur'}],
}

// 打开界面时
const openDialog=()=>{
  getCustomer()
  getLevel()
  dialogVisible.value=true
}

// 关闭界面时
const closeDialog=()=>{
  beforeClose()
  dialogVisible.value=false
}

// 获取功能树形菜单
const beforeClose=()=>{
  formData.value={}
  data.value=[]
  currentPage.value=1
  pageSize.value=5
  total.value=0
  form.value.clearValidate()
}

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 获取表格数据
const getTableData = async () => {
  getNurseContentSetPaged(formData.value.cid,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 获取顾客菜单
const getCustomer = async ()=>{
  getCustomCustomerServiceMenue(keyword.value).then(resp=>{
    customer.value=resp.data.customerMenue
  })
}

// 获取护理级别菜单
const getLevel = async ()=>{
  getLevelMenue().then(resp=>{
    level.value=resp.data.levelMenue
  })
}

// 提交表单
const submitPurchase = async ()=>{
  await form.value.validate()
  addServiceFollow(formData.value).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
      emit('success')
    }else {
      ElNotification.error({
        title: '失败',
        message: resp.msg,
        offset: 48
      })
    }
    closeDialog()
  })
}

// 暴露给父组件方法
defineExpose({
  openDialog
})
</script>

<template>
  <el-dialog title="购买护理服务" width="30%" v-model="dialogVisible" @close="beforeClose">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="老人姓名" label-width="100px" prop="uid">
        <el-select v-model="formData.uid" clearable>
          <template #header>
            <el-input v-model="keyword" placeholder="输入客户姓名" @keyup="getCustomer" clearable></el-input>
          </template>
          <el-option v-for="c in customer" :key="c.value" :label="c.label" :value="c.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="护理级别" label-width="100px" prop="cid">
        <el-select v-model="formData.cid" clearable @change="getTableData">
          <el-option v-for="l in level" :key="l.nid" :label="l.level" :value="l.nid"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" label-width="100px" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
    </el-form>

    <!--表格回显数据-->
    <el-table
        :data="data"
        style="min-width: 100%; margin-top: 10px"
        :row-style="{ height: '50px' }"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
        table-layout="auto">
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
    <el-divider />
    <el-row style="margin-top: 5px">
      <el-col :span="10" :offset="16">
        <el-button type="primary" @click="submitPurchase">确定</el-button>
        <el-button @click="closeDialog">取消</el-button>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<style scoped lang="less">

</style>