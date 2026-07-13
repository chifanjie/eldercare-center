import request from "@/api/request.js";

export const getCheckinPaged = (keyword,round,pageNum,pageSize) =>{
    return request.get('/customer/customer_paged',{ keyword,round,pageNum,pageSize })
}

export const addCheckinRecords = (customer)=>{
    return request.post('/customer/add_checkin',customer)
}

export const deleteCheckinRecord = (customer)=>{
    return request.put('/customer/delete_checkin',customer)
}

export const whereIsCustomer = (uid)=>{
    return request.get('/customer/where_is_customer',{uid})
}

export const changeCustomerCheckin = (customer)=>{
    return request.put('/customer/change_checkin',customer)
}