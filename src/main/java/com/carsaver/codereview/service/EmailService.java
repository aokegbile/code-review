package com.carsaver.codereview.service;

import com.carsaver.codereview.model.User;
import com.carsaver.codereview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
    //CODEREVIEW implement Sending at a later date. Is this a intentional Stub?
    public void sendConfirmation(String email) {
        System.out.print("sending confirmation to " + email);
    }

}
