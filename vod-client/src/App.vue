<template>
  <div id="app">
    <nav class="navbar">
      <div class="nav-brand">🐾 API Manager</div>
      <div class="nav-links">
        <router-link to="/dogs">Dogs</router-link>
        <router-link to="/posters">Posters</router-link>
        <router-link v-if="!isLoggedIn" to="/login">Login</router-link>
        <router-link v-if="!isLoggedIn" to="/register">Register</router-link>
        <router-link v-if="isLoggedIn" to="/profile">Profile</router-link>
        <button v-if="isLoggedIn" @click="logout" class="btn-logout">Logout</button>
      </div>
    </nav>
    <main class="container">
      <router-view />
    </main>
  </div>
</template>

<script>
import api from './services/api.js'

export default {
  name: 'App',
  data() {
    return {
      isLoggedIn: !!localStorage.getItem('token'),
    }
  },
  watch: {
    '$route'() {
      this.isLoggedIn = !!localStorage.getItem('token')
    }
  },
  methods: {
    async logout() {
      try {
        await api.post('/auth/logout')
      } catch (e) { /* ignore */ }
      localStorage.removeItem('token')
      this.isLoggedIn = false
      this.$router.push('/login')
    }
  }
}
</script>
