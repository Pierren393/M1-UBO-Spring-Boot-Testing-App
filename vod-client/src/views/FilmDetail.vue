<template>
  <div v-if="loading" class="loading">Chargement...</div>
  <div v-else-if="error" class="error">{{ error }}</div>
  <div v-else-if="film" class="film-detail">
    <div class="hero" :style="{ backgroundImage: 'url(' + (film.backdropUrl || film.posterUrl) + ')' }">
      <div class="hero-overlay">
        <div class="content">
          <h1>{{ film.title }}</h1>
          <div class="meta">
            <span>{{ film.releaseYear || film.year }}</span> • 
            <span>{{ film.duration || '2h 15min' }}</span> • 
            <span>{{ film.genre }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="details container">
      <div class="synopsis">
        <h2>Synopsis</h2>
        <p>{{ film.description || 'Pas de description disponible pour ce film.' }}</p>
      </div>
      <div class="actions">
        <button @click="reserve" class="btn-reserve">Réserver ce film</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { posterService } from '../services/filmService'

const route = useRoute()
const film = ref(null)
const loading = ref(true)
const error = ref(null)

const fetchData = async () => {
  try {
    const data = await posterService.getPosterById(route.params.id)
    // Mapping pour compatibilité template
    film.value = {
      ...data,
      title: data.titreFilm,
      posterUrl: data.imageUrl,
      year: data.anneeSortie
    }
  } catch (err) {
    error.value = "Erreur lors du chargement des détails du poster."
  } finally {
    loading.value = false
  }
}

const reserve = () => {
  alert("Fonctionnalité de réservation bientôt disponible !")
}

onMounted(fetchData)
</script>

<style scoped>
.hero {
  height: 60vh;
  background-size: cover;
  background-position: center;
  position: relative;
}
.hero-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  padding: 4rem 10%;
  background: linear-gradient(transparent, #0f0f0f);
}
.content h1 { font-size: 3.5rem; margin-bottom: 1rem; }
.meta { color: #ccc; font-size: 1.2rem; }
.details { padding: 3rem 10%; }
.synopsis p { line-height: 1.6; font-size: 1.1rem; color: #bbb; }
.btn-reserve {
  margin-top: 2rem;
  padding: 1rem 2rem;
  background: #e50914;
  color: white;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
}
</style>
