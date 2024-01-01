package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.config.UserDetailsImpl;
import kw.pollub.myboardgamelist.model.User;
import kw.pollub.myboardgamelist.exception.UserAlreadyExistsException;
import kw.pollub.myboardgamelist.exception.UserNotFoundException;
import kw.pollub.myboardgamelist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsersByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return UserDetailsImpl.build(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUsersByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User addUser(User user) {

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        //TODO
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        //TODO
    }
}
