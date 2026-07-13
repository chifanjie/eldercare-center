<script setup>
import Page_container from "@/view/components/page_container.vue";
import router from "@/router/index.js";
import {onActivated, ref} from "vue";
import {useRoute} from "vue-router";
import {User} from "@element-plus/icons-vue";
import {ElButton} from "element-plus";
import {getNurseLevelPaged} from "@/api/nurse/nurse.js";
import {getCustomerRecord, getHealthCare} from "@/api/health/health.js";

const uid=ref()
const data=ref()
const route=useRoute()
const switcher=ref('0')
const customer=ref({})

// 选项卡切换
const tabClick=(tab,event)=>{
  switcher.value=tab.props.name
}

// 获取护理记录数据
const getTableData = async () => {
  getHealthCare(uid.value).then(resp=>{
    data.value=resp.data.nurseContents
  })
}

// 获取老人记录数据
const getCustomer = async ()=>{
  getCustomerRecord(uid.value).then(resp=>{
    customer.value=resp.data.customer
    customer.value={...customer.value,buildingName:customer.value.buildingObj.name,roomName:customer.value.buildingObj.roomName,bedName:customer.value.buildingObj.bedName}
  })
}

// 生命周期钩子
onActivated(async ()=>{
  switcher.value='0'
  uid.value=route.query.uid
  await getTableData()
  await getCustomer()
})
</script>

