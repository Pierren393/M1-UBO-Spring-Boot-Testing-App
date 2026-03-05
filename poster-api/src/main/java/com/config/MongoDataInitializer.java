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
            Poster p1 = new Poster();
            p1.setTitle("Inception");
            p1.setImageUrl("https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEhniW0khKSBa.jpg");
            p1.setMovieId(27205L);
            p1.setDescription("Poster officiel du film Inception de Christopher Nolan");
            p1.setPrice(19.99);
            p1.setWidth(60.0);
            p1.setHeight(90.0);
            posterRepository.save(p1);

            Poster p2 = new Poster();
            p2.setTitle("The Dark Knight");
            p2.setImageUrl("https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911BTUNoM8JTol0.jpg");
            p2.setMovieId(155L);
            p2.setDescription("Poster du film The Dark Knight avec Heath Ledger");
            p2.setPrice(24.99);
            p2.setWidth(60.0);
            p2.setHeight(90.0);
            posterRepository.save(p2);

            Poster p3 = new Poster();
            p3.setTitle("Interstellar");
            p3.setImageUrl("https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg");
            p3.setMovieId(157336L);
            p3.setDescription("Poster du film Interstellar de Christopher Nolan");
            p3.setPrice(22.99);
            p3.setWidth(40.0);
            p3.setHeight(60.0);
            posterRepository.save(p3);

            Poster p4 = new Poster();
            p4.setTitle("Pulp Fiction");
            p4.setImageUrl("https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg");
            p4.setMovieId(680L);
            p4.setDescription("Poster culte de Pulp Fiction par Quentin Tarantino");
            p4.setPrice(29.99);
            p4.setWidth(80.0);
            p4.setHeight(120.0);
            posterRepository.save(p4);

            Poster p5 = new Poster();
            p5.setTitle("The Matrix");
            p5.setImageUrl("https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg");
            p5.setMovieId(603L);
            p5.setDescription("Poster emblématique de The Matrix");
            p5.setPrice(21.99);
            p5.setWidth(60.0);
            p5.setHeight(90.0);
            posterRepository.save(p5);
        }
    }
}
