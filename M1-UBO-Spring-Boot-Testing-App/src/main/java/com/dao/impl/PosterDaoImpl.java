package com.dao.impl;

import com.dao.PosterDao;
import com.entities.Poster;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation DAO pour l'accès aux données des posters via le driver MongoDB natif.
 */
@Component
public class PosterDaoImpl implements PosterDao {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    private MongoCollection<Document> collection;

    @PostConstruct
    public void init() {
        MongoClient client = MongoClients.create(mongoUri);
        MongoDatabase database = client.getDatabase("poster_db");
        collection = database.getCollection("posters");
    }

    @Override
    public Poster save(Poster poster) {
        if (poster.getId() == null) {
            Document doc = toDocument(poster);
            collection.insertOne(doc);
            poster.setId(doc.getObjectId("_id").toHexString());
        } else {
            collection.replaceOne(Filters.eq("_id", new ObjectId(poster.getId())), toDocument(poster));
        }
        return poster;
    }

    @Override
    public Optional<Poster> findById(String id) {
        try {
            Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            return Optional.ofNullable(doc).map(this::fromDocument);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Poster> findAll() {
        List<Poster> posters = new ArrayList<>();
        for (Document doc : collection.find()) {
            posters.add(fromDocument(doc));
        }
        return posters;
    }

    @Override
    public List<Poster> findByMovieId(Long movieId) {
        List<Poster> posters = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("movieId", movieId))) {
            posters.add(fromDocument(doc));
        }
        return posters;
    }

    @Override
    public boolean existsById(String id) {
        try {
            return collection.find(Filters.eq("_id", new ObjectId(id))).first() != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void deleteById(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    @Override
    public long count() {
        return collection.countDocuments();
    }

    private Document toDocument(Poster poster) {
        Document doc = new Document();
        if (poster.getId() != null) {
            doc.append("_id", new ObjectId(poster.getId()));
        }
        doc.append("title", poster.getTitle())
           .append("imageUrl", poster.getImageUrl())
           .append("movieId", poster.getMovieId())
           .append("description", poster.getDescription())
           .append("price", poster.getPrice())
           .append("width", poster.getWidth())
           .append("height", poster.getHeight());
        return doc;
    }

    private Poster fromDocument(Document doc) {
        Poster poster = new Poster();
        poster.setId(doc.getObjectId("_id").toHexString());
        poster.setTitle(doc.getString("title"));
        poster.setImageUrl(doc.getString("imageUrl"));
        poster.setMovieId(doc.getLong("movieId"));
        poster.setDescription(doc.getString("description"));
        poster.setPrice(doc.getDouble("price"));
        poster.setWidth(doc.getDouble("width"));
        poster.setHeight(doc.getDouble("height"));
        return poster;
    }
}
