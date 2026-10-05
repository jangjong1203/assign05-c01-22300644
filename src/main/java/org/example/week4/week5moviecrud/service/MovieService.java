package org.example.week4.week5moviecrud.service;




import org.example.week4.week5moviecrud.domain.Movie;
import org.example.week4.week5moviecrud.dto.MovieRequest;
import org.example.week4.week5moviecrud.dto.MovieResponse;
import org.example.week4.week5moviecrud.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
        Movie m=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not found: "+id));
        return toResponse(m);
    }

    public MovieResponse Update(long id, MovieRequest m){
        Movie fM=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not found: "+id));
        fM.setTitle(m.title());
        fM.setDirector(m.director());
        fM.setGenre(m.genre());
        fM.setYear(m.year());
        fM.setRating(m.rating());
        return toResponse(repository.update(fM));
    }

    public void DeleteById(long id){
        repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not found: "+id));
        repository.deleteById(id);
    }

        private MovieResponse toResponse(Movie m){
                return new MovieResponse(m.getId(),m.getTitle(),m.getDirector(),m.getGenre(),m.getYear(),m.getRating());
        }


        public List<MovieResponse> ratingCut(float minR){
            List<MovieResponse> ResponseM=new ArrayList<>();
            List<Movie> ratingMovie=repository.ratingCut(minR);
            for(Movie movie : ratingMovie){
                    ResponseM.add(toResponse(movie));
            }
            return ResponseM;
        }


}
