<template>
  <div>
    <h1>🐕 Dogs</h1>

    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="success" class="success">{{ success }}</div>

    <!-- Formulaire d'ajout -->
    <div class="card">
      <h3>{{ editingDog ? 'Modifier un chien' : 'Ajouter un chien' }}</h3>
      <form @submit.prevent="editingDog ? updateDog() : addDog()">
        <div class="form-group">
          <label>Nom</label>
          <input v-model="form.name" required placeholder="Nom du chien" />
        </div>
        <div class="form-group">
          <label>Race</label>
          <input v-model="form.race" required placeholder="Race du chien" />
        </div>
        <div class="actions">
          <button type="submit" class="btn btn-primary">
            {{ editingDog ? 'Modifier' : 'Ajouter' }}
          </button>
          <button v-if="editingDog" type="button" class="btn btn-warning" @click="cancelEdit()">
            Annuler
          </button>
        </div>
      </form>
    </div>

    <!-- Liste des chiens -->
    <table v-if="dogs.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nom</th>
          <th>Race</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="dog in dogs" :key="dog.id">
          <td>{{ dog.id }}</td>
          <td>{{ dog.name }}</td>
          <td>{{ dog.race }}</td>
          <td>
            <div class="actions">
              <button class="btn btn-warning" @click="editDog(dog)">✏️</button>
              <button class="btn btn-danger" @click="deleteDog(dog.id)">🗑️</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-else>Aucun chien trouvé.</p>
  </div>
</template>

<script>
import api from '../services/api.js'

export default {
  name: 'Dogs',
  data() {
    return {
      dogs: [],
      form: { name: '', race: '' },
      editingDog: null,
      error: null,
      success: null,
    }
  },
  mounted() {
    this.fetchDogs()
  },
  methods: {
    async fetchDogs() {
      try {
        const res = await api.get('/dogs')
        this.dogs = res.data
      } catch (e) {
        this.error = 'Erreur lors du chargement des chiens'
      }
    },
    async addDog() {
      try {
        await api.post('/dogs', this.form)
        this.success = 'Chien ajouté avec succès'
        this.form = { name: '', race: '' }
        this.fetchDogs()
        this.clearMessages()
      } catch (e) {
        this.error = 'Erreur lors de l\'ajout'
      }
    },
    editDog(dog) {
      this.editingDog = dog.id
      this.form = { name: dog.name, race: dog.race }
    },
    async updateDog() {
      try {
        await api.put(`/dogs/${this.editingDog}`, this.form)
        this.success = 'Chien modifié avec succès'
        this.cancelEdit()
        this.fetchDogs()
        this.clearMessages()
      } catch (e) {
        this.error = 'Erreur lors de la modification'
      }
    },
    cancelEdit() {
      this.editingDog = null
      this.form = { name: '', race: '' }
    },
    async deleteDog(id) {
      if (!confirm('Supprimer ce chien ?')) return
      try {
        await api.delete(`/dogs/${id}`)
        this.success = 'Chien supprimé'
        this.fetchDogs()
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
