<script setup>
import {ref} from "vue";
import {getSupportMenue} from "@/api/menue/menue.js";
import {addSupportRecord} from "@/api/stay/information.js";
import {ElMessage, ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const keyword=ref('')
const support=ref([])
const uid=ref()
const rules={
  sid:[{required: true, message: '请选择护工', trigger: 'blur'}]
}

// 打开界面时
const openDialog=(data)=>{
  uid.value=data
  getSupport()
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

// 获取护工菜单
const getSupport=async ()=>{
  getSupportMenue(keyword.value).then(resp=>{
    support.value=resp.data.staffs
  })
}

// 提交表单
const submitAdd=async ()=>{
  await form.value.validate()
  formData.value={...formData.value,uid:uid.value}
  addSupportRecord(formData.value).then(resp=>{
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
  <el-dialog title="添加护工" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        label-position="right"
        label-width="auto"
        ref="form"
        :rules="rules"
        :model="formData"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="护工" label-width="100px" prop="sid">
        <el-select v-model="formData.sid" style="width: 200px">
          <template #header>
            <el-input v-model="keyword" placeholder="输入护工姓名" @keyup="getSupport" clearable></el-input>
          </template>
          <el-option v-for="s in support" :key="s.value" :label="s.name" :value="s.sid"></el-option>
        </el-select>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitAdd">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>