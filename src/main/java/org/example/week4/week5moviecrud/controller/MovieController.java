package org.example.week4.week5moviecrud.controller;

import org.example.week4.week5moviecrud.dto.MovieRequest;
import org.example.week4.week5moviecrud.dto.MovieResponse;
import org.example.week4.week5moviecrud.service.MovieService;
import org.springframework.web.bind.annotation.*;

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
        return movieService.Update(id,request);
    }

    @DeleteMapping("/{id}")
    public void Delete(@PathVariable long id){
        movieService.DeleteById(id);
    }

}
