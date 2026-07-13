<script setup>
import {ref} from "vue";
import {getMealMenue} from "@/api/menue/menue.js";
import {setMealRecord} from "@/api/meal/record.js";
import {ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const meals=ref([])
const form=ref()
const rules={
  mid:[{ required: true, message: '请选择膳食', trigger: 'blur'}],
  week:[{ required: true, message: '请选择提供该膳食的时间', trigger: 'blur'}],
  time:[{ required: true, message: '请选择该膳食的餐时', trigger: 'blur'}],
}

// 打开界面时
const openDialog=()=>{
  getMealRecord()
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
  form.value.clearValidate()
}

// 获取膳食记录
const getMealRecord = async ()=>{
  getMealMenue().then(resp=>{
    meals.value=resp.data.mealMenue
  })
}

// 提交表单
const submitSetRecord=async ()=>{
  await form.value.validate()
  setMealRecord(formData.value).then(resp=>{
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

// 对外暴露方法
defineExpose({
  openDialog
})
</script>

<template>
  <el-dialog title="设置膳食日记" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="膳食" label-width="100px" prop="mid">
        <el-select v-model="formData.mid" clearable>
          <el-option v-for="m in meals" :key="m.mid" :label="m.name" :value="m.mid"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="时间" label-width="100px" prop="week">
        <el-select v-model="formData.week" clearable>
          <el-option label="周一" value = '1'></el-option>
          <el-option label="周二" value = '2'></el-option>
          <el-option label="周三" value = '3'></el-option>
          <el-option label="周四" value = '4'></el-option>
          <el-option label="周五" value = '5'></el-option>
          <el-option label="周六" value = '6'></el-option>
          <el-option label="周日" value = '7'></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="餐时" label-width="100px" prop="time">
        <el-select v-model="formData.time" clearable>
          <el-option label="早餐" value = '1'></el-option>
          <el-option label="午餐" value = '2'></el-option>
          <el-option label="晚餐" value = '3'></el-option>
        </el-select>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitSetRecord">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>