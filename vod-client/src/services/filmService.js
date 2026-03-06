import api from './api'

export const filmService = {
  async getFilms() {
    const response = await api.get('/movies')
    return response.data
  },
  async getFilmById(id) {
    const response = await api.get(`/movies/${id}`)
    return response.data
  }
}

export const posterService = {
  async getPosters() {
    const response = await api.get('/poster')
    return response.data
  },
  async getPosterById(id) {
    const response = await api.get(`/poster/${id}`)
    return response.data
  }
}

export const artistService = {
  async getArtists() {
    const response = await api.get('/artists')
    return response.data
  }
}
