<script setup>
import {ref} from "vue";
import {getMealsTagMenue} from "@/api/menue/menue.js";
import {getMealManageSetInfo, setMealManage} from "@/api/meal/manage.js";
import {ElNotification} from "element-plus";

const drawerVisible=ref(false)
const emit = defineEmits(['success'])
const formData=ref({})
const form=ref()
const meals=ref([])
const main=ref([])
const bigMeat=ref([])
const smallMeat=ref([])
const vegetable=ref([])
const fruit=ref([])
const soup=ref([])
const complex=ref([])
const dessert=ref([])
const rules={}
const cid=ref()

// 打开界面时
const openDrawer=(data)=>{
  cid.value=data.uid
  getMenue()
  getRecord()
  drawerVisible.value=true
}

// 打开界面时
const closeDrawer=()=>{
  beforeClose()
  drawerVisible.value=false
}

// 获取膳食管理记录
const getRecord = async ()=>{
  getMealManageSetInfo(cid.value).then(resp=>{
    formData.value.mid=resp.data.setInfo
  })
}

// 关闭界面前
const beforeClose=()=>{
  formData.value={}
  main.value=[]
  bigMeat.value=[]
  smallMeat.value=[]
  vegetable.value=[]
  fruit.value=[]
  soup.value=[]
  complex.value=[]
  dessert.value=[]
  form.value.clearValidate()
}

// 获取膳食标签并处理
const getMenue=async ()=>{
  getMealsTagMenue().then(resp=>{
    meals.value=resp.data.mealMenue
    meals.value.forEach((m)=>{
      if (m.type==='1'||m.type===1){
        main.value.push(m)
      }
      if (m.type==='2'||m.type===2){
        soup.value.push(m)
      }
      if (m.type==='3'||m.type===3){
        bigMeat.value.push(m)
      }
      if (m.type==='4'||m.type===4){
        smallMeat.value.push(m)
      }
      if (m.type==='5'||m.type===5){
        vegetable.value.push(m)
      }
      if (m.type==='6'||m.type===6){
        fruit.value.push(m)
      }
      if (m.type==='7'||m.type===7){
        dessert.value.push(m)
      }
      if (m.type==='8'||m.type===8){
        complex.value.push(m)
      }
    })
  })
}

// 提交表单
const submitSet=async ()=>{
  formData.value={...formData.value,cid:cid.value}
  setMealManage(formData.value).then(resp=>{
    if (resp.code===200){
      ElNotification.success({
        title: '成功',
        message: resp.msg,
        offset: 48
      })
      emit('success')
    }else {
      ElNotification.error({
        title: '失败',
        message: resp.msg,
        offset: 48
      })
    }
    closeDrawer()
  })
}

// 暴露给父组件的方法
defineExpose({
  openDrawer
})
</script>

<template>
  <el-drawer title="设置膳食管理记录" v-model="drawerVisible" size="30%" @close="beforeClose">
    <el-divider />
    <el-form
        ref="form"
        :rules="rules"
        :model="formData"
        label-position="right"
        label-width="auto"
        style="max-width: 350px;margin-top: 15px;margin-left: 15px">
      <el-form-item label="主食">
        <el-checkbox-group v-model="formData.mid">
          <el-checkbox v-for="m in main" :key="m.mid" :label="m.name" :value="m.mid">
            {{ m.name }}
            <el-tag type="success"  v-if="m.tid===2||m.tid===4||m.tid===6||m.tid===8">{{m.tagName}}</el-tag>
            <el-tag type="danger" v-if="m.tid===1||m.tid===3||m.tid===5||m.tid===7">{{m.tagName}}</el-tag>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="辅食">
        <el-checkbox-group v-model="formData.mid">
          <el-checkbox v-for="s in soup" :key="s.mid" :label="s.name" :value="s.mid">
            {{ s.name }}
            <el-tag type="success"  v-if="s.tid===2||s.tid===4||s.tid===6||s.tid===8">{{s.tagName}}</el-tag>
            <el-tag type="danger" v-if="s.tid===1||s.tid===3||s.tid===5||s.tid===7">{{s.tagName}}</el-tag>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="大荤">
        <el-checkbox-group v-model="formData.mid">
          <el-checkbox v-for="b in bigMeat" :key="b.mid" :label="b.name" :value="b.mid">
            {{ b.name }}
            <el-tag type="success"  v-if="b.tid===2||b.tid===4||b.tid===6||b.tid===8">{{b.tagName}}</el-tag>
            <el-tag type="danger" v-if="b.tid===1||b.tid===3||b.tid===5||b.tid===7">{{b.tagName}}</el-tag>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="小荤">
        <el-checkbox-group v-model="formData.mid">
          <el-checkbox v-for="s in smallMeat" :key="s.mid" :label="s.name" :value="s.mid">
            {{ s.name }}
            <el-tag type="success"  v-if="s.tid===2||s.tid===4||s.tid===6||s.tid===8">{{s.tagName}}</el-tag>
            <el-tag type="danger" v-if="s.tid===1||s.tid===3||s.tid===5||s.tid===7">{{s.tagName}}</el-tag>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="素菜">
        <el-checkbox-group v-model="formData.mid">
          <el-checkbox v-for="v in vegetable" :key="v.mid" :label="v.name" :value="v.mid">
            {{ v.name }}
            <el-tag type="success"  v-if="v.tid===2||v.tid===4||v.tid===6||v.tid===8">{{v.tagName}}</el-tag>
            <el-tag type="danger" v-if="v.tid===1||v.tid===3||v.tid===5||v.tid===7">{{v.tagName}}</el-tag>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="水果">
        <el-checkbox-group v-model="formData.mid">
          <el-checkbox v-for="f in fruit" :key="f.mid" :label="f.name" :value="f.mid">
            {{ f.name }}
            <el-tag type="success"  v-if="f.tid===2||f.tid===4||f.tid===6||f.tid===8">{{f.tagName}}</el-tag>
            <el-tag type="danger" v-if="f.tid===1||f.tid===3||f.tid===5||f.tid===7">{{f.tagName}}</el-tag>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="甜点">
        <el-checkbox-group v-model="formData.mid">
          <el-checkbox v-for="d in dessert" :key="d.mid" :label="d.name" :value="d.mid">
            {{ d.name }}
            <el-tag type="success"  v-if="d.tid===2||d.tid===4||d.tid===6||d.tid===8">{{d.tagName}}</el-tag>
            <el-tag type="danger" v-if="d.tid===1||d.tid===3||d.tid===5||d.tid===7">{{d.tagName}}</el-tag>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="套餐">
        <el-checkbox-group v-model="formData.mid">
          <el-checkbox v-for="c in complex" :key="c.mid" :label="c.name" :value="c.mid">
            {{ c.name }}
            <el-tag type="success"  v-if="c.tid===2||c.tid===4||c.tid===6||c.tid===8">{{c.tagName}}</el-tag>
            <el-tag type="danger" v-if="c.tid===1||c.tid===3||c.tid===5||c.tid===7">{{c.tagName}}</el-tag>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-divider />
      <el-row style="margin-top: 5px">
        <el-col :span="10" :offset="20">
          <el-button type="primary" @click="submitSet">确定</el-button>
          <el-button @click="closeDrawer">取消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="less">

</style>