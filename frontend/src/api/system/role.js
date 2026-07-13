import request from "@/api/request.js";

export const submitAddRole = (role) =>{
    return request.post('/user/add_role',role)
}

export const getRolePaged = (keyword,pageNum,pageSize) =>{
    return request.get('/user/role_paged',{ keyword,pageNum,pageSize })
}

export const deleteRole = (role) =>{
    return request.put('/user/delete_role',role)
}

export const getRoleSetFormData = (rid)=>{
    return request.get('/user/role_form_data',{rid})
}

export const changeRole = (role) =>{
    return request.put('/user/role_change',role)
}