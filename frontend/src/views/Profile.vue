<template>
  <div>
    <h1>👤 Mon Profil</h1>

    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="success" class="success">{{ success }}</div>

    <div v-if="user" class="card">
      <h3>Informations</h3>
      <p><strong>ID:</strong> {{ user.id }}</p>
      <p><strong>Username:</strong> {{ user.username }}</p>
      <p><strong>Email:</strong> {{ user.email }}</p>
    </div>

    <div v-if="user" class="card">
      <h3>Modifier mon profil</h3>
      <form @submit.prevent="updateProfile">
        <div class="form-group">
          <label>Username</label>
          <input v-model="form.username" required />
        </div>
        <div class="form-group">
          <label>Email</label>
          <input v-model="form.email" type="email" required />
        </div>
        <div class="actions">
          <button type="submit" class="btn btn-primary">Sauvegarder</button>
          <button type="button" class="btn btn-danger" @click="deleteAccount">Supprimer mon compte</button>
        </div>
      </form>
    </div>

    <p v-if="!user && !error">Chargement...</p>
  </div>
</template>

<script>
import api from '../services/api.js'

export default {
  name: 'Profile',
  data() {
    return {
      user: null,
      form: { username: '', email: '' },
      error: null,
      success: null,
    }
  },
  mounted() {
    this.fetchProfile()
  },
  methods: {
    async fetchProfile() {
      try {
        const res = await api.get('/auth/me')
        this.user = res.data
        this.form = { username: res.data.username, email: res.data.email }
      } catch (e) {
        this.error = 'Vous devez être connecté pour accéder à cette page'
        setTimeout(() => this.$router.push('/login'), 2000)
      }
    },
    async updateProfile() {
      try {
        await api.put(`/users/${this.user.id}`, this.form)
        this.success = 'Profil mis à jour'
        this.fetchProfile()
        setTimeout(() => { this.success = null }, 3000)
      } catch (e) {
        this.error = 'Erreur lors de la mise à jour'
        setTimeout(() => { this.error = null }, 3000)
      }
    },
    async deleteAccount() {
      if (!confirm('Êtes-vous sûr de vouloir supprimer votre compte ?')) return
      try {
        await api.delete(`/users/${this.user.id}`)
        localStorage.removeItem('token')
        this.$router.push('/login')
      } catch (e) {
        this.error = 'Erreur lors de la suppression du compte'
      }
    }
  }
}
</script>
