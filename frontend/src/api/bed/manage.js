import request from "@/api/request.js";

export const getBedRecordPaged=(keyword,pageNum,pageSize)=>{
    return request.get('/bed/bed_paged',{ keyword,pageNum,pageSize })
}

export const getCustomerBedRecord=(uid)=>{
    return request.get('/bed/bed_customer',{ uid })
}

export const submitExchangeRecord=(exchange)=>{
    return request.put('/bed/exchange_customer_bed',exchange)
}

export const submitChangeRecord=(exchange)=>{
    return request.put('/bed/reset_customer_bed',exchange)
}