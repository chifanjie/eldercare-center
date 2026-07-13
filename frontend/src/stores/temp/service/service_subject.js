import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'service_subject',
    ()=>{
        const subject=ref([
            {
                name:'synthetic-fixture',
                tel:'synthetic-fixture',
                floor:'synthetic-fixture',
                mark:'synthetic-fixture',
                operate:'synthetic-fixture',
            }
        ])
        return {subject}
    },
    {
        persist: true
    }
)