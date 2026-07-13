import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'meal_manage',
    ()=>{
        const manage=ref([
            {
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
                habit:'synthetic-fixture',
                attention:'synthetic-fixture',
                mark:'synthetic-fixture'
            }
        ])
        return {manage}
    },
    {
        persist: true
    }
)