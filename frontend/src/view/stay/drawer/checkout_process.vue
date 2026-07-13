<script setup>
import {ref} from "vue";
import {approveCheckoutRecord} from "@/api/stay/checkout.js";
import {ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const cid=ref()
const uid=ref()
const rules=ref({
  statue:[{ required: true, message: '请选择审批状态', trigger: 'blur'}],
})

// 打开界面时
const openDrawer=(data)=>{
  cid.value=data.cid
  uid.value=data.uid
  drawerVisible.value=true
}

// 关闭界面时
const closeDrawer=()=>{
  beforeClose()
  drawerVisible.value=false
}

// 关闭界面前
const beforeClose=()=>{
  formData.value={}
  form.value.clearValidate()
}

// 提交审核
const submitApprove=async ()=>{
  await form.value.validate()
  formData.value.cid=cid.value
  formData.value.uid=uid.value
  approveCheckoutRecord(formData.value).then(resp=>{
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
    closeDrawer()
  })
}

// 暴露给父组件方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="审核" v-model="drawerVisible" size="30%" @close="closeDrawer">
    <el-divider />
    <el-form
        :model="formData"
        :rules="rules"
        ref="form"
        label-position="right"
        label-width="auto"
        style="max-width: 350px">
      <el-form-item label="审核状态" label-width="100px" prop="statue">
        <el-select clearable v-model="formData.statue">
          <el-option label="审批通过" value="1"></el-option>
          <el-option label="审批不通过" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核意见" label-width="100px">
        <el-input clearable v-model="formData.memo"></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitApprove">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>