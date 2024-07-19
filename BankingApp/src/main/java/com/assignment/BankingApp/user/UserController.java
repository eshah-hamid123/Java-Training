package com.assignment.BankingApp.user;

import com.assignment.BankingApp.login.Login;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/all-users")
    public ResponseEntity<List<UserAccountDTO>> getAllUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "1000") Integer size) {
        List<UserAccountDTO> users = userService.getAllUserAccountDTOs(page, size);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/edit-user/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @Valid @RequestBody User updatedUser) {
        User updated = userService.updateUser(userId, updatedUser);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('admin','account-holder')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserAccountDTO> getUserById(@PathVariable Long userId) {
        UserAccountDTO userAccountDTO = userService.getUserById(userId);
        if (userAccountDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userAccountDTO);
    }

    @PostMapping("/login-user")
    public ResponseEntity<String> login(@RequestBody Login login) {
        Optional<User> userOptional = userService.login(login);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok("{\"message\":\"Login successful\", \"role\":\"" + user.getRole() + "\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
