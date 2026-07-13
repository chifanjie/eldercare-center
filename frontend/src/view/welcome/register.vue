<script setup>
import * as THREE from 'three'
import NET from 'vanta/src/vanta.net.js'
import {nextTick, onActivated, onDeactivated, ref} from "vue";
import {getCaptcha} from "@/api/captcha/captcha";
import {userRegister} from "@/api/welcome/user"
import {ElMessage} from "element-plus";
import { useRouter } from 'vue-router'

let vantaEffect=null
const Area=ref(null)
const form=ref()
const formData=ref({})
const router=useRouter()
const disable=ref(false)
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

// 用户注册
const register = async()=>{
  await form.value.validate()
  formData.value={...formData.value,captcha:captcha.value}
  userRegister(formData.value).then(resp=>{
    if (resp.code===200){
      ElMessage.success(resp.msg)
      setTimeout(() => {
        router.push('/login');
      }, 1500);
    }else {
      ElMessage.error(resp.msg)
    }
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
          <vuetyped :strings="['介绍下你自己吧!']" :loop="false" :smart-backspace='true'>
            <h1 class="typing" style="font-size: xxx-large"></h1>
          </vuetyped>
          <vuetyped :strings="['品质管理，呵护生活','凝聚力量，共筑幸福']" :loop="true" :smart-backspace='true'>
            <h2 class="typing"></h2>
          </vuetyped>
        </div>
      </div>
    </el-col>

    <!--右侧表单-->
    <el-col :span="9" :offset="3" class="form">
      <div class="glass_card">
        <span class="login_title">我是谁?</span>
        <div class="form_content">
          <el-form
              label-position="right"
              label-width="auto"
              ref="form"
              :model="formData"
              :rules="rules"
              style="max-width: 350px">
            <el-form-item label="用户名" prop="username">
              <el-input placeholder="请输入用户名" clearable v-model="formData.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input placeholder="请输入密码" show-password clearable v-model="formData.password"></el-input>
            </el-form-item>
            <el-form-item label="再次输入密码" prop="rePassword">
              <el-input placeholder="请再次输入密码" show-password clearable v-model="formData.rePassword"></el-input>
            </el-form-item>
            <el-form-item label="电子邮箱" prop="email">
              <el-input placeholder="请输入邮箱地址" clearable v-model="formData.email"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="vailCode">
              <el-input placeholder="请输入验证码" clearable v-model="formData.vailCode" style="width: 140px"></el-input>
              <el-button type="primary" :disabled="disable" @click="getVailCode" style="margin-left: 1px">{{captchaBtn}}</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" round size="large" @click="register" style="width: 150px;margin-left: 100px">注册</el-button>
            </el-form-item>
            <el-form-item>
              <el-link :underline="false" @click="$router.push('/login')">登录</el-link>
              <el-link :underline="false" @click="$router.push('/forget')" style="margin-left: 255px">忘记密码</el-link>
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
  left: 43%;
  top: 22%;
  font-family: "Yu Gothic UI";
}
.form_content{
  position: relative;
  top: 30%;
  right: 5%;
  margin-left: 130px;
}
</style>