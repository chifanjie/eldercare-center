<script setup>
import {ref} from "vue";
import {addServicePurchase} from "@/api/service/follow.js";
import {ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const rules={
  num:[
      { required: true, message: '请输入购买数量', trigger: 'blur'},
      {type: 'number', min: 1, message: '请输入大于零的整数', trigger: 'blur'}
  ]
}

// 打开界面时
const openDrawer=(row)=>{
  formData.value.sid=row.sid
  formData.value.contentName=row.contentName
  formData.memo=row.memo
  drawerVisible.value=true
}

// 关闭界面时
const closeDrawer=()=>{
  beforeClose()
  drawerVisible.value=false
}

// 获取功能树形菜单
const beforeClose=()=>{
  formData.value={}
  form.value.clearValidate()
}

// 提交表单
const submitPurchase=async ()=>{
  await form.value.validate()
  addServicePurchase(formData.value).then(resp=>{
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

// 关闭界面前
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="购买护理内容" v-model="drawerVisible" size="30%" @close="closeDrawer">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="护理内容名称" prop="contentName">
        <el-input v-model="formData.contentName" clearable disabled></el-input>
      </el-form-item>
      <el-form-item label="购买数量" prop="num">
        <el-input v-model.number="formData.num" placeholder="请输入购买数量" clearable></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-divider />
    <el-row style="margin-top: 5px">
      <el-col :span="10" :offset="15">
        <el-button type="primary" @click="submitPurchase">确定</el-button>
        <el-button @click="closeDrawer">取消</el-button>
      </el-col>
    </el-row>
  </el-drawer>
</template>

<style scoped lang="less">

</style>