<template>
  <page_container title="健康档案">

    <!--  面包屑导航  -->
    <el-row style="margin-bottom: 30px">
      <el-page-header @back="()=>{router.go(-1)}">
        <template #breadcrumb>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path:'/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path:'/health',query:{ uid:uid }}">健康档案</el-breadcrumb-item>
          </el-breadcrumb>
        </template>
        <template #content>
          <span class="text-large font-600 mr-3">健康档案</span>
        </template>
      </el-page-header>
    </el-row>

    <!--表单切换栏-->
    <el-row>
      <el-col :span="25">
        <el-tabs class="demo-tabs" type="card" v-model="switcher" @tab-click="tabClick">
          <el-tab-pane label="基本信息" name="0"></el-tab-pane>
          <el-tab-pane label="健康档案" name="1"></el-tab-pane>
          <el-tab-pane label="健康指导" name="2"></el-tab-pane>
          <el-tab-pane label="护理档案" name="3"></el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>

    <!--基本信息-->
    <div v-if="switcher==='0'" style="margin-top: 15px">
      <el-row>
        <el-col :span="5" :offset="1">
          <img
              style="margin-top: 20px"
              src="../../assets/background/title.png"
              alt="头像">
        </el-col>
        <el-col :span="17">
          <el-descriptions border direction="horizontal" :column="16" style="margin-top: 15px">
            <el-descriptions-item label="姓名" label-align="center" :span="4">
              {{ customer.name }}
            </el-descriptions-item>
            <el-descriptions-item label="性别" label-align="center" :span="4">
              {{ customer.gander }}
            </el-descriptions-item>
            <el-descriptions-item label="年龄" label-align="center" :span="4">
              {{ customer.age }}
            </el-descriptions-item>
            <el-descriptions-item label="入住时间" label-align="center" :span="4">
              {{ customer.checkin }}
            </el-descriptions-item>
            <el-descriptions-item label="合约过期时间" label-align="center" :span="4">
              {{ customer.expiration }}
            </el-descriptions-item>
            <el-descriptions-item label="联系方式" label-align="center" :span="4">
              {{ customer.tel }}
            </el-descriptions-item>
            <el-descriptions-item label="档案编号" label-align="center" :span="4">
              {{ customer.file }}
            </el-descriptions-item>
            <el-descriptions-item label="身份证号" label-align="center" :span="4">
              {{ customer.id }}
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions border direction="horizontal" :column="8" style="margin-top: 15px">
            <el-descriptions-item label="居住楼房" label-align="center" :span="2">
              {{ customer.buildingName }}
            </el-descriptions-item>
            <el-descriptions-item label="房间号" label-align="center" :span="2">
              {{ customer.roomName }}
            </el-descriptions-item>
            <el-descriptions-item label="床位" label-align="center" :span="4">
              {{ customer.bedName }}
            </el-descriptions-item>
            <el-descriptions-item label="备注" label-align="center" :span="4">
              {{ customer.memo }}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>

    </div>

    <!--健康档案-->
    <div v-if="switcher==='1'">

      <div style="color: #EBEDF0;background-color: #409EFF">
        <span style="margin-left: 15px">生活习惯</span>
      </div>
      <el-row style="margin-top: 15px">
        <el-col :span="5">
          <span>睡眠情况:</span>
        </el-col>
        <el-col :span="15">
          <span>长期的失眠很容易引起老人其它身体及心理上的疾病，老年人失眠怎么治疗是老年人及其家属应该重视的问题。
            老年失眠症不同于中青年的失眠特点，在病因病机方面与精神思想因素关系不大， 不像中青年那样主要由精神负担沉重、
            思虑过度、心血耗伤所致，故其治疗如同于中青年之失眠， 则效果多不满意。其实，老年失眠症是由年老带来的全身和大脑皮质生理变化所导致的，
            治疗应从改善老年人全身和大脑生理衰退状况为主</span>
        </el-col>
        <el-divider/>
      </el-row>
      <el-row>
        <el-col :span="5">
          <span>睡眠情况:</span>
        </el-col>
        <el-col :span="15">
          <span>老年人每日进餐次数、每日餐量、每次餐量，根据老年人的饮食习惯选择食物和烹制方法，
            适当补充蔬菜水果，经常调换口味，促进老年人的食欲。老年人的饮食习惯直接影响着老年人的身体健康。</span>
        </el-col>
        <el-divider/>
      </el-row>
      <el-row>
        <el-col :span="5">
          <span>心理状态:</span>
        </el-col>
        <el-col :span="15">
          <span>认识能力低下： 中老年人身体机能衰退，大脑功能发生改变，中枢神经系统递质的合成和代谢减弱，
            导致感觉能力降低，意识性差，反应迟钝，注意力不集中等。</span>
        </el-col>
        <el-divider/>
      </el-row>

    </div>

    <!--健康指导-->
    <div v-if="switcher==='2'">

      <el-row>
        <el-col :span="5" :offset="1">
          <img
              style="margin-top: 20px;width: 250px"
              src="../../assets/res/appendicitis.jpg"
              alt="头像">
        </el-col>
        <el-col :span="10" :offset="1">
          <div style="margin-top: 30px">
            <span>阑尾炎:</span>
            <div style="margin-top: 5px">阑尾炎是因多种因素而形成的炎性改变，为外科常见病，以青年最为多见，男性多于女性。</div>
            <div>临床上急性阑尾炎较为常见，各年龄段及妊娠期妇女均可发病。慢性阑尾炎较为少见。</div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10" :offset="1">
          <div style="margin-top: 50px">
            <span>心脏病:</span>
            <div style="margin-top: 5px">心脏病是一类比较常见的循环系统疾病。循环系统由心脏、血管和调节血液循环的神经体液组织构成,</div>
            <div>包括上述所有组织器官的疾病，在内科疾病中属于常见病， 其中以心脏病最为多见，能显著地影响患者的劳动力。</div>
          </div>
        </el-col>
        <el-col :span="5" :offset="1">
          <img
              style="margin-top: 20px;width: 250px"
              src="../../assets/res/heart.jpg"
              alt="头像">
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="5" :offset="1">
          <img
              style="margin-top: 20px;width: 250px"
              src="../../assets/res/pressure.jpg"
              alt="头像">
        </el-col>
        <el-col :span="10" :offset="1">
          <div style="margin-top: 50px">
            <span>高血压:</span>
            <div style="margin-top: 5px">高血压（hypertension）是指以体循环动脉血压（收缩压和/或舒张压）增高为主要特征 </div>
            <div>收缩压≥140毫米汞柱，舒张压≥90毫米汞柱），可伴有心、脑、肾等器官的功能或器质性损害的临床综合征。</div>
          </div>
        </el-col>
      </el-row>

    </div>

    <!--护理档案-->
    <div v-if="switcher==='3'">
      <el-table
          :data="data"
          style="min-width: 100%; margin-top: 10px"
          :row-style="{ height: '50px' }"
          :header-cell-style="{ 'text-align': 'center' }"
          :cell-style="{ 'text-align': 'center' }"
          table-layout="fixed">
        <el-table-column label="序号" type="index" width="60px"></el-table-column>
        <el-table-column label="编号" prop="no"></el-table-column>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="描述" prop="description"></el-table-column>
        <el-table-column label="是否增值服务">
          <template v-slot="{ row: { value } }">
            <el-tag type="success" v-if="value === 1">是</el-tag>
            <el-tag type="danger" v-if="value === 0">否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="memo"></el-table-column>
        <template #empty>
          <el-empty description="空空如也" />
        </template>
      </el-table>

    </div>

  </page_container>
</template>

<style scoped lang="less">
</style>