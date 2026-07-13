<script setup>
import * as THREE from 'three'
import NET from 'vanta/src/vanta.net.js'
import {nextTick, onActivated, onDeactivated, ref} from "vue";
import {getCaptcha} from "@/api/captcha/captcha";
import {isUserExist,changePassword} from "@/api/welcome/user";
import {ElMessage} from "element-plus";

let vantaEffect=null
const Area=ref(null)
const active = ref(0)
const form=ref()
const formData=ref({})
const disable=ref(false)
const icon=ref('info')
const title=ref('')
const captchaBtn=ref('获取验证码')
const time=ref(60)
const captcha=ref('')
const rules={
  username:[
    { required: true, message: '请输入用户名', trigger: 'blur'},
    { min: 5, max: 10, message: '用户名长度应该在5-10个字符内', trigger: 'blur'}
  ],
  ["password"]:[
    { required: true, message: '请输入密码', trigger: 'blur'},
    { pattern: /^[.\w@]+$/, message: '密码只能包括数字字母 "." "_" "@"', trigger: 'blur'},
    { min: 6, max: 16, message: '密码长度应该在6-16个字符内', trigger: 'blur'}
  ],
  rePassword:[
    { required: true, message: '请输入密码', trigger: 'blur'},
    { pattern: /^[.\w@]+$/, message: '密码只能包括数字字母 "." "_" "@"', trigger: 'blur'},
    { min: 6, max: 16, message: '密码长度应该在6-16个字符内', trigger: 'blur'},
    { validator: (rule, value, callback)=>{if (value!==formData.value.password){callback(new Error('两次输入的密码不一致'))}else {callback()}}, trigger: 'blur'}
  ],
  email:[
    { required: true, message: '请输入邮箱地址', trigger: 'blur'},
    { pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,message: '请输入正确的邮箱地址', trigger: 'blur'}
  ],
  vailCode:[{ required: true, message: '请输入验证码', trigger: 'blur'}],
}

// 下一步切换组件控制器
const next = () => {
  if (active.value++ > 2) active.value = 0
}

// 生命周期钩子
onActivated(()=>{
  // 初始化vanta
  vantaEffect = NET({
    el: Area.value,
    THREE: THREE,
    color: 0xff3f81,
    backgroundColor: 0x23153c,
    points: 20,
    maxDistance: 27,
    spacing: 20,
    showDots: true
  })
  // 初始化步骤条
  active.value=0
  // 初始化表单及表单验证
  nextTick(() => {
    form.value.clearValidate()
  })
  formData.value={}
})

// 生命周期钩子
onDeactivated(()=>{
  // 在组件卸载前销毁vanta
  if (vantaEffect){
    vantaEffect.destroy()
  }
})

// 获取验证码
const getVailCode=()=>{
  const rule=/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
  if (rule.test(formData.value.email)&&formData.value.email!==''){
    disable.value=true
    getCaptchaServer()
    let timer =setInterval(()=>{
      if (time.value > 0){
        time.value--
        captchaBtn.value=time.value+'s'
      }else {
        clearInterval(timer)
        time.value=60
        captchaBtn.value='获取验证码'
        disable.value=false
      }
    },1000)
  }else {
    ElMessage.error('请输入正确的邮箱地址')
  }
}

// 用户是否存在
const isCountCheck=async ()=>{
  const checkUsername = await form.value.validateField('username')
  const checkEmail = await form.value.validateField('email')
  const checkVailCode = await form.value.validateField('vailCode')
  if (checkUsername && checkEmail && checkVailCode){
    formData.value={...formData.value,captcha:captcha.value}
    isUserExist(formData.value).then(resp=>{
      if (resp.code===200){
        next()
      }else if (resp.code===500){
        ElMessage.error(resp.msg)
      }
    })
  }else {
    ElMessage.error("请输入详细信息!")
  }
}

// 重置密码提交
const updatePassword=async ()=>{
  await form.value.validate()
  changePassword(formData.value).then(resp=>{
    if (resp.code===200){
      icon.value='success'
      ElMessage.success(resp.msg)
    }else {
      icon.value='error'
      ElMessage.error(resp.msg)
    }
    title.value=resp.msg
    next()
  })
}

// 请求验证码接口
const getCaptchaServer=async()=>{
  getCaptcha(formData.value.email).then(resp=>{
    captcha.value=resp.data.captcha
    ElMessage.success(resp.msg)
  })
}

