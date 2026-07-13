<script setup>
import {ref} from "vue";
import {changeMealManage} from "@/api/meal/manage.js";
import {ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const rules={
  habit:[{required: true, message: '请输入平时喜好信息', trigger: 'blur'}],
  warn:[{required: true, message: '请输入注意事项信息', trigger: 'blur'}]
}

// 打开界面时
const openDrawer=(data)=>{
  formData.value.uid=data.uid
  formData.value.habit=data.habit
  formData.value.warn=data.warn
  formData.value.memo=data.memo
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
const submitChange = async ()=>{
  await form.value.validate()
  changeMealManage(formData.value).then(resp=>{
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

// 暴露给父组件的方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="修改膳食管理记录" v-model="drawerVisible" size="30%" @close="beforeClose">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="平时喜好" label-width="100px" prop="habit">
        <el-input v-model="formData.habit" placeholder="请输入【平时喜好】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="注意事项" label-width="100px" prop="warn">
        <el-input v-model="formData.warn" placeholder="请输入【注意事项】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="备注" label-width="100px">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitChange">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>