package com.nisum.usercreation.apiuser.controller;

import com.nisum.usercreation.apiuser.config.JwtTokenUtil;
import com.nisum.usercreation.apiuser.entity.Phone;
import com.nisum.usercreation.apiuser.entity.User;
import com.nisum.usercreation.apiuser.repository.IUserRepository;
import com.nisum.usercreation.apiuser.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @PostMapping("/registerUser")
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
            String validFields = validateUserFields(user);
            if(validFields.equals("Correct")){
                String newUserIdentifier = UUID.randomUUID().toString();
                System.out.println("UUID: " + newUserIdentifier);
                user.setId(newUserIdentifier);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String formattedDate = formatter.format(date);
                user.setCreated(formattedDate);

                // encrypt password before saving it into database
                user.setPassword(bcryptEncoder.encode(user.getPassword()));

                user.setLastLogin(formattedDate);
                String token = jwtTokenUtil.generateTokenForNewUser(user.getName());
                user.setToken(token);
                return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
            } else {
                throw new IllegalArgumentException(validFields);
            }
        } catch (Exception exception){
            System.out.println("El error fue: " + exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Verify that the fields on the received user are all valid
     * @param user
     * @return
     */
    private String validateUserFields(User user){
        String result = "Correct";
        if(!isValidEmailAddress(user.getEmail()) || user == null){
            result = "Invalid email format";
        } else {
            if(emailAlreadyExists(user.getEmail()) || user.getEmail().isEmpty()){
                result = "empty or a user with that email already exists on database";
            } else {
                if(!user.getPassword().isEmpty()) {
                    if (!validatePasswordStrength(user.getPassword())) {
                        result = "weak password";
                    }
                } else {
                    result = "empty password";
                }
            }
        }

        return result;
    }

    /**
     * Validates if a given email address is correct
     * @param email
     * @return
     */
    private boolean isValidEmailAddress(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        boolean result = true;
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            result = false;
        }
        return result;
    }

    /**
     * Checks if there is a user using the given email
     * @param email
     * @return
     */
    private boolean emailAlreadyExists(String email){
        boolean result = false;
        if(userRepository.findByEmail(email) != null){
            result = true;
        }

        return result;
    }

    /**
     * Validates the strength of a given password
     * @param password
     * @return
     */
    /*private boolean validatePasswordStrength(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        // if the password doesn't contain at least 1 lowercase letter, 1 uppercase letter and 2 digits

        return matcher.matches();
    }*/

    /**
     * Validates the strength of a given password
     * @param password
     * @return
     */
    private boolean validatePasswordStrength(String password){
        boolean lowerCase = false;
        boolean upperCase = false;
        boolean firstNumber = false;
        boolean secondNumber = false;

        for(int i =0; i < password.length(); i++){
            if(Character.isLowerCase(password.charAt(i)))
                lowerCase = true;
            if(Character.isUpperCase(password.charAt(i)))
                upperCase = true;
            if(Character.isDigit(password.charAt(i))) {
                if(!firstNumber)
                    firstNumber = true;
                else
                    secondNumber = true;
            }
            if(lowerCase && upperCase && firstNumber && secondNumber)
                break;
        }

        boolean result = lowerCase && upperCase && firstNumber && secondNumber ? true : false;
        return result;
    }

}
