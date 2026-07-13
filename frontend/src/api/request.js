import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useToken } from '@/stores/index'
import router from '@/router/index.js'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 60000
})

request.interceptors.request.use(
  (config) => {
    const tokenStore = useToken()
    if (tokenStore.token) config.headers.token = tokenStore.token
    return config
  },
  (error) => Promise.reject(error)
)

const handleError = async (error) => {
  const status = error?.response?.status
  if (status === 401) {
    const tokenStore = useToken()
    tokenStore.removeToken()
    ElMessage.error({ message: '登录失效，请重新登录', grouping: true })
    await router.push('/login')
  } else if (status === 500) {
    ElMessage.error({ message: '服务器出错啦', grouping: true })
  }
  return Promise.reject(error)
}

export const get = async (url, params = {}) => {
  try {
    const response = await request.get(url, { params })
    return response.data
  } catch (error) {
    return handleError(error)
  }
}

export const post = async (url, data = {}) => {
  try {
    const response = await request.post(url, data)
    return response.data
  } catch (error) {
    return handleError(error)
  }
}

export const put = async (url, data = {}) => {
  try {
    const response = await request.put(url, data)
    return response.data
  } catch (error) {
    return handleError(error)
  }
}

export const del = async (url, data = {}) => {
  try {
    const response = await request.delete(url, { data })
    return response.data
  } catch (error) {
    return handleError(error)
  }
}

export default { get, post, put, del }
