package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    public UserApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    List<User> all() {
        List<User> users = userRepository.findAll();
        log.debug("getBoards().size() 호출전");
        log.debug("getBoards().size() : {} ", users.get(0).getBoards().size());
        log.debug("getBoards().size() 호출 후");
        return users;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id).map(user -> {
//            user.setTitle(newUser.getTitle());
//            user.setContent(newUser.getContent());
            user.getBoards().clear();
            user.getBoards().addAll(newUser.getBoards());
           // user.setBoards(newUser.getBoards());
            for(Board board : user.getBoards()){
                board.setUser(user);
            }
            return userRepository.save(user);
        }).orElseGet(()->{
            newUser.setId(id);
            return userRepository.save(newUser);
        });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }

}
