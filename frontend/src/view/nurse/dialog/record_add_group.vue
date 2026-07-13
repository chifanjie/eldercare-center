<script setup>
import {ref} from "vue";
import {getCustomerMenue} from "@/api/menue/menue.js";
import {addGroupNurseRecord} from "@/api/nurse/nurse.js";
import {ElMessage, ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const keyword=ref('')
const customer=ref([])
const formData=ref({})
const form=ref()
const rules={
  uidList:[{ required: true, message: '请选择需要提供服务的客户', trigger: 'blur'}]
}

// 打开界面时
const openDialog=()=>{
  getMenue()
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
  formData.value={}
  form.value.clearValidate()
}

// 全选顾客
const selectAllCustomer=()=>{
  let uidList=[]
  customer.value.forEach(c=>{
    uidList.push(c.value)
  })
  formData.value.uidList=uidList
}

// 获取顾客菜单
const getMenue = async ()=>{
  getCustomerMenue(keyword.value).then(resp=>{
    customer.value=resp.data.customerMenue
  })
}

// 提交表单
const submitGroupAdd=async ()=>{
  await form.value.validate()
  addGroupNurseRecord(formData.value).then(resp=>{
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
  <el-dialog title="添加今日护理安排" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="客户" label-width="100px" prop="uidList">
        <el-select multiple clearable v-model="formData.uidList">
          <template #header>
            <el-input v-model="keyword" clearable @keyup="getMenue" placeholder="请输入客户名称"></el-input>
          </template>
          <el-option v-for="c in customer" :key="c.value" :label="c.label" :value="c.value"></el-option>
        </el-select>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="18" :offset="10">
          <el-button type="primary" @click="submitGroupAdd">确定</el-button>
          <el-button type="success" @click="selectAllCustomer">选择所有客户</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>