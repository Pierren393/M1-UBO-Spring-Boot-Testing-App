package com.config;

import com.entity.Poster;
import com.repository.PosterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initialisation des données de test pour MongoDB.
 */
@Component
@RequiredArgsConstructor
public class MongoDataInitializer implements CommandLineRunner {

    private final PosterRepository posterRepository;

    @Override
    public void run(String... args) {
        // Vérifie si la collection est vide
        if (posterRepository.count() == 0) {
            // Classiques
            savePoster("Inception", "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEhniW0khKSBa.jpg", 27205L,
                    "Christopher Nolan", 19.99);
            savePoster("The Dark Knight", "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911BTUNoM8JTol0.jpg", 155L,
                    "Heath Ledger", 24.99);
            savePoster("Interstellar", "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", 157336L,
                    "Christopher Nolan", 22.99);
            savePoster("Pulp Fiction", "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg", 680L,
                    "Quentin Tarantino", 29.99);
            savePoster("The Matrix", "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg", 603L,
                    "Lana & Lilly Wachowski", 21.99);

            // Nouveautés & Premium
            savePoster("Oppenheimer", "https://image.tmdb.org/t/p/w500/8GxvA0zO0UozS6rkBaseuC2Ssy5.jpg", 872585L,
                    "Cillian Murphy", 29.99);
            savePoster("Dune: Part Two", "https://image.tmdb.org/t/p/w500/czemO0YAnUWhv9R2RjR6R9v0mS4.jpg", 693134L,
                    "Denis Villeneuve", 24.50);
            savePoster("Spider-Man: Across the Spider-Verse",
                    "https://image.tmdb.org/t/p/w500/8Vt6S9kR01EXznq40vOkvdm60oi.jpg", 569041L, "Joaquim Dos Santos",
                    19.90);
            savePoster("Blade Runner 2049", "https://image.tmdb.org/t/p/w500/g99SUEpXmE89BN6S6pI36997p67.jpg", 335984L,
                    "Ryan Gosling", 27.00);
            savePoster("The Godfather", "https://image.tmdb.org/t/p/w500/3bhkrj0v9ovfAt6RS9657pS0Aas.jpg", 238L,
                    "Francis Ford Coppola", 35.00);
        }
    }

    private void savePoster(String title, String url, Long movieId, String desc, Double price) {
        Poster p = new Poster();
        p.setTitle(title);
        p.setImageUrl(url);
        p.setMovieId(movieId);
        p.setDescription(desc);
        p.setPrice(price);
        p.setWidth(60.0);
        p.setHeight(90.0);
        posterRepository.save(p);
    }
}
