package com.carsaver.codereview.web.api;

import com.carsaver.codereview.model.User;
import com.carsaver.codereview.repository.UserRepository;
import com.carsaver.codereview.service.ZipCodeLookupService;
import com.carsaver.codereview.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
//CODEREVIEW SHould this be replaced with rest controller? or add requestbody to responses?
//Should the views be implemented?
@Controller
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ZipCodeLookupService zipCodeLookupService;
// CODEREVIEW. Should this be a post? Should it be passing a object?
    @GetMapping(value = "/users/create", params={"firstName","lastName","email"})
    public User createuser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
        return createuser(firstName,lastName,email,null,null);
    }

    @GetMapping(value="/users/create", params={"firstName","lastName","email","zipCode","city"})
    public User createuser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
                           @RequestParam String zipCode, @RequestParam String city) {
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setZipCode(zipCode);
        newUser.setCity(city);

        if(!email.contains("@test.com")) {
            newUser.enabled = true;
        }

        User user = userRepository.save(newUser);

        if(user.isEnabled()) {
            emailService.sendConfirmation(email);
        }

        return user;
    }

    /**
     * updates user's address
     * @param id - assume valid existing id
     * @param zipCode - assume valid zipCode
     * @param city - assume valid if present otherwise null
     * @return updated User
     */
    @GetMapping("/users/updateLocation")
    public User updateUserLocation(@RequestParam Long id, @RequestParam String zipCode, @RequestParam(required = false) String city) {
        User user = userRepository.findById(id).orElseThrow();

        user.setZipCode(zipCode);
        user.setCity(Optional.ofNullable(city).orElse(zipCodeLookupService.lookupCityByZip(zipCode)));

        return userRepository.save(user);
    }
    //CODEREVIEW replace get mapping with update location
    @DeleteMapping("/users/delete")
    public void deleteUser(@RequestParam String userid) {
        userRepository.deleteById(Long.parseLong(userid));
    }
}
