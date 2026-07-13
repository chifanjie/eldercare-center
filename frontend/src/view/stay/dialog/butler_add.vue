<script setup>
import {ref} from "vue";
import {getButlerMenue} from "@/api/menue/menue.js";
import {addButlerRecord} from "@/api/stay/information.js";
import {ElMessage, ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const keyword=ref('')
const butler=ref([])
const uid=ref()
const rules={
  sid:[{required: true, message: '请选择管家', trigger: 'blur'}]
}

// 打开界面时
const openDialog=(data)=>{
  uid.value=data
  getButler()
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

// 获取管家菜单
const getButler=async ()=>{
  getButlerMenue(keyword.value).then(resp=>{
    butler.value=resp.data.staffs
  })
}

// 提交表单
const submitAdd=async ()=>{
  await form.value.validate()
  formData.value={...formData.value,uid:uid.value}
  addButlerRecord(formData.value).then(resp=>{
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
  <el-dialog title="添加健康管家" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        label-position="right"
        label-width="auto"
        ref="form"
        :rules="rules"
        :model="formData"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="管家" label-width="100px" prop="sid">
        <el-select v-model="formData.sid" style="width: 200px">
          <template #header>
            <el-input v-model="keyword" placeholder="输入管家姓名" @keyup="getButler" clearable></el-input>
          </template>
          <el-option v-for="b in butler" :key="b.value" :label="b.name" :value="b.sid"></el-option>
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