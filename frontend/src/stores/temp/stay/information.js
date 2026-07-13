import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'information',
    ()=>{
        const information=ref([
            {
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
                files:'synthetic-fixture',
                assessing:'synthetic-fixture',
                responsible:'synthetic-fixture',
                butler:'synthetic-fixture',
                build:'synthetic-fixture',
                bed:'synthetic-fixture',
                inTime:'synthetic-fixture',
                over:'synthetic-fixture',
                health:'synthetic-fixture',
                level:1000,
                connect:'synthetic-fixture',
                tel:'synthetic-fixture',
                result:'synthetic-fixture',

            }
        ])
        return {information}
    },
    {
        persist: true
    }
)