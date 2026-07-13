import request from "@/api/request.js";

export const getMenu = ()=>{
    return request.get('/menue/menue')
}

export const getTreeMenu = ()=>{
    return request.get('/menue/tree_menue')
}

export const getRoleMenue=()=>{
    return request.get('/menue/role_menue')
}

export const getNurseMenue=()=>{
    return request.get('/menue/nurse_menue')
}

export const getBuildingMenue=()=>{
    return request.get('/menue/building_menue')
}

export const getCustomerMenue=(keyword)=>{
    return request.get('/menue/customer_menue',{ keyword })
}

export const getStaffMenue=(keyword)=>{
    return request.get('/menue/staff_menue',{ keyword })
}

export const getTagMenue=()=>{
    return request.get('/menue/tag_menue')
}

export const getServiceMenue=(keyword)=>{
    return request.get('/menue/service_menue',{ keyword })
}

export const getTypeMenue=()=>{
    return request.get('/menue/type_menue')
}

export const getMealMenue=()=>{
    return request.get('/menue/meal_menue')
}

export const getMealsTagMenue=()=>{
    return request.get('/menue/meals_tag_menue')
}

export const getLevelMenue=()=>{
    return request.get('/menue/level_menue')
}

export const getButlerMenue=(keyword)=>{
    return request.get('/menue/butler_menue',{ keyword })
}

export const getSupportMenue=(keyword)=>{
    return request.get('/menue/support_menue',{ keyword })
}

export const getCustomCustomerServiceMenue=(keyword)=>{
    return request.get('/menue/custom_customer_menue',{ keyword })
}