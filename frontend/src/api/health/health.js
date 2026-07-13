import request from "@/api/request.js";

export const getHealthCare=(uid)=>{
    return request.get('/service/health_data_care',{ uid })
}

export const getCustomerRecord=(uid)=>{
    return request.get('/service/customer_record',{ uid })
}