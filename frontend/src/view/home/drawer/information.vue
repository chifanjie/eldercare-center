<script setup>
import {ref} from "vue";
import {changeUserInfo, getUserInfo} from "@/api/welcome/user.js";
import {ElNotification} from "element-plus";
import Avatar_add from "@/view/home/dialog/avatar_add.vue";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const sid=ref()
const disable=ref(true)
const rules={
  name:[{ required: true, message: '请输入用户姓名', trigger: 'blur'}],
  age:[{ required: true, message: '请输入用户年龄', trigger: 'blur'}],
  gander:[{ required: true, message: '请选择用户性别', trigger: 'blur'}],
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
}
const add=ref()
const avatarChange=ref(false)

// 打开窗口时
const openDrawer=(data)=>{
  disable.value=true
  avatarChange.value=false
  sid.value=data
  getInfo()
  drawerVisible.value=true
}

// 关闭窗口时
const closeDrawer=()=>{
  beforeClose()
  drawerVisible.value=false
  if (avatarChange.value===true){
    emit('success')
  }
}

// 窗口关闭前的回调
const beforeClose=()=>{
  formData.value={}
  form.value.clearValidate()
}

// 设置是否启用个人资料修改
const changeDisable=()=>{
  disable.value=!disable.value
  form.value.clearValidate()
}

// 打开上传头像对话框
const openAddDialog=()=>{
  add.value.openDialog()
}

// 上传头像成功时
const onAddAvatarSuccess=()=>{
  getInfo()
  avatarChange.value=true
}

// 获取个人信息数据回显
const getInfo=async ()=>{
  getUserInfo(sid.value).then(resp=>{
    formData.value=resp.data.staff
  })
}

// 上传修改表单
const submitInfoChange=async ()=>{
  if (avatarChange.value===true){
    emit('success')
  }
  if (disable.value===true){
    closeDrawer()
  }else {
    await form.value.validate()
    formData.value={...formData.value,sid:sid.value}
    changeUserInfo(formData.value).then(resp=>{
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
}

// 对外暴露方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="个人资料" v-model="drawerVisible" size="35%" @close="beforeClose">
    <div class="info_content">

      <!--  背景图    -->
      <div class="background_img">
        <img src="../../../assets/background/default.png" width="100%" height="200" alt="背景图">
      </div>

      <!--  表单   -->
      <div class="self_content" style="margin-top: 15px;margin-left: 15px">
        <el-avatar
            class="custom-avatar"
            :size="150"
            :src="formData.avatar"
            style="float: left;cursor: pointer"
            title="点击更换头像"
            @click="openAddDialog"></el-avatar>
        <el-form
            inline
            ref="form"
            :rules="rules"
            :hide-required-asterisk="true"
            :model="formData">
          <el-form-item label-width="70" label="姓名" prop="name">
            <el-input v-model="formData.name" clearable :disabled="disable"></el-input>
          </el-form-item>
          <el-form-item label-width="70" label="性别" prop="gander">
            <el-select v-model="formData.gander" clearable :disabled="disable" style="width: 200px;">
              <el-option label="男" value = '男'></el-option>
              <el-option label="女" value = '女'></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label-width="70" label="年龄" prop="age">
            <el-input v-model="formData.age" clearable :disabled="disable"></el-input>
          </el-form-item>
          <el-form-item label-width="220" label="电话" prop="tel">
            <el-input v-model="formData.tel" clearable :disabled="disable"></el-input>
          </el-form-item>
          <el-form-item label-width="220" label="邮箱" prop="email">
            <el-input v-model="formData.email" clearable :disabled="disable"></el-input>
          </el-form-item>
          <el-form-item label-width="220" label="密码" prop="password">
            <el-input v-model="formData.password" show-password clearable :disabled="disable"></el-input>
          </el-form-item>
          <el-row style="margin-top: 5px">
            <el-col :span="13" :offset="10">
              <el-button type="primary" @click="submitInfoChange">确定</el-button>
              <el-button type="success" @click="changeDisable">修改个人资料</el-button>
              <el-button @click="closeDrawer">取消</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
  </el-drawer>

  <!-- 上传头像Dialog  -->
  <avatar_add ref="add" @success="onAddAvatarSuccess"></avatar_add>

</template>

<style scoped lang="less">
.custom-avatar {
  position: relative;
  display: inline-block; /* 确保在添加伪元素时不会影响布局 */
  overflow: hidden; /* 隐藏超出 avatar 的内容 */
}

.custom-avatar::after {
  content: "+"; /* 假设你使用文本作为加号 */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 居中加号 */
  color: #fff; /* 加号颜色 */
  font-size: 24px; /* 加号大小 */
  opacity: 0; /* 初始状态为透明 */
  transition: opacity 0.3s ease; /* 添加过渡效果 */
  pointer-events: none; /* 确保鼠标事件可以穿透到 avatar 上 */
}

.custom-avatar:hover::after {
  width: 35px;
  height: 35px;
  opacity: 1; /* hover 时显示加号 */
  background-color: rgba(0, 0, 0, 0.5); /* 灰色透明背景 */
  padding: 10px; /* 根据需要调整内边距以显示背景 */
  border-radius: 50%; /* 保持圆形背景（如果 avatar 是圆形的） */
}
</style>