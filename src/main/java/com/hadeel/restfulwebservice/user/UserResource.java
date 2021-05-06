package com.hadeel.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
    public EntityModel<User> findUser(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("id-"+id);
        }
        //HATEOAS
        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).retrieveAll());
        resource.add(webMvcLinkBuilder.withRel("all-users"));
        return resource;
    }

    @DeleteMapping( "/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = userDaoService.deleteById(id);
        if(user == null){
            throw new UserNotFoundException("id-"+id);
        }
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody User user){
        User saved = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
