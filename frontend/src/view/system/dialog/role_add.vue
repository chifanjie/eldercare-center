<script setup>
import {ref} from "vue";
import {getTreeMenu} from "@/api/menue/menue";
import {submitAddRole} from "@/api/system/role";
import {ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const treeSelect=ref()
const form=ref()
const rules={
  role:[{ required: true, message: '请输入角色名称', trigger: 'blur'}],
  del:[{required: true, message: '请选择启用状态', trigger: 'blur'}],
  auth:[{required: true, message: '请确定角色权限', trigger: 'blur'}]
}

// 打开界面时
const openDialog=()=>{
  getMenue()
  dialogVisible.value=true
}

// 关闭界面时
const closeDialog=()=>{
  beforeClose()
  dialogVisible.value=false
}

// 获取功能树形菜单
const getMenue = async ()=>{
  getTreeMenu().then(resp=>{
    treeSelect.value=resp.data.tree
  })
}

// 提交表单
const addRole= async ()=>{
  await form.value.validate()
  submitAddRole(formData.value).then(resp=>{
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

// 关闭界面前
const beforeClose=()=>{
  formData.value={}
  form.value.clearValidate()
}

// 暴露给父组件方法
defineExpose({
  openDialog
})
</script>

<template>
  <el-dialog title="添加角色" width="30%" v-model="dialogVisible" @close="beforeClose">
    <el-divider />
    <el-form
        label-position="right"
        label-width="auto"
        ref="form"
        :rules="rules"
        :model="formData"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="角色名称" label-width="100px" prop="role">
        <el-input v-model="formData.role" clearable placeholder="请输入【角色名称】信息"></el-input>
      </el-form-item>
      <el-form-item label="权限" label-width="100px" prop="auth">
        <el-tree-select
            :data="treeSelect"
            v-model="formData.auth"
            multiple
            show-checkbox>
        </el-tree-select>
      </el-form-item>
      <el-form-item label="启用状态" label-width="100px" prop="del">
        <el-select v-model="formData.del" clearable>
          <el-option label="启用" value = '0'></el-option>
          <el-option label="禁用" value = '1'></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" label-width="100px" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="addRole">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>