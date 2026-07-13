import request from "@/api/request.js";

export const getCheckoutPaged = (keyword,pageNum,pageSize) =>{
    return request.get('/customer/checkout_paged',{ keyword,pageNum,pageSize })
}

export const addCheckoutRecord=(checkout)=>{
    return request.post('/customer/add_checkout',checkout)
}

export const deleteCheckoutRecord=(checkout)=>{
    return request.del('/customer/delete_checkout',checkout)
}

export const approveCheckoutRecord=(checkout)=>{
    return request.post('/customer/approve_checkout',checkout)
}

export const changeCheckoutRecord=(checkout)=>{
    return request.put('/customer/change_checkout',checkout)
}