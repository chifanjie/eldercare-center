<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Edit,Delete} from "@element-plus/icons-vue";
import {onActivated, ref} from "vue";
import router from "@/router/index.js";
import {deleteMealRecord, getMealRecordPage} from "@/api/meal/record.js";
import {ElMessageBox, ElNotification} from "element-plus";
import Record_add from "@/view/meal/dialog/record_add.vue";
import Record_edit from "@/view/meal/dialog/record_edit.vue";
import Record_set from "@/view/meal/dialog/record_set.vue";

const switcher=ref('1')
const data=ref([])
const morning=ref([])
const noon=ref([])
const afternoon=ref([])
const add=ref()
const edit=ref()
const set=ref()

// 选项卡切换
const tabClick=(tab,event)=>{
  switcher.value=tab.props.name
  getTableData()
}

// 打开添加Dialog
const openAddDialog=()=>{
  add.value.openDialog()
}

// 打开编辑Dialog
const openEditDialog=(row)=>{
  edit.value.openDialog(row)
}

// 打开设置Dialog
const openSetDialog=()=>{
  set.value.openDialog()
}

// 添加成功时
const onAddSuccess=()=>{
  getTableData()
}

// 编辑成功时
const onEditSuccess=()=>{
  getTableData()
}

// 设置成功时
const onSetSuccess=()=>{
  getTableData()
}

// 获取显示数据
const getTableData = async () => {
  getMealRecordPage(switcher.value).then(resp=>{
    morning.value=[]
    noon.value=[]
    afternoon.value=[]
    data.value=resp.data.recordP
    data.value.forEach((d)=>{
      if (d.time==='1'||d.time===1){
        morning.value.push(d)
      }
      else if (d.time==='2'||d.time===2){
        noon.value.push(d)
      }
      else if (d.time==='3'||d.time===3){
        afternoon.value.push(d)
      }
    })
  })
}

