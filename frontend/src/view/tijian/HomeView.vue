<template>

  <page_container title="入住登记" class="page-container">
    <!--主界面-->
    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="() => { router.go(-1) }">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/tijian' }">体检管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/tijian/manage' }">用户管理</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">用户管理</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和添加按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="4">
          <el-form-item>
            <el-input v-model="keyword" placeholder="客户姓名" clearable>
              <!-- <template #append>
                <el-button :icon="Search" @click="submitForm" />
              </template> -->
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item >
            <el-select v-model="formLabelAlign.smid" class="m-2" placeholder="套餐类型" style="width: 200px">
              <el-option v-for="item in options" :key="item.smid" :label="item.name" :value="item.smid" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4" style="margin-left: 30px">
          <el-button type="primary"  @click="submitForm">查询</el-button>
        </el-col>
        <el-col :span="1" style="margin-left: -120px;">
          <el-button type="primary" @click="resetForm">重置</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!-- <el-button type="primary" @click="loaddate">查询</el-button>
          <el-button type="primary" @click="preData4Add"> 新建 </el-button> -->
    <el-card class="box-card">
      <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName">
        <el-table-column prop="orderid" label="预约编号" width="120" />
<!--        <el-table-column prop="userid" label="手机号码" width="150" />-->
        <!-- <el-table-column prop="realname" label="真实姓名" width="180" /> -->
        <el-table-column label="客户姓名" width="150">
          <!--dept对象不是原生对象了所以用插槽-->
          <template #default="scope">
            {{
              scope.row.users == null ? '' : scope.row.users.realname
            }}
          </template>
        </el-table-column>
        <el-table-column label="性别" width="100">
          <!--dept对象不是原生对象了所以用插槽-->
          <template #default="scope">
            {{
              scope.row.users == null ? '' : scope.row.users.s.sex
            }}
          </template>
        </el-table-column>

        <el-table-column label="体检套餐类型" width="150">
          <!--dept对象不是原生对象了所以用插槽-->
          <template #default="scope">
            {{
              scope.row.setmeal == null ? '' : scope.row.setmeal.name
            }}
          </template>
        </el-table-column>
        <el-table-column label="体检医院" width="140">
          <!--dept对象不是原生对象了所以用插槽-->
          <template #default="scope">
            {{
              scope.row.hospital == null ? '' : scope.row.hospital.name

            }}
          </template>
        </el-table-column>
        <el-table-column prop="orderDate" label="体检日期" width="150" />
        <el-table-column label="状态">

          <template #default="{ row }">
            <el-tag :type="getTagType(row.state)" style="width: 48px;">{{ getTagText(row.state) }}</el-tag>
          </template>

        </el-table-column>
        <el-table-column label="操作">

          <template #default="scope">
            <el-button link type="primary" size="small" @click="EditClick(scope.$index, scope.row)">
              编辑体检报告</el-button>

          </template>
        </el-table-column>
      </el-table>
      <div class="demo-pagination-block">
        <el-pagination style="margin-top: 8px;margin-left: 20%;" v-model:current-page="currentPage"
          v-model:page-size="pageSize" :page-sizes="[7, 8, 10, 12]" :small="small" :disabled="disabled"
          :background="background" layout="total, sizes, prev, pager, next, jumper" :total="total"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" />
      </div>
    </el-card>



  </page_container>


</template>

<script setup>
import AsideMenu from '../components/home/asideMenu.vue'
import indexMenu from '../components/home/indexMenu.vue'
import HomeBreadcrumb from '../components/home/HomeBreadcrumb.vue';//面包屑
import { Setting } from '@element-plus/icons-vue'
import { Search, Plus } from '@element-plus/icons-vue'
import { ref } from 'vue'
import Page_container from "@/view/components/page_container.vue";
import {
  Document,
  Menu as IconMenu,
  Location

} from '@element-plus/icons-vue'
//侧边栏
import axios from 'axios'
// import $axios from '../utils/axios';
import $axios from '../../utils/axios';
import { useRouter } from 'vue-router';
const router = useRouter()
//分页的
const keyword = ref('')
const currentPage = ref(1)
const getTagType = (state) => {
  switch (state) {
    case 1:
      return 'success';
    case 2:
      return '';
    case 'pending':
      return 'warning';
    default:
      return 'info';
  }
};
const getTagText = (state) => {
  switch (state) {
    case 1:
      return '未归档';
    case 2:
      return '归档';
    default:
      return '';
  }
};
const pageSize = ref(7)
const small = ref(false)
const background = ref(true)
const total = ref(20)
//显示数据表
const tableData = ref([])//响应式

