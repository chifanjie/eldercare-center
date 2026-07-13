<template>
  <el-breadcrumb :separator-icon="ArrowRight">
    <el-breadcrumb-item :to="{ path: '/' }"
      >homepage</el-breadcrumb-item
    >
    <el-breadcrumb-item
      :key="index"
      v-for="(item, index) in currentpath"
    >
      {{ item }}</el-breadcrumb-item
    >
  </el-breadcrumb>
</template>

<script setup>
import { ArrowRight } from '@element-plus/icons-vue'
// 引入路由
// vue3 中，用啥，引啥

import { useRouter } from 'vue-router'
import { watch, ref } from 'vue'

const paths = {
  emp: ['aaaa', 'bbbbb'],
  dept: ['ccccc', 'dddddd']
}

const currentpath = ref([])
// 实例化路由对象
const router = useRouter()
// 监听当前路由
watch(
  () => router.currentRoute.value, //  箭头路径的改变
  (newValue) => {
    // (void 0)
    // 使用[ ] ,通过反射机制调用对象属性
    //  key = 'emp'
    // paths[key]  ====  paths.emp

    currentpath.value = paths[newValue.name]
  },
  { immediate: true } // immediate 箭头立即生效
)
</script>
