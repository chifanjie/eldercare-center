<template>
  <div style="margin: 30px" />
  <el-form
    :label-position="labelPosition"
    label-width="80px"
    :model="formLabelAlign"
    style="max-width: 260px"
    ref ="ruleFormRef"
  >
    <div class="title">
      <h3>体检客户查询</h3>
    </div>
    <el-form-item label="手机号码" >
      <el-input v-model="formLabelAlign.userid" />
    </el-form-item>
    <el-form-item label="姓名">
      <el-input v-model="formLabelAlign.realname" />
    </el-form-item>
    <el-form-item label="性别">
    <el-radio-group v-model="formLabelAlign.sex">
    <el-radio label="1">男</el-radio>
    <el-radio label="2">女</el-radio>
    </el-radio-group>
    </el-form-item>
    <el-form-item label="套餐类型">
      <el-select v-model="formLabelAlign.smid" class="m-2" placeholder="套餐类型"  style="max-width: 260px">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
    </el-form-item>
    <el-form-item label="体检日期">
      
      <el-date-picker
        v-model="formLabelAlign.orderDate"
        type="date"
        placeholder="体检日期"
        style="max-width:400px"
        value-format="YYYY-MM-DD"
      />
    </el-form-item>
    <el-form-item label="是否归档">

    <el-radio-group v-model="formLabelAlign.state">
      <el-radio label="1" border>未归档</el-radio>
      <el-radio label="2" border>已归档</el-radio>
    </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)"
        >查询</el-button
      >
      <el-button @click="resetForm(ruleFormRef)">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { Setting} from '@element-plus/icons-vue'
import {ref} from 'vue'

import {
  Document,
  Menu as IconMenu,
  Location
  
} from '@element-plus/icons-vue'
const ruleFormRef= ref()
const formLabelAlign = ref({
  userid: '',
  realname: '',
  orderDate: '',
  state:0,
  smid:'',
  sex:0
})
const options = [
  {
    value: 1,
    label: '套餐1',
  },
  {
    value: 2,
    label: '套餐2',
  },
  {
    value: 3,
    label: '套餐3',
  },
  {
    value: 4,
    label: '套餐4',
  }
]
const submitForm = () => {
    (void 0);
}

const resetForm = (formLabelAlign) => {
 
}

</script>

<style lang="scss" scoped>

.title{

  
  align-items: center;
  margin-bottom: 30px;
  text-align: center;
  h3{
    font-size: 20px;
    font-weight: 550;
    font-family: '微软雅黑', Courier, monospace;
  }
}
.demo-date-picker {
  display: flex;
 
 
  padding: 0;
  flex-wrap: wrap;
}

.demo-date-picker .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}

.demo-date-picker .block:last-child {
  border-right: none;
}



</style>
