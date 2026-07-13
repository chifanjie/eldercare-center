import request from "@/api/request.js";

export const getCaptcha = (email) =>{
    return request.get('/welcome/captcha', { email })
}