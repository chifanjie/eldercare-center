<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Search} from "@element-plus/icons-vue";
import {onActivated, ref} from "vue";
import router from "@/router/index.js";
import {getStatisticsPaged} from "@/api/meal/statistics.js";
import Statistics_set from "@/view/meal/drawer/statistics_set.vue";

const data=ref([]);
const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const week=ref('1')
const time=ref('1')
const set=ref()

// 分页组件：页面内容大小改变时
const onSizeChange=(value)=>{
  getTableData()
}

// 分页组件：当前页改变时
const onCurrentChange=(value)=>{
  getTableData()
}

// 获取表格数据
const getTableData = async () => {
  getStatisticsPaged(week.value,time.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 修改成功时
const onSetSuccess= () => {
  currentPage.value=1
  getTableData()
}

// 打开修改Drawer
const openStatisticsSetDrawer=(data)=> {
  set.value.openDrawer(data)
}

// 生命周期钩子
onActivated(()=>{
  getTableData()
})
</script>

<template>
  <page_container title="膳食统计">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/meals'}">膳食</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/meals/statistics' }">膳食统计</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">膳食统计</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="10">
          <el-form-item>
            <el-select style="width: 100px" v-model="time" placeholder="选择提供膳食的时间">
              <el-option label="早餐" value="1" />
              <el-option label="午餐" value="2" />
              <el-option label="晚餐" value="3" />
            </el-select>
            <el-select style="width: 200px" placeholder="选择提供膳食的星期" v-model="week">
              <el-option label="星期一" value="1" />
              <el-option label="星期二" value="2" />
              <el-option label="星期三" value="3" />
              <el-option label="星期四" value="4" />
              <el-option label="星期五" value="5" />
              <el-option label="星期六" value="6" />
              <el-option label="星期日" value="7" />
            </el-select>
            <el-button :icon="Search" @click="getTableData">快速查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--表格数据-->
    <el-table
        :data="data"
        style="min-width: 100%; margin-top: 10px"
        :row-style="{ height: '50px' }"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
        table-layout="fixed">
      <el-table-column label="序号" type="index" width="60px"></el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
      <el-table-column label="数量" prop="num"></el-table-column>
      <el-table-column label="分类">
        <template v-slot="{ row: { typeName } }">
          <el-tag type="primary">{{ typeName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openStatisticsSetDrawer(scope.row)">修改</el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="空空如也" />
      </template>
    </el-table>

    <!--分页-->
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5,10,20,50]"
        :small="false"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
    />

    <!--  修改山是统计Drawer  -->
    <statistics_set ref="set" @success="onSetSuccess"></statistics_set>

  </page_container>
</template>

<style scoped lang="less">

</style>