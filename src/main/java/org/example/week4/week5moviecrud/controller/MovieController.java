package org.example.week4.week5moviecrud.controller;

import org.example.week4.week5moviecrud.dto.MovieRequest;
import org.example.week4.week5moviecrud.dto.MovieResponse;
import org.example.week4.week5moviecrud.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/movies")

public class MovieController {
    MovieService movieService;
    MovieController (MovieService movieService){
        this.movieService=movieService;
    }

    @PostMapping
    public MovieResponse Post(@RequestBody MovieRequest request){
        if(request.title()==null||request.title().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"title is empty");
        }else if(request.director()==null||request.director().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "director is empty");
        }else if(request.genre()==null||request.genre().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"genre is empty");
        }
        if(request.year()<0||request.year()>2026){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"year is wrong(input:0~2026)");
        } else if(request.rating()<0||request.rating()>10){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"rating is wrong(input:0.0~10.0)");
        }
        return movieService.Create(request);
    }

    @GetMapping
    public List<MovieResponse> GetAll(){
        return movieService.findAll();
    }
    @GetMapping("/{id}")
    public MovieResponse GetById(@PathVariable long id){
        return movieService.findById(id);
    }

    @PutMapping("/{id}")
    public MovieResponse Put(@PathVariable long id,@RequestBody MovieRequest request){
        if(request.title()==null||request.title().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"title is empty");
        }else if(request.director()==null||request.director().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "director is empty");
        }else if(request.genre()==null||request.genre().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"genre is empty");
        }
        if(request.year()<0||request.year()>2026){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"year is wrong(input:0~2026)");
        } else if(request.rating()<0||request.rating()>10){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"rating is wrong(input:0.0~10.0)");
        }
        return movieService.Update(id,request);
    }

    @DeleteMapping("/{id}")
    public void Delete(@PathVariable long id){
        movieService.DeleteById(id);
    }

    @GetMapping("/rating/{minR}")
    public List<MovieResponse> Rating(@PathVariable float minR){
        return movieService.ratingCut(minR);
    }

}
