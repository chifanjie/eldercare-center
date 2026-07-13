<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {ElButton, ElMessage, ElMessageBox, ElNotification} from "element-plus";
import {onActivated, ref} from "vue";
import router from "@/router/index.js";
import {getBedRecordPaged, getCustomerBedRecord, submitChangeRecord, submitExchangeRecord} from "@/api/bed/manage.js";
import {deleteCheckinRecord} from "@/api/stay/checkin.js";
import Bea_add from "@/view/bed/dialog/bed_add.vue";
import Bed_set from "@/view/bed/drawer/bed_set.vue";
import {getBuildingMenue, getCustomerMenue} from "@/api/menue/menue.js";

const selectBar=ref('overview')
const currentPage=ref(1)
const pageSize=ref(10)
const total=ref(0)
const data=ref()
const beforeMenue=ref([])
const afterMenue=ref([])
const setMenue=ref([])
const treeSelect = ref([])
const keyword=ref('')
const keywordAfter=ref('')
const keywordBefore=ref('')
const keywordSet=ref('')
const exchange=ref()
const change=ref()
const add=ref()
const set=ref()
const formData=ref({
  before:'',
  after:'',
  beforeData:{},
  afterData:{}
})
const changeFormData=ref({
  set:'',
  setData:{}
})
const exchangeRule={
  before:[{ required: true, message: '请选择需要对调的老人姓名', trigger: 'blur'}],
  after:[{ required: true, message: '请选择对调的老人姓名', trigger: 'blur'}],
}
const changeRule={
  set:[{ required: true, message: '请选择需要调换的老人姓名', trigger: 'blur'}],
  building:[{ required: true, message: '请选择调换后位置', trigger: 'blur'}],
}

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 表格数据查询
const getTableData = async () => {
  getBedRecordPaged(keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 获取调换前客户菜单
const getBeforeMenue = async ()=>{
  getCustomerMenue(keywordBefore.value).then(resp=>{
    beforeMenue.value=resp.data.customerMenue
  })
}

// 获取调换后客户菜单
const getAfterMenue = async ()=>{
  getCustomerMenue(keywordAfter.value).then(resp=>{
    afterMenue.value=resp.data.customerMenue
  })
}

// 获取客户菜单
const getSetMenue = async ()=>{
  getCustomerMenue(keywordSet.value).then(resp=>{
    setMenue.value=resp.data.customerMenue
  })
}

// 删除入住登记
// const deleteCheckin = async (id)=>{
//   await ElMessageBox.confirm('您确定要删除该床位记录吗?', '提示', {
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

// 获取树形菜单（床位）
const getTreeMenue = async ()=>{
  getBuildingMenue().then(resp=>{
    treeSelect.value=resp.data.buildingMenue
  })
}

// 获取调换前的床位对应客户信息
const getBeforeChange = async ()=>{
  getCustomerBedRecord(formData.value.before).then(resp=>{
    formData.value.beforeData=resp.data.records
  })
}

// 获取调换后的床位对应客户信息
const getAfterChange = async ()=>{
  getCustomerBedRecord(formData.value.after).then(resp=>{
    formData.value.afterData=resp.data.records
  })
}

// 获取床位对应的客户信息
const getBedSet = async ()=>{
  getCustomerBedRecord(changeFormData.value.set).then(resp=>{
    changeFormData.value.setData=resp.data.records
  })
}

// 提交对调床位信息
const submitExchange = async ()=>{
  await exchange.value.validate()
  submitExchangeRecord(formData.value).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
    }else {
      ElNotification.error({
        title: '失败',
        message: resp.msg,
        offset: 48
      })
    }
    getTableData()
    getBeforeChange()
    getAfterChange()
  })
}

// 提交床位调整信息
const submitSetChange = async ()=>{
  await change.value.validate()
  submitChangeRecord(changeFormData.value).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
    }else {
      ElNotification.error({
        title: '失败',
        message: resp.msg,
        offset: 48
      })
    }
    getTableData()
    getBedSet()
  })
}

// 打开添加床位信息对话框
const openBedAddDialog=()=>{
  add.value.openDialog()
}

// 打开修改床位信息抽屉
const openBedSetDrawer=(obj)=>{
  set.value.openDrawer(obj)
}

// 添加床位信息成功
const onAddSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 修改床位信息成功
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 生命周期钩子
onActivated(()=>{
  selectBar.value='overview'
  getTreeMenue()
  getTableData()
  getAfterMenue()
  getBeforeMenue()
  getSetMenue()
})
</script>

