package org.example.week4.week5moviecrud.repository;

import org.example.week4.week5moviecrud.domain.Movie;

import java.util.List;

public interface MovieRepository {
     Movie save(Movie movie);
     List<Movie> findAll();
     Movie update(Movie m);
     void deleteById(long m);
}
