import request from "@/api/request.js";

export const getBedDesignRecord=(round,keyword)=>{
    return request.get('/bed/get_bed_design',{ round,keyword })
}

export const getBedCustomer=(uid)=>{
    return request.get('/bed/bed_customer_record',{ uid })
}