import axios from 'axios'

const api = axios.create({
  baseURL: '/api', // L'API Gateway est proxied par NGINX via /api
  headers: {
    'Content-Type': 'application/json',
  },
})

// Ajouter le token JWT aux requêtes si disponible
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token && token !== 'undefined' && token !== 'null') {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export default api
