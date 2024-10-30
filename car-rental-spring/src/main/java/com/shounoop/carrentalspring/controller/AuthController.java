package com.shounoop.carrentalspring.controller;

import com.shounoop.carrentalspring.dto.AuthenticationRequest;
import com.shounoop.carrentalspring.dto.AuthenticationResponse;
import com.shounoop.carrentalspring.dto.SignupRequest;
import com.shounoop.carrentalspring.dto.UserDto;
import com.shounoop.carrentalspring.dto.ForgotPasswordRequest;
import com.shounoop.carrentalspring.dto.ResetPasswordRequest;
import com.shounoop.carrentalspring.entity.User;
import com.shounoop.carrentalspring.repository.UserRepository;
import com.shounoop.carrentalspring.services.auth.AuthService;
import com.shounoop.carrentalspring.services.jwt.UserService;
import com.shounoop.carrentalspring.utils.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final JavaMailSender emailSender;
    @PostMapping("/signup/customer")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("Customer already exists", HttpStatus.NOT_ACCEPTABLE);

        UserDto createdCustomerDto = authService.createCustomer(signupRequest);

        if (createdCustomerDto == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            sendEmail(
                    signupRequest.getEmail(),
                    "Welcome to Our Service",
                    "Dear " + createdCustomerDto.getName() + ",<br><br>Thank you for signing up with us!<br><br>Best regards,<br>Your Team"
            );
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send welcome email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }

    @PostMapping("/signup/agentimmob")
    public ResponseEntity<?> signupAgent(@RequestBody SignupRequest signupRequest) {
        if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("Agent already exists", HttpStatus.NOT_ACCEPTABLE);

        UserDto createdAgentDto = authService.createAgentImmobilier(signupRequest);

        if (createdAgentDto == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            sendEmail(
                    signupRequest.getEmail(),
                    "Welcome to Our Service",
                    "Dear " + createdAgentDto.getName() + ",<br><br>Thank you for signing up as an agent with us!<br><br>Best regards,<br>Your Team"
            );
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send welcome email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(createdAgentDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Incorrect Email Or Password.");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());

        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        authService.initiatePasswordReset(email);
        return ResponseEntity.ok("If an account with that email exists, a password reset link will be sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean success = authService.resetPassword(token, newPassword);
        if (success) {
            return ResponseEntity.ok("Password has been reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }
    }

    private void sendEmail(String to, String subject, String text ) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<html><body style='font-family: Arial, sans-serif;'>");

        // Cadre bleu avec le texte du sujet
        emailContent.append("<div style='background-color:#007bff; color:#fff; padding:10px; text-align:center;'>");
        emailContent.append("<h1>").append(subject).append("</h1>");
        emailContent.append("</div>");

        // Texte de base de l'e-mail
        emailContent.append("<p>").append(text).append("</p>");

        emailContent.append("</body></html>");

        helper.setText(emailContent.toString(), true);
        emailSender.send(message);
    }
}
