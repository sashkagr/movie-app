package by.hribouskaya.app.movie.model.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "MOVIE")
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "director_id")
    @JsonIgnore
    private Director director;
    private int year;
    private String title;
    private String genre;
    @Column(columnDefinition = "TEXT")
    private String description;
}
