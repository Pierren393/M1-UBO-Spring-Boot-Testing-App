<template>
  <div class="auth-container">
    <h1>📝 Inscription</h1>

    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="success" class="success">{{ success }}</div>

    <div class="card">
      <form @submit.prevent="register">
        <div class="form-group">
          <label>Nom d'utilisateur</label>
          <input v-model="form.username" required placeholder="Username" />
        </div>
        <div class="form-group">
          <label>Email</label>
          <input v-model="form.email" type="email" required placeholder="Email" />
        </div>
        <div class="form-group">
          <label>Mot de passe</label>
          <input v-model="form.password" type="password" required placeholder="Password" />
        </div>
        <button type="submit" class="btn btn-success" style="width:100%">S'inscrire</button>
      </form>
      <p style="margin-top:1rem;text-align:center">
        Déjà un compte ? <router-link to="/login">Se connecter</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import api from '../services/api.js'

export default {
  name: 'Register',
  data() {
    return {
      form: { username: '', email: '', password: '' },
      error: null,
      success: null,
    }
  },
  methods: {
    async register() {
      try {
        await api.post('/auth/signup', this.form)
        this.success = 'Inscription réussie ! Vous pouvez vous connecter.'
        this.form = { username: '', email: '', password: '' }
        setTimeout(() => this.$router.push('/login'), 2000)
      } catch (e) {
        this.error = e.response?.data?.message || 'Erreur lors de l\'inscription'
      }
    }
  }
}
</script>
