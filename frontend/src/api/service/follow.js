import request from "@/api/request.js";

export const getFollowCustomerPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/service/service_customer_paged',{ keyword,pageNum,pageSize })
}

export const getServicePaged=(keyword,pageNum,pageSize)=>{
    return request.get('/service/service_paged',{ keyword,pageNum,pageSize })
}

export const addServiceFollow=(service)=>{
    return request.post('/service/add_service_purchase',service)
}

export const alertService=(service)=>{
    return request.put('/service/alert_service_purchase',service)
}

export const addServicePurchase=(service)=>{
    return request.put('/service/add_service_record_purchase',service)
}