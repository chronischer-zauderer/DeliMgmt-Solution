package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.User;
import Uv.DeliMgmt.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Declara PasswordEncoder

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Inyecta PasswordEncoder
    }

    // Create
    public void createUser(User user) {
        // Codificar la contraseña antes de guardar el usuario
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    // Get all
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get one by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update
    public void updateUser(User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(updatedUser.getUserId());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            // Codificar la contraseña solo si ha cambiado
            if (!updatedUser.getPassword().equals(existingUser.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + updatedUser.getUserId());
        }
    }
    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean validateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Compara la contraseña codificada
            System.out.println(password);
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
    public User getAuthorities(String username, String password) {
        // Verificar si las credenciales son válidas
        if (validateUser(username, password)) {
            Optional<User> userOpt = userRepository.findByUsername(username);
            return userOpt.orElse(null); // Retorna el usuario si se encuentra
        }
        return null; // Retorna null si las credenciales no son válidas
    }

}