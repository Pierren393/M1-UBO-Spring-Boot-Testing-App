package com.config;

import com.entities.Poster;
import com.repositories.PosterRepository;
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
            posterRepository.save(new Poster(null, "Inception", 
                "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEhniW0khKSBa.jpg", 
                27205L, "Poster officiel du film Inception de Christopher Nolan", 19.99, 60.0, 90.0));
            
            posterRepository.save(new Poster(null, "The Dark Knight", 
                "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911BTUNoM8JTol0.jpg", 
                155L, "Poster du film The Dark Knight avec Heath Ledger", 24.99, 60.0, 90.0));
            
            posterRepository.save(new Poster(null, "Interstellar", 
                "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg", 
                157336L, "Poster du film Interstellar de Christopher Nolan", 22.99, 40.0, 60.0));
            
            posterRepository.save(new Poster(null, "Pulp Fiction", 
                "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg", 
                680L, "Poster culte de Pulp Fiction par Quentin Tarantino", 29.99, 80.0, 120.0));
            
            posterRepository.save(new Poster(null, "The Matrix", 
                "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg", 
                603L, "Poster emblématique de The Matrix", 21.99, 60.0, 90.0));
        }
    }
}