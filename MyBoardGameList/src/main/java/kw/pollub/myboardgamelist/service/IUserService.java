package kw.pollub.myboardgamelist.service;

import kw.pollub.myboardgamelist.model.User;

import java.util.List;

public interface IUserService {

    List<User> findAllUsers();
    User findUserById(Long userId);
    User findUserByEmail(String email);
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Long userId);

}
