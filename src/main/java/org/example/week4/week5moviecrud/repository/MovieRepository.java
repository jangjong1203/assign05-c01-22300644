package org.example.week4.week5moviecrud.repository;

import org.example.week4.week5moviecrud.domain.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface MovieRepository {
     Movie save(Movie movie);
     List<Movie> findAll();
     Optional<Movie> findById(long id);
     Movie update(Movie m);
     void deleteById(long m);
}
