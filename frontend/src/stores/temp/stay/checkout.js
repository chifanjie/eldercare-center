import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'checkout',
    ()=>{
        const checkout=ref([
            {
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
                files:'synthetic-fixture',
                inTime:'synthetic-fixture',
                outTime:'synthetic-fixture',
                outType:1000,
                outReason:'synthetic-fixture',
                statue:1000,
                applyTime:'synthetic-fixture',
                processOpinion:'synthetic-fixture',
                processor:'synthetic-fixture',
                processTime:'synthetic-fixture',
                mark:'synthetic-fixture'

            }
        ])
        return {checkout}
    },
    {
        persist: true
    }
)