package com.heitor.cursomc.resources;

import com.heitor.cursomc.domain.dto.EmailDTO;
import com.heitor.cursomc.security.JWTUtil;
import com.heitor.cursomc.security.UserSecuritySpring;
import com.heitor.cursomc.services.AuthService;
import com.heitor.cursomc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResources {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService serviceAuth;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<?> refreshTiken(HttpServletResponse response){
        UserSecuritySpring user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<?> forgot(@Valid @RequestBody EmailDTO emailDTO){
         serviceAuth.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
