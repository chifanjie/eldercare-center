import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'nurse_content',
    ()=>{
        const content=ref([
            {
                no:'synthetic-fixture',
                name:'synthetic-fixture',
                price:'synthetic-fixture',
                memo:'synthetic-fixture',
                valueAdded:'synthetic-fixture',
                statue:1000,
                mark:'synthetic-fixture'
            }
        ])
        return {content}
    },
    {
        persist: true
    }
)