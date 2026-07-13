<script setup>
import {ref} from "vue";
import {Plus} from "@element-plus/icons-vue";
import {ElButton, ElMessageBox, ElNotification} from "element-plus";
import {
  deleteButlerRecord,
  deleteEmergencyRecord,
  deleteFamilyRecord, deleteSupportRecord,
  getButlerPaged,
  getEmergencyPaged,
  getFamilyPaged,
  getSupportPaged
} from "@/api/stay/information.js";
import Butler_add from "@/view/stay/dialog/butler_add.vue";
import Emergency_add from "@/view/stay/dialog/emergency_add.vue";
import Family_add from "@/view/stay/dialog/family_add.vue";
import Support_add from "@/view/stay/dialog/support_add.vue";
import Family_set from "@/view/stay/dialog/family_set.vue";
import Emergency_set from "@/view/stay/dialog/emergency_set.vue";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const uid=ref()
const switcher=ref(0)
const currentPageFamily=ref(1)
const totalFamily=ref(0)
const currentPageEmergency=ref(1)
const totalEmergency=ref(0)
const currentPageButler=ref(1)
const totalButler=ref(0)
const currentPageSupport=ref(1)
const totalSupport=ref(0)
const family=ref([])
const emergency=ref([])
const butler=ref([])
const support=ref([])
const addFamily=ref()
const addEmergency=ref()
const addButler=ref()
const addSupport=ref()
const setFamily=ref()
const setEmergency=ref()

// 打开界面时
const openDrawer=(data)=>{
  uid.value=data.uid
  switcher.value=0
  getTableDataFamily()
  getTableDataEmergency()
  getTableDataButler()
  getTableDataSupport()
  drawerVisible.value=true
}

// 关闭界面时
const closeDrawer=()=>{
  drawerVisible.value=false
}

// 选项卡切换
const tabClick=(tab,event)=>{
  switcher.value=tab.props.name
}

// 分页器：页面切换
const onCurrentChangeFamily=(value)=>{
  getTableDataFamily()
}

// 分页器：页面切换
const onCurrentChangeEmergency=(value)=>{
  getTableDataEmergency()
}

// 分页器：页面切换
const onCurrentChangeButler=(value)=>{
  getTableDataButler()
}

// 分页器：页面切换
const onCurrentChangeSupport=(value)=>{
  getTableDataSupport()
}

// 打开添加家庭成员界面
const openAddFamilyDialog=()=>{
  addFamily.value.openDialog(uid.value)
}

// 打开添加紧急联络人界面
const openAddEmergencyDialog=()=>{
  addEmergency.value.openDialog(uid.value)
}

// 打开添加健康管家界面
const openAddButlerDialog=()=>{
  addButler.value.openDialog(uid.value)
}

// 打开添加护工界面
const openAddSupportDialog=()=>{
  addSupport.value.openDialog(uid.value)
}

// 打开家庭成员设置界面
const openSetFamilyDialog=(data)=>{
  setFamily.value.openDialog(data)
}

// 打开紧急联络人设置界面
const openSetEmergencyDialog=(data)=>{
  setEmergency.value.openDialog(data)
}

// 删除家庭成员记录
const onAddFamilySuccess=()=>{
  currentPageFamily.value=1
  getTableDataFamily()
}

// 添加紧急联络人成功
const onAddEmergencySuccess=()=>{
  currentPageEmergency.value=1
  getTableDataEmergency()
}

// 添加健康管家成功
const onAddButlerSuccess=()=>{
  currentPageButler.value=1
  getTableDataButler()
}

// 添加护工成功
const onAddSupportSuccess=()=>{
  currentPageSupport.value=1
  getTableDataSupport()
}

// 修改家庭成员成功
const onSetFamilySuccess=()=>{
  currentPageFamily.value=1
  getTableDataFamily()
}

// 修改紧急联络人成功
const onSetEmergencySuccess=()=>{
  currentPageEmergency.value=1
  getTableDataEmergency()
}

