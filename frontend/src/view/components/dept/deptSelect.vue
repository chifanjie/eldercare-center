<template>
    <el-select v-model="value" 
    class="m-2" 
    placeholder="选择员工部门"
    @change="valuechange"
    value-key="deptno"
    >
    <!---->
      <el-option
       v-for="item in options" 
       :key="item.deptno" 
       :label="item.dname" 
       :value="item.deptno"
        >
  
        <div
          style="
            display: flex;
            justify-content: space-between;
          "
        >
          <span>{{ item.dname }}</span>
          <el-link
            href="#"
            target="_blank"
            >default</el-link
          >
        </div>
        </el-option>
    </el-select>
  </template>
  <script setup>
  import { ref } from 'vue'
  import $axios from '../../utils/axios'
  const value = ref('')
  //在子组件中定义一个事件 数组 emits中可以有多个
  const emits=defineEmits(['selectchanged','selectchanged1'])
  const options = ref([
    {
      deptno: 10,
      dname: 'aaa'
    },
    {
      deptno: 20,
      dname: 'bbb'
    },
    {
      deptno: 30,
      dname: 'ccc'
    }
  ])
  
  //在组件中定义属性
  //父向子传值，defineProps,props.initvalue收到父组件传过来的数据
  //props中可以定义好多
  //自定义属性只可读不可用于双向绑定
  const props=defineProps({
      initvalue:{
          type:Number,
          required:true,
          default:20
      },
      initvalue2:{
          type:Number,
          required:true,
          default:20
      }
  })
  //D同步，读取父组件传递的数据给value
  value.value=props.initvalue
  value.value=props.initvalue2
  let loaddata=async ()=>{
      const path = '/dept/f/all'
      const{data:res}=await $axios.get(path)
      if(res.code===200){
          options.value=res.result
      }
      (void 0);
  }
  loaddata()//让select显示数据
  //获得最新的选择数据
  
  //获得最新的选择数据
  let valuechange=(val)=>{
  //子组件向父组件传值，依赖组件的自定义事件完成
  //子组件发生一个事件 冒泡事件，向父组件传递
  emits('selectchanged',val)//emits(op1是事件名称，op2事件携带的数据)
  // (void 0);
  
  emits('selectchanged1',val)
  
  }
  //传递 
  //1.获得最新数据 绑定change，改变的时候获取值
  //2.传递最新数据 先定义一个事件，然后发生一个事件将值传到父组件中
  //3.接收最新数据 在父组件中进行接收，通过@方法定义接收函数接收子组件中的值
  
  </script>
  