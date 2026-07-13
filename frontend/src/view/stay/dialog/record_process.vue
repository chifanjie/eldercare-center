<script setup>
import {ref} from "vue";
import {approveEgress} from "@/api/stay/egress.js";
import {ElNotification} from "element-plus";

const dialogVisible = ref(false)
const approved = ref([])
const form=ref()
const formData=ref({})
const emit = defineEmits(['success'])
const rules={
  statue:[{ required: true, message: '请选择审批状态', trigger: 'blur'}]
}

// 打开界面时
const openDialog=(data)=>{
  approved.value=data
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

// 提交审核
const approve = async ()=>{
  await form.value.validate()
  let approveData=ref([])
  approved.value.forEach((i)=>{
    let obj={}
    obj.eid=i.eid
    obj.statue=formData.value.statue
    obj.uid=i.uid
    approveData.value.push(obj)
  })
  approveEgress(approveData.value).then(resp=>{
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
  <el-dialog title="审核" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        :model="formData"
        :rules="rules"
        ref="form"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px;margin-left: 10px">
      <el-form-item label="审批状态" label-width="100px" prop="statue">
        <el-select v-model="formData.statue" clearable>
          <el-option label="审批通过" value="1"></el-option>
          <el-option label="审批不通过" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="approve">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>