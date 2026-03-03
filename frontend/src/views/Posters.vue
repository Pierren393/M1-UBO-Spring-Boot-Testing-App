<template>
  <div>
    <h1>🎬 Posters</h1>

    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="success" class="success">{{ success }}</div>

    <!-- Formulaire -->
    <div class="card">
      <h3>{{ editingPoster ? 'Modifier un poster' : 'Ajouter un poster' }}</h3>
      <form @submit.prevent="editingPoster ? updatePoster() : addPoster()">
        <div class="form-group">
          <label>Titre</label>
          <input v-model="form.title" required placeholder="Titre du poster" />
        </div>
        <div class="form-group">
          <label>Description</label>
          <textarea v-model="form.description" placeholder="Description"></textarea>
        </div>
        <div class="form-group">
          <label>Movie ID</label>
          <input v-model.number="form.movieId" type="number" placeholder="ID du film" />
        </div>
        <div class="form-group">
          <label>URL de l'image</label>
          <input v-model="form.imageUrl" placeholder="URL de l'image" />
        </div>
        <div class="actions">
          <button type="submit" class="btn btn-primary">
            {{ editingPoster ? 'Modifier' : 'Ajouter' }}
          </button>
          <button v-if="editingPoster" type="button" class="btn btn-warning" @click="cancelEdit()">
            Annuler
          </button>
        </div>
      </form>
    </div>

    <!-- Filtre par movieId -->
    <div class="card">
      <div class="form-group">
        <label>Filtrer par Movie ID</label>
        <div class="actions">
          <input v-model.number="filterMovieId" type="number" placeholder="Movie ID" />
          <button class="btn btn-primary" @click="fetchPosters()">Filtrer</button>
          <button class="btn btn-warning" @click="filterMovieId = null; fetchPosters()">Reset</button>
        </div>
      </div>
    </div>

    <!-- Liste -->
    <table v-if="posters.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Titre</th>
          <th>Description</th>
          <th>Movie ID</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="poster in posters" :key="poster.id">
          <td>{{ poster.id }}</td>
          <td>{{ poster.title }}</td>
          <td>{{ poster.description }}</td>
          <td>{{ poster.movieId }}</td>
          <td>
            <div class="actions">
              <button class="btn btn-warning" @click="editPoster(poster)">✏️</button>
              <button class="btn btn-danger" @click="deletePoster(poster.id)">🗑️</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-else>Aucun poster trouvé.</p>
  </div>
</template>

<script>
import api from '../services/api.js'

export default {
  name: 'Posters',
  data() {
    return {
      posters: [],
      form: { title: '', description: '', movieId: null, imageUrl: '' },
      editingPoster: null,
      filterMovieId: null,
      error: null,
      success: null,
    }
  },
  mounted() {
    this.fetchPosters()
  },
  methods: {
    async fetchPosters() {
      try {
        const params = this.filterMovieId ? { movieId: this.filterMovieId } : {}
        const res = await api.get('/posters', { params })
        this.posters = res.data
      } catch (e) {
        this.error = 'Erreur lors du chargement des posters'
      }
    },
    async addPoster() {
      try {
        await api.post('/posters', this.form)
        this.success = 'Poster ajouté avec succès'
        this.form = { title: '', description: '', movieId: null, imageUrl: '' }
        this.fetchPosters()
        this.clearMessages()
      } catch (e) {
        this.error = 'Erreur lors de l\'ajout'
      }
    },
    editPoster(poster) {
      this.editingPoster = poster.id
      this.form = { title: poster.title, description: poster.description, movieId: poster.movieId, imageUrl: poster.imageUrl }
    },
    async updatePoster() {
      try {
        await api.put(`/posters/${this.editingPoster}`, this.form)
        this.success = 'Poster modifié avec succès'
        this.cancelEdit()
        this.fetchPosters()
        this.clearMessages()
      } catch (e) {
        this.error = 'Erreur lors de la modification'
      }
    },
    cancelEdit() {
      this.editingPoster = null
      this.form = { title: '', description: '', movieId: null, imageUrl: '' }
    },
    async deletePoster(id) {
      if (!confirm('Supprimer ce poster ?')) return
      try {
        await api.delete(`/posters/${id}`)
        this.success = 'Poster supprimé'
        this.fetchPosters()
        this.clearMessages()
      } catch (e) {
        this.error = 'Erreur lors de la suppression'
      }
    },
    clearMessages() {
      setTimeout(() => { this.error = null; this.success = null }, 3000)
    }
  }
}
</script>
