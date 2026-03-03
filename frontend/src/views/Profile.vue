<template>
  <div class="profile-container">
    <h2>Mon Profil</h2>
    <div v-if="user" class="profile-card">
      <div class="profile-info">
        <p><strong>Pseudo :</strong> {{ user.pseudo }}</p>
        <p><strong>Nom :</strong> {{ user.nom }}</p>
        <p><strong>Prénom :</strong> {{ user.prenom }}</p>
        <p><strong>Âge :</strong> {{ user.age }} ans</p>
        <p><strong>Adresse :</strong> {{ user.adresse }}</p>
        <p><strong>Rôle :</strong> {{ user.role }}</p>
      </div>

      <h3>Modifier mon profil</h3>
      <form @submit.prevent="handleUpdate">
        <div class="form-group">
          <label for="nom">Nom</label>
          <input type="text" id="nom" v-model="editForm.nom" placeholder="Nouveau nom" />
        </div>
        <div class="form-group">
          <label for="prenom">Prénom</label>
          <input type="text" id="prenom" v-model="editForm.prenom" placeholder="Nouveau prénom" />
        </div>
        <div class="form-group">
          <label for="age">Âge</label>
          <input type="number" id="age" v-model.number="editForm.age" min="0" placeholder="Nouvel âge" />
        </div>
        <div class="form-group">
          <label for="adresse">Adresse</label>
          <input type="text" id="adresse" v-model="editForm.adresse" placeholder="Nouvelle adresse" />
        </div>
        <div class="form-group">
          <label for="motDePasse">Nouveau mot de passe</label>
          <input type="password" id="motDePasse" v-model="editForm.motDePasse" placeholder="Laisser vide pour ne pas changer" />
        </div>
        <p v-if="successMsg" class="success">{{ successMsg }}</p>
        <p v-if="error" class="error">{{ error }}</p>
        <button type="submit" :disabled="loading">{{ loading ? 'Mise à jour...' : 'Mettre à jour' }}</button>
      </form>

      <button class="delete-btn" @click="handleDelete">Supprimer mon compte</button>
      <button class="logout-btn" @click="handleLogout">Se déconnecter</button>
    </div>
    <div v-else>
      <p>Chargement du profil...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api'

const router = useRouter()
const user = ref(null)
const loading = ref(false)
const error = ref('')
const successMsg = ref('')
const editForm = ref({
  nom: '',
  prenom: '',
  age: null,
  adresse: '',
  motDePasse: ''
})

onMounted(() => {
  const stored = localStorage.getItem('user')
  if (stored) {
    user.value = JSON.parse(stored)
    editForm.value.nom = user.value.nom
    editForm.value.prenom = user.value.prenom
    editForm.value.age = user.value.age
    editForm.value.adresse = user.value.adresse
  } else {
    router.push('/login')
  }
})

const handleUpdate = async () => {
  loading.value = true
  error.value = ''
  successMsg.value = ''
  try {
    const payload = {}
    if (editForm.value.nom) payload.nom = editForm.value.nom
    if (editForm.value.prenom) payload.prenom = editForm.value.prenom
    if (editForm.value.age != null) payload.age = editForm.value.age
    if (editForm.value.adresse) payload.adresse = editForm.value.adresse
    if (editForm.value.motDePasse) payload.motDePasse = editForm.value.motDePasse

    const response = await api.put(`/user/${user.value.pseudo}`, payload)
    user.value = response.data
    localStorage.setItem('user', JSON.stringify(response.data))
    successMsg.value = 'Profil mis à jour avec succès'
    editForm.value.motDePasse = ''
  } catch (err) {
    error.value = err.response?.data?.message || 'Erreur lors de la mise à jour'
  } finally {
    loading.value = false
  }
}

const handleDelete = async () => {
  if (!confirm('Êtes-vous sûr de vouloir supprimer votre compte ?')) return
  try {
    await api.delete(`/user/${user.value.pseudo}`)
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    router.push('/login')
  } catch (err) {
    error.value = err.response?.data?.message || 'Erreur lors de la suppression'
  }
}

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
.profile-container {
  max-width: 500px;
  margin: 2rem auto;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
h2 { text-align: center; margin-bottom: 1.5rem; }
h3 { margin-top: 2rem; margin-bottom: 1rem; }
.profile-info { background: #f5f5f5; padding: 1rem; border-radius: 4px; margin-bottom: 1rem; }
.profile-info p { margin: 0.5rem 0; }
.form-group { margin-bottom: 1rem; }
.form-group label { display: block; margin-bottom: 0.5rem; font-weight: bold; }
.form-group input { width: 100%; padding: 0.5rem; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
button { width: 100%; padding: 0.75rem; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 1rem; margin-top: 0.5rem; }
button:hover { background-color: #45a049; }
button:disabled { background-color: #ccc; cursor: not-allowed; }
.delete-btn { background-color: #f44336; margin-top: 1rem; }
.delete-btn:hover { background-color: #d32f2f; }
.logout-btn { background-color: #757575; margin-top: 0.5rem; }
.logout-btn:hover { background-color: #616161; }
.error { color: red; text-align: center; }
.success { color: green; text-align: center; }
</style>
