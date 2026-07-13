<script setup>

import {ref} from "vue";
import axios from "axios";
import {useBackend} from "@/stores/base/backend.js";
import {useToken} from "@/stores/index.js";
import {ElMessage, ElNotification} from "element-plus";
import router from "@/router/index.js";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const load=ref(false)
const formData=ref({})
const form=ref()
const rules={
  begin:[{required: true, message: '请选择开始时间', trigger: 'blur'}],
  end:[
    {required: true, message: '请选择结束时间', trigger: 'blur'},
    {validator:(rule, value, callback) => {if (formData.value.begin && value){if (new Date(formData.value.begin)>new Date(value)){callback(new Error('开始时间必须大于结束时间'))}else {callback()}}else {callback()}},trigger: 'blur'}
  ]
}

// 打开界面时
const openDialog=()=>{
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
  load.value=false
  form.value.clearValidate()
}

// 请求xlsx文件
const requestXLSX=async ()=>{
  await form.value.validate()
  load.value=true
  axios({
    method:'get',
    url:useBackend().backend+'/nurse/record_sheet',
    params:{
      begin:formData.value.begin,
      end:formData.value.end
    },
    headers:{
      'token':useToken().token,
    },
    responseType:'blob'
  }).then(resp=>{
    const objUrl=URL.createObjectURL(
        new Blob([resp.data],{
          type:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        })
    )
    const link =document.createElement('a')
    link.download=formData.value.begin+'至'+formData.value.end+'护理记录表.xlsx'
    link.style.display='none'
    link.href=objUrl
    link.click()
    document.body.append(link)
    ElNotification.success({
      title: '成功',
      message: '已导出'+formData.value.begin+'至'+formData.value.end+"期间的护理数据",
      offset: 48
    })
    load.value=false
    closeDialog()
  }).catch(error=>{
    if ( error.response.status === 401){
      const tokenStore = useToken()
      tokenStore.removeToken()
      ElMessage.error({
        message:'登录失效，请重新登录',
        grouping: true,
      })
      router.push('/login')
    }else if (error.response.status === 404){
      ElNotification.error({
        title: '失败',
        message: '所选择期间没有护理数据',
        offset: 48
      })
    }else if (error.response.status === 500){
      ElNotification.error({
        title: '失败',
        message: '服务器异常',
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
  <el-dialog title="导出清单" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="开始时间" label-width="100px" prop="begin">
        <el-date-picker
            v-model="formData.begin"
            value-format="YYYY-MM-DD"
            type="date"
            placeholder="年-月-日"
            style="width: 250px;"
            clearable
        />
      </el-form-item>
      <el-form-item label="结束时间" label-width="100px" prop="end">
        <el-date-picker
            v-model="formData.end"
            value-format="YYYY-MM-DD"
            type="date"
            placeholder="年-月-日"
            style="width: 250px;"
            clearable
        />
      </el-form-item>
      <el-divider/>
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="requestXLSX" :loading="load.value">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>