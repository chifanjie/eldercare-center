<script setup>
import {ref} from "vue";
import {getCustomerMenue, getServiceMenue} from "@/api/menue/menue.js";
import {addTempNurseRecord} from "@/api/nurse/nurse.js";
import {ElMessage, ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const keyword=ref('')
const keywordService=ref('')
const customer=ref([])
const service=ref([])
const formData=ref({})
const form=ref()
const rules={
  uid:[{ required: true, message: '请选择需要提供服务的客户', trigger: 'blur'}],
  cid:[{ required: true, message: '请选择需要提供服务', trigger: 'blur'}],
  time:[
      { required: true, message: '请输入服务次数', trigger: 'blur'},
      { type: 'number', min: 0, message: '请输入大于等于0的数字', trigger: 'blur'}
  ],
}

// 打开界面时
const openDialog=()=>{
  getMenue()
  getServiceRecord()
  dialogVisible.value=true
}

// 关闭界面时
const closeDialog=()=>{
  beforeClose()
  dialogVisible.value=false
}

// 关闭界面前
const beforeClose=()=>{
  keyword.value=''
  keywordService.value=''
  formData.value={}
  form.value.clearValidate()
}

// 获取客户菜单
const getMenue = async ()=>{
  getCustomerMenue(keyword.value).then(resp=>{
    customer.value=resp.data.customerMenue
  })
}

// 获取服务项目菜单
const getServiceRecord = async ()=>{
  getServiceMenue(keywordService.value).then(resp=>{
    service.value=resp.data.serviceMenue
  })
}

// 提交表单
const submitTempAdd=async ()=>{
  await form.value.validate()
  addTempNurseRecord(formData.value).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
      closeDialog()
      emit('success')
    }else {
      if (resp.data.err===500){
        ElMessage.error(resp.msg)
      }else {
        ElNotification.error({
          title: '失败',
          message: resp.msg,
          offset: 48
        })
        closeDialog()
      }
    }
  })
}

// 暴露给父组件方法
defineExpose({
  openDialog
})
</script>

<template>
  <el-dialog title="添加临时护理项目" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="客户" label-width="100px" prop="uid">
        <el-select clearable v-model="formData.uid">
          <template #header>
            <el-input v-model="keyword" clearable @keyup="getMenue" placeholder="请输入客户名称"></el-input>
          </template>
          <el-option v-for="c in customer" :key="c.value" :label="c.label" :value="c.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="服务项目" label-width="100px" prop="cid">
        <el-select clearable v-model="formData.cid">
          <template #header>
            <el-input v-model="keywordService" clearable @keyup="getServiceRecord" placeholder="请输入服务名称"></el-input>
          </template>
          <el-option v-for="s in service" :key="s.cid" :label="s.name" :value="s.cid"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="数量" label-width="100px" prop="time">
        <el-input v-model.number="formData.time" placeholder="输入数量" clearable></el-input>
      </el-form-item>
      <el-form-item label="服务内容" label-width="100px" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitTempAdd">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>