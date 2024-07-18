package by.hribouskaya.app.movie.model.models.dao;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "DIRECTORS")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Movie> movie = new ArrayList<>();

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
