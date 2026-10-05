package org.example.week4.week5moviecrud.repository;

import org.example.week4.week5moviecrud.domain.Movie;

import java.util.*;

public class MemoryMovieRepository implements MovieRepository{
    Map<Long,Movie> store=new LinkedHashMap<>();
    Long id=0L;

    @Override
    public Movie save(Movie movie) {
        movie.setId(++id);
        store.put(movie.getId(),movie);
        return movie;
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Movie> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Movie update(Movie newm) {
        store.put(newm.getId(),newm);
        return newm;
    }

    @Override
    public void deleteById(long id) {
        store.remove(id);
    }


    public List<Movie> ratingCut(float minR){
        List<Movie> ratingMovie=new ArrayList<>();
        for(Movie movie : store.values()){
            if(movie.getRating()>=minR){
                ratingMovie.add(movie);
            }
        }

        return ratingMovie;
    }
}
