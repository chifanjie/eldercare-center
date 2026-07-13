import request from "@/api/request.js";

export const getServiceSetPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/service/service_set_paged',{ keyword,pageNum,pageSize })
}

export const deleteServiceSet=(service)=>{
    return request.del('/service/delete_service_set',service)
}

export const getServiceSetInfo=(sid)=>{
    return request.get('/service/service_set_info',{ sid })
}

export const changeServiceSet=(staff)=>{
    return request.put('/service/change_service_set',staff)
}