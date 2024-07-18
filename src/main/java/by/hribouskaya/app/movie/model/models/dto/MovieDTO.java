package by.hribouskaya.app.movie.model.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieDTO {
    private long id;
    private long directorId;
    private String title;
    private String genre;
    private String description;
    private int year;
}
