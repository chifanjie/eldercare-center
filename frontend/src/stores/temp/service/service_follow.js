import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'service_follow',
    ()=>{
        const customer=ref([
            {
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
            }
        ])
        const service=ref([
            {
                name:'synthetic-fixture',
                service:'synthetic-fixture',
                period:1000,
                buy:1000,
                sum:1000,
                current:1000,
                time:'synthetic-fixture',
                statue:1000,
                mark:'synthetic-fixture'
            }
        ])
        return {customer,service}
    },
    {
        persist: true
    }
)