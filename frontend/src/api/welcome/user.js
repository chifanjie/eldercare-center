import request from "@/api/request.js";

export const userRegister = (welcome) =>{
    return request.post('/welcome/register',welcome)
}

export const userLogin = (welcome) =>{
    return request.post('/welcome/login',welcome)
}

export const isUserExist = (welcome)=>{
    return request.post('/welcome/count_exist',welcome)
}

export const changePassword = (welcome)=>{
    return request.put('/welcome/forget',welcome)
}

export const userLogout=()=>{
    return request.get('/user/logout')
}

export const getOnlineUserInfo=()=>{
    return request.get('/user/login_info')
}

export const getUserInfo=(sid)=>{
    return request.get('/user/user_record_info',{ sid })
}

export const changeUserInfo=(user)=>{
    return request.put('/user/change_user_record_info',user)
}