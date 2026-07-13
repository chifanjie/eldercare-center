import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'user',
    ()=>{
        const user=ref([
            {
                photo:'synthetic-fixture',
                usr:'synthetic-fixture',
                email:'synthetic-fixture',
                pwd:'example',
                role:1000,
                create:'synthetic-fixture',
                statue:1000,
                mark:'synthetic-fixture'
            }
        ])
        return {user}
    },
    {
        persist: true
    }
)