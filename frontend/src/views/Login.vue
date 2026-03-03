<template>
  <div class="auth-container">
    <h1>🔐 Connexion</h1>

    <div v-if="error" class="error">{{ error }}</div>

    <div class="card">
      <form @submit.prevent="login">
        <div class="form-group">
          <label>Nom d'utilisateur</label>
          <input v-model="form.username" required placeholder="Username" />
        </div>
        <div class="form-group">
          <label>Mot de passe</label>
          <input v-model="form.password" type="password" required placeholder="Password" />
        </div>
        <button type="submit" class="btn btn-primary" style="width:100%">Se connecter</button>
      </form>
      <p style="margin-top:1rem;text-align:center">
        Pas de compte ? <router-link to="/register">S'inscrire</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import api from '../services/api.js'

export default {
  name: 'Login',
  data() {
    return {
      form: { username: '', password: '' },
      error: null,
    }
  },
  methods: {
    async login() {
      try {
        const res = await api.post('/auth/login', this.form)
        if (res.data.token) {
          localStorage.setItem('token', res.data.token)
        }
        this.$router.push('/dogs')
      } catch (e) {
        this.error = e.response?.data?.message || 'Identifiants incorrects'
      }
    }
  }
}
</script>
