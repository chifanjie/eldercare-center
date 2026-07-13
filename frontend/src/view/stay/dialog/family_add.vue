<script setup>
import {ref} from "vue";
import {addFamilyRecord} from "@/api/stay/information.js";
import {ElMessage, ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const uid=ref()
const rules={
  name:[{ required: true, message: '请输入姓名', trigger: 'blur'}],
  age:[
    { required: true, message: '请输入年龄', trigger: 'blur' },
    { type: 'number', min: 0,max:100, message: '请输入大于等于0小于等于100的数字', trigger: 'blur' }
  ],
  gander:[{required: true, message: '请选择性别', trigger: 'blur'}],
  tel:[
    {required: true, message: '请输入联系电话', trigger: 'blur'},
    {min:11,max:11, message: '请输入正确的电话号码', trigger: 'blur'}
  ],
  relation:[{required: true, message: '请输入与老人关系', trigger: 'blur'}],
  address:[{ required: true, message: '请输入地址', trigger: 'blur'}],
  id:[{ required: true, message: '请输入身份证号', trigger: 'blur'}]
}

// 打开界面时
const openDialog=(data)=>{
  uid.value=data
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
const submitAdd=async ()=>{
  await form.value.validate()
  formData.value={...formData.value,uid:uid.value}
  addFamilyRecord(formData.value).then(resp=>{
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
  <el-dialog title="添加家属信息" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        label-position="right"
        label-width="auto"
        ref="form"
        :rules="rules"
        :model="formData"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="姓名" label-width="100px" prop="name">
        <el-input v-model="formData.name" placeholder="请输入【家属姓名】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别" label-width="100px" prop="gander">
        <el-select v-model="formData.gander" clearable>
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" label-width="100px" prop="age">
        <el-input v-model.number="formData.age" placeholder="请输入【年龄】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="与老人关系" label-width="100px" prop="relation">
        <el-input v-model="formData.relation" placeholder="请输入【与老人关系】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="身份证号" label-width="100px" prop="id">
        <el-input v-model.number="formData.id" placeholder="请输入【身份证号】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="住址" label-width="100px" prop="address">
        <el-input v-model="formData.address" placeholder="请输入【地址】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="电话" label-width="100px" prop="tel">
        <el-input v-model="formData.tel" placeholder="请输入【联系电话】信息" clearable></el-input>
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