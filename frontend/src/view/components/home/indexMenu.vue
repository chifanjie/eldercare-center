<template>
    <div id="indexhead">
        <div style="display: flex;">
        
        <div class="title">
            <p>Neusoft 东软体检管理系统</p>
        </div>
        </div>
         
        <div style="display:flex;align-items: center">
            <div class="block" style="margin-right: 5px;">
            <el-avatar :size="50" :src="neu" />
            </div>
            <!--align-items: center上下居中对齐-->
            <!-- <el-button :icon="SwitchButton">退出</el-button> -->
            <el-button type="primary" :icon="SwitchButton" @click="tuichu"/>
        </div>
    </div>
</template>

<script setup>
import neu from '../../../assets/imgs/neu.png'

import {
  ArrowLeft,
  ArrowRight,
  Delete,
  Edit,
  Share,
  SwitchButton
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

import { useRouter } from 'vue-router';
const router = useRouter()
const tuichu = () => {
    ElMessageBox.confirm(
        '确定退出登录?',
        '警告',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',

            type: 'warning',
        }
    )
        .then(() => {


            ElMessage({
                type: 'success',
                message: '退出成功',

            })
            router.push('/Logindoctor')
        })
        .catch(() => {
            ElMessage({
                type: 'failed',
                message: '取消退出',
            })
        })
}
</script>

<style lang="scss" scoped>


    #indexhead{
        //平分在左右
        justify-content: space-between;
        display: flex;
      .logo{
        img{
            height: 68px;
        }
    }
    .title{
        p{
            //设计字体样式大小
            line-height: 68px;//行高
            color: rgb(38, 83, 163);
            font-family: '微软雅黑', Courier, monospace;
            font-size: 1.875rem;
        }
    }
  background-color:rgb(179, 192, 209);
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

    }
   

</style>