const ruleFormRef = ref()
//form表单
const formLabelAlign = ref({
  userid: '',
  realname: '',
  orderDate: '',
  state: '',
  smid: '',
  sex: 0
})
const selectDate = async () => {
  const path = '/setmeal/g/select'
  const { data: res } = await $axios.get(path)
  (void 0)
  options.value = res.result
  options.value = res.result
}
selectDate()
const options = ref([
  {
    smid: 1,
    name: '套餐1',
  },
  {
    smid: 2,
    name: '套餐2',
  },
  {
    smid: 3,
    name: '套餐3',
  },
  {
    smid: 4,
    name: '套餐4',
  }
])
//动态查询
const submitForm = async () => {
  formLabelAlign.value.realname = keyword
  (void 0);
  const path = `/orders/f/sql/${currentPage.value}/${pageSize.value}`
  const { data: res } = await $axios.post(path, formLabelAlign.value, {
    headers: {
      //调用axios，实现javascript对象转换为FormData数据
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
  (void 0)

  tableData.value = res.result;
  total.value = res.code;

}

const resetForm = () => {
  formLabelAlign.value = JSON.parse(JSON.stringify({
    userid: '',
    realname: '',
    orderDate: '',
    state: 0,
    smid: '',
    sex: 0

  }))
  load()
}

//主界面
//为表格加载数据
const load = async () => {
  //定义请求路径
  const path = `/orders/f/owithy/${currentPage.value}/${pageSize.value}`
  //发同步请求
  //发完ajax之后进入等待状态直到服务器作出相应或者超时
  //并且返回服务器做出的相应
  const { data: res } = await $axios.get(path)

  tableData.value = res.result
  count()
  (void 0);
}
load()
const handleSizeChange = () => {
  submitForm()
}
const handleCurrentChange = () => {
  submitForm()
}

const count = async () => {
  //定义请求路径
  const path = '/orders/count'
  //发同步请求
  //发完ajax之后进入等待状态直到服务器作出相应或者超时
  //并且返回服务器做出的相应
  const { data: res } = await $axios.get(path)

  total.value = res.result


}
count()

const EditClick = (index, row) => {
  sessionStorage.setItem("row", JSON.stringify(row))
  (void 0);
  router.push("/tijian/reportmanage")
}

</script>
<!--只对当前页面生效-->
<style scoped lang="scss">
// <!-- lang="scss" -->支持嵌套
.page-container {

  min-height: 100%;
  width: 100%;
  flex: auto;
  background-attachment: scroll;

  .el-aside {
    background-color: rgb(211, 220, 230);
    width: 18%;

    .title {


      align-items: center;
      margin-bottom: 30px;
      text-align: center;

      h3 {
        font-size: 20px;
        font-weight: 550;
        font-family: '微软雅黑', Courier, monospace;
      }
    }

    .demo-date-picker {
      display: flex;


      padding: 0;
      flex-wrap: wrap;
    }

    .demo-date-picker .block {
      padding: 30px 0;
      text-align: center;
      border-right: solid 1px var(--el-border-color);
      flex: 1;
    }

    .demo-date-picker .block:last-child {
      border-right: none;
    }
  }

  .el-header {
    height: 68px;
    width: 100%;
    background-color: rgb(179, 192, 209);
  }

  .el-main {

    // background-color: rgb(233, 238, 243);
    .el-card {
      border-radius: 1rem;

    }

  }

}
</style>
