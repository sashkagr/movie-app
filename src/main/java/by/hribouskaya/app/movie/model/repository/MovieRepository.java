package by.hribouskaya.app.movie.model.repository;

import by.hribouskaya.app.movie.model.models.dao.Movie;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public MovieRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Movie> findAll() {
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("from Movie", Movie.class).list();
        }
    }

    @Transactional
    public Optional<Movie> findById(Long id) {
        try (var session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Movie.class, id));
        }
    }

    @Transactional
    public Movie save(Movie movie) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.saveOrUpdate(movie);
            transaction.commit();
            return movie;
        }
    }

    @Transactional
    public List<Movie> saveAll(List<Movie> movies) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            List<Movie> savedMovies = new ArrayList<>();
            for (Movie movie : movies) {
                session.saveOrUpdate(movie);
                savedMovies.add(movie);
            }
            transaction.commit();
            return savedMovies;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            Movie movie = session.get(Movie.class, id);
            if (movie != null) {
                session.delete(movie);
            }
            transaction.commit();
        }
    }

    @Transactional
    public boolean checkAvailability(String title, String description, Integer year, String genre) {
        try (var session = sessionFactory.openSession()) {
            var query = session.createQuery("from Movie where title = :title and description = :description and year = :year and genre = :genre", Movie.class);
            query.setParameter("title", title);
            query.setParameter("description", description);
            query.setParameter("year", year);
            query.setParameter("genre", genre);
            if (query.getResultList().isEmpty()) {
                return false;
            }
            return true;
        }
    }

    @Transactional
    public List<Movie> findByDirectorId(Long directorId) {
        try (var session = sessionFactory.openSession()) {
            var query = session.createQuery("from Movie where director.id = :directorId", Movie.class);
            query.setParameter("directorId", directorId);
            return query.getResultList();
        }
    }
}