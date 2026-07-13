<script setup>
import {onActivated, ref} from "vue";
import {getCustomerMenue, getStaffMenue} from "@/api/menue/menue.js";
import {addEgressRecord, changeEgressRecord} from "@/api/stay/egress.js";
import {ElMessage, ElNotification} from "element-plus";

const drawerVisible = ref(false)
const emit = defineEmits(['success'])
const customer=ref([])
const staff=ref([])
const keyword=ref('')
const staffKeyword=ref('')
const formData=ref({})
const form=ref()
const rules=ref({
  uid:[{ required: true, message: '请选择需要外出的老人姓名', trigger: 'blur'}],
  reason:[{ required: true, message: '请输入外出原因', trigger: 'blur'}],
  egress:[{ required: true, message: '请选择外出时间', trigger: 'blur'}],
  predict:[{ required: true, message: '请选择预计回院时间', trigger: 'blur'}],
  id:[{ required: true, message: '请选择陪同人员', trigger: 'blur'}],
  relation:[{ required: true, message: '请输入与陪同人员的关系', trigger: 'blur'}],
  tel:[{ required: true, message: '请输入联系电话', trigger: 'blur'}]
})

// 打开界面时
const openDrawer=(data)=>{
  (void 0)
  formData.value.eid=data.eid
  formData.value.uid=data.uid
  formData.value.reason=data.reason
  formData.value.egress=data.egress
  formData.value.predict=data.predict
  formData.value.id=data.id
  formData.value.relation=data.relation
  formData.value.tel=data.tel
  formData.value.memo=data.memo
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

// 获取客户菜单
const getMenue = async ()=>{
  getCustomerMenue(keyword.value).then(resp=>{
    customer.value=resp.data.customerMenue
  })
}

// 获取用户菜单
const getStaff = async ()=>{
  getStaffMenue(staffKeyword.value).then(resp=>{
    staff.value=resp.data.staffMenue
  })
}

// 提交表单
const submitChangeEgress = async ()=>{
  await form.value.validate()
  changeEgressRecord(formData.value).then(resp=>{
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

// 生命周期钩子
onActivated(()=>{
  getMenue()
  getStaff()
})

// 暴露给父组件方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="外出登记" width="30%" v-model="drawerVisible" @close="closeDrawer">
    <el-divider />
    <el-form
        :model="formData"
        :rules="rules"
        ref="form"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px;margin-left: 10px">
      <el-form-item label="客户" label-width="100px" prop="uid">
        <el-select clearable v-model="formData.uid">
          <template #header>
            <el-input v-model="keyword" clearable @keyup="getMenue" placeholder="请输入客户名称"></el-input>
          </template>
          <el-option v-for="c in customer" :key="c.value" :label="c.label" :value="c.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="外出事由" label-width="100px" prop="reason">
        <el-input placeholder="请输入外出事由" clearable v-model="formData.reason"></el-input>
      </el-form-item>
      <el-form-item label="外出时间" label-width="100px" prop="egress">
        <el-date-picker
            v-model="formData.egress"
            value-format="YYYY-MM-DD"
            type="date"
            placeholder="年-月-日"
            style="width: 250px;"
            clearable
        />
      </el-form-item>
      <el-form-item label="预计回院" label-width="100px" prop="predict">
        <el-date-picker
            v-model="formData.predict"
            value-format="YYYY-MM-DD"
            type="date"
            placeholder="年-月-日"
            style="width: 250px;"
            clearable
        />
      </el-form-item>
      <el-form-item label="陪同人" label-width="100px" prop="id">
        <el-select clearable v-model="formData.id">
          <template #header>
            <el-input v-model="staffKeyword" clearable @keyup="getStaff" placeholder="请输入工作人员姓名"></el-input>
          </template>
          <el-option v-for="s in staff" :key="s.value" :label="s.label" :value="s.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="与老人关系" label-width="100px" prop="relation">
        <el-input placeholder="请输入与老人的关系" clearable v-model="formData.relation"></el-input>
      </el-form-item>
      <el-form-item label="陪同人电话" label-width="100px" prop="tel">
        <el-input placeholder="请输入陪同人的电话" clearable v-model="formData.tel"></el-input>
      </el-form-item>
      <el-form-item label="备注" label-width="100px">
        <el-input clearable v-model="formData.memo"></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitChangeEgress">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>