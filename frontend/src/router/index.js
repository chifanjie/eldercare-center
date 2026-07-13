import { createRouter, createWebHistory } from "vue-router";
import {ElMessage} from "element-plus";
import {useToken} from "@/stores/index";
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const router = createRouter({
    //history: createWebHashHistory(),
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        { path:'/system',redirect:'/system/user' },
        { path:'/bed',redirect:'/bed/manage' },
        { path:'/meals',redirect:'/meals/manage' },
        { path:'/nurse',redirect:'/nurse/level' },
        { path:'/service',redirect:'/service/follow' },
        { path:'/stay',redirect:'/stay/checkin' },
        { path:'/tijian',redirect:'/tijian/manage' },
        {
            path: '/', redirect:'/home' ,component: () => import('@/view/home/home.vue'),
            children :[
                { path:'/home', component:()=>import('@/view/home/index.vue') },
                { path:'/home/robot', component:()=>import('@/view/home/robot.vue') ,meta:{title:'颐养咨询'}},
                { path:'/bed/manage', component:()=>import('@/view/bed/bed.vue') ,meta:{title:'床位管理'}},
                { path:'/bed/design', component:()=>import('@/view/bed/design.vue') ,meta:{title:'床位示意图'}},
                { path:'/system/user', component:()=>import('@/view/system/user.vue') ,meta:{title:'用户管理'}},
                { path:'/system/role', component:()=>import('@/view/system/role.vue') ,meta:{title:'权限管理'}},
                { path:'/nurse/level', component:()=>import('@/view/nurse/level.vue') ,meta:{title:'护理级别'}},
                { path:'/nurse/content', component:()=>import('@/view/nurse/content.vue') ,meta:{title:'护理内容'}},
                { path:'/nurse/record', component:()=>import('@/view/nurse/record.vue') ,meta:{title:'护理记录'}},
                { path:'/meals/manage', component:()=>import('@/view/meal/manage.vue') ,meta:{title:'膳食管理'}},
                { path:'/meals/record', component:()=>import('@/view/meal/record.vue') ,meta:{title:'膳食日记'}},
                { path:'/meals/statistics', component:()=>import('@/view/meal/statistics.vue') ,meta:{title:'膳食统计'}},
                { path:'/service/follow', component:()=>import('@/view/service/follow.vue') ,meta:{title:'服务关注'}},
                { path:'/service/set', component:()=>import('@/view/service/set.vue') ,meta:{title:'设置服务对象'}},
                { path:'/stay/checkin', component:()=>import('@/view/stay/checkin.vue') ,meta:{title:'入住登记'}},
                { path:'/stay/checkout', component:()=>import('@/view/stay/checkout.vue') ,meta:{title:'退住登记'}},
                { path:'/stay/record', component:()=>import('@/view/stay/record.vue') ,meta:{title:'外出登记'}},
                { path:'/stay/information', component:()=>import('@/view/stay/information.vue') ,meta:{title:'客户信息'}},
                { path:'/health',component:()=>import('@/view/file/health.vue'),meta:{title: '健康档案'}},
                { path:'/tijian/manage',component:()=>import('@/view/tijian/HomeView.vue'),meta:{title: '体检管理'}},
                { path:'/tijian/reportmanage',component:()=>import('@/view/tijian/ReportManage.vue'),meta:{title: '编辑体检报告'}},
                { path:'/tijian/HospitalManage',component:()=>import('@/view/tijian/HospitalManage.vue'),meta:{title: '医院管理'}},
                { path:'/tijian/DoctorManage',component:()=>import('@/view/tijian/DoctorManage.vue'),meta:{title: '医生管理'}},
                { path:'/tijian/HospitalView',component:()=>import('@/view/tijian/HospitalView.vue'),meta:{title: '医院选择'}},
                { path:'/tijian/setmealView',component:()=>import('@/view/tijian/setmealView.vue'),meta:{title: '套餐选择'}},
                { path:'/tijian/timeView',component:()=>import('@/view/tijian/timeView.vue'),meta:{title: '时间选择'}},
                { path:'/tijian/OrderView',component:()=>import('@/view/tijian/OrderView.vue'),meta:{title: '订单确认'}},
                { path:'/tijian/ResultView',component:()=>import('@/view/tijian/ResultView.vue'),meta:{title: '预约成功'}}
                


                
            ]
        },
        { path: '/login', component: ()=>import('@/view/welcome/login.vue') },
        { path: '/register', component: ()=>import('@/view/welcome/register.vue') },
        { path: '/forget', component: ()=>import('@/view/welcome/forget.vue') },

    ]
});

router.beforeEach((to) => {
    NProgress.start()
    const tokenStore=useToken()
    if ((to.path !== '/login' && to.path !== '/register' && to.path !== '/forget') && (tokenStore.token===''||tokenStore.token===null)) {
        ElMessage.error('请先登录!')
        return '/login'
    }else if ((to.path === '/login' || to.path === '/register' || to.path === '/forget') && (tokenStore.token !== '' && tokenStore.token !== null)){
        ElMessage.warning("请先退出当前账户!")
        return '/'
    }
})

router.afterEach((to,from)=>{
    NProgress.done()
})

export default router;

