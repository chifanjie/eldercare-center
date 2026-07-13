<script setup>
import {ref} from "vue";
import {addNewNurseContent} from "@/api/nurse/nurse.js";
import {ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const rules={
  no:[{ required: true, message: '请输入护理内容编号', trigger: 'blur'}],
  name:[{required: true, message: '请输入护理内容名称', trigger: 'blur'}],
  price:[{ required: true, message: '请输入护理内容价格', trigger: 'blur'}],
  description:[{required: true, message: '请输入护理内容描述', trigger: 'blur'}],
  value:[{ required: true, message: '请选择是否启用增值服务', trigger: 'blur'}],
  statue:[{required: true, message: '请选择启用状态', trigger: 'blur'}],
}

// 打开界面时
const openDialog=()=>{
  dialogVisible.value=true
}

// 关闭界面时
const closeDialog=()=>{
  beforeClose()
  dialogVisible.value=false
}

// 关闭界面前
const beforeClose=()=>{
  formData.value={}
  form.value.clearValidate()
}

// 提交表单
const addContent=async ()=>{
  await form.value.validate()
  addNewNurseContent(formData.value).then(resp=>{
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
  <el-dialog title="添加护理内容" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="编号" label-width="100px" prop="no">
        <el-input v-model="formData.no" placeholder="请输入【编号】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="名称" label-width="100px" prop="name">
        <el-input v-model="formData.name" placeholder="请输入【名称】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="价格" label-width="100px" prop="price">
        <el-input v-model="formData.price" placeholder="请输入价格【价格/单位】" clearable></el-input>
      </el-form-item>
      <el-form-item label="描述" label-width="100px" prop="description">
        <el-input v-model="formData.description" placeholder="请输入【描述】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="增值服务" label-width="100px" prop="value">
        <el-select v-model="formData.value" clearable>
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="启用" label-width="100px" prop="statue">
        <el-select v-model="formData.statue" clearable>
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" label-width="100px">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="addContent">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>