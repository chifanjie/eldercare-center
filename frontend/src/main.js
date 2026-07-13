import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
import router from "./router/index"
import pinia from './stores'
import vuetyped from 'vue3typed'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import naive from 'naive-ui'
import App from './App.vue'

const app=createApp(App)
app.use(router)
app.use(pinia)
app.use(ElementPlus,{locale:zhCn})
app.use(naive)
app.use(vuetyped)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')