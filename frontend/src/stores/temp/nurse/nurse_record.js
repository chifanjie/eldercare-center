import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'nurse_record',
    ()=>{
        const customer=ref([
            {
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
                bed:'synthetic-fixture',
                level:1000,
            }
        ])
        const record=ref([
            {
                name:'synthetic-fixture',
                service:'synthetic-fixture',
                time:'synthetic-fixture',
                num:1000,
                content:'synthetic-fixture',
                server:'synthetic-fixture'
            }
        ])
        return {customer,record}
    },
    {
        persist: true
    }
)