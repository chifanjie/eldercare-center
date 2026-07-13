<script setup>
import Page_container from "@/view/components/page_container.vue";
import {onActivated, ref} from "vue";
import {getOnlineUserInfo} from "@/api/welcome/user.js";

// 导入图片
import image1 from '@/assets/titile/yy1.png';
import image2 from '@/assets/titile/yy2.png';
import image3 from '@/assets/titile/yy3.png';
import image4 from '@/assets/titile/yy4.png';


const dateTime = ref();
const welcome = ref();
const userinfo = ref({});
const load = ref();

// 实时获取时间
const getTime = () => {
  let year = new Date().getFullYear();
  let month = new Date().getMonth() + 1 < 10 ? "0" + (new Date().getMonth() + 1) : new Date().getMonth() + 1;
  let date = new Date().getDate() < 10 ? "0" + new Date().getDate() : new Date().getDate();
  let hh = new Date().getHours() < 10 ? "0" + new Date().getHours() : new Date().getHours();
  let mm = new Date().getMinutes() < 10 ? "0" + new Date().getMinutes() : new Date().getMinutes();
  let ss = new Date().getSeconds() < 10 ? "0" + new Date().getSeconds() : new Date().getSeconds();
  if (hh >= 0 && hh < 12) {
    welcome.value = '早上好';
  } else if (hh >= 12 && hh < 18) {
    welcome.value = '下午好';
  } else if (hh >= 18 && hh < 24) {
    welcome.value = '晚上好';
  }
  dateTime.value = year + "年" + month + "月" + date + "日  " + "星期" + '日一二三四五六'.charAt(new Date().getDay()) + " " + hh + ":" + mm + ':' + ss;
  load.value = false;
}

// 获取当前登录用户数据
const getUserInfo = async () => {
  getOnlineUserInfo().then(resp => {
    userinfo.value = resp.data.online;
    if (userinfo.value.gander === '男') {
      userinfo.value.gander = '先生';
    } else {
      userinfo.value.gander = '女士';
    }
    (void 0)
    (void 0)
    (void 0)
    (void 0)
    
    sessionStorage.setItem('name',userinfo.value.name)
    sessionStorage.setItem('gander',userinfo.value.gander)
    sessionStorage.setItem('age',userinfo.value.age)
    sessionStorage.setItem('userid',userinfo.value.sid)
  });
  
}

// 定义轮播图的图片路径
// 定义轮播图的图片路径
const items = [image1, image2, image3, image4];

// 生命周期钩子
onActivated(() => {
  load.value = true;
  getUserInfo();
  setInterval(getTime, 1000);
})

</script>

<template>
  <page_container title="首页" style="background:rgb(249,249,249)">
    <!-- 骨架屏加载   -->
    <el-skeleton
        style="--el-skeleton-circle-size: 150px"
        animated
        :loading="load"
        :throttle="500">
      <template #template>
        <el-row gutter="20">
          <el-col :span="3">
            <el-skeleton-item variant="circle"/>
          </el-col>
          <el-col :span="9">
            <div style="margin-top: 30px">
              <el-skeleton-item variant="h3" style="width: 220px" /><br/>
              <el-skeleton-item variant="h1" style="width: 280px;margin-top: 15px" />
            </div>
          </el-col>
        </el-row>
      </template>
      <template #default>
        <div class="container">
          <el-row gutter="20" align="middle">
            <el-col :span="3">
              <el-avatar :src="userinfo.avatar" :size="150"></el-avatar>
            </el-col>
            <el-col :span="9">
              <h5>现在是: {{ dateTime }}</h5>
              <h2>{{ welcome }}, {{userinfo.roleName}} {{ userinfo.name }}{{ userinfo.gander }}</h2>
            </el-col>
          </el-row>
        </div>
      </template>
    </el-skeleton>

    <!--轮播图-->
    <el-carousel indicator-position="outside" interval="3000" type="card" height="255px" autoplay="true" style="background:rgb(249,249,249)">
      <el-carousel-item v-for="(item, index) in items" :key="index" style="width: 615px">
        <img :src="item" alt="Carousel Image" class="carousel-image" />
      </el-carousel-item>
    </el-carousel>


  </page_container>


</template>

<style scoped lang="less">
.container {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
}
h5 {
  margin: 10px 0;
  font-size: 1.2em;
  color: #666;
}
h2 {
  margin: 10px 0;
  font-size: 1.5em;
  color: #333;
}

.carousel-image {
  width: 615px;
  height: 255px;
  text-align: center;
  object-fit: cover;
}
.el-carousel__item:nth-child(2n) {
  background-color: rgb(249,249,249);
  width: 100%;
  height: 100%;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color:rgb(249,249,249);
  width: 100%;
  height: 100%;
}

</style>
