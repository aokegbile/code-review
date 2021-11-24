package com.carsaver.codereview.service;

import com.carsaver.codereview.model.User;
import com.carsaver.codereview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toMap;

@Service
public class UserService {
    private UserRepository repository;
    private EmailService emailService;

    public UserService (UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }
    //CODEREVIEW should we implement other sorts?
    public List<User> findAll(){
        return repository.findAllByOrderByIdAsc();
    }
    //CODEREVIEW SEMANTIC, I would make this non nullable call, make callers check and handle nulls.
    // Should unused methods be removed?
    public User updateEmail(User user, String email) {
        if (user != null) {
            if (user.getId() != null)
                if (email != null) {
                    user.setEmail(email);
                    repository.save(user);
                    emailService.sendConfirmation(email);
                }
            else
                return user;
        }
        return user;
    }
    //CODEREVIEW Should unused methods be removed?
    public Map<Long, String> getNames() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(toMap(User::getId, user -> user.getFirstName() + ", " + user.getFirstName()));

    }

}
