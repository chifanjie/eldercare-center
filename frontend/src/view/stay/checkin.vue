<script setup>
import {Search,Plus} from '@element-plus/icons-vue'
import Page_container from "@/view/components/page_container.vue";
import {onActivated, ref} from "vue";
import Checkin_add from "@/view/stay/dialog/checkin_add.vue";
import Checkin_set from "@/view/stay/drawer/checkin_set.vue";
import {deleteCheckinRecord, getCheckinPaged} from "@/api/stay/checkin.js";
import {ElMessageBox, ElNotification} from "element-plus";
import router from "@/router/index.js";

const switcher=ref(0)
const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const keyword=ref('')
const data=ref()
const add=ref()
const set=ref()

// 表单切换控制
const tabClick=(tab,event)=>{
  switcher.value=tab.props.name
  getTableData()
}

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 打开添加对话框
const openCheckinAddDialog=()=>{
  add.value.openDialog()
}

// 打开设置对话框
const openCheckinSetDrawer=(obj)=>{
  set.value.openDrawer(obj)
}

// 添加成功
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 设置成功
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 删除登记记录
// const deleteCheckin=async (id)=>{
//   await ElMessageBox.confirm('您确定要删除该入住登记记录吗?', '提示', {
//     confirmButtonText: '确定',
//     cancelButtonText: '取消',
//     type: 'warning'
//   })
//   let customer=ref({})
//   customer.value={...customer.value,uid:id}
//   deleteCheckinRecord(customer.value).then(resp=>{
//     if (resp.code===200){
//       ElNotification.success({
//         title: '成功',
//         message: resp.msg,
//         offset: 48
//       })
//     }else {
//       ElNotification.error({
//         title: '失败',
//         message: resp.msg,
//         offset: 48
//       })
//     }
//     getTableData()
//   })
// }

// 获取表格数据
const getTableData = async () => {
  getCheckinPaged (keyword.value,switcher.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 生命周期钩子
onActivated(()=>{
  getTableData()
})
</script>

<template>
  <page_container title="入住登记">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/stay'}">住宿变更</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/stay/checkin' }">入住登记</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">入住登记</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和添加按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input v-model="keyword" placeholder="客户姓名" clearable>
              <template #append>
                <el-button :icon="Search" @click="getTableData"/>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" :icon="Plus" @click="openCheckinAddDialog">登记</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--表单切换栏-->
    <el-row>
      <el-col :span="25">
        <el-tabs class="demo-tabs" type="card" v-model="switcher" @tab-click="tabClick">
          <el-tab-pane label="活力老人" :name="0"></el-tab-pane>
          <el-tab-pane label="自理老人" :name="1"></el-tab-pane>
          <el-tab-pane label="护理老人" :name="2"></el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>

    <!--表格数据-->
    <el-table
        :data="data"
        style="min-width: 100%; margin-top: 10px"
        :row-style="{ height: '50px' }"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
        table-layout="fixed">
      <el-table-column label="序号" type="index" width="60px"></el-table-column>
      <el-table-column label="姓名" prop="name"></el-table-column>
      <el-table-column label="性别" prop="gander"></el-table-column>
      <el-table-column label="年龄" prop="age"></el-table-column>
      <el-table-column label="身份证号" prop="id"></el-table-column>
      <el-table-column label="房间号">
        <template v-slot="{ row: { room } }">
          <el-tag type="primary">{{ room }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="楼层" width="95">
        <template v-slot="{ row: { building } }">
          <el-tag type="primary">{{ building }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="档案号" prop="file"></el-table-column>
      <el-table-column label="入住时间" prop="checkin" width="130px"></el-table-column>
      <el-table-column label="合同到期时间" prop="expiration" width="130px"></el-table-column>
      <el-table-column label="电话" prop="tel"></el-table-column>
      <el-table-column label="家属" prop="family">
        <template v-slot="{ row: { families } }">
          <el-tag type="primary" v-for="f in families" :key="f.value">{{ f.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="护工" prop="support" width="130px">
        <template v-slot="{ row: { support } }">
          <el-tag type="primary" v-for="s in support" :key="s.value">{{ s }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="健康管家" prop="butler" width="130px">
        <template v-slot="{ row: { butler } }">
          <el-tag type="primary" v-for="b in butler" :key="b.value">{{ b }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo"></el-table-column>
      <el-table-column label="操作" width="135px" fixed="right">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openCheckinSetDrawer(scope.row)">修改</el-button>
<!--          <el-button size="small" type="danger" @click="deleteCheckin(scope.row.uid)">删除</el-button>-->
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

    <!-- 添加入住登记Dialog   -->
    <checkin_add ref="add" @success="onAddSuccess"></checkin_add>

    <!-- 修改入住登记Drawer   -->
    <checkin_set ref="set" @success="onSetSuccess"></checkin_set>

  </page_container>
</template>

<style scoped lang="less">

</style>