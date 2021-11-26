package com.example.controller;

import com.example.entity.User;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    // get all Users
      @GetMapping
      public List<User> getAllUsers(){
          return (List<User>) this.userRepository.findAll();
      }

    // get user by id

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") long userId){
          return this.userRepository.findById(userId)
                  .orElseThrow(() -> new ResourceNotFoundException("User Not found with Id :"+userId));
    }

    // create user
    @PostMapping
    public User createUser(@RequestBody User user){
           return this.userRepository.save(user);
    }

    // update user
    @PutMapping("/{id}")
   public User updateUser(@RequestBody User user, @PathVariable(value = "id") long userId){
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not found with Id :"+userId));
        existingUser.setId(userId);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.userRepository.save(existingUser);
   }

    // delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") long userId){
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not found with Id :"+userId));
         this.userRepository.delete(existingUser);
         return ResponseEntity.ok().build();
    }

}
