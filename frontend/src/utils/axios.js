import axios from 'axios'

const instance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: { 'Content-Type': 'application/json; charset=utf-8' },
  timeout: 3000,
  withCredentials: false,
  responseType: 'json'
})

instance.interceptors.request.use(
  (config) => config,
  (error) => Promise.reject(error)
)

instance.interceptors.response.use(
  (response) => response,
  (error) => Promise.reject(error)
)

export default instance
