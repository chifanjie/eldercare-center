import request from "@/api/request.js";

export const getEgressPaged = (keyword,pageNum,pageSize)=>{
    return request.get('/customer/customer_egress_paged',{ keyword,pageNum,pageSize })
}

export const approveEgress = (egress)=>{
    return request.post('/customer/approve_egress',egress)
}

export const deleteEgress = (egress)=>{
    return request.del('/customer/delete_egress',egress)
}

export const addEgressRecord = (egress)=>{
    return request.post('/customer/add_egress',egress)
}

export const changeEgressRecord = (egress)=>{
    return request.put('/customer/change_egress',egress)
}

export const submitEgressReally = (egress)=>{
    return request.put('/customer/add_really',egress)
}