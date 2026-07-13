import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'role',
    ()=>{
        const role=ref([
            {
                name:'synthetic-fixture',
                power:'synthetic-fixture',
                statue:1000,
                mark:'synthetic-fixture'
            }
        ])
        return {role}
    },
    {
        persist: true
    }
)