<script setup>
import {ref} from "vue";
import {changeNurseLevel} from "@/api/nurse/nurse.js";
import {ElNotification} from "element-plus";
const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const rules={
  level:[{ required: true, message: '请输入级别名称', trigger: 'blur'}],
  statue:[{required: true, message: '请选择启用状态', trigger: 'blur'}],
}

// 打开界面时
const openDrawer=(row)=>{
  formData.value.nid=row.nid
  formData.value.level=row.level
  formData.value.statue=String(row.statue)
  formData.value.memo=row.memo
  drawerVisible.value=true
}

// 关闭界面时
const closeDrawer=()=>{
  beforeClose()
  drawerVisible.value=false
}

// 提交表单
const submitChangeData= async ()=>{
  await form.value.validate()
  changeNurseLevel(formData.value).then(resp=>{
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
const beforeClose=()=>{
  formData.value={}
  form.value.clearValidate()
}

// 暴露给父组件方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="护理级别修改" v-model="drawerVisible" size="30%" @close="closeDrawer">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="名称" label-width="100px" prop="level">
        <el-input v-model="formData.level" placeholder="请输入【名称】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="状态" label-width="100px" prop="statue">
        <el-select v-model="formData.statue" clearable>
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" label-width="100px" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitChangeData">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>