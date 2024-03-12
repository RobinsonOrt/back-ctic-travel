package com.robinson.ctic_travel.daos.usersDao;

import com.robinson.ctic_travel.models.Users;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class UsersDaoImplement implements UsersDao{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void registerUser(Users user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return entityManager.createQuery("FROM Users u WHERE u.userEmail = :userEmail", Users.class)
                .setParameter("userEmail", email)
                .getResultList()
                .stream()
                .findFirst();
    }
}
