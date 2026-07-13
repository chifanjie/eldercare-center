<script setup>
import {ref} from "vue";
import {getSetStatisticsInfo, setStatisticsRecord} from "@/api/meal/statistics.js";
import {ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const mid=ref()
const week=ref()
const time=ref()

// 打开界面时
const openDrawer=(data)=>{
  mid.value=data.mid
  week.value=data.week
  time.value=data.time
  getRecord()
  drawerVisible.value=true
}

// 关闭界面时
const closeDrawer=()=>{
  beforeClose()
  drawerVisible.value=false
}

// 关闭界面前
const beforeClose=()=>{
  formData.value={}
  form.value.clearValidate()
}

// 获取膳食统计记录
const getRecord=async ()=>{
  getSetStatisticsInfo(mid.value,week.value,time.value).then(resp=>{
    formData.value=resp.data.mealInfo
  })
}

// 提交表单
const submitSet=async ()=>{
  formData.value.mid=mid.value
  formData.value.week=week.value
  formData.value.time=time.value
  setStatisticsRecord(formData.value).then(resp=>{
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

// 暴露给父组件的方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="修改膳食统计记录" v-model="drawerVisible" size="30%" @close="beforeClose">
    <el-divider />
    <el-form
        ref="form"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="活动名称" prop="name">
        <el-input v-model="formData.name" clearable placeholder="请输入活动名称"></el-input>
      </el-form-item>
      <el-form-item label="活动地点" prop="position">
        <el-input v-model="formData.position" clearable placeholder="请输入活动地点"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitSet">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>