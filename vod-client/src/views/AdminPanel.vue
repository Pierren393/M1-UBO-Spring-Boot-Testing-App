<template>
  <div class="admin-container">
    <h2>Administration - Upload d'Affiche</h2>
    <form @submit.prevent="handleUpload" class="admin-form">
      <div class="form-group">
        <label for="titreFilm">Titre du film *</label>
        <input type="text" id="titreFilm" v-model="form.titreFilm" required placeholder="Ex: Inception" />
      </div>
      <div class="form-group">
        <label for="realisateur">Réalisateur</label>
        <input type="text" id="realisateur" v-model="form.realisateur" placeholder="Ex: Christopher Nolan" />
      </div>
      <div class="form-group">
        <label for="anneeSortie">Année de sortie</label>
        <input type="number" id="anneeSortie" v-model.number="form.anneeSortie" placeholder="Ex: 2010" />
      </div>
      <div class="form-group">
        <label for="price">Prix (€)</label>
        <input type="number" step="0.01" id="price" v-model.number="form.price" placeholder="Ex: 19.99" />
      </div>
      <div class="form-group">
        <label for="image">Affiche (Image) *</label>
        <input type="file" id="image" @change="onFileChange" accept="image/*" required />
      </div>
      
      <p v-if="message" :class="{'success': isSuccess, 'error': !isSuccess}">{{ message }}</p>
      <button type="submit" :disabled="loading">{{ loading ? 'Envoi en cours...' : "Ajouter l'affiche" }}</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '../services/api'

const form = ref({
  titreFilm: '',
  realisateur: '',
  anneeSortie: null,
  price: null
})

const file = ref(null)
const loading = ref(false)
const message = ref('')
const isSuccess = ref(false)

const onFileChange = (e) => {
  file.value = e.target.files[0]
}

const handleUpload = async () => {
  if (!file.value) {
    message.value = 'Veuillez sélectionner une image.'
    isSuccess.value = false
    return
  }

  loading.value = true
  message.value = ''
  
  try {
    const formData = new FormData()
    // Création de l'objet JSON (metadata) pour le paramètre "data" exigé par l'API Poster
    formData.append('data', JSON.stringify({
      ...form.value,
      // Valeurs par défaut pour combler le DTO
      genres: ["Action", "Sci-Fi"],
      acteursPrincipaux: [],
      dimensions: { largeur: 60, hauteur: 90 },
      synopsis: "Résumé de film générique ajouté via l'administration",
      description: "Affiche uploadée via l'application Vue.js",
      movieId: Math.floor(Math.random() * 10000)
    }))
    
    // Le paramètre "image" attendu par l'API
    formData.append('image', file.value)

    await api.post('/poster', formData, {
      headers: {
        'Content-Type': 'multipart/form-data' // Axios gère ça automatiquement avec FormData, mais c'est par sécurité
      }
    })
    
    isSuccess.value = true
    message.value = 'Affiche ajoutée avec succès !'
    // reset form
    form.value = { titreFilm: '', realisateur: '', anneeSortie: null, price: null }
    file.value = null
    document.getElementById('image').value = ''
  } catch (err) {
    isSuccess.value = false
    message.value = err.response?.data?.message || err.message || "Erreur lors de l'envoi."
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.admin-container {
  max-width: 600px;
  margin: 2rem auto;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
h2 { text-align: center; margin-bottom: 1.5rem; }
.admin-form { display: flex; flex-direction: column; }
.form-group { margin-bottom: 1rem; }
.form-group label { display: block; margin-bottom: 0.5rem; font-weight: bold; }
.form-group input { width: 100%; padding: 0.5rem; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
button { width: 100%; padding: 0.75rem; background-color: #2196F3; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 1rem; margin-top: 1rem; font-weight: bold;}
button:hover { background-color: #1976D2; }
button:disabled { background-color: #ccc; cursor: not-allowed; }
.success { color: green; text-align: center; margin-top: 1rem; font-weight: bold; }
.error { color: red; text-align: center; margin-top: 1rem; font-weight: bold; }
</style>
