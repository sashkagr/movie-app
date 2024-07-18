package by.hribouskaya.app.movie.controller;

import by.hribouskaya.app.movie.model.models.dto.MovieDTO;
import by.hribouskaya.app.movie.service.impl.MovieServiceImpl;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/app/movies")
public class MovieController {

    private final MovieServiceImpl movieService;

    @Autowired
    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/all")
    public ResponseEntity<List<MovieDTO>> addMovies(@RequestBody final List<MovieDTO> movies) throws BadRequestException {
        List<MovieDTO> moviesDTO = movieService.createMovies(movies);
        return ResponseEntity.ok(moviesDTO);
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.read());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        MovieDTO movieDTO = movieService.getMovieById(id);
        return ResponseEntity.ok(movieDTO);
    }

    @GetMapping("/by/director/{id}")
    public ResponseEntity<List<MovieDTO>> getMovieByDirectorId(@PathVariable Long id) {
        List<MovieDTO> movieDTOs = movieService.getMovieByDirectorId(id, true);
        return ResponseEntity.ok(movieDTOs);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) throws BadRequestException {
        MovieDTO createdMovie = movieService.create(movieDTO);
        return ResponseEntity.ok(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) throws BadRequestException {
        MovieDTO updatedMovie = movieService.update(id, movieDTO);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}