package by.hribouskaya.app.movie.model.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DirectorDTO {
    private long id;
    private String name;
    private String description;
    private List<Long> movieIds;
}