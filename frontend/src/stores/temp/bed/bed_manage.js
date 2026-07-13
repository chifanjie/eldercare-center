import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'bed_manage',
    ()=>{
        const manage=ref([
            {
                files:'synthetic-fixture',
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
                start:'synthetic-fixture',
                end:'synthetic-fixture',
                info:'synthetic-fixture',
            }
        ])
        return {manage}
    },
    {
        persist: true
    }
)