package hu.flowacademy.homeproject.controller;

import hu.flowacademy.homeproject.model.User;
import hu.flowacademy.homeproject.model.dto.UserRequestDTO;
import hu.flowacademy.homeproject.model.dto.UserResponseDTO;
import hu.flowacademy.homeproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> findAllUser () {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO findUserById (@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/users/currentuser")
    public User findMyContact() {
        return userService.findCurrentUser();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser (@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser (@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        return userService.deleteUser(id);
    }



}
