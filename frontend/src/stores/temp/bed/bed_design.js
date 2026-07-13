import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'bed_design',
    ()=>{
        const design=ref([
            {
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
                build:'synthetic-fixture',
                floor:'synthetic-fixture',
                room:'synthetic-fixture',
                tel:'synthetic-fixture',
                start:'synthetic-fixture',
                end:'synthetic-fixture',
            }
        ])
        return {design}
    },
    {
        persist: true
    }
)