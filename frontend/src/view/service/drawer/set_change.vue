<script setup>
import {ref} from "vue";
import {getCustomerMenue} from "@/api/menue/menue.js";
import {changeServiceSet, getServiceSetInfo} from "@/api/service/set.js";
import {ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const keyword=ref('')
const customer=ref([])
const sid=ref()

// 打开界面时
const openDrawer=(row)=>{
  sid.value=row
  drawerVisible.value=true
  getCustomer()
  getInfo()
}

// 关闭界面时
const closeDrawer=()=>{
  beforeClose()
  drawerVisible.value=false
}

// 获取功能树形菜单
const beforeClose=()=>{
  formData.value={}
  form.value.clearValidate()
}

// 获取客户菜单
const getCustomer = async ()=>{
  getCustomerMenue(keyword.value).then(resp=>{
    customer.value=resp.data.customerMenue
  })
}

// 获取数据回显
const getInfo=async ()=>{
  getServiceSetInfo(sid.value).then(resp=>{
    formData.value.uid=resp.data.serviceSet
  })
}

// 提交表单
const submitChange=async ()=>{
  formData.value.sid=sid.value
  changeServiceSet(formData.value).then(resp=>{
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

// 暴露给父组件方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="设置服务对象" v-model="drawerVisible" size="30%" @close="closeDrawer">
    <el-divider />
    <el-form
        ref="form"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="服务对象" v-model="formData.uid">
        <el-select v-model="formData.uid" clearable multiple>
          <template #header>
            <el-input v-model="keyword" placeholder="输入客户姓名" @keyup="getCustomer" clearable></el-input>
          </template>
          <el-option v-for="c in customer" :key="c.value" :label="c.label" :value="c.value"></el-option>
        </el-select>

      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="15">
          <el-button type="primary" @click="submitChange">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>