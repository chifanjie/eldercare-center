<script setup>
import {ref} from "vue";
import {getBuildingMenue} from "@/api/menue/menue.js";
import {changeCustomerCheckin, whereIsCustomer} from "@/api/stay/checkin.js";
import {ElMessage, ElNotification} from "element-plus";

const drawerVisible=ref(false)
const treeSelect = ref([])
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const rules={
  name:[{ required: true, message: '请输入姓名', trigger: 'blur'}],
  age:[
    { required: true, message: '请输入护理内容价格', trigger: 'blur'},
    {type: 'number', min: 0,max:100, message: '请输入大于等于0小于等于100的数字', trigger: 'blur'}
  ],
  gander:[{required: true, message: '请选择性别', trigger: 'blur'}],
  building:[{required: true, message: '请选择所属楼房', trigger: 'blur'}],
  statue:[{required: true, message: '请选择老人类型', trigger: 'blur'}],
  checkin:[{required: true, message: '请选择入住时间', trigger: 'blur'}],
  expiration:[
    {required: true, message: '请选择合同到期时间', trigger: 'blur'},
    {validator:(rule, value, callback) => {if (formData.value.checkin && value){if (new Date(formData.value.checkin)>new Date(value)){callback(new Error('合同到期时间必须大于等于入住时间'))}else {callback()}}else {callback()}},trigger: 'blur'},
  ],
  tel:[
    {required: true, message: '请输入联系电话', trigger: 'blur'},
    {min:11,max:11, message: '请输入正确的电话号码', trigger: 'blur'}
  ],
  id:[{ required: true, message: '请输入身份证号', trigger: 'blur'}]
}

// 打开界面时
const openDrawer=(obj)=>{
  formData.value.uid=obj.uid
  formData.value.name=obj.name
  formData.value.age=obj.age
  formData.value.id=obj.id
  formData.value.gander=obj.gander
  formData.value.checkin=obj.checkin
  formData.value.expiration=obj.expiration
  formData.value.building=obj.building
  formData.value.tel=obj.tel
  formData.value.memo=obj.memo
  formData.value.statue=String(obj.statue)
  getMenue()
  getPosition()
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

// 获取客户位置
const getPosition=async ()=>{
  whereIsCustomer(formData.value.uid).then(resp=>{
    formData.value.building=resp.data.building
  })
}

// 获取床位菜单
const getMenue=async ()=>{
  getBuildingMenue().then(resp=>{
    treeSelect.value=resp.data.buildingMenue
  })
}

// 提交表单
const submitCheckinSet=async ()=>{
  await form.value.validate()
  changeCustomerCheckin(formData.value).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
      closeDrawer()
      emit('success')
    }else {
      if (resp.data.err===500){
        ElMessage.error(resp.msg)
      }else {
        ElNotification.error({
          title: '失败',
          message: resp.msg,
          offset: 48
        })
        closeDrawer()
      }
    }
  })
}

// 暴露给父组件方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="修改入住登记" v-model="drawerVisible" size="30%" @close="closeDrawer">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="姓名" label-width="100px" prop="name">
        <el-input v-model="formData.name" placeholder="请输入【客户姓名】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="年龄" label-width="100px" prop="age">
        <el-input v-model.number="formData.age" placeholder="请输入【年龄】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="身份证号" label-width="100px" prop="id">
        <el-input v-model.number="formData.id" placeholder="请输入【身份证号】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别" label-width="100px" prop="gander">
        <el-select v-model="formData.gander" clearable>
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属楼房" label-width="100px" prop="building">
        <el-tree-select
            :data="treeSelect"
            v-model="formData.building"
            :render-after-expand="false"
            show-checkbox>
        </el-tree-select>
      </el-form-item>
      <el-form-item label="老人类型" label-width="100px" prop="statue">
        <el-select v-model="formData.statue" clearable>
          <el-option label="活力老人" value="0"></el-option>
          <el-option label="自理老人" value="1"></el-option>
          <el-option label="护理老人" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="入住时间" label-width="100px" prop="checkin">
        <el-date-picker
            v-model="formData.checkin"
            value-format="YYYY-MM-DD"
            type="date"
            placeholder="年-月-日"
            style="width: 250px;"
            clearable
        />
      </el-form-item>
      <el-form-item label="合同期至" label-width="100px" prop="expiration">
        <el-date-picker
            v-model="formData.expiration"
            value-format="YYYY-MM-DD"
            type="date"
            placeholder="年-月-日"
            style="width: 250px;"
            clearable
        />
      </el-form-item>
      <el-form-item label="联系电话" label-width="100px" prop="tel">
        <el-input v-model="formData.tel" placeholder="请输入【联系电话】信息" clearable></el-input>
      </el-form-item>
      <el-form-item label="备注" label-width="100px">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitCheckinSet">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>