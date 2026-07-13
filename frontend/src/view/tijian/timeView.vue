<template>
          <el-row style="margin-bottom: 30px">
      <el-page-header @back="() => { router.go(-1) }">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '' }">体检管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '' }">体检预约</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">时间选择</span>
        </template>
      </el-page-header>
    </el-row>
    <body class="sub_page">
        <div class="hero_area">

        </div>

        <!-----------------contact section----------------------->
        <div class="container">
            <div class="col-md-7">
                <div class="elstep">
                    <el-steps :active="active" finish-status="success" align-center>
                        <el-step title="医院选择" />
                        <el-step title="套餐套餐" />
                        <el-step title="时间选择" />
                    </el-steps>
                </div>

                <el-calendar v-model="datavalue">
                    <template #date-cell="{ data }">
                        <p :class="data.isSelected ? 'is-selected' : ''">
                            {{ data.day.split('-').slice(1).join('-') }}
                            {{ data.isSelected ? '✔️' : '' }}
                        </p>
                    </template>
                </el-calendar>

                <!-- <n-calendar
                    v-model:value="datavalue"
                    #="{ year, month, date }"
                    :is-date-disabled="isDateDisabled"
                    
                >
                    {{ year }}-{{ month }}-{{ date }}
                </n-calendar> -->

                <!-- <el-button type="primary" class="button" @click="cardclick">下一步</el-button> -->
                <n-button type="primary" class="button" @click="cardclick">下一步</n-button>

            </div>
            <div class="col-md-5">
                <div class="img-box">
                    <img src="../images/contact-img.jpg" alt="" />
                </div>
            </div>
        </div>

    </body>
</template>
<script setup>
import { ref } from 'vue'
import $axios from '../../utils/axios';
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from 'dayjs';
import { useRouter } from 'vue-router';
import { defineComponent } from "vue";

const router = useRouter()
const active = ref(2)
const datavalue = ref()

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
            router.push('/loginT')
            sessionStorage.clear();
        })
        .catch(() => {
            ElMessage({
                type: 'failed',
                message: '取消退出',
            })
        })
}
const shangyibu = () => {
    router.push('/setmeal')
}
const cardclick = async () => {
    (void 0);
    if (datavalue.value < new Date() - 3600 * 1000 * 24) {
        ElMessage({

            type: 'warning',
            message: '不可选择今天之前的日期'
        })
    }
    else if (datavalue.value == undefined) {
        ElMessage({

            type: 'warning',
            message: '请选择日期'
        })
    }
    else {
        if (datavalue.value != undefined) {
            const formattedDate = dayjs(datavalue.value).format('YYYY-MM-DD');
            (void 0);
            sessionStorage.setItem('date', formattedDate);
            ElMessage({
                message: '选择成功',
                type: 'success',

            })
            ElMessage({

                type: 'success',
                message: '正在跳转至订单页面请稍等'
            })
            setTimeout(() => {
                router.push('/tijian/OrderView');

            }, 1500);
        }
    }

}




</script>
    
<style lang="scss" scoped>


@import url('../css/font-awesome.min.css');


/* 以下是自定义样式，请根据需要进行调整 */
@import url('../css/style.css');
@import url('../css/responsive.css');
@import url('../css/bootstrap.css');

.container {
    justify-content: space-between;
    height: 100%;
    display: flex;
}

.col-md-7 {
    display: flex;
    flex-direction: column;

    .elstep {
        position: relative;
        top: 4%;
        left: 5%;

    }

    .demo-collapse {
        position: relative;
        top: 14%
    }



    .el-calendar {
        border: 1px solid black;
        /* 边框样式，1像素宽度，黑色边框 */

    }

    .el-calendar {
        position: relative;
        top: 5%;
        border: 1px solid black;
        /* 边框样式 */
        border-radius: 4px;
        /* 边框圆角 */
        padding: 10px;
        /* 内边距 */
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.12);
        /* 阴影效果 */
        /* 边框样式，1像素宽度，黑色边框 */
        width: 110%;
        position: relative;
        left: 0%;
        height: 80%;


        background-color: #f5f5f5;
        /* 设置背景颜色 */
        border: 1px solid black;
        border-radius: 4px;
        padding: 10px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.12);

    }
}

.col-md-5 {

    display: flex;
    align-items: center;
    position: relative;
    bottom: 6%;
    left: 8%;
}

.card-item {
    margin-bottom: 20px;
    /* 设置卡片之间的间距 */
}

.calendarstyle {
    top: 20%;
}

.button {
    width: 16%;
    position: relative;
    left: 93%;
    top: 8%;
}
</style>
    