import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore(
    'checkin',
    ()=>{
        const checkin=ref([
            {
                name:'synthetic-fixture',
                gander:'synthetic-fixture',
                age:'synthetic-fixture',
                id:'synthetic-fixture',
                room:'synthetic-fixture',
                floor:'synthetic-fixture',
                files:'synthetic-fixture',
                inTime:'synthetic-fixture',
                endTime:'synthetic-fixture',
                tel:'synthetic-fixture',
                family:'synthetic-fixture',
                companion:'synthetic-fixture',
                assessing:'synthetic-fixture',
                responsible:'synthetic-fixture',
                support:'synthetic-fixture',
                butler:'synthetic-fixture',
                mark:'synthetic-fixture'

            }
        ])
        return { checkin }
    },
    {
        persist: true
    }
)