const deleteRecord = async (mid,week,time)=>{
  await ElMessageBox.confirm('您确定要删除该膳食吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  let meal={mid:mid,week:week,time:time}
  deleteMealRecord(meal).then(resp=>{
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
  })
}

// 生命周期钩子
onActivated(()=>{
  getTableData()
})
</script>

<template>
  <page_container title="膳食日记">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/meals'}">膳食</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/meals/record' }">膳食日记</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">膳食日记</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-button type="primary" :icon="Edit" @click="openAddDialog">添加膳食</el-button>
          <el-button type="primary" :icon="Edit" @click="openSetDialog">设置膳食</el-button>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--表单切换栏-->
    <el-row style="margin-top: 10px">
      <el-col :span="25">
        <el-tabs class="demo-tabs" type="card" v-model="switcher" @tab-click="tabClick">
          <el-tab-pane label="周一" name="1"></el-tab-pane>
          <el-tab-pane label="周二" name="2"></el-tab-pane>
          <el-tab-pane label="周三" name="3"></el-tab-pane>
          <el-tab-pane label="周四" name="4"></el-tab-pane>
          <el-tab-pane label="周五" name="5"></el-tab-pane>
          <el-tab-pane label="周六" name="6"></el-tab-pane>
          <el-tab-pane label="周日" name="7"></el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>

    <!--膳食日记-->
    <div>
      <page_container title="早餐" style="height: 50vh">
        <div class="card">
          <span>早餐</span>
        </div>
        <el-card shadow="hover" class="food_card" v-for="m in morning" :key="m.mid">
          <template #header>
            <div class="card-header">
              <span><el-tag type="danger" v-if="m.tid===1||m.tid===3||m.tid===5||m.tid===7">{{ m.tagName }}</el-tag></span>
              <span><el-tag type="success" v-if="m.tid===2||m.tid===4||m.tid===6||m.tid===8">{{ m.tagName }}</el-tag></span>
              <span style="margin-left: 5px">{{ m.name }}</span>
              <div style="text-align: right">
                <span><el-icon style="cursor: pointer" @click="openEditDialog(m)"><Edit /></el-icon></span>
                <span style="margin-left: 5px;cursor: pointer" @click="deleteRecord(m.mid,m.week,m.time)"><el-icon><Delete /></el-icon></span>
              </div>
            </div>
          </template>
          <img
              :src="m.food"
              style="width: 100%"
              alt="膳食图片"
          />
          <p>类别：{{ m.typeName }}</p>
          <p>价钱：{{ m.price }}元</p>
          <p v-if="m.muslim===0">是否清真：否</p>
          <p v-if="m.muslim===1">是否清真：是</p>
        </el-card>
      </page_container>
      <page_container title="午餐" style="height: 50vh;margin-top: 10px">
        <div class="card">
          <span>午餐</span>
        </div>
        <el-card shadow="hover" class="food_card" v-for="n in noon" :key="n.mid">
          <template #header>
            <div class="card-header">
              <span><el-tag type="danger" v-if="n.tid===1||n.tid===3||n.tid===5||n.tid===7">{{ n.tagName }}</el-tag></span>
              <span><el-tag type="success" v-if="n.tid===2||n.tid===4||n.tid===6||n.tid===8">{{ n.tagName }}</el-tag></span>
              <span style="margin-left: 5px">{{ n.name }}</span>
              <div style="text-align: right">
                <span><el-icon style="cursor: pointer" @click="openEditDialog(n)"><Edit /></el-icon></span>
                <span style="margin-left: 5px;cursor: pointer" @click="deleteRecord(n.mid,n.week,n.time)"><el-icon><Delete /></el-icon></span>
              </div>
            </div>
          </template>
          <img
              :src="n.food"
              style="width: 100%"
              alt="膳食图片"
          />
          <p>类别：{{ n.typeName }}</p>
          <p>价钱：{{ n.price }}元</p>
          <p v-if="n.muslim===0">是否清真：否</p>
          <p v-if="n.muslim===1">是否清真：是</p>
        </el-card>
      </page_container>
      <page_container title="晚餐" style="height: 50vh;margin-top: 10px">
        <div class="card">
          <span>晚餐</span>
        </div>
        <el-card shadow="hover" class="food_card" v-for="a in afternoon" :key="a.mid">
          <template #header>
            <div class="card-header">
              <span><el-tag type="danger" v-if="a.tid===1||a.tid===3||a.tid===5||a.tid===7">{{ a.tagName }}</el-tag></span>
              <span><el-tag type="success" v-if="a.tid===2||a.tid===4||a.tid===6||a.tid===8">{{ a.tagName }}</el-tag></span>
              <span style="margin-left: 5px">{{ a.name }}</span>
              <div style="text-align: right">
                <span><el-icon style="cursor: pointer" @click="openEditDialog(a)"><Edit /></el-icon></span>
                <span style="margin-left: 5px;cursor: pointer" @click="deleteRecord(a.mid,a.week,a.time)"><el-icon><Delete /></el-icon></span>
              </div>
            </div>
          </template>
          <img
              :src="a.food"
              style="width: 100%"
              alt="膳食图片"
          />
          <p>类别：{{ a.typeName }}</p>
          <p>价钱：{{ a.price }}元</p>
          <p v-if="a.muslim===0">是否清真：否</p>
          <p v-if="a.muslim===1">是否清真：是</p>
        </el-card>
      </page_container>
    </div>

    <!-- 添加膳食日记Dialog   -->
    <record_add ref="add" @success="onAddSuccess"></record_add>

    <!-- 修改膳食日记Dialog  -->
    <record_edit ref="edit" @success="onEditSuccess"></record_edit>

    <!-- 设置膳食Dialog   -->
    <record_set ref="set" @success="onSetSuccess"></record_set>

  </page_container>
</template>

<style scoped lang="less">
.card{
  color: #EBEDF0;
  writing-mode: vertical-rl;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 45vh;
  float: left;
  background-color: #409EFF;
  text-align: center
}
.food_card{
  margin-left: 5px;
  float: left;
  width: 180px;
  height: 45vh;
}
</style>