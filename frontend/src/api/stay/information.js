import request from "@/api/request.js";

export const getInformationPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/customer/customer_info_record_paged',{ keyword,pageNum,pageSize })
}

export const getFamilyPaged=(uid,pageNum)=>{
    return request.get('/customer/family_paged',{ uid,pageNum })
}

export const getEmergencyPaged=(uid,pageNum)=>{
    return request.get('/customer/emergency_paged',{ uid,pageNum })
}

export const getButlerPaged=(uid,pageNum)=>{
    return request.get('/customer/butler_paged',{ uid,pageNum })
}

export const getSupportPaged=(uid,pageNum)=>{
    return request.get('/customer/support_paged',{ uid,pageNum })
}

export const addFamilyRecord=(family)=>{
    return request.post('/customer/add_family',family)
}

export const addEmergencyRecord=(emergency)=>{
    return request.post('/customer/add_emergency',emergency)
}

export const addButlerRecord=(user)=>{
    return request.post('/customer/add_butler',user)
}

export const addSupportRecord=(user)=>{
    return request.post('/customer/add_support',user)
}

export const changeFamilyRecord=(family)=>{
    return request.put('/customer/change_family',family)
}

export const changeEmergencyRecord=(emergency)=>{
    return request.put('/customer/change_emergency',emergency)
}

export const deleteFamilyRecord=(family)=>{
    return request.del('/customer/delete_family',family)
}

export const deleteEmergencyRecord=(emergency)=>{
    return request.del('/customer/delete_emergency',emergency)
}

export const deleteButlerRecord=(user)=>{
    return request.del('/customer/delete_butler',user)
}

export const deleteSupportRecord=(user)=>{
    return request.del('/customer/delete_support',user)
}