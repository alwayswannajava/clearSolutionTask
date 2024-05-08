package com.spring.clearsolutions.repository;

import com.spring.clearsolutions.domain.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Log4j2
public class UserRepository {

    private final NavigableMap<Integer, User> users = new ConcurrentSkipListMap<>();

    private AtomicInteger userCounter = new AtomicInteger();


    public int saveUser(User user) {
        log.info("Repo level. Saving user: {}", user);
        int userId = user.getId();
        if (userId < 1) {
            user.setId(userCounter.incrementAndGet());
        }
        users.put(userCounter.get(), user);
        return userId;
    }

    public List<User> searchByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
        log.info("Repo level. Searching by birth date from: {}, to: {} ", fromDate.toString(), toDate.toString());
        List<User> usersList = new ArrayList<>();
        for (var entry : users.descendingMap().entrySet()) {
            if(entry.getValue().getBirthDate().isAfter(fromDate) && entry.getValue().getBirthDate().isBefore(toDate)) {
                usersList.add(entry.getValue());
            }
        }
        return usersList;
    }

    public Optional<User> get(int userId) {
        log.info("Repo level. Getting user: {}", userId);
        return Optional.ofNullable(users.get(userId));
    }

    public boolean delete(int userId) {
        log.info("Repo level. Deleting user by id: {}", userId);
        return users.remove(userId) != null;
    }


}
