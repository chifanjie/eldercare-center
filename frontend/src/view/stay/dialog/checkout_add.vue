<script setup>
import {ref} from "vue";
import {getCustomerMenue} from "@/api/menue/menue.js";
import {addCheckoutRecord} from "@/api/stay/checkout.js";
import {ElNotification} from "element-plus";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const customer=ref([])
const keyword=ref('')
const formData=ref({})
const form=ref()
const rules={
  uid:[{ required: true, message: '请选择客户', trigger: 'blur'}],
  type:[{ required: true, message: '请选择退住类型', trigger: 'blur'}],
  reason:[{ required: true, message: '请输入退住原因', trigger: 'blur'}],
  checkout:[{ required: true, message: '请选择退住时间', trigger: 'blur'}],
}

// 打开界面时
const openDialog=()=>{
  getMenue()
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

// 获取顾客菜单
const getMenue = async ()=>{
  getCustomerMenue(keyword.value).then(resp=>{
    customer.value=resp.data.customerMenue
  })
}

// 提交表单
const submitCheckout = async ()=>{
  await form.value.validate()
  addCheckoutRecord(formData.value).then(resp=>{
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

// 暴露给父组件方法
defineExpose({
  openDialog
})
</script>

<template>
  <el-dialog title="退住办理" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        :model="formData"
        ref="form"
        :rules="rules"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="客户" label-width="100px" prop="uid">
        <el-select clearable v-model="formData.uid">
          <template #header>
            <el-input clearable v-model="keyword" placeholder="输入客户姓名" @keyup="getMenue"></el-input>
          </template>
          <el-option v-for="c in customer" :key="c.value" :label="c.label" :value="c.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="退住类型" label-width="100px" prop="type">
        <el-select clearable v-model="formData.type">
          <el-option label="正常退住" value="1"></el-option>
          <el-option label="死亡退住" value="2"></el-option>
          <el-option label="保留床位" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="退住原因" label-width="100px" prop="reason">
        <el-input clearable v-model="formData.reason" placeholder="请输入退住原因"></el-input>
      </el-form-item>
      <el-form-item label="退住时间" label-width="100px" prop="checkout">
        <el-date-picker
            v-model="formData.checkout"
            value-format="YYYY-MM-DD"
            type="date"
            placeholder="年-月-日"
            style="width: 250px;"
            clearable
        />
      </el-form-item>
      <el-form-item label="备注" label-width="100px">
        <el-input clearable v-model="formData.memo"></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitCheckout">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>