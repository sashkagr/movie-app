package by.hribouskaya.app.movie.controller;

import org.apache.coyote.BadRequestException;
import by.hribouskaya.app.movie.model.models.dto.DirectorDTO;
import by.hribouskaya.app.movie.service.impl.DirectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/directors")
public class DirectorController {

    private final DirectorServiceImpl directorService;

    @Autowired
    public DirectorController(DirectorServiceImpl directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public List<DirectorDTO> getAllDirectors() {
        return directorService.read();
    }

    @PostMapping("/all")
    public ResponseEntity<List<DirectorDTO>> addDirectors(@RequestBody final List<DirectorDTO> directorDTOS) throws BadRequestException {
        List<DirectorDTO> directorDTOS1 = directorService.createDirectors(directorDTOS);
        return ResponseEntity.ok(directorDTOS1);
    }

    @GetMapping("/{id}")
    public DirectorDTO getDirectorById(@PathVariable Long id) {
        return directorService.getDirectorById(id);
    }

    @PostMapping
    public DirectorDTO createDirector(@RequestBody DirectorDTO directorDTO) throws BadRequestException {
        return directorService.create(directorDTO);
    }

    @PutMapping("/{id}")
    public DirectorDTO updateDirector(@PathVariable Long id, @RequestBody DirectorDTO directorDTO) throws BadRequestException {
        return directorService.update(id, directorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDirector(@PathVariable Long id) {
        directorService.delete(id);
    }
}
