<template>
        <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        router
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><location /></el-icon>
            <span>东软体检管理系统</span>
          </template>
          
            <el-menu-item index="/hospitalmanage"><el-icon><setting /></el-icon><span>医院管理</span></el-menu-item>
            <el-menu-item index="/doctormanage"><el-icon><setting /></el-icon><span>医生管理</span></el-menu-item>
         <el-menu-item index="/usermanage"><el-icon><setting /></el-icon><span>体检人员管理</span></el-menu-item>
          
            <el-menu-item index="/zongjie"><el-icon><document /></el-icon><span>体检人员统计</span></el-menu-item>
            
    
        </el-sub-menu>
      </el-menu>
</template>
<script setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
</script>

<style scoped>
.el-menu{
     background-color: rgb(211, 220, 230);
}
.el-menu-item{
    background-color: rgb(211, 220, 230);
}
.el-sub-menu{
    background-color: rgb(211, 220, 230);
}
.el-menu-item-group{
    background-color: rgb(211, 220, 230);
}

</style>