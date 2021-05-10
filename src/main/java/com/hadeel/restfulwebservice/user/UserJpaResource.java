package com.hadeel.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping( "/jpa/users")
    public List<User> retrieveAll(){
        return userRepository.findAll();
    }

    @GetMapping( "/jpa/users/{id}")
    public EntityModel<User> findUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id-"+id);
        }
        //HATEOAS
        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(methodOn(this.getClass()).retrieveAll());
        resource.add(webMvcLinkBuilder.withRel("all-users"));
        return resource;
    }

    @DeleteMapping( "/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/jpa/users")
    public ResponseEntity createUser(@Valid @RequestBody User user, HttpServletRequest request) throws URISyntaxException {
        User saved = userRepository.save(user);
        URI location = new URI(request.getRequestURL() +"/" + saved.getId());
       // URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("user-id: " + id);
        }
        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post, HttpServletRequest request) throws URISyntaxException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("user-id: " + id);
        }
        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);
        URI location = new URI(request.getRequestURL() +"/" + post.getId());
        return ResponseEntity.created(location).build();
    }
}
