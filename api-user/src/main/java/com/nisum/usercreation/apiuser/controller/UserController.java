package com.nisum.usercreation.apiuser.controller;

import com.nisum.usercreation.apiuser.entity.Phone;
import com.nisum.usercreation.apiuser.entity.User;
import com.nisum.usercreation.apiuser.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    IUserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity<User> save(@RequestBody User user){
        try{
            System.out.println("Datos del usuario: ");
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            System.out.println(user.getPassword());
            System.out.println("Datos de los fones:");
            if(user.getPhones() != null){
                if(user.getPhones().size() > 0){
                    for (Phone phone : user.getPhones()){
                        System.out.println(phone.getNumber());
                        System.out.println(phone.getCitycode());
                        System.out.println(phone.getContrycode());
                    }
                } else {
                    System.out.println("El listado de fones esta vacio");
                }
            }
            String newUserIdentifier = UUID.randomUUID().toString();
            System.out.println("UUID: " + newUserIdentifier);
            user.setId(newUserIdentifier);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String formattedDate = formatter.format(date);
            user.setCreated(formattedDate);
            user.setLastLogin(formattedDate);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception exception){
            System.out.println("El error fue: " + exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
