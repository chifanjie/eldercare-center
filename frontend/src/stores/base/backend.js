import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useBackend = defineStore('backend', () => {
  const backend = ref(import.meta.env.VITE_API_BASE_URL)
  const setBackend = () => {
    backend.value = import.meta.env.VITE_API_BASE_URL
  }
  const removeBackend = () => {
    backend.value = import.meta.env.VITE_API_BASE_URL
  }
  return { backend, setBackend, removeBackend }
})
