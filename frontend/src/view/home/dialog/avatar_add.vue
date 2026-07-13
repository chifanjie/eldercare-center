<script setup>
import {ref} from "vue";
import axios from "axios";
import {useBackend} from "@/stores/base/backend.js";
import {useToken} from "@/stores/index.js";
import {ElMessage, ElNotification} from "element-plus";
import router from "@/router/index.js";

const dialogVisible = ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const avatar=ref([])
const avatarUrl=ref('')

// 打开对话框时
const openDialog=()=>{
  dialogVisible.value=true
}

// 关闭对话框时
const closeDialog=()=>{
  beforeClose()
  dialogVisible.value=false
}

// 关闭对话框前
const beforeClose=()=>{
  formData.value={}
  avatarUrl.value=''
  avatar.value=[]
}

// 上传文件资源（头像）
const handleSubmitAvatar=async (files)=>{
  let file=new FormData()
  file.append("avatar",files.file)
  axios({
    method:'post',
    url:useBackend().backend+'/user/reset_user_avatar',
    headers:{
      'token':useToken().token,
      'Content-Type':'multipart/form-data'
    },
    data:file
  }).then(resp=>{
    if (resp.data.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.data.msg,
        offset: 48
      })
      emit('success')
      closeDialog()
    }else {
      ElNotification.error({
        title: '失败',
        message: resp.data.msg,
        offset: 48
      })
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
  avatarUrl.value=''
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
  <el-dialog title="修改头像" width="30%" v-model="dialogVisible" @close="closeDialog">
    <el-form
        label-position="right"
        label-width="auto"
        ref="form"
        :model="formData"
        style="max-width: 500px;margin-top: 15px">
      <el-upload
          :file-list="avatar"
          :before-upload="handleTestFile"
          :on-change="handleExceed"
          :on-remove="handleRemove"
          :http-request="handleSubmitAvatar"
          :limit="2"
          drag
          list-type="picture"
          accept="image/*"
          :auto-upload="true">
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          拖拽文件到此处<em>或点击选择文件上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            上传文件只能是jpg/png格式且大小小于50MB
          </div>
        </template>
      </el-upload>
    </el-form>
  </el-dialog>
</template>

<style scoped lang="less">

</style>