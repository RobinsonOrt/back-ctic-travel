package com.robinson.ctic_travel.daos.usersDao;

import com.robinson.ctic_travel.models.Users;

import java.util.Optional;

public interface UsersDao {
    void registerUser(Users user);
    Optional<Users> findByEmail(String email);
}
