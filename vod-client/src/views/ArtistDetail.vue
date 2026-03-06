<template>
  <div v-if="loading" class="loading">Chargement...</div>
  <div v-else-if="artist" class="artist-detail container">
    <div class="header">
      <img :src="artist.imageUrl || 'https://via.placeholder.com/200'" class="avatar" />
      <div class="info">
        <h1>{{ artist.name }}</h1>
        <p class="specialty">{{ artist.specialty }}</p>
      </div>
    </div>
    <div class="bio">
      <h2>Biographie</h2>
      <p>{{ artist.bio || 'Aucune biographie disponible.' }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const artist = ref(null)
const loading = ref(true)

const fetchData = async () => {
  // Simulation pour l'instant
  setTimeout(() => {
    artist.value = { name: "Artiste " + route.params.id, specialty: "Acteur / Réalisateur" }
    loading.value = false
  }, 500)
}

onMounted(fetchData)
</script>

<style scoped>
.artist-detail { padding-top: 4rem; text-align: center; }
.avatar { width: 200px; height: 200px; border-radius: 50%; object-fit: cover; border: 4px solid #333; }
.info h1 { margin: 1.5rem 0 0.5rem; font-size: 2.5rem; }
.specialty { color: #ffc107; font-size: 1.2rem; }
.bio { max-width: 800px; margin: 3rem auto; text-align: left; }
</style>