</script>

<template>
  <el-row class="login">

    <!--背景及左侧logo文字-->
    <el-col :span="12" class="background">
      <div class="vanta_area" ref="Area">
        <div class="logo_area">
          <vuetyped :strings="['遗忘是很正常的事']" :loop="false" :smart-backspace='true'>
            <h1 class="typing" style="font-size: xxx-large"></h1>
          </vuetyped>
          <vuetyped :strings="['安全护航，温馨家园','呵护生活，智慧护理']" :loop="true" :smart-backspace='true'>
            <h2 class="typing"></h2>
          </vuetyped>
        </div>
      </div>
    </el-col>

    <!--右侧表单-->
    <el-col :span="9" :offset="3" class="form">
      <div class="glass_card">
        <span class="login_title">我曾是?</span>
        <el-steps style="max-width: 300px" :active="active" finish-status="success" align-center class="step_bar">
          <el-step title="确认身份" />
          <el-step title="重置密码" />
          <el-step title="结果" />
        </el-steps>
        <div class="form_content">
          <el-form
              label-position="right"
              label-width="auto"
              ref="form"
              :model="formData"
              :rules="rules"
              style="max-width: 350px"
          >
            <transition name="slide" mode="out-in">
              <div v-if="active===0">
                <el-form-item label="用户名" prop="username">
                  <el-input placeholder="请输入用户名" clearable v-model="formData.username"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" prop="email">
                  <el-input placeholder="请输入注册时的邮箱地址" clearable v-model="formData.email"></el-input>
                </el-form-item>
                <el-form-item label="验证码" prop="vailCode">
                  <el-input placeholder="请输入验证码" clearable v-model="formData.vailCode" style="width: 140px"></el-input>
                  <el-button type="primary" @click="getVailCode" :disabled="disable" style="margin-left: 2px">{{ captchaBtn }}</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" round size="large" @click="isCountCheck" style="width: 150px;margin-left: 100px">下一步</el-button>
                </el-form-item>
              </div>
              <div v-else-if="active===1">
                <el-form-item label="密码" prop="password">
                  <el-input placeholder="请输入密码" clearable show-password v-model="formData.password"></el-input>
                </el-form-item>
                <el-form-item label="再次输入密码" prop="rePassword">
                  <el-input placeholder="请再次输入密码" clearable show-password v-model="formData.rePassword"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" round size="large" @click="updatePassword" style="width: 150px;margin-left: 100px">下一步</el-button>
                </el-form-item>
              </div>
              <div v-else-if="active===2">
                <el-form-item>
                  <el-result
                      :icon="icon.value"
                      :title="title"
                      sub-title="点击下方按钮返回登陆页面"
                      style="position: relative;left: 20%;right: 20%"
                  >
                    <template #extra>
                      <el-button type="primary" round size="large" @click="$router.push('/login')">返回登录界面</el-button>
                    </template>
                  </el-result>
                </el-form-item>
              </div>
            </transition>
            <el-form-item v-if="active !== 2">
              <el-link :underline="false" @click="$router.push('/login')">登录</el-link>
              <el-link :underline="false" @click="$router.push('/register')" style="margin-left: 230px">第一次登录?</el-link>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-col>

  </el-row>

</template>

<style lang="less" scoped>
::v-deep .el-form-item__label{
  color: white;
}
::v-deep .el-link{
  color:white;
}
::v-deep .el-link:hover{
  color:#409EFF;
}
.vanta_area{
  width: 100vw;
  height: 100vh;
  z-index: -1;
  overflow: hidden;
}
.step_bar{
  position: relative;
  top: 25%;
  left: 25%;
  right: 5%;
}
.login{
  height: 100vh;
}
.glass_card{
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 15px;
  height: 100vh;
}
.logo_area{
  z-index: 1;
  position: absolute;
  top: 40%;
  left: 10%;
  right: 60%;
  color: white;
}
.login_title{
  font-size: xx-large;
  margin: 0;
  color: white;
  position: relative;
  left: 40%;
  top: 20%;
  font-family: "Yu Gothic UI";
}
.form_content{
  position: relative;
  top: 30%;
  right: 5%;
  margin-left: 130px;
}
/* 过渡动画效果 */
.slide-enter-active, .slide-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}
.slide-enter, .slide-leave-to{
  opacity: 0;
  transform: translateX(-20%);
}
</style>