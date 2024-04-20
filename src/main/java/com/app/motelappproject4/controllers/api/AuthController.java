package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.auth.JwtUntil;
import com.app.motelappproject4.models.*;
import com.app.motelappproject4.dtos.LoginReq;
import com.app.motelappproject4.dtos.ErrorRes;
import com.app.motelappproject4.dtos.LoginRes;
import com.app.motelappproject4.services.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rest/auth")
public class AuthController {
    private AuthService authService;
    @Autowired
    private JwtUntil jwtUntil;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PostRepository postRepository;
//    private final AuthenticationManager authenticationManager;
//    public AuthController(AuthenticationManager authenticationManager, JwtUntil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//    }

//    @ResponseBody
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ResponseEntity login(@RequestBody LoginReq loginReq) {
////        try {
//////            Authentication authentication =
//////                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
//////            String email = authentication.getName();
////            User user = usersRepository.findUserByEmail(loginReq.getEmail());
////            String token = jwtUntil.createToken(user);
////            LoginRes loginRes = new LoginRes(user.getId(),user.getEmail(),token);
////            return ResponseEntity.ok(loginRes);
////        }catch (BadCredentialsException e){
////            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
////        }catch (Exception e){
////            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
////        }
//        System.out.println(loginReq.getEmail() + " " + loginReq.getPassword());
//        return ResponseEntity.ok(loginReq);
//    }

    @GetMapping("/api/getAllPost")
    public List<Post> index() {
        return (List<Post>) postRepository.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login2(@RequestBody LoginReq loginReq) {
        User user = usersRepository.findUserByEmail(loginReq.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("Email không tồn tại");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu không chính xác");
        }
        var token = jwtUntil.createToken(user);

        var role = userRoleRepository.findUserRoleByUser(user);
        System.out.println(role);
        LoginRes loginRes = new LoginRes(user.getId(), user.getEmail(), token, role.getRole().getName());
        return ResponseEntity.ok().body(loginRes);
    }

    @RequestMapping(value = "/regis/{isOwner}", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody User user, @PathVariable boolean isOwner) {
        User userExist = usersRepository.findUserByEmail(user.getEmail());
        if (userExist != null) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        try{
            usersRepository.save(user);

            UserRole userRole = new UserRole();

            userRole.setUser(user);

            Role r = new Role();

            r.setId(isOwner == true ? 2 : 3);

            userRole.setRole(r);

            userRoleRepository.save(userRole);

            return ResponseEntity.ok().body(user);
        }catch (Exception ex){
            return ResponseEntity.status(400).body(ex);
        }

    }
}
