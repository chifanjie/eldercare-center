<template>
  <page_container title="首页" style="background:rgb(249,249,249)">
    <!-- 骨架屏加载   -->
    <div class="box">
      <div class="content">

        <div class="message" id='message-box'>
          <div v-for="(msg, index) in msgList" :key="index" :class="{
            'user': msg.role === 'user',
            'assistant': msg.role === 'assistant'
          }">
            <div>
              <div>
                <img class='role-img' :src="userImg" v-if="msg.role === 'user'" />
              </div>
              <div class='imgbox' v-if="msg.role === 'assistant'">
                <img class='role-img' :src="aiImg" />
                <div class='name'>智能颐养客服</div>
              </div>
            </div>
            <div v-highlight v-html='marked(msg.content)'></div>
          </div>
        </div>

        <!-- 默认问题部分 -->
        <div class="questions" >
          <h3>常问问题如下:</h3>
          <h4>点击可以直接提问哦o(*￣▽￣*)ブ）</h4>
          <ul>
            <li v-for="question in questions" :key="question.id" @click="handleQuestionClick(question)">
              {{ question.content }}
            </li>
          </ul>
        </div>


        <div id="app" class="footer">
          <textarea placeholder="给“智能颐养客服”发送消息" class="text" v-model="msgValue2"></textarea>
          <button style="border-radius: 10px" class="btn" @click="submitMsg">发送</button>
        </div>
      </div>
    </div>
  </page_container>
</template>

<script setup>
import userImg from "@/assets/user.png"
import aiImg from "@/assets/titile/logo.svg"
import { nextTick, onMounted, ref } from 'vue'
import TTSRecorder from "@/stores/TTSRecorder.js"
import { userMsgStore } from '@/stores/msgStore.js'
const msgStore = userMsgStore()
const msgValue2 = ref("")
let ttsRecorder = new TTSRecorder()
const msgList = ref([])
let msgDom = ref(null)
import { marked } from 'marked'
import Page_container from "@/view/components/page_container.vue";

// 初始化默认问题数据
const questions = ref([
  { id: 1, content: "如何保持健康的饮食习惯？", index: 1 },
  { id: 2, content: "如何管理压力？", index: 2 },
  { id: 3, content: "如何保持良好的睡眠习惯？", index: 3 },
  { id: 4, content: "如何保持身体活动？", index: 4 },
  { id: 5, content: "如何保持心理健康？", index: 5 }
]);

onMounted(() => {
  msgDom.value = document.getElementById("message-box")
  msgList.value = msgStore.list
  scroll()

  // 添加机器人默认消息
  msgStore.aiAddMsg(`
    您好，欢迎使用! 请问您需要什么帮助？
  `, 2)
  scroll()
})

// 滚动到最底部
const scroll = () => {
  nextTick(() => {
    msgDom.value.scrollTop = msgDom.value.scrollHeight
  })
}

// 发送消息
const submitMsg = async () => {
  msgStore.userAddMsg(msgValue2.value)
  msgValue2.value = ""
  // 开始提问
  ttsRecorder.start(msgStore, msgDom)
  scroll()
}

// 处理问题点击
const handleQuestionClick = (question) => {
  msgValue2.value = question.content
  submitMsg()
}
</script>

<style scoped lang="less">
.container {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
}
h5 {
  margin: 10px 0;
  font-size: 1.2em;
  color: #666;
}
h2 {
  margin: 10px 0;
  font-size: 1.5em;
  color: #333;
}
.box {
  border-radius: 10px;
  width: 100%;
  height: 500px;
  background-color: #fafafa;
  position: relative;
  padding: 20px;
}
.content{
  border-radius: 10px;
  height: 100%;
  position: relative;

  .message{
    font-family: 宋体, SimSun;
    line-height: 1.2;
    text-align: justify;
    font-size: 16px;
    margin: 0;
    padding: 10px;
    word-wrap: break-word;
    position: absolute;
    top: 0;
    left: 20%;
    right: 20%;
    bottom: 120px;
    display: flex;
    overflow: auto;
    flex-direction: column;
    .user{
      background-color: #ebf7f8;
      padding: 15px;
      box-sizing: border-box;
      display: flex;
      flex-direction: column;
      align-items: flex-end;
      border-bottom: 1px solid #dfdfdf;
    }
    .assistant{
      background-color: #f7f7f7;
      padding: 15px;
      box-sizing: border-box;
      border-bottom: 1px solid #dfdfdf;
    }
  }

  .footer{
    position: absolute;
    bottom: 15px;
    left: 20%;
    right: 20%;
    display: flex;
    align-items: flex-end;
    gap: 15px;
    .text{
      width: 100%;
    }
    .btn{
      width: 100px;
      height: 40px;
      background-color: #1a60ea;
      color: white;
      border: none;
    }
  }

  @media screen and (max-width: 768px) {
    .message,.footer {
      left: 0;
      right: 0;
    }
    .message{
      bottom: 100px;
    }
    .footer{
      bottom: 10px;
    }
  }
}

.questions {
  font-family: 宋体, SimSun;
  padding: 20px;

}
.questions ul {
  list-style: none;
  padding: 0;
}
.questions li {
  cursor: pointer;
  padding: 10px;
  margin-bottom: 5px; /* 增加底部间距 */
  border-radius: 5px;
}
.questions li:hover {
  color: blue; /* 鼠标悬停时字体颜色变蓝 */
}

.imgbox{
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  .name{
    font-size: 15px;
    color: #0200fd;
    font-weight: 400;
  }
}

.role-img{
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
}

.text {
  border-radius: 10px;
  resize: none;
  height: 35px;
  width: 300px; /* 你可以根据需要调整宽度 */
  text-align: left; /* 调整输入的文本的位置 */
  font-size: 15px; /* 调整输入的文字大小 */
  line-height: 35px; /* 使输入的文字垂直居中 */
}

.text::placeholder {
  font-size: 15px; /* 调整占位符文字的大小 */
  text-align: center; /* 使占位符文字居中 */
  line-height: 35px; /* 使占位符文字垂直居中 */
}

</style>
