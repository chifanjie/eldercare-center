<script setup>
import * as THREE from 'three'
import NET from 'vanta/src/vanta.net.js'
import {nextTick, onActivated, onDeactivated, ref} from "vue";
import { useRouter } from 'vue-router'
import {userLogin} from "@/api/welcome/user.js";
import {ElMessage} from "element-plus";
import {useToken} from "@/stores/index";

let vantaEffect=null
const Area=ref(null)
const form=ref()
const router=useRouter()
const formData=ref({})
const useTokenStore=useToken()
const rules={
  username:[{ required: true, message: '请输入用户名', trigger: 'blur'}],
  ["password"]:[{ required: true, message: '请输入密码', trigger: 'blur'}]
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

// 用户登录
const login = async ()=>{
  await form.value.validate()
  userLogin(formData.value).then(resp=>{
    (void 0)
    if (resp.code===200){
      ElMessage.success(resp.msg)
      useTokenStore.setToken(resp.data.token)
      localStorage.setItem("token",resp.data.token)
      (void 0)
      
      setTimeout(() => {
        router.push('/');
      }, 1500);
    }else {
      ElMessage.error(resp.msg)
    }
  })
}

</script>

<template>
  <el-row class="login">

    <!-- 背景及左侧logo文字 -->
    <el-col :span="12" class="background">
      <div class="vanta_area" ref="Area">
        <div class="logo_area">
          <vuetyped :strings="['东软颐养中心']" :loop="false" :smart-backspace='true'>
            <h1 class="typing" style="font-size: xxx-large"></h1>
          </vuetyped>
          <vuetyped :strings="['智慧护理，关怀无限','科技驱动，关爱呵护']" :loop="true" :smart-backspace='true'>
            <h2 class="typing"></h2>
          </vuetyped>
        </div>
      </div>
    </el-col>
 
    <!--右侧表单-->
    <el-col :span="9" :offset="3" class="form">
      <div class="glass_card">
        <span class="login_title">你是谁?</span>
        <div class="form_content">
          <el-form
              label-position="right"
              label-width="auto"
              ref="form"
              :model="formData"
              :rules="rules"
              style="max-width: 350px"
              :hide-required-asterisk="true"
              @keyup.enter="login">
            <el-form-item label="用户名" prop="username">
              <el-input placeholder="请输入用户名" v-model="formData.username" clearable></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input show-password placeholder="请输入密码" v-model="formData.password" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" round size="large" @click="login" style="width: 150px;margin-left: 100px">登录</el-button>
            </el-form-item>
            <el-form-item>
              <el-link :underline="false" @click="$router.push('/register')">第一次登录?</el-link>
              <el-link :underline="false" @click="$router.push('/forget')" style="margin-left: 215px">忘记密码</el-link>
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
  right: 55%;
  color: white;
}
.login_title{
  font-size: xx-large;
  margin: 0;
  color: white;
  position: relative;
  left: 42%;
  right: 20%;
  top: 32%;
  font-family: "Yu Gothic UI";
}
.form_content{
  position: relative;
  top: 40%;
  right: 4%;
  margin-left: 135px;
}
</style>