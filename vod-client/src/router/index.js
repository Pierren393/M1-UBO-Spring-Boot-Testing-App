import { createRouter, createWebHistory } from 'vue-router'
import FilmList from '../views/FilmList.vue'
import FilmDetail from '../views/FilmDetail.vue'
import ArtistList from '../views/ArtistList.vue'
import ArtistDetail from '../views/ArtistDetail.vue'
import MyReservations from '../views/MyReservations.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import AdminPanel from '../views/AdminPanel.vue'

const routes = [
  { path: '/', redirect: '/films' },
  { path: '/films', name: 'FilmList', component: FilmList },
  { path: '/films/:id', name: 'FilmDetail', component: FilmDetail },
  { path: '/artists', name: 'ArtistList', component: ArtistList },
  { path: '/artists/:id', name: 'ArtistDetail', component: ArtistDetail },
  { path: '/reservations', name: 'MyReservations', component: MyReservations },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/admin', name: 'AdminPanel', component: AdminPanel },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
