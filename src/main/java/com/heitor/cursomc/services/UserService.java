package com.heitor.cursomc.services;

import com.heitor.cursomc.security.UserSecuritySpring;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSecuritySpring authenticated(){
        try {
            return (UserSecuritySpring) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
}
