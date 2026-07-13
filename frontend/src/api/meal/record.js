import request from "@/api/request.js";

export const getMealRecordPage=(round)=>{
    return request.get('/meal/meal_paged',{ round })
}

export const deleteMealRecord=(meal)=>{
    return request.put('/meal/delete_meal_record',meal)
}

export const submitMealRecord=(meal)=>{
    return request.post('/meal/add_meal_record',meal)
}

export const getMealRecordInfo=(mid)=>{
    return request.get('/meal/meal_record_info',{ mid })
}

export const changeMealRecord=(meal)=>{
    return request.put('/meal/change_meal_record',meal)
}

export const setMealRecord=(meal)=>{
    return request.post('/meal/set_meal_record',meal)
}