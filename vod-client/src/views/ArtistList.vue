<template>
  <div class="artist-list">
    <div class="header-section">
      <h1>⭐ Nos Artistes</h1>
      <p class="subtitle">Les talents derrière vos films préférés</p>
    </div>

    <div v-if="loading" class="loading-state">
      <div class="loader"></div>
      <p>Chargement des artistes...</p>
    </div>

    <div v-else-if="error" class="error-state">
      <p>❌ {{ error }}</p>
      <button @click="fetchData" class="btn-retry">Réessayer</button>
    </div>

    <div v-else class="grid">
      <div v-for="artist in artists" :key="artist.id" class="artist-card">
        <div class="avatar">
          <img :src="artist.imageUrl || 'https://via.placeholder.com/150?text=Artist'" :alt="artist.name" />
        </div>
        <div class="content">
          <h3>{{ artist.name }}</h3>
          <p class="role">{{ artist.specialty || 'Comédien' }}</p>
          <router-link :to="'/artists/' + artist.id" class="btn-profile">Voir profil</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { filmService } from '../services/filmService'

const artists = ref([])
const loading = ref(true)
const error = ref(null)

const fetchData = async () => {
  loading.value = true
  error.value = null
  try {
    artists.value = await filmService.getArtists()
  } catch (err) {
    console.error(err)
    error.value = "Impossible de charger les artistes. Vérifiez votre connexion VPN."
  } finally {
    loading.value = false
  }
}

onMounted(fetchData)
</script>

<style scoped>
.artist-list {
  padding: 2rem 0;
}

.header-section {
  text-align: center;
  margin-bottom: 3rem;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 2rem;
}

.artist-card {
  background: #1a1a1a;
  border-radius: 12px;
  padding: 1.5rem;
  text-align: center;
  border: 1px solid #333;
  transition: transform 0.3s ease;
}

.artist-card:hover {
  transform: scale(1.05);
  border-color: #ffc107;
}

.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  margin: 0 auto 1.2rem;
  overflow: hidden;
  border: 3px solid #333;
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.content h3 {
  margin: 0 0 0.4rem 0;
  font-size: 1.1rem;
}

.role {
  color: #888;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.btn-profile {
  display: inline-block;
  font-size: 0.85rem;
  color: #ffc107;
  text-decoration: none;
  border: 1px solid #ffc107;
  padding: 0.4rem 1rem;
  border-radius: 20px;
  transition: all 0.3s ease;
}

.btn-profile:hover {
  background: #ffc107;
  color: black;
}

.loading-state, .error-state {
  text-align: center;
  padding: 4rem 0;
}

.loader {
  border: 4px solid #333;
  border-top: 4px solid #ffc107;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin: 0 auto 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>
