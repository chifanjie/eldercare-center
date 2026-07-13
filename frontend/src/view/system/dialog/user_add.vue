<script setup>
import {ref} from "vue";
import {ElMessage, ElNotification} from "element-plus";
import axios from "axios";
import {useToken} from "@/stores/index.js";
import router from "@/router/index.js";
import {useBackend} from "@/stores/base/backend.js";
import {getRoleMenue} from "@/api/menue/menue.js";
import {addUserinfo} from "@/api/system/user.js";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const avatar=ref([])
const roles=ref([])
const avatarUrl=ref('')
const rules={
  name:[{ required: true, message: '请输入用户姓名', trigger: 'blur'}],
  age:[{ required: true, message: '请输入用户年龄', trigger: 'blur'}],
  gander:[{ required: true, message: '请输入用户性别', trigger: 'blur'}],
  username:[
      { required: true, message: '请输入用户名', trigger: 'blur'},
      { min: 5, max: 10, message: '用户名长度应该在5-10个字符内', trigger: 'blur'}
  ],
  ["password"]:[
      { required: true, message: '请输入密码', trigger: 'blur'},
      { pattern: /^[.\w@]+$/, message: '密码只能包括数字字母 "." "_" "@"', trigger: 'blur'},
      { min: 6, max: 16, message: '密码长度应该在6-16个字符内', trigger: 'blur'}
  ],
  email:[
      { required: true, message: '请输入邮箱地址', trigger: 'blur'},
      { pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,message: '请输入正确的邮箱地址', trigger: 'blur'}
  ],
  tel:[
      { required: true, message: '请输入电话号', trigger: 'blur'},
      { pattern: /^1[3-9]\d{9}$/,message: '请输入正确的电话号', trigger: 'blur'}
  ],
  role:[{ required: true, message: '请输入角色名称', trigger: 'blur'}],
  del:[{required: true, message: '请选择启用状态', trigger: 'blur'}],
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

// 关闭界面前
const beforeClose=()=>{
  formData.value={}
  avatarUrl.value=''
  avatar.value=[]
  form.value.clearValidate()
}

// 获取角色菜单
const getMenue=async ()=>{
  getRoleMenue().then(resp=>{
    if (resp.code===200){
      roles.value=resp.data.roleMenue
    }
  })
}

// 上传文件资源（头像）
const handleSubmitAvatar=async (files)=>{
  let file=new FormData()
  file.append("avatar",files.file)
  axios({
    method:'post',
    url:useBackend().backend+'/user/upload_avatar',
    headers:{
      'token':useToken().token,
      'Content-Type':'multipart/form-data'
    },
    data:file
  }).then(resp=>{
    if (resp.data.code===200){
      ElMessage.success(resp.data.msg)
      avatarUrl.value=resp.data.data.avatar
    }else {
      ElMessage.error(resp.data.msg)
    }
  }).catch(error=>{
    if ( error.response.status === 401){
      (void 0)
      const tokenStore = useToken()
      tokenStore.removeToken()
      ElMessage.error({
        message:'登录失效，请重新登录',
        grouping: true,
      })
      router.push('/login')
    }
  })
}

// 上传资源前，校验资源
const handleTestFile=(file)=>{
  let regx=/^image\/(jpeg|png|jpg|JPEG|PNG|JPG)$/
  if (file.size>50*1024*1024){
    ElMessage.error("最大图片大小50Mb")
    return false
  }
  if (!regx.test(file.type)){
    ElMessage.error("图片格式不正确!")
    return false
  }
  return true
}

// 移除上传资源
const handleRemove=()=>{
  avatarUrl.value=''
}

// 当上传复数文件时
const handleExceed=(file,fileList)=>{
  if (fileList.length > 1) {
    fileList.splice(0, 1);
  }
}

// 提交表单
const submitAddUser=async ()=>{
  await form.value.validate()
  formData.value={...formData.value,avatar:avatarUrl.value.name}
  addUserinfo(formData.value).then(resp=>{
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
  <el-dialog title="添加账户" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        label-position="right"
        label-width="auto"
        ref="form"
        :rules="rules"
        :model="formData"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="头像" label-width="100px" prop="avatar">
        <el-upload
            :file-list="avatar"
            :before-upload="handleTestFile"
            :on-change="handleExceed"
            :on-remove="handleRemove"
            :http-request="handleSubmitAvatar"
            :limit="2"
            list-type="picture"
            accept="image/*"
            :auto-upload="true">
          <template #trigger>
            <el-button type="primary">选择图片</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip">
              图片必须是jpg/png/jpeg格式且小于50Mb
            </div>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="姓名" label-width="100px" prop="name">
        <el-input v-model="formData.name" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别" label-width="100px" prop="gander">
        <el-select v-model="formData.gander" clearable>
          <el-option label="男" value = '男'></el-option>
          <el-option label="女" value = '女'></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" label-width="100px" prop="age">
        <el-input v-model="formData.age" clearable></el-input>
      </el-form-item>
      <el-form-item label="用户名" label-width="100px" prop="username">
        <el-input v-model="formData.username" clearable></el-input>
      </el-form-item>
      <el-form-item label="邮箱" label-width="100px" prop="email">
        <el-input v-model="formData.email" clearable></el-input>
      </el-form-item>
      <el-form-item label="电话" label-width="100px" prop="tel">
        <el-input v-model="formData.tel" clearable></el-input>
      </el-form-item>
      <el-form-item label="密码" label-width="100px" prop="password">
        <el-input v-model="formData.password" show-password clearable></el-input>
      </el-form-item>
      <el-form-item label="角色" label-width="100px" prop="role">
        <el-select v-model="formData.role" clearable >
          <el-option v-for="r in roles" :key="r.rid" :label="r.role" :value="r.rid"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="启用" label-width="100px" prop="del">
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
          <el-button type="primary" @click="submitAddUser">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>