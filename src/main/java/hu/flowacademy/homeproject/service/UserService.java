package hu.flowacademy.homeproject.service;
import hu.flowacademy.homeproject.Exception.ValidationException;
import hu.flowacademy.homeproject.model.Role;
import hu.flowacademy.homeproject.model.User;
import hu.flowacademy.homeproject.model.dto.UserRequestDTO;
import hu.flowacademy.homeproject.model.dto.UserResponseDTO;
import hu.flowacademy.homeproject.repository.RecipeRepository;
import hu.flowacademy.homeproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        if (userRepository.findAll().size() > 0) {
            List<User> users = userRepository.findAll();
            List<UserResponseDTO> listOfUserResponseDTO = users.stream().map(user -> {
                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setRecipes(user.getRecipes());
                userResponseDTO.setDateOfBirth(user.getDateOfBirth());
                userResponseDTO.setLastName(user.getLastName());
                userResponseDTO.setFirstName(user.getFirstName());
                userResponseDTO.setEmail(user.getEmail());
                userResponseDTO.setId(user.getId());
                userResponseDTO.setUsername(user.getUsername());
                return userResponseDTO;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(listOfUserResponseDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public UserResponseDTO findUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setEmail(user.getEmail());
            userResponseDTO.setFirstName(user.getFirstName());
            userResponseDTO.setRecipes(user.getRecipes());
            userResponseDTO.setLastName(user.getLastName());
            userResponseDTO.setDateOfBirth(user.getDateOfBirth());
            userResponseDTO.setId(user.getId());
            userResponseDTO.setUsername(user.getUsername());
            return userResponseDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()) == null) {
            userRequestDTO.toEntity();
            User user = new User();
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            user.setRole(Role.USER);
            user.setUsername(userRequestDTO.getUsername());
            user.setFirstName(userRequestDTO.getFirstName());
            user.setLastName(userRequestDTO.getLastName());
            user.setEmail(userRequestDTO.getEmail());
            user.setDateOfBirth(userRequestDTO.getDateOfBirth());
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            throw new ValidationException("A megadott email cím már használatban van");
        }
    }


    public ResponseEntity<Void> updateUser(User user, Long id) {
        if (userRepository.findById(id).isPresent()) {
            user.setUsername(userRepository.findById(id).get().getUsername());
            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<Void> deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public User findCurrentUser () {
        String currentUserName = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(
                Authentication::getName).orElse("");
        return userRepository.findByUsername(currentUserName);
    }

    public void passwordValidation(User user) {
        if(!user.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")){
            throw new ValidationException("A jelszónak tartalmaznia kell legalább 8 karaktert," +
                    "legalább egy nagybetűt, egy kisbetűt, egy számot és egy speciális karaktert. ");
        }
    }
}
