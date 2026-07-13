import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'go_out',
    ()=>{
        const go=ref([
            {
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
                files:'synthetic-fixture',
                outReason:'synthetic-fixture',
                outTime:'synthetic-fixture',
                expectTime:'synthetic-fixture',
                reallyTime:'synthetic-fixture',
                accompany:'synthetic-fixture',
                relation:'synthetic-fixture',
                tel:'synthetic-fixture',
                statue:1000,
                processor:'synthetic-fixture',
                processTime:'synthetic-fixture',
                mark:'synthetic-fixture'
            }
        ])
        return {go}
    },
    {
        persist: true
    }
)