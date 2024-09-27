package Uv.DeliMgmt.backend.Services;

import Uv.DeliMgmt.backend.Models.User;
import Uv.DeliMgmt.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create
    public void createUser(User user) {
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
            existingUser.setPassword(updatedUser.getPassword());
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
        return false;
    }
}