// 获取家庭成员表格数据
const getTableDataFamily = async ()=>{
  getFamilyPaged(uid.value,currentPageFamily.value).then(resp=>{
    totalFamily.value=resp.data.recordP.total
    family.value=resp.data.recordP.records
  })
}

// 获取紧急联络人表格数据
const getTableDataEmergency = async ()=>{
  getEmergencyPaged(uid.value,currentPageEmergency.value).then(resp=>{
    totalEmergency.value=resp.data.recordP.total
    emergency.value=resp.data.recordP.records
  })
}

// 获取健康关键表格数据
const getTableDataButler = async ()=>{
  getButlerPaged(uid.value,currentPageButler.value).then(resp=>{
    totalButler.value=resp.data.recordP.total
    butler.value=resp.data.recordP.records
  })
}

// 获取护工表格数据
const getTableDataSupport = async ()=>{
  getSupportPaged(uid.value,currentPageSupport.value).then(resp=>{
    totalSupport.value=resp.data.recordP.total
    support.value=resp.data.recordP.records
  })
}

// 删除家庭成员记录
const deleteFamily=async (id)=>{
  await ElMessageBox.confirm('您确定要删除该家属信息记录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  let family={fid:id}
  deleteFamilyRecord(family).then(resp=>{
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
    getTableDataFamily()
  })
}

// 删除紧急联络人
const deleteEmergency=async (id)=>{
  await ElMessageBox.confirm('您确定要删除该紧急联系人记录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  let emergency={eid:id}
  deleteEmergencyRecord(emergency).then(resp=>{
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
    getTableDataEmergency()
  })
}

// 删除健康管家记录
const deleteButler=async (id)=>{
  await ElMessageBox.confirm('您确定要删除该健康管家吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  let butler={uid:uid.value,sid:id}
  deleteButlerRecord(butler).then(resp=>{
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
    getTableDataButler()
  })
}

// 删除护工记录
const deleteSupport=async (id)=>{
  await ElMessageBox.confirm('您确定要删除该护工记录吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  let support={uid:uid.value,sid:id}
  deleteSupportRecord(support).then(resp=>{
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
    getTableDataSupport()
  })
}

// 暴露给父组件方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="详细设置" v-model="drawerVisible" size="40%" @close="closeDrawer">

    <!--表单切换栏-->
    <el-row>
      <el-col :span="25">
        <el-tabs class="demo-tabs" type="card" v-model="switcher" @tab-click="tabClick">
          <el-tab-pane label="家属信息" :name="0"></el-tab-pane>
          <el-tab-pane label="紧急联络人" :name="1"></el-tab-pane>
          <el-tab-pane label="健康管家" :name="2"></el-tab-pane>
          <el-tab-pane label="护工" :name="3"></el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>

    <!-- 家属信息  -->
    <div v-if="switcher===0">

      <!--添加按钮-->
      <el-row style="margin-top: 15px">
        <el-col :span="1">
          <el-button type="primary" :icon="Plus" @click="openAddFamilyDialog">添加</el-button>
        </el-col>
      </el-row>
      <el-divider/>

      <!--表格数据-->
      <el-table
          :data="family"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="性别" prop="gander"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="与老人关系" prop="relation"></el-table-column>
        <el-table-column label="身份证号" prop="id"></el-table-column>
        <el-table-column label="住址" prop="address"></el-table-column>
        <el-table-column label="电话" prop="tel"></el-table-column>
        <el-table-column label="操作" fixed="right" width="140">
          <template #default="scope">
            <el-button size="small" type="primary" @click="openSetFamilyDialog(scope.row)">修改</el-button>
            <el-button size="small" type="danger" @click="deleteFamily(scope.row.fid)">删除</el-button>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="空空如也" />
        </template>
      </el-table>

      <!-- 分页 -->
      <el-pagination
          v-model:current-page="currentPageFamily"
          :page-size="5"
          :small="true"
          layout="prev, pager, next"
          :total="totalFamily"
          @current-change="onCurrentChangeFamily"
          style="margin-top: 20px; justify-content: flex-end"
      />

    </div>

    <!-- 紧急联络人 -->
    <div v-if="switcher===1">

      <el-row style="margin-top: 15px">
        <el-col :span="1">
          <el-button type="primary" :icon="Plus" @click="openAddEmergencyDialog">添加</el-button>
        </el-col>
      </el-row>
      <el-divider/>

      <el-table
          :data="emergency"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="性别" prop="gander"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="与老人关系" prop="relation"></el-table-column>
        <el-table-column label="身份证号" prop="id"></el-table-column>
        <el-table-column label="住址" prop="address"></el-table-column>
        <el-table-column label="电话" prop="tel"></el-table-column>
        <el-table-column label="操作" fixed="right" width="140">
          <template #default="scope">
            <el-button size="small" type="primary" @click="openSetEmergencyDialog(scope.row)">修改</el-button>
            <el-button size="small" type="danger" @click="deleteEmergency(scope.row.eid)">删除</el-button>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="空空如也" />
        </template>
      </el-table>

      <el-pagination
          v-model:current-page="currentPageEmergency"
          :page-size="5"
          :small="true"
          layout="prev, pager, next"
          :total="totalEmergency"
          @current-change="onCurrentChangeEmergency"
          style="margin-top: 20px; justify-content: flex-end"
      />

    </div>

    <!--  健康管家  -->
    <div v-if="switcher===2">

      <el-row style="margin-top: 15px">
        <el-col :span="1">
          <el-button type="primary" :icon="Plus" @click="openAddButlerDialog">添加</el-button>
        </el-col>
      </el-row>
      <el-divider/>

      <el-table
          :data="butler"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="管家姓名" prop="name"></el-table-column>
        <el-table-column label="联系电话" prop="tel"></el-table-column>
        <el-table-column label="备注" prop="memo"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" type="danger" @click="deleteButler(scope.row.sid)">删除</el-button>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="空空如也" />
        </template>
      </el-table>

      <el-pagination
          v-model:current-page="currentPageButler"
          :page-size="5"
          :small="true"
          layout="prev, pager, next"
          :total="totalButler"
          @current-change="onCurrentChangeButler"
          style="margin-top: 20px; justify-content: flex-end"
      />

    </div>

    <!--  护工  -->
    <div v-if="switcher===3">

      <el-row style="margin-top: 15px">
        <el-col :span="1">
          <el-button type="primary" :icon="Plus" @click="openAddSupportDialog">添加</el-button>
        </el-col>
      </el-row>
      <el-divider/>

      <el-table
          :data="support"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="护工姓名" prop="name"></el-table-column>
        <el-table-column label="联系电话" prop="tel"></el-table-column>
        <el-table-column label="备注" prop="memo"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" type="danger" @click="deleteSupport(scope.row.sid)">删除</el-button>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="空空如也" />
        </template>
      </el-table>

      <el-pagination
          v-model:current-page="currentPageSupport"
          :page-size="5"
          :small="true"
          layout="prev, pager, next"
          :total="totalSupport"
          @current-change="onCurrentChangeSupport"
          style="margin-top: 20px; justify-content: flex-end"
      />

    </div>

    <!--  添加健康管家Dialog  -->
    <butler_add ref="addButler" @success="onAddButlerSuccess"></butler_add>

    <!--  添加护工Dialog  -->
    <support_add ref="addSupport" @success="onAddSupportSuccess"></support_add>

    <!--  添加紧急联络人Dialog  -->
    <emergency_add ref="addEmergency" @success="onAddEmergencySuccess"></emergency_add>

    <!--  添加家庭成员Dialog  -->
    <family_add ref="addFamily" @success="onAddFamilySuccess"></family_add>

    <!-- 设置家庭成员Dialog   -->
    <family_set ref="setFamily" @success="onSetFamilySuccess"></family_set>

    <!--  设置紧急联系人Dialog  -->
    <emergency_set ref="setEmergency" @success="onSetEmergencySuccess"></emergency_set>

  </el-drawer>
</template>

<style scoped lang="less">

</style>