<template>
  <page_container title="床位管理">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/bed'}">床位管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/bed/manage' }">床位管理</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">床位管理</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="2">
          <el-form-item>
            <el-select v-model="selectBar" style="width: 100px">
              <el-option label="床位总览" value="overview" />
              <el-option label="床位对调" value="exchange" />
              <el-option label="床位调换" value="change" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4" v-show="selectBar==='overview'">
          <el-form-item>
            <el-input v-model="keyword" placeholder="客户姓名" clearable>
              <template #append>
                <el-button :icon="Search" @click="getTableData"/>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="2" v-show="selectBar==='overview'">
          <el-form-item>
            <el-button type="primary" v-show="selectBar==='overview'" :icon="Plus" @click="openBedAddDialog">添加</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--表格数据-->
    <div v-if="selectBar==='overview'">
      <el-table
          :data="data"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="档案编号" prop="file"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="性别" prop="gander"></el-table-column>
        <el-table-column label="床位使用起始日期" prop="checkin"></el-table-column>
        <el-table-column label="床位使用结束日期" prop="expiration"></el-table-column>
        <el-table-column label="床位详细信息">
          <template v-slot="{ row: { bedInfo } }">
            <el-tag type="primary">{{ bedInfo }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" type="primary" @click="openBedSetDrawer(scope.row)">修改</el-button>
<!--            <el-button size="small" type="danger" @click="deleteCheckin(scope.row.uid)">删除</el-button>-->
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
    </div>

    <!--床位对调-->
    <div v-if="selectBar==='exchange'">
      <el-form
          ref="exchange"
          :model="formData"
          :rules="exchangeRule"
          inline
          label-position="right"
          label-width="auto">
        <el-row>
          <el-col :span="8">
            <el-form-item label="老人姓名" prop="before">
              <el-select v-model="formData.before" style="width: 200px" @change="getBeforeChange">
                <template #header>
                  <el-input v-model="keywordBefore" placeholder="输入客户姓名" @keyup="getBeforeMenue" clearable></el-input>
                </template>
                <el-option v-for="m in beforeMenue" :key="m.value" :label="m.label" :value="m.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="老人姓名" prop="after">
              <el-select v-model="formData.after" style="width: 200px" @change="getAfterChange">
                <template #header>
                  <el-input v-model="keywordAfter" placeholder="输入客户姓名" @keyup="getAfterMenue" clearable></el-input>
                </template>
                <el-option v-for="m in afterMenue" :key="m.value" :label="m.label" :value="m.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="楼号" prop="beforeBuilding">
              <el-input v-model="formData.beforeData.name" disabled  ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="楼号" prop="afterBuilding">
              <el-input v-model="formData.afterData.name" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="楼层" prop="beforeFloor">
              <el-input v-model="formData.beforeData.floor" disabled ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="楼层" prop="afterFloor">
              <el-input v-model="formData.afterData.floor" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="房间号" prop="beforeRoom">
              <el-input v-model="formData.beforeData.roomName" disabled ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="房间号" prop="afterRoom">
              <el-input v-model="formData.afterData.roomName" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="床号" prop="beforeBed">
              <el-input v-model="formData.beforeData.bedName" disabled ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="床号" prop="afterBed">
              <el-input v-model="formData.afterData.bedName" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="6">
            <el-form-item>
              <el-button type="primary" @click="submitExchange">确认对调</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!--床位调换-->
    <div v-if="selectBar==='change'">
      <el-form
          :model="changeFormData"
          :rules="changeRule"
          ref="change"
          inline
          label-position="right"
          label-width="auto">
        <el-row>
          <el-col :span="8">
            <el-form-item label="老人姓名" prop="set">
              <el-select v-model="changeFormData.set" style="width: 200px" @change="getBedSet">
                <template #header>
                  <el-input v-model="keywordSet" placeholder="输入客户姓名" @keyup="getSetMenue" clearable></el-input>
                </template>
                <el-option v-for="m in setMenue" :key="m.value" :label="m.label" :value="m.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="楼号" prop="setBuilding">
              <el-input v-model="changeFormData.setData.name" disabled ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="新所属楼房" style="width: 280px" prop="building">
              <el-tree-select
                  :data="treeSelect"
                  v-model="changeFormData.building"
                  :render-after-expand="false"
                  show-checkbox>
              </el-tree-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="楼层" prop="setFloor">
              <el-input v-model="changeFormData.setData.floor" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="房间号" prop="setRoom">
              <el-input v-model="changeFormData.setData.roomName" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="床号" prop="setBed">
              <el-input v-model="changeFormData.setData.bedName" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="6">
            <el-form-item>
              <el-button type="primary" @click="submitSetChange">确认调换</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- 添加床位信息Dialog  -->
    <bea_add ref="add" @success="onAddSuccess"></bea_add>

    <!--  修改床位信息Drawer  -->
    <bed_set ref="set" @success="onSetSuccess"></bed_set>

  </page_container>
</template>

<style scoped lang="less">

</style>