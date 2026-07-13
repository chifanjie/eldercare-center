<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {onActivated, ref} from "vue";
import Record_add from "@/view/stay/dialog/record_add.vue";
import Record_process from "@/view/stay/dialog/record_process.vue";
import Record_set from "@/view/stay/drawer/record_set.vue";
import Record_show from "@/view/stay/drawer/record_show.vue";
import router from "@/router/index.js";
import {deleteEgress, getEgressPaged} from "@/api/stay/egress.js";
import {ElMessageBox, ElNotification} from "element-plus";
import Record_change from "@/view/stay/drawer/record_change.vue";
import {getOnlineUserInfo} from "@/api/welcome/user.js";

const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const keyword=ref('')
const selection=ref([])
const data=ref()
const add=ref()
const process=ref()
const change=ref()
const set=ref()
const show=ref()
const userinfo=ref({})

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 打开添加信息对话框
const openRecordAddDialog=()=>{
  add.value.openDialog()
}

// 打开修改登记记录对话框
const openRecordSetDrawer=(data)=>{
  if ((data.really===''||data.really===null)&&data.statue===1){
    set.value.openDrawer(data)
  }else {
    ElNotification.error({
      title: '失败',
      message: '只有未登记实际回院时间的,且审批通过的记录才能登记实际回院时间',
      offset: 48
    })
  }
}

// 打开信息详情对话框
const openRecordShowDrawer=(data)=>{
  show.value.openDrawer(data)
}

// 打开修改对话框
const openRecordChangeDrawer=(data)=>{
  if (data.statue===1||data.statue===2){
    ElNotification.error({
        title: '失败',
        message: '只有未审批的记录才能被修改',
        offset: 48
    })
  }else {
    change.value.openDrawer(data)
  }
}

// 审批外出登记成功
const onProcessSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 添加外出登记成功
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 修改外出登记成功
const onChangeSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 回院记录成功
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 打开审核对话框
const openRecordProcessDialog=()=>{
  let flag=false
  if (selection.value.length!==0){
    selection.value.forEach((item)=>{
      if (item.statue===1||item.statue===2){
        if (flag===false){
          ElNotification.error({
            title: '失败',
            message: '所选记录中存在已审批记录',
            offset: 48
          })
        }
        flag=true
      }
    })
    if (!flag){
      process.value.openDialog(selection.value)
    }
  }else {
    ElNotification.error({
      title: '失败',
      message: '请选择需要审批的项目',
      offset: 48
    })
  }
}

// 多选数据处理
const handleSelectionChange=(e)=>{
  selection.value=e.map((item)=>item)
}

// 获取登录用户信息
const getUserInfo=async ()=>{
  getOnlineUserInfo().then(resp=>{
    userinfo.value=resp.data.online
  })
}

// 获取表格数据
const getTableData = async () => {
  getEgressPaged (keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 删除外出登记记录
const deleteRecord = async (id)=>{
  let egress={}
  egress.eid=id
  await ElMessageBox.confirm('您确定要删除该外出登记记录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  deleteEgress(egress).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
      getTableData()
    }else {
      if (resp.data.code===500){
        ElNotification.error({
          title: '失败',
          message: resp.msg,
          offset: 48
        })
      }else {
        ElNotification.error({
          title: '失败',
          message: resp.msg,
          offset: 48
        })
        getTableData()
      }
    }
  })
}

// 生命周期钩子
onActivated(()=>{
  getTableData()
  getUserInfo()
})
</script>

<template>
  <page_container title="外出登记">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/stay'}">住宿变更</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/stay/record' }">外出登记</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">外出登记</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input placeholder="客户姓名" v-model="keyword" clearable>
              <template #append>
                <el-button :icon="Search" @click="getTableData" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button
              type="primary"
              :icon="Plus"
              @click="openRecordAddDialog"
              v-if="userinfo.role===1||userinfo.role===3">登记</el-button>
          <el-button
              type="success"
              :icon="Plus"
              @click="openRecordProcessDialog"
              v-if="userinfo.role===1||userinfo.role===25">审批</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--表格数据-->
    <el-table
        :data="data"
        @selection-change="handleSelectionChange"
        style="min-width: 100%; margin-top: 10px"
        :row-style="{ height: '50px' }"
        :header-cell-style="{ 'text-align': 'center' }"
        :cell-style="{ 'text-align': 'center' }"
        table-layout="fixed">
      <el-table-column type="selection" width="55px" fixed="left" v-if="userinfo.role===1||userinfo.role===25" />
      <el-table-column label="序号" type="index" width="60px"></el-table-column>
      <el-table-column label="审批状态">
        <template v-slot="{ row: { statue } }">
          <el-tag type="danger" v-if="statue===0">未审批</el-tag>
          <el-tag type="success" v-if="statue===1">审批通过</el-tag>
          <el-tag type="danger" v-if="statue===2">未通过</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="姓名" prop="name"></el-table-column>
      <el-table-column label="性别" prop="gander"></el-table-column>
      <el-table-column label="年龄" prop="age"></el-table-column>
      <el-table-column label="档案号" prop="file"></el-table-column>
      <el-table-column label="外出事由" prop="reason"></el-table-column>
      <el-table-column label="外出时间" prop="egress" width="103px"></el-table-column>
      <el-table-column label="预计回院时间" prop="predict" width="103px"></el-table-column>
      <el-table-column label="实际回院时间" prop="really" width="103px"></el-table-column>
      <el-table-column label="陪同人" prop="relationName"></el-table-column>
      <el-table-column label="与老人关系" prop="relation"></el-table-column>
      <el-table-column label="陪同人电话" prop="tel"></el-table-column>
      <el-table-column label="审批人" prop="approverName"></el-table-column>
      <el-table-column label="审批时间" prop="approve" width="103px"></el-table-column>
      <el-table-column label="备注" prop="memo" width="80"></el-table-column>
      <el-table-column label="操作" width="300px" fixed="right">
        <template #default="scope">
          <el-button
              size="small"
              type="primary"
              @click="openRecordChangeDrawer(scope.row)"
              v-if="userinfo.role===1||userinfo.role===3">修改</el-button>
          <el-button
              size="small"
              type="danger"
              @click="deleteRecord(scope.row.eid)"
              v-if="userinfo.role===1||userinfo.role===3">删除</el-button>
          <el-button
              size="small"
              type="warning"
              @click="openRecordSetDrawer(scope.row)"
              v-if="userinfo.role===1||userinfo.role===3">登记回院时间</el-button>
          <el-button
              size="small"
              type="success"
              @click="openRecordShowDrawer(scope.row)"
              v-if="userinfo.role===1||userinfo.role===3||userinfo.role===25">查看</el-button>
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

    <!-- 添加外出登记Dialog   -->
    <record_add ref="add" @success="onAddSuccess"></record_add>

    <!-- 审核外出登记Drawer   -->
    <record_process ref="process" @success="onProcessSuccess"></record_process>

    <!-- 修改外出登记Drawer   -->
    <record_change ref="change" @success="onChangeSuccess"></record_change>

    <!-- 登记实际回院时间Drawer   -->
    <record_set ref="set" @success="onSetSuccess"></record_set>

    <!-- 详细信息Drawer   -->
    <record_show ref="show"></record_show>

  </page_container>
</template>

<style scoped lang="less">

</style>