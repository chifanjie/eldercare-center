import request from "@/api/request.js";

export const getMealManagePaged=(keyword,pageNum,pageSize)=>{
    return request.get('/meal/meal_manage_paged',{ keyword,pageNum,pageSize })
}

export const changeMealManage=(mealManage)=>{
    return request.put('/meal/change_meal_manage',mealManage)
}

export const getMealManageSetInfo=(cid)=>{
    return request.get('/meal/meal_manage_set_info',{ cid })
}

export const setMealManage=(mealManage)=>{
    return request.put('/meal/set_meal_manage',mealManage)
}

