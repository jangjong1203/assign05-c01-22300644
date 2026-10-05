package org.example.week4.week5moviecrud.repository;

import org.example.week4.week5moviecrud.domain.Movie;

import java.util.List;

public class MemoryMovieRepository implements MovieRepository{
    @Override
    public Movie save(Movie movie) {
        return null;
    }

    @Override
    public List<Movie> findAll() {
        return List.of();
    }

    @Override
    public Movie update(Movie m) {
        return null;
    }

    @Override
    public void deleteById(long m) {

    }
}
