<script setup>
import {ref} from "vue";

const drawerVisible=ref(false)
const formData=ref({})

// 打开界面时
const openDrawer=(data)=>{
  formData.value.name=data.name
  formData.value.gander=data.gander
  formData.value.age=data.age
  formData.value.file=data.file
  formData.value.reason=data.reason
  formData.value.egress=data.egress
  formData.value.predict=data.predict
  formData.value.really=data.really
  formData.value.relationName=data.relationName
  formData.value.relation=data.relation
  formData.value.tel=data.tel
  formData.value.statue=data.statue
  formData.value.approverName=data.approverName
  formData.value.approve=data.approve
  formData.value.memo=data.memo
  drawerVisible.value=true
}

// 关闭界面时
const closeDrawer=()=>{
  formData.value={}
  drawerVisible.value=false
}

// 暴露给父组件方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="详细信息" v-model="drawerVisible" size="30%" @close="closeDrawer">
    <el-divider />
    <el-descriptions :column="5" border direction="horizontal">
      <el-descriptions-item label="客户" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.name }}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="外出事由" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.reason }}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="外出时间" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.egress }}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="预计回院时间" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.predict }}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="实际回院时间" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.really }}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="陪同人" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.relationName }}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="与老人关系" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.relation }}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="陪同人电话" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.tel }}</el-text>
      </el-descriptions-item>
      <el-descriptions-item label="备注" label-align="center" span="5">
        <el-text style="margin-left: 2px">{{ formData.tel }}</el-text>
      </el-descriptions-item>
    </el-descriptions>
  </el-drawer>
</template>

<style scoped lang="less">

</style>