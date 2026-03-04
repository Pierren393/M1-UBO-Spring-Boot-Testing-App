<template>
  <div class="register-container">
    <h2>Inscription</h2>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="username">Pseudo d'affichage</label>
        <input type="text" id="username" v-model="form.username" required placeholder="Votre pseudo" />
      </div>
      <div class="form-group">
        <label for="email">Adresse Email</label>
        <input type="email" id="email" v-model="form.email" required placeholder="Votre email" />
      </div>
      <div class="form-group">
        <label for="password">Mot de passe</label>
        <input type="password" id="password" v-model="form.password" required placeholder="Votre mot de passe" />
      </div>
      <p v-if="error" class="error">{{ error }}</p>
      <button type="submit" :disabled="loading">{{ loading ? 'Inscription...' : "S'inscrire" }}</button>
    </form>
    <p class="login-link">Déjà un compte ? <router-link to="/login">Se connecter</router-link></p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api'

const router = useRouter()
const loading = ref(false)
const error = ref('')
const form = ref({
  username: '',
  email: '',
  password: ''
})

const handleRegister = async () => {
  loading.value = true
  error.value = ''
  try {
    const response = await api.post('/auth/register', form.value)
    localStorage.setItem('token', response.data.accessToken)
    router.push('/')
  } catch (err) {
    if (err.response?.status === 409) {
      error.value = 'Cet email est déjà utilisé'
    } else {
      error.value = err.response?.data?.message || "Erreur lors de l'inscription"
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  max-width: 400px;
  margin: 2rem auto;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
h2 { text-align: center; margin-bottom: 1.5rem; }
.form-group { margin-bottom: 1rem; }
.form-group label { display: block; margin-bottom: 0.5rem; font-weight: bold; }
.form-group input { width: 100%; padding: 0.5rem; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
button { width: 100%; padding: 0.75rem; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 1rem; }
button:hover { background-color: #45a049; }
button:disabled { background-color: #ccc; cursor: not-allowed; }
.error { color: red; text-align: center; }
.login-link { text-align: center; margin-top: 1rem; }
</style>
