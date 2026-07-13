import request from "@/api/request.js";

export const getNurseLevelPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/nurse/nurse_level_paged',{ keyword,pageNum,pageSize })
}

export const addNurseLevel=(level)=>{
    return request.post('/nurse/add_nurse_level',level)
}

export const changeNurseLevel=(level)=>{
    return request.put('/nurse/change_nurse_level',level)
}

export const deleteNurseLevel=(level)=>{
    return request.put('/nurse/delete_nurse_level',level)
}

export const getNurseContentPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/nurse/nurse_content_paged',{ keyword,pageNum,pageSize })
}

export const addNewNurseContent=(content)=>{
    return request.post('/nurse/add_nurse_content',content)
}

export const changeNurseContent=(content)=>{
    return request.put('/nurse/change_nurse_content',content)
}

export const deleteNurseContent=(content)=>{
    return request.put('/nurse/delete_nurse_content',content)
}

export const getNurseContentSetPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/nurse/nurse_content_set_paged',{ keyword,pageNum,pageSize })
}

export const addNewNurseContentSet=(levelContent)=>{
    return request.post('/nurse/add_nurse_content_set',levelContent)
}

export const deleteNurseContentSet=(levelContent)=>{
    return request.put('/nurse/delete_nurse_content_set',levelContent)
}

export const changeNurseContentSet=(levelContent)=>{
    return request.put('/nurse/change_nurse_content_set',levelContent)
}

export const getCustomerRecord=(keyword,pageNum,pageSize)=>{
    return request.get('/nurse/nurse_record_customer_paged',{ keyword,pageNum,pageSize })
}

export const getNurseRecordPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/nurse/nurse_record_paged',{ keyword,pageNum,pageSize })
}

export const addTempNurseRecord=(nurseRecord)=>{
    return request.post('/nurse/add_nurse_temp_record',nurseRecord)
}

export const addGroupNurseRecord=(nurseRecord)=>{
    return request.post('/nurse/add_group_nurse_record',nurseRecord)
}

export const getUserBuyServicePaged=(keyword,pageNum,pageSize)=>{
    return request.get('/nurse/customer_buy_paged',{ keyword,pageNum,pageSize })
}

export const deleteCustomerBuyService=(service)=>{
    return request.del('/nurse/delete_customer_buy_service',service)
}

export const changeCustomerBuyService=(service)=>{
    return request.put('/nurse/change_customer_buy_service',service)
}