<script setup>
import Page_container from "@/view/components/page_container.vue";
import {Plus, Search} from "@element-plus/icons-vue";
import {ElButton, ElMessage} from "element-plus";
import {onActivated, ref} from "vue";
import router from "@/router/index.js";
import {getBedDesignRecord} from "@/api/bed/design.js";
import Design_info from "@/view/bed/dialog/design_info.vue";

const currentPage=ref(1)
const pageSize=ref(5)
const total=ref(0)
const keyword=ref('')
const round=ref('瑞慈花园1#')
const record=ref({})
const info=ref()

// 打开床位详情Dialog
const openInfoDialog = (data)=>{
  if (data.uid===null){
    ElMessage.warning("该床位未分配")
  }else {
    info.value.openDialog(data)
  }
}

// 获取床位可视化数据
const getDesign=async ()=>{
  getBedDesignRecord(round.value,keyword.value).then(resp=>{
    record.value=resp.data.record
  })
}

// 生命周期钩子
onActivated(()=>{
  getDesign()
})
</script>

<template>
  <page_container title="床位示意图">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/bed'}">床位管理</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/bed/design' }">床位示意图</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">床位示意图</span>
        </template>
      </el-page-header>
    </el-row>

    <!--搜索栏和按钮-->
    <el-form inline>
      <el-row>
        <el-col :span="8">
          <el-form-item>
            <el-input placeholder="输入房间号或客户姓名" v-model="keyword" clearable>
              <template #append>
                <el-button :icon="Search" @click="getDesign" />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="3">
          <el-form-item label="楼层">
            <el-select v-model="round" style="width: 100px" @change="getDesign">
              <el-option label="一楼" value="瑞慈花园1#" />
              <el-option label="二楼" value="瑞慈花园2#" />
              <el-option label="三楼" value="瑞慈花园3#" />
              <el-option label="四楼" value="瑞慈花园4#" />
              <el-option label="五楼" value="瑞慈花园5#" />
              <el-option label="六楼" value="瑞慈花园6#" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <img src="../../assets/bed/bed_free.svg" style="width: 25px" alt="没人使用"/>
          <span style="margin-left: 5px">空床位</span>
          <img src="../../assets/bed/bed_out.svg" style="width: 25px;margin-left: 15px" alt="外出"/>
          <span style="margin-left: 5px">外出</span>
          <img src="../../assets/bed/bed_exist.svg" style="width: 25px;margin-left: 15px" alt="有人使用"/>
          <span style="margin-left: 5px">有人</span>
        </el-col>
      </el-row>
    </el-form>
    <el-divider />

    <!--  床位数据可视化  -->
    <div>
      <div class="show_box" v-if="record!==null">
        <el-card shadow="hover" class="room" style="background-color: #409EFF">
          <span>卫生间</span>
          <div style="margin-top: 50%;margin-left: 23%">
            <img src="../../assets/bed/human-male-female.svg" width="40px" alt="卫生间">
          </div>
        </el-card>
        <el-card shadow="hover" class="room" v-for="r in record.room" :key="r.value">
          <template #header>
            <span>{{ r.roomName }}</span>
          </template>
          <div class="bed" v-for="b in r.bed" :key="b.value">
            <div class="bed_container" @click="openInfoDialog(b)">
              <img src="../../assets/bed/bed_free.svg" width="30px" style="margin-left: 8px" v-if="b.statue===0" alt="没人使用"/>
              <img src="../../assets/bed/bed_out.svg" width="30px" style="margin-left: 8px" v-if="b.statue===2" alt="外出"/>
              <img src="../../assets/bed/bed_exist.svg" width="30px" style="margin-left: 8px" v-if="b.statue===1" alt="有人使用"/>
              <div style="margin-left: 3px">{{ b.bedName }}</div>
            </div>
          </div>
        </el-card>
        <el-card shadow="hover" class="room" style="background-color: #409EFF">
          <span>楼梯</span>
          <div style="margin-top: 50%;margin-left: 23%">
            <img src="../../assets/bed/stairs.svg" width="40px" alt="楼梯">
          </div>
        </el-card>
      </div>
      <div style="background-color: #409EFF;height: 50px;margin-top: 10px;margin-left: 10px;line-height: 50px" v-if="record!==null">
        <span style="margin-left: 50%;">走廊</span>
      </div>
      <div v-if="record===null">
        <el-empty description="未找到目标"></el-empty>
      </div>
    </div>

    <!-- 床位详细信息Dialog  -->
    <design_info ref="info"></design_info>

  </page_container>
</template>

<style scoped lang="less">
.show_box{
  display: flex;
  justify-content: space-between;
  width: 100%;
}
.room{
  width: 130px;
  height: 30vh;
  margin-left: 10px;
}
.bed_container{
  cursor: pointer;
  margin-left: 20px;
  margin-top: 8px;
}
</style>