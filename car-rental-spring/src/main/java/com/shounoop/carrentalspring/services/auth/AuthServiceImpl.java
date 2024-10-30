package com.shounoop.carrentalspring.services.auth;

import com.shounoop.carrentalspring.dto.SignupRequest;
import com.shounoop.carrentalspring.dto.UserDto;
import com.shounoop.carrentalspring.entity.PasswordResetToken;
import com.shounoop.carrentalspring.entity.User;
import com.shounoop.carrentalspring.enums.UserRole;
import com.shounoop.carrentalspring.repository.PasswordResetTokenRepository;
import com.shounoop.carrentalspring.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender emailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);

        if (adminAccount == null) {
            User newAdminAccount = new User();
            newAdminAccount.setName("Admin");
            newAdminAccount.setEmail("admin@test.com");
            newAdminAccount.setPassword(passwordEncoder.encode("admin"));
            newAdminAccount.setUserRole(UserRole.ADMIN);
            userRepository.save(newAdminAccount);
            System.out.println("Admin account created");
        }
    }

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);

        User createdUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setName(createdUser.getName());
        userDto.setEmail(createdUser.getEmail());
        userDto.setUserRole(createdUser.getUserRole());

        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
    // Méthode pour créer un agent immobilier
    public UserDto createAgentImmobilier(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.AGENT_IMMOBILIER);

        User createdUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setName(createdUser.getName());
        userDto.setEmail(createdUser.getEmail());
        userDto.setUserRole(createdUser.getUserRole());

        return userDto;
    }
    @SneakyThrows
    @Override
    public void initiatePasswordReset(String email) {
        Optional<User> user = userRepository.findFirstByEmail(email);
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setEmail(email);
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // Valide pour 1 heure
            passwordResetTokenRepository.save(resetToken);

            String resetUrl = "http://localhost:8081/api/auth/reset-password?token=" + token;
            sendEmail(email, "Password Reset Request",
                    "To reset your password, click the link below:<br><br><a href=\"" + resetUrl + "\">Reset Password</a>");
        }
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken.isPresent() && resetToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            Optional<User> user = userRepository.findFirstByEmail(resetToken.get().getEmail());
            if (user.isPresent()) {
                User u = user.get();
                u.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(u);
                passwordResetTokenRepository.delete(resetToken.get());
                return true;
            }
        }
        return false;
    }

    private void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        emailSender.send(message);
    }
}



