package com.nisum.usercreation.apiuser.controller;

import com.nisum.usercreation.apiuser.entity.Phone;
import com.nisum.usercreation.apiuser.repository.IPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneController {
    @Autowired
    IPhoneRepository phoneRepository;

    @PostMapping("/phone")
    public ResponseEntity<Phone> save(@RequestBody Phone phone){
        try{
            return new ResponseEntity<>(phoneRepository.save(phone), HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
