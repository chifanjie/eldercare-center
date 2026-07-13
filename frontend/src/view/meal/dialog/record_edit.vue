<script setup>
import {ref} from "vue";
import {getTagMenue, getTypeMenue} from "@/api/menue/menue.js";
import axios from "axios";
import {useBackend} from "@/stores/base/backend.js";
import {useToken} from "@/stores/index.js";
import {ElMessage, ElNotification} from "element-plus";
import router from "@/router/index.js";
import {changeMealRecord, getMealRecordInfo} from "@/api/meal/record.js";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const id=ref()
const tagMenue=ref([])
const typeMenue=ref([])
const formData=ref({})
const form=ref()
const food=ref([])
const foodUrl=ref('')
const rules={
  name:[{ required: true, message: '请输入膳食名称', trigger: 'blur'}],
  num:[
      { required: true, message: '请输入膳食分量', trigger: 'blur'},
    {type: 'number',min:1, message: '输入项必须为大于0的数字类型', trigger: 'blur'}
  ],
  price:[
    { required: true, message: '请输入膳食价格', trigger: 'blur'},
    {type: 'number', message: '输入项必须为数字类型', trigger: 'blur'}
  ],
  week:[{ required: true, message: '请选择提供该膳食的时间', trigger: 'blur'}],
  time:[{ required: true, message: '请选择该膳食的餐时', trigger: 'blur'}],
  type:[{ required: true, message: '请选择该膳食的类别', trigger: 'blur'}],
  tid:[{ required: true, message: '请选择该膳食的标签', trigger: 'blur'}],
  muslim:[{ required: true, message: '请选择该膳食是否清真', trigger: 'blur'}],
}

// 打开界面时
const openDialog=(data)=>{
  id.value=data.mid
  getMealInfo(data)
  getTagNameMenue()
  getTypeNameMenue()
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
  foodUrl.value=''
  food.value=[]
  form.value.clearValidate()
}

// 获取标签菜单
const getTagNameMenue = async ()=>{
  getTagMenue().then(resp=>{
    tagMenue.value=resp.data.tagMenue
  })
}

// 获取类别菜单
const getTypeNameMenue = async ()=>{
  getTypeMenue().then(resp=>{
    typeMenue.value=resp.data.typeMenue
  })
}

// 获取膳食信息
const getMealInfo = async(data) =>{
  getMealRecordInfo(id.value).then(resp=>{
    if (resp.code===200){
      formData.value=resp.data.mealRecord
      food.value=[{name:'图片',url:formData.value.food}]
      let url=formData.value.food
      let parts=url.split('/food/')
      foodUrl.value=parts[1]
      formData.value.food=foodUrl.value
      formData.value.muslim=String(resp.data.mealRecord.muslim)
      formData.value.time=String(data.time)
      formData.value.week=String(data.week)
      formData.value.beforeWeek=String(data.week)
      formData.value.beforeTime=String(data.time)
    }
  })
}

// 上传文件资源(图片)
const handleSubmitAvatar=async (files)=>{
  let file=new FormData()
  file.append("food",files.file)
  axios({
    method:'post',
    url:useBackend().backend+'/meal/upload_food',
    headers:{
      'token':useToken().token,
      'Content-Type':'multipart/form-data'
    },
    data:file
  }).then(resp=>{
    if (resp.data.code===200){
      ElMessage.success(resp.data.msg)
      foodUrl.value=resp.data.data.food.name
    }else {
      ElMessage.error(resp.data.msg)
    }
  }).catch(error=>{
    if ( error.response.status === 401){
      (void 0)
      const tokenStore = useToken()
      tokenStore.removeToken()
      ElMessage.error({
        message:'登录失效，请重新登录',
        grouping: true,
      })
      router.push('/login')
    }
  })
}

// 提交表单
const submitRecordChange = async ()=>{
  await form.value.validate()
  formData.value.food=foodUrl.value
  (void 0)
  changeMealRecord(formData.value).then(resp=>{
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

// 上传资源前，校验资源
const handleTestFile=(file)=>{
  let regx=/^image\/(jpeg|png|jpg|JPEG|PNG|JPG)$/
  if (file.size>50*1024*1024){
    ElMessage.error("最大图片大小50Mb")
    return false
  }
  if (!regx.test(file.type)){
    ElMessage.error("图片格式不正确!")
    return false
  }
  return true
}

// 移除上传资源
const handleRemove=()=>{
  foodUrl.value=''
}

// 当上传复数文件时
const handleExceed=(file,fileList)=>{
  if (fileList.length > 1) {
    fileList.splice(0, 1);
  }
}

// 暴露给父组件的方法
defineExpose({
  openDialog
})
</script>

<template>
  <el-dialog title="修改膳食日记" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px">
      <el-form-item label="名称" label-width="100px" prop="name">
        <el-input v-model="formData.name" clearable placeholder="请输入膳食名称"></el-input>
      </el-form-item>
      <el-form-item label="价格" label-width="100px" prop="price">
        <el-input v-model.number="formData.price" clearable placeholder="请输入膳食价格"></el-input>
      </el-form-item>
      <el-form-item label="数量" label-width="100px" prop="num">
        <el-input v-model.number="formData.num" clearable placeholder="请输入数量"></el-input>
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
      <el-form-item label="类别" label-width="100px" prop="type">
        <el-select v-model="formData.type" clearable>
          <el-option v-for="t in typeMenue" :key="t.typeValue" :label="t.typeName" :value="t.typeValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标签" label-width="100px" prop="tid">
        <el-select v-model="formData.tid" clearable>
          <el-option v-for="t in tagMenue" :key="t.tagValue" :label="t.tagName" :value="t.tagValue"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否清真" label-width="100px" prop="muslim">
        <el-select v-model="formData.muslim" clearable>
          <el-option label="否" value = '0'></el-option>
          <el-option label="是" value = '1'></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" label-width="100px" prop="memo">
        <el-input v-model="formData.memo" clearable></el-input>
      </el-form-item>
      <el-form-item label="图片" label-width="100px" prop="food">
        <el-upload
            :file-list="food"
            :before-upload="handleTestFile"
            :on-change="handleExceed"
            :on-remove="handleRemove"
            :http-request="handleSubmitAvatar"
            :limit="2"
            list-type="picture"
            accept="image/*"
            :auto-upload="true">
          <template #trigger>
            <el-button type="primary">选择图片</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip">
              图片必须是jpg/png/jpeg格式且小于50Mb
            </div>
          </template>
        </el-upload>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitRecordChange">确定</el-button>
          <el-button @click="closeDialog">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>