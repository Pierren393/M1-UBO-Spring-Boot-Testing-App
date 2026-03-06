<template>
  <div class="film-list">
    <div class="header-section">
      <h1>🎬 Bibliothèque de Films</h1>
      <p class="subtitle">Découvrez notre catalogue complet synchronisé avec l'UBO</p>
    </div>

    <div v-if="loading" class="loading-state">
      <div class="loader"></div>
      <p>Chargement des films...</p>
    </div>

    <div v-else-if="error" class="error-state">
      <p>❌ {{ error }}</p>
      <button @click="fetchData" class="btn-retry">Réessayer</button>
    </div>

    <div v-else class="grid">
      <div v-for="film in films" :key="film.id" class="card">
        <div class="card-image">
          <img :src="film.posterUrl || 'https://via.placeholder.com/300x450?text=No+Poster'" :alt="film.title" />
          <div class="overlay">
            <router-link :to="'/films/' + film.id" class="btn-detail">Voir détails</router-link>
          </div>
        </div>
        <div class="card-content">
          <h3>{{ film.title }}</h3>
          <p class="year">{{ film.releaseYear || film.year }}</p>
          <div class="meta">
            <span class="genre">{{ film.genre }}</span>
            <span class="rating">⭐ {{ film.rating || 'N/A' }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { filmService } from '../services/filmService'

const films = ref([])
const loading = ref(true)
const error = ref(null)

const fetchData = async () => {
  loading.ref = true
  error.value = null
  try {
    films.value = await filmService.getFilms()
  } catch (err) {
    console.error(err)
    error.value = "Impossible de charger les films. L'API Gateway ne répond pas ou le VPN est déconnecté."
  } finally {
    loading.value = false
  }
}

onMounted(fetchData)
</script>

<style scoped>
.film-list {
  padding: 2rem 0;
}

.header-section {
  text-align: center;
  margin-bottom: 3rem;
}

.subtitle {
  color: #888;
  font-size: 1.1rem;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 2rem;
}

.card {
  background: #1a1a1a;
  border-radius: 12px;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border: 1px solid #333;
}

.card:hover {
  transform: translateY(-10px);
  box-shadow: 0 10px 20px rgba(0,0,0,0.5);
}

.card-image {
  position: relative;
  aspect-ratio: 2/3;
  overflow: hidden;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.card:hover .overlay {
  opacity: 1;
}

.btn-detail {
  padding: 0.6rem 1.2rem;
  background: #e50914;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-weight: bold;
}

.card-content {
  padding: 1.2rem;
}

.card-content h3 {
  margin: 0 0 0.5rem 0;
  font-size: 1.1rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.year {
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 0.8rem;
}

.meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.85rem;
}

.genre {
  background: #333;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
}

.rating {
  color: #ffc107;
}

.loading-state, .error-state {
  text-align: center;
  padding: 4rem 0;
}

.loader {
  border: 4px solid #333;
  border-top: 4px solid #e50914;
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

.btn-retry {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background: #444;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
