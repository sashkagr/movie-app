package by.hribouskaya.app.movie.model.repository;

import by.hribouskaya.app.movie.model.models.dao.Director;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DirectorRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public DirectorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Director> findAll() {
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("from Director", Director.class).list();
        }
    }

    @Transactional
    public Optional<Director> findById(Long id) {
        try (var session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Director.class, id));
        }
    }

    @Transactional
    public Director save(Director director) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.saveOrUpdate(director);
            transaction.commit();
            return director;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            Director director = session.get(Director.class, id);
            if (director != null) {
                session.delete(director);
            }
            transaction.commit();
        }
    }

    @Transactional
    public boolean checkAvailability(String name, String description) {
        try (var session = sessionFactory.openSession()) {
            var query = session.createQuery("from Director where name = :name and description = :description", Director.class);
            query.setParameter("name", name);
            query.setParameter("description", description);
            if (query.getResultList().isEmpty()) {
                return false;
            }
            return true;
        }
    }

    @Transactional
    public List<Director> saveAll(List<Director> directors) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            List<Director> savedDirectors = new ArrayList<>();
            for (Director director : directors) {
                session.saveOrUpdate(director);
                savedDirectors.add(director);
            }
            transaction.commit();
            return savedDirectors;
        }
    }
}
