import request from "@/api/request.js";

export const getStatisticsPaged=(keyword,round,pageNum,pageSize)=>{
    return request.get('/meal/meal_statistics_paged',{ keyword,round,pageNum,pageSize })
}

export const getSetStatisticsInfo=(mid,week,time)=>{
    return request.get('/meal/meal_statistics_info',{ mid,week,time })
}

export const setStatisticsRecord=(meal)=>{
    return request.put('/meal/set_meal_statistics',meal)
}