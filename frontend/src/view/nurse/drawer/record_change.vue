<script setup>
import {ref} from "vue";
import {changeCustomerBuyService} from "@/api/nurse/nurse.js";
import {ElMessage, ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const sid=ref()
const rules={
  frequency:[
      { required: true, message: '请输入执行次数', trigger: 'blur'},
      { type: 'number', min: 1, message: '请输入大于0的数字', trigger: 'blur'}
  ]
}

// 打开界面时
const openDrawer=(row)=>{
  sid.value=row.sid
  formData.value.frequency=row.frequency
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

// 提交表单
const submitChange=async ()=>{
  await form.value.validate()
  formData.value={...formData.value,sid:sid.value}
  changeCustomerBuyService(formData.value).then(resp=>{
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
  <el-drawer title="设置" v-model="drawerVisible" size="30%" @close="closeDrawer">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="执行次数" label-width="100px" prop="frequency">
        <el-input v-model.number="formData.frequency" placeholder="请输入【编号】信息" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-divider />
    <el-row style="margin-top: 5px">
      <el-col :span="10" :offset="20">
        <el-button type="primary" @click="submitChange">确定</el-button>
        <el-button @click="closeDrawer">取消</el-button>
      </el-col>
    </el-row>
  </el-drawer>
</template>

<style scoped lang="less">

</style>