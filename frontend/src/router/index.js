import { createRouter, createWebHistory } from 'vue-router'
import Dogs from '../views/Dogs.vue'
import Posters from '../views/Posters.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Profile from '../views/Profile.vue'

const routes = [
  { path: '/', redirect: '/dogs' },
  { path: '/dogs', name: 'Dogs', component: Dogs },
  { path: '/posters', name: 'Posters', component: Posters },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/profile', name: 'Profile', component: Profile },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
