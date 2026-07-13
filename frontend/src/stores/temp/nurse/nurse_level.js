import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'nurse_level',
    ()=>{
        const level=ref([
            {
                level:'synthetic-fixture',
                statue:1000,
                mark:'synthetic-fixture'
            }
        ])
        return {level}
    },
    {
        persist: true
    }
)