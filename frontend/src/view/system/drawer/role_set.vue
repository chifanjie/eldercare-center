<script setup>
import {ref} from "vue";
import {getTreeMenu} from "@/api/menue/menue.js";
import {changeRole, getRoleSetFormData} from "@/api/system/role.js";
import {ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const rid=ref()
const treeSelect=ref()
const form=ref()
const rules={
  role:[{ required: true, message: '请输入角色名称', trigger: 'blur'}],
  del:[{required: true, message: '请选择启用状态', trigger: 'blur'}],
  auth:[{required: true, message: '请确定角色权限', trigger: 'blur'}]
}

// 打开界面时
const openDrawer=(row)=>{
  rid.value=row
  getMenue()
  getSetData()
  drawerVisible.value=true
}

// 关闭界面时
const closeDrawer=()=>{
  beforeClose()
  drawerVisible.value=false
}

// 获取功能树形菜单
const getMenue = async ()=>{
  getTreeMenu().then(resp=>{
    treeSelect.value=resp.data.tree
  })
}

// 获取数据回显
const getSetData = async ()=>{
  getRoleSetFormData(rid.value).then(resp=>[
      formData.value=resp.data.form
  ])
}

// 提交表单
const submitSetData= async ()=>{
  await form.value.validate()
  formData.value={...formData.value,rid:rid.value}
  changeRole(formData.value).then(resp=>{
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
  <el-drawer title="权限修改" v-model="drawerVisible" size="30%" @close="beforeClose">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
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
          <el-option label="启用" :value = '0'></el-option>
          <el-option label="禁用" :value = '1'></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" label-width="100px" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitSetData">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>