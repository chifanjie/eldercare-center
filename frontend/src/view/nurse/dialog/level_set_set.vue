<script setup>
import {ref} from "vue";
import {getNurseMenue} from "@/api/menue/menue.js";
import {changeNurseContentSet} from "@/api/nurse/nurse.js";
import {ElMessage} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const nid=ref()
const formData=ref({})
const form=ref()
const menue=ref()
const rules={
  content:[{ required: true, message: '请选择护理项目', trigger: 'blur'}],
  cycle:[{required: true, message: '请选择执行周期', trigger: 'blur'}],
  frequency:[
    {required: true, message: '请输入执行次数', trigger: 'blur'},
    {type: 'number', min: 0, message: '请输入大于等于0的数字', trigger: 'blur'}
  ],
  sort:[
    {required: true, message: '请输入顺序', trigger: 'blur'},
    {type: 'number', min: 0, message: '请输入大于等于0的数字', trigger: 'blur'}
  ],
}

// 打开界面时
const openDialog=(value,data)=>{
  nid.value=value
  formData.value=data
  getMenue()
  formData.value={...formData.value,cycle:String(formData.value.cycle),defaultId:formData.value.cid}
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

// 获取护理菜单
const getMenue=async ()=>{
  getNurseMenue().then(resp=>{
    if (resp.code===200){
      menue.value=resp.data.nurseMenue
    }
  })
}

// 提交表单
const submitChangeSet=async ()=>{
  await form.value.validate()
  changeNurseContentSet(formData.value).then(resp=>{
    if (resp.code===200){
      ElMessage.success(resp.msg)
      emit('success')
    }else {
      ElMessage.error(resp.msg)
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
  <el-dialog title="修改护理级别" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="护理项目" label-width="100px" prop="content">
        <el-select v-model="formData.cid" clearable>
          <el-option v-for="m in menue" :key="m.cid" :label="m.name" :value="m.cid"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="执行周期" label-width="100px" prop="cycle">
        <el-select v-model="formData.cycle" clearable>
          <el-option label="每天" value="1"></el-option>
          <el-option label="每周" value="2"></el-option>
          <el-option label="每月" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="执行次数" label-width="100px" prop="frequency">
        <el-input v-model.number="formData.frequency" clearable placeholder="请输入执行次数"></el-input>
      </el-form-item>
      <el-form-item label="排序" label-width="100px" prop="sort">
        <el-input v-model.number="formData.sort" clearable placeholder="请输入顺序"></el-input>
      </el-form-item>
      <el-form-item label="备注" label-width="100px" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitChangeSet">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>