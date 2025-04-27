package com.amu.controller;

import com.amu.config.JwtProvider;
import com.amu.dto.auth.AuthResponse;
import com.amu.dto.login.LoginRequest;
import com.amu.entities.CustomUserDetails;
import com.amu.entities.User;
import com.amu.exception.AuthenticationException;
import com.amu.repositories.UserDetailsRepository;
import com.amu.repositories.UserRepository;
import com.amu.service.CustomerUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.amu.entities.UserDetails;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserServiceImpl customUserDetails;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        // Kiểm tra các trường thông tin cần thiết
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, "Email không được để trống", false));
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, "Mật khẩu không được để trống", false));
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, "Email đã tồn tại", false));
        }

        // Tạo mới User và lưu vào database
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole() != null ? user.getRole() : "ROLE_USER");

        User savedUser = userRepository.save(newUser);

        // Tạo mới UserDetails và liên kết với User
        UserDetails newUserDetails = new UserDetails();
        newUserDetails.setUser(savedUser);

        // Thiết lập thông tin mặc định cho UserDetails
        newUserDetails.setAvatarUrl("");
        newUserDetails.setPhoneNumber("");
        newUserDetails.setAddress("");
        newUserDetails.setDateOfBirth(null);
        newUserDetails.setGithubUrl("");
        newUserDetails.setJobTitle("");
        newUserDetails.setCompanyName("");
        newUserDetails.setCreatedAt(LocalDateTime.now());
        newUserDetails.setUpdatedAt(LocalDateTime.now());

        // Lưu UserDetails vào database
        userDetailsRepository.save(newUserDetails);

        // Cấp token JWT và trả về phản hồi
        CustomUserDetails userDetailsForResponse = (CustomUserDetails) customUserDetails.loadUserByUsername(savedUser.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetailsForResponse, null, userDetailsForResponse.getAuthorities()
        );
        String token = jwtProvider.generateToken(authentication);

        return ResponseEntity.ok(new AuthResponse(token, "Đăng ký thành công", true));
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token, "Đăng nhập thành công", true));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "Invalid username or password", false));
        }
    }

    private Authentication authenticate(String username, String password) {
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetails.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
