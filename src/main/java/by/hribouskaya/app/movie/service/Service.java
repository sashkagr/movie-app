package by.hribouskaya.app.movie.service;

import org.apache.coyote.BadRequestException;

import java.util.List;

public interface Service<T> {
    T create(T entity) throws BadRequestException;

    void delete(Long id);

    T update(Long id, T entity) throws BadRequestException;

    List<T> read();
}
