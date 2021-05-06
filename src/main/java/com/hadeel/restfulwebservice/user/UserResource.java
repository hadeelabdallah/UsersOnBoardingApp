package com.hadeel.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    UserDaoService userDaoService;

    @GetMapping( "/users")
    public List<User> retrieveAll(){
        return userDaoService.findAll();
    }

    @GetMapping( "/users/{id}")
    public User findUser(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("id-"+id);
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user){
        User saved = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}