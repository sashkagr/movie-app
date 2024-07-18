package by.hribouskaya.app.movie.service.impl;

import by.hribouskaya.app.movie.model.models.dto.DirectorDTO;
import by.hribouskaya.app.movie.model.models.dto.MovieDTO;
import by.hribouskaya.app.movie.service.Service;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import by.hribouskaya.app.movie.model.models.dao.Director;
import by.hribouskaya.app.movie.model.repository.DirectorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class DirectorServiceImpl implements Service<DirectorDTO> {

    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;
    private final MovieServiceImpl movieService;

    @Autowired
    public DirectorServiceImpl(DirectorRepository directorRepository, ModelMapper modelMapper, MovieServiceImpl movieService) {
        this.directorRepository = directorRepository;
        this.modelMapper = modelMapper;
        this.movieService = movieService;
    }

    @Override
    public List<DirectorDTO> read() {
        return directorRepository.findAll().stream()
                .map(director -> {
                    DirectorDTO directorDTO = modelMapper.map(director, DirectorDTO.class);
                    directorDTO.setMovieIds(getMoviesByIds(directorDTO));
                    return directorDTO;
                })
                .collect(Collectors.toList());
    }

    public DirectorDTO getDirectorById(Long id) {
        Director director = directorRepository.findById(id).orElseThrow(() -> new RuntimeException("Director not found"));
        DirectorDTO directorDTO = modelMapper.map(director, DirectorDTO.class);
        directorDTO.setMovieIds(getMoviesByIds(directorDTO));
        return directorDTO;
    }

    @Override
    public DirectorDTO create(DirectorDTO directorDTO) throws BadRequestException {
        Director director = checkConditions(directorDTO);
        director = directorRepository.save(director);
        return modelMapper.map(director, DirectorDTO.class);
    }

    @SneakyThrows
    public List<DirectorDTO> createDirectors(List<DirectorDTO> directorDTOS) throws BadRequestException {
        List<Director> filteredDirectors = directorDTOS.stream()
                .map(movieDTO -> {
                    try {
                        return this.checkConditions(movieDTO);
                    } catch (BadRequestException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        if (filteredDirectors.isEmpty()) {
            throw new BadRequestException("Not correct data!");
        }
        List<Director> savedDirectors = directorRepository.saveAll(filteredDirectors);

        return savedDirectors.stream()
                .map(director -> modelMapper.map(director, DirectorDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DirectorDTO update(Long id, DirectorDTO directorDTO) throws BadRequestException {
        Director existingDirector = directorRepository.findById(id).orElseThrow(() -> new RuntimeException("Director not found"));
        Director updatedDirector = checkConditions(directorDTO);
        existingDirector.setName(updatedDirector.getName());
        existingDirector.setDescription(updatedDirector.getDescription());
        updatedDirector = directorRepository.save(existingDirector);
        directorDTO = modelMapper.map(updatedDirector, DirectorDTO.class);
        directorDTO.setMovieIds(getMoviesByIds(directorDTO));
        return directorDTO;
    }

    @Override
    public void delete(Long id) {
        directorRepository.deleteById(id);
    }

    private Director checkConditions(DirectorDTO directorDTO) throws BadRequestException {
        Director director = modelMapper.map(directorDTO, Director.class);
        if (directorRepository.checkAvailability(directorDTO.getName(), directorDTO.getDescription())) {
            throw new BadRequestException("Director already exists");
        }
        if (directorDTO.getName() != null && directorDTO.getDescription() != null) {
            return director;
        }
        throw new BadRequestException("Not correct data!");
    }

    private List<Long> getMoviesByIds(DirectorDTO directorDTO) {
        List<MovieDTO> dtos = movieService.getMovieByDirectorId(directorDTO.getId(), false);
        if (dtos != null) {
            List<Long> ids = new ArrayList<>();
            for (MovieDTO dto : dtos) {
                ids.add(dto.getId());
            }
            return ids;
        }
        return null;
    }
}
