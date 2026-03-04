package com.servlet;

import com.repository.PosterDao;
import com.dto.PosterDto;
import com.dto.PosterInputDto;
import com.entity.Poster;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.PosterMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servlet pour la gestion des posters via MongoDB.
 * Endpoints :
 *   GET    /poster       → liste tous les posters
 *   GET    /poster/{id}  → récupère un poster par ID
 *   POST   /poster       → crée un poster (multipart avec 'image' et 'data')
 *   PUT    /poster/{id}  → modifie un poster
 *   DELETE /poster/{id}  → supprime un poster
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2 MB
        maxFileSize = 1024 * 1024 * 10,       // 10 MB
        maxRequestSize = 1024 * 1024 * 50     // 50 MB
)
public class PosterServlet extends HttpServlet {

    private PosterDao posterDao;
    private PosterMapper posterMapper;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.posterDao = ctx.getBean(PosterDao.class);
        this.posterMapper = ctx.getBean(PosterMapper.class);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // GET /poster → liste tous les posters
            List<PosterDto> posters = posterDao.findAll().stream()
                    .map(posterMapper::toDto)
                    .collect(Collectors.toList());
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), posters);
        } else {
            // GET /poster/{id}
            String id = pathInfo.substring(1);
            Optional<Poster> poster = posterDao.findById(id);
            if (poster.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), posterMapper.toDto(poster.get()));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), new ErrorResponse("Poster non trouvé"));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Récupération des données JSON envoyées en tant que `data` (String part)
        Part dataPart = req.getPart("data");
        if (dataPart == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse("Les métadonnées 'data' sont obligatoires"));
            return;
        }
        PosterInputDto input = objectMapper.readValue(dataPart.getInputStream(), PosterInputDto.class);

        if (input.getTitreFilm() == null || input.getTitreFilm().isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse("Le titre est obligatoire"));
            return;
        }

        // Récupération de l'image (Part)
        Part imagePart = req.getPart("image");
        if (imagePart == null || imagePart.getSize() == 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse("Le fichier 'image' est obligatoire"));
            return;
        }

        Poster poster = posterMapper.toEntity(input);
        
        // La sauvegarde dans GridFS sera gérée par le DAO et nous retournera l'ID du fichier GridFS ou l'entité mise à jour
        String filename = imagePart.getSubmittedFileName();
        String contentType = imagePart.getContentType();
        try (InputStream imageStream = imagePart.getInputStream()) {
            // posterDao gère dorénavant l'upload GridFS ! (cf refactorisation Dao)
            poster = posterDao.saveWithImage(poster, imageStream, filename, contentType);
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getWriter(), posterMapper.toDto(poster));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse("ID obligatoire"));
            return;
        }

        String id = pathInfo.substring(1);
        Optional<Poster> existing = posterDao.findById(id);
        if (existing.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse("Poster non trouvé"));
            return;
        }

        // Note : En put classique, on pourrait aussi gérer Multipart si l'image change, mais commençons par les métadonnées pour l'API OpenAPI
        PosterInputDto input = objectMapper.readValue(req.getReader(), PosterInputDto.class);
        Poster poster = existing.get();
        posterMapper.updateEntity(input, poster);
        poster = posterDao.save(poster);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getWriter(), posterMapper.toDto(poster));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse("ID obligatoire"));
            return;
        }

        String id = pathInfo.substring(1);
        if (!posterDao.existsById(id)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse("Poster non trouvé"));
            return;
        }

        posterDao.deleteById(id); // Dao devra aussi supprimer l'image GridFS
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * Classe interne pour les réponses d'erreur.
     */
    private record ErrorResponse(String message) {}
}
