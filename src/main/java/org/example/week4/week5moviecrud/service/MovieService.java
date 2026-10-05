package org.example.week4.week5moviecrud.service;




import org.example.week4.week5moviecrud.domain.Movie;
import org.example.week4.week5moviecrud.dto.MovieRequest;
import org.example.week4.week5moviecrud.dto.MovieResponse;
import org.example.week4.week5moviecrud.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
        private MovieRepository repository;
        public MovieService(MovieRepository repository){
            this.repository=repository;
        }

        public MovieResponse Create(MovieRequest MRQ){
                Movie m=new Movie(0,MRQ.title(),MRQ.director(),MRQ.genre(),MRQ.year(),MRQ.rating());
                return toResponse(repository.save(m));
        }
        public List<MovieResponse> findAll() {
            List<Movie> m=repository.findAll();
            return m.stream().map(this::toResponse).toList();
        }

    public MovieResponse findById(long id){

    }

    public MovieResponse Upadte(long id, MovieRequest m){

    }

    public MovieResponse DelleteById(long id){

    }

        private MovieResponse toResponse(Movie m){
                return new MovieResponse(null,m.getTitle(),m.getDirector(),m.getGenre(),m.getYear(),m.getRating());
        }



}
