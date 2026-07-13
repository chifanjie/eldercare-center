<script setup>
import {ref} from "vue";
import {submitEgressReally} from "@/api/stay/egress.js";
import {ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const uid=ref()
const rules=ref({
  really:[
      { required: true, message: '请选择实际回院时间', trigger: 'blur'},
      {validator:(rule, value, callback) => {if (formData.value.egress && value){if (new Date(formData.value.egress)>new Date(value)){callback(new Error('回院时间必须大于外出时间'))}else {callback()}}else {callback()}},trigger: 'blur'},
  ],
})

// 打开界面时
const openDrawer=(data)=>{
  uid.value=data.uid
  formData.uid=data.uid
  formData.value.eid=data.eid
  formData.value.egress=data.egress
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

// 提交表单
const submitReally = async ()=>{
  await form.value.validate()
  formData.value={...formData.value,uid:uid.value}
  submitEgressReally(formData.value).then(resp=>{
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
  <el-drawer title="登记回院时间" v-model="drawerVisible" size="30%" @close="closeDrawer">
    <el-divider />
    <el-form
        :model="formData"
        :rules="rules"
        ref="form"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="回院时间" label-width="100px" prop="really">
        <el-date-picker
            v-model="formData.really"
            value-format="YYYY-MM-DD"
            type="date"
            placeholder="年-月-日"
            style="width: 250px;"
            clearable
        />
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitReally">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>