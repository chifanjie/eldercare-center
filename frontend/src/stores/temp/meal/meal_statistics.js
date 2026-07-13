import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'statistics',
    ()=>{
        const statistics=ref([
            {
                name:'synthetic-fixture',
                num:1000,
                type:1000,
                mark:'synthetic-fixture'
            }
        ])
        return {statistics}
    },
    {
        persist: true
    }
)