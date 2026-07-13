<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Refresh, Search} from "@element-plus/icons-vue";
import {onActivated, onDeactivated, ref} from "vue";
import {useStore} from "@/stores/temp/stay/information";
import router from "@/router/index.js";
import {getInformationPaged} from "@/api/stay/information.js";
import {getCheckinPaged} from "@/api/stay/checkin.js";
import {ElNotification} from "element-plus";
import Information_set from "@/view/stay/drawer/information_set.vue";

const currentPage=ref(1)
const pageSize=ref(5)
const total=ref(0)
const keyword=ref('')
const data=ref([])
const set=ref()

// 分页器：页面内容大小切换
const onSizeChange=(value)=>{
  getTableData()
}

// 分页器：页面切换
const onCurrentChange=(value)=>{
  getTableData()
}

// 同步数据
const synchronous=()=>{
  getTableData()
  ElNotification.success({
    title: '成功',
    message: '已同步数据',
    offset: 48
  })
}

// 详细设置成功
const onSetSuccess=()=>{
  currentPage.value=1
  getTableData()
}

// 打开详情设置对话框
const openSetDrawer=(data)=>{
  set.value.openDrawer(data)
}

// 获取表格数据
const getTableData = async () => {
  getInformationPaged (keyword.value,currentPage.value,pageSize.value).then(resp=>{
    total.value=resp.data.recordP.total
    pageSize.value=resp.data.recordP.size
    data.value=resp.data.recordP.records
  })
}

// 生命周期钩子

onActivated(()=>{
  getTableData()
})

// 生命周期钩子
onDeactivated(()=>{
  data.value=[]
})
</script>

<template>
  <page_container title="客户信息">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/stay'}">住宿变更</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/stay/information' }">客户信息</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">客户信息</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和添加按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input placeholder="客户姓名" clearable v-model="keyword">
              <template #append>
                <el-button :icon="Search" @click="getTableData"/>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" :icon="Refresh" @click="synchronous">同步信息</el-button>
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
        :show-overflow-tooltip="true"
        table-layout="fixed">
      <el-table-column label="序号" type="index" width="60px"></el-table-column>
      <el-table-column label="姓名" prop="name"></el-table-column>
      <el-table-column label="性别" prop="gander"></el-table-column>
      <el-table-column label="年龄" prop="age"></el-table-column>
      <el-table-column label="档案号" prop="file" width="150px"></el-table-column>
      <el-table-column label="联系方式" prop="tel" width="150px"></el-table-column>
      <el-table-column label="健康管家" prop="butler">
        <template v-slot="{ row: { butler } }">
          <el-tag type="primary" v-for="b in butler" :key="b.value">{{ b }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="护工" prop="support">
        <template v-slot="{ row: { support } }">
          <el-tag type="primary" v-for="s in support" :key="s.value">{{ s }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="家属" prop="family">
        <template v-slot="{ row: { families } }">
          <el-tag type="primary" v-for="f in families" :key="f.value">{{ f.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="家属联系电话" prop="family" width="120px">
        <template v-slot="{ row: { families } }">
          <span v-for="f in families" :key="f.value">{{f.tel}}</span>
        </template>
      </el-table-column>
      <el-table-column label="楼房" width="95px">
        <template v-slot="{ row: { buildingObj } }">
          <el-tag type="primary">{{buildingObj.name}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="房间号">
        <template v-slot="{ row: { buildingObj } }">
          <el-tag type="primary">{{buildingObj.roomName}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="床号">
        <template v-slot="{ row: { buildingObj } }">
          <el-tag type="primary">{{buildingObj.bedName}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="入住时间" prop="checkin" width="120px"></el-table-column>
      <el-table-column label="合同到期时间" prop="expiration" width="120px"></el-table-column>
      <el-table-column label="紧急联系人">
        <template v-slot="{ row: { emergencies } }">
          <el-tag type="primary" v-for="e in emergencies" :key="e.value">{{ e.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="联系人电话" width="120px">
        <template v-slot="{ row: { emergencies } }">
          <span v-for="e in emergencies" :key="e.value">{{ e.tel }}</span>
        </template>
      </el-table-column>
      <el-table-column label="健康档案" fixed="right">
        <template #default="scope">
          <el-button size="small" type="success" @click="router.push({path:'/health',query:{ uid:scope.row.uid }})">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column label="护理记录" fixed="right">
        <template #default="scope">
          <el-button size="small" type="success" @click="router.push({path:'/nurse/record',query:{name:scope.row.name}})">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template #default="scope">
          <el-button size="small" type="warning" @click="openSetDrawer(scope.row)">详情</el-button>
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

    <!-- 客户信息详情Drawer   -->
    <information_set ref="set" @success="onSetSuccess"></information_set>

  </page_container>
</template>

<style scoped lang="less">

</style>