package com.smartfactory.erp.service;

import com.smartfactory.erp.entity.User;
import com.smartfactory.erp.repository.UserRePository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRePository userRePository;

    public boolean login(String email, String password){
        User user = userRePository.findByEmail(email);
        if(user != null && user.getPassword().equals(password)){
            return true;
        }
        return false;
    }
}
