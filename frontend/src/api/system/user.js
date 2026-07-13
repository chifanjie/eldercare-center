import request from "@/api/request.js";

export const getUserPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/user/userinfo_paged',{ keyword,pageNum,pageSize })
}

export const addUserinfo=(user)=>{
    return request.post('/user/add_userinfo',user)
}

export const deleteUserinfo=(user)=>{
    return request.put('/user/delete_userinfo',user)
}

export const getUserinfoFormData=(id)=>{
    return request.get('/user/userinfo_form_data',{ id })
}

export const changeUserinfo=(user)=>{
    return request.put('/user/userinfo_change',user)
}