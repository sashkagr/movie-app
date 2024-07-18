package by.hribouskaya.app.movie.service.impl;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import by.hribouskaya.app.movie.model.models.dto.MovieDTO;
import by.hribouskaya.app.movie.model.models.dao.Director;
import by.hribouskaya.app.movie.model.models.dao.Movie;
import by.hribouskaya.app.movie.model.repository.DirectorRepository;
import by.hribouskaya.app.movie.model.repository.MovieRepository;
import by.hribouskaya.app.movie.service.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@org.springframework.stereotype.Service
public class MovieServiceImpl implements Service<MovieDTO> {

    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(DirectorRepository directorRepository, ModelMapper modelMapper, MovieRepository movieRepository) {
        this.directorRepository = directorRepository;
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDTO> read() {
        return movieRepository.findAll().stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(toList());
    }

    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        return modelMapper.map(movie, MovieDTO.class);
    }

    public List<MovieDTO> getMovieByDirectorId(Long id, boolean answer) {
        List<Movie> movies = movieRepository.findByDirectorId(id);
        if (movies.isEmpty()) {
            if (answer) {
                throw new RuntimeException("Movies not found");
            }
            return null;
        }
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO create(MovieDTO movieDTO) throws BadRequestException {
        Movie movie = checkConditions(movieDTO);
        movie = movieRepository.save(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    @SneakyThrows
    public List<MovieDTO> createMovies(List<MovieDTO> movieDTOs) throws BadRequestException {
        List<Movie> filteredMovies = movieDTOs.stream()
                .map(movieDTO -> {
                    try {
                        return this.checkConditions(movieDTO);
                    } catch (BadRequestException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        if (filteredMovies.isEmpty()) {
            throw new BadRequestException("Not correct data!");
        }
        List<Movie> savedMovies = movieRepository.saveAll(filteredMovies);

        return savedMovies.stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MovieDTO update(Long id, MovieDTO movieDTO) throws BadRequestException {
        Movie existingMovie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        Movie updatedMovie = checkConditions(movieDTO);
        existingMovie.setDescription(updatedMovie.getDescription());
        existingMovie.setTitle(updatedMovie.getTitle());
        updatedMovie = movieRepository.save(existingMovie);
        return modelMapper.map(updatedMovie, MovieDTO.class);
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    private Movie checkConditions(MovieDTO movieDTO) throws BadRequestException {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        if (movieDTO.getDirectorId() > 0) {
            Director director = directorRepository.findById(movieDTO.getDirectorId())
                    .orElseThrow(() -> new RuntimeException("Director not found"));
            movie.setDirector(director);
        }
        if (movieRepository.checkAvailability(movieDTO.getTitle(), movieDTO.getDescription(), movieDTO.getYear(), movieDTO.getGenre())) {
            throw new BadRequestException("Movie already exists");
        }
        if (movieDTO.getYear() > 1895 && movieDTO.getYear() <= LocalDate.now().getYear()) {
            if (movieDTO.getTitle() != null && movieDTO.getDescription() != null && movieDTO.getGenre() != null) {
                return movie;
            }
        }
        throw new BadRequestException("Not correct data!");
    }

}
