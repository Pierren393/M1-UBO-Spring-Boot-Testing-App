<template>
  <div id="app">
    <nav class="navbar">
      <div class="nav-brand">🎬 VOD Manager</div>
      <div class="nav-links">
        <router-link to="/films">Films</router-link>
        <router-link to="/artists">Artistes</router-link>
        <router-link v-if="isLoggedIn" to="/reservations">Mes Réservations</router-link>
        <router-link v-if="isLoggedIn" to="/admin">Admin</router-link>
        <router-link v-if="!isLoggedIn" to="/login">Connexion</router-link>
        <router-link v-if="!isLoggedIn" to="/register">Inscription</router-link>
        <button v-if="isLoggedIn" @click="logout" class="btn-logout">Déconnexion</button>
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
