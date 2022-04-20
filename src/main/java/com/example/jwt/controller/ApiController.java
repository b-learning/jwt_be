package com.example.jwt.controller;


import com.example.jwt.dto.UserSecurity;
import com.example.jwt.dto.request.LoginForm;
import com.example.jwt.entity.UserEntity;
import com.example.jwt.jwt.TokenHandler;
import com.example.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    /*
        - POST : /login : ko phân quyền
        - POST : /signup: ko phân quyền
        - GET  : /getdata: chỉ user và admin đều truy cập đc
        - GET  : /getusers: chỉ admin sử dụng đc
    * */

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenHandler tokenHandler;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity login(@RequestBody LoginForm form){
        // Authentication: Xác thực
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                form.getUsername(),
                form.getPassword()
            )
        );

        // nếu nó chạy đc ra tới đây => user này có thật => genarate token
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenHandler.generateToken((UserSecurity) authentication.getPrincipal());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody LoginForm form){
        // check username password

        // neu dung username, password => generate token

        UserEntity userEntity = new UserEntity(-1, form.getUsername(), form.getPassword(), "ROLE_USER");
        System.out.println(passwordEncoder);
        userEntity.setPassword(passwordEncoder.encode(form.getPassword()));
        userRepository.save(userEntity);
        return ResponseEntity.ok("Sign up user "+form.getUsername()+" success!");
    }

    @GetMapping("/getdata")
    public ResponseEntity getData(){
        return ResponseEntity.ok("Get data success");
    }

    @GetMapping("/getusers")
    public ResponseEntity getUsers(){
        return ResponseEntity.ok("Get user success");
    }


}
