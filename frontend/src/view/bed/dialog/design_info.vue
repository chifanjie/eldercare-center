<script setup>
import {ref} from "vue";
import {getBedCustomer} from "@/api/bed/design.js";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const uid=ref()
const customerRecord=ref({})
const bedRecord=ref({})
const emergencyRecord=ref({name:'',tel:''})

// 打开界面时
const openDialog=(data)=>{
  bedRecord.value=data
  uid.value=data.uid
  getRecord()
  dialogVisible.value=true
}

// 关闭界面时
const closeDialog=()=>{
  dialogVisible.value=false
}

// 获取床位详细信息
const getRecord=async ()=>{
  getBedCustomer(uid.value).then(resp=>{
    customerRecord.value=resp.data.customer
    emergencyRecord.value.name=customerRecord.value.emergency.name
    emergencyRecord.value.tel=customerRecord.value.emergency.tel
  })
}

// 对外暴露方法
defineExpose({
  openDialog
})
</script>

<template>
  <el-dialog title="床位信息" width="40%" v-model="dialogVisible" @close="closeDialog">
    <el-descriptions border direction="horizontal" :column="2" style="margin-top: 15px">
      <el-descriptions-item label="床位号" label-align="center" :span="1" :width="120">
        {{ bedRecord.bedName }}
      </el-descriptions-item>
      <el-descriptions-item label="床位状态" label-align="center" :span="1" :width="120">
        <el-tag type="success" v-if="bedRecord.statue===0">空床位</el-tag>
        <el-tag type="primary" v-if="bedRecord.statue===2">老人外出</el-tag>
        <el-tag type="info" v-if="bedRecord.statue===1">入住</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="老人姓名" label-align="center" :span="1" :width="120">
        {{ customerRecord.name }}
      </el-descriptions-item>
      <el-descriptions-item label="老人状态" label-align="center" :span="1" :width="120">
        <el-tag type="primary" v-if="bedRecord.statue===2">外出</el-tag>
        <el-tag type="success" v-if="bedRecord.statue===1">在院</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="老人年龄" label-align="center" :span="1" :width="120">
        {{ customerRecord.age }}
      </el-descriptions-item>
      <el-descriptions-item label="老人性别" label-align="center" :span="1" :width="120">
        {{ customerRecord.gander }}
      </el-descriptions-item>
      <el-descriptions-item label="老人电话" label-align="center" :span="1" :width="120">
        {{ customerRecord.tel }}
      </el-descriptions-item>
      <el-descriptions-item label="身份证号" label-align="center" :span="1">
        {{ customerRecord.id }}
      </el-descriptions-item>
      <el-descriptions-item label="老人类型" label-align="center" :span="1">
        <el-tag type="success" v-if="customerRecord.statue===0">活力老人</el-tag>
        <el-tag type="primary" v-if="customerRecord.statue===2">自理老人</el-tag>
        <el-tag type="warning" v-if="customerRecord.statue===1">护理老人</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="档案号" label-align="center" :span="1">
        {{ customerRecord.file }}
      </el-descriptions-item>
      <el-descriptions-item label="入住时间" label-align="center" :span="1">
        {{ customerRecord.checkin }}
      </el-descriptions-item>
      <el-descriptions-item label="合同到期时间" label-align="center" :span="1">
        {{ customerRecord.expiration }}
      </el-descriptions-item>
      <el-descriptions-item label="紧急联络人" label-align="center" :span="1" :width="120">
        {{ emergencyRecord.name }}
      </el-descriptions-item>
      <el-descriptions-item label="紧急联络人电话" label-align="center" :span="1" :width="120">
        {{ emergencyRecord.tel }}
      </el-descriptions-item>
      <el-descriptions-item label="备注" label-align="center" :span="2">
        {{ customerRecord.memo }}
      </el-descriptions-item>
    </el-descriptions>
    <el-divider />
    <el-row style="margin-top: 5px">
      <el-col :span="10" :offset="21">
        <el-button @click="closeDialog" type="primary">关闭</el-button>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<style scoped lang="less">

</style>