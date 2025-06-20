package ma.enset.hospitalapp.security.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.enset.hospitalapp.security.entities.AppRole;
import ma.enset.hospitalapp.security.entities.AppUser;
import ma.enset.hospitalapp.security.repository.AppRoleRepository;
import ma.enset.hospitalapp.security.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor

public class AccountserviceImpl implements Accountservice {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;



    @Override
    public AppUser addUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser= appUserRepository.findByUsername(username);
        if (appUser != null) throw new RuntimeException("this user already exists");
        if (!password.equals(confirmPassword)) throw new RuntimeException("passwords do not match");
        appUser  =AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser saveAppUser = appUserRepository.save(appUser);
        return saveAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole=appRoleRepository.findById(role).orElse(null);
        if (appRole != null) throw new RuntimeException("this user already exists");
        appRole=AppRole.builder()
                .role(role)
                .build();

        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
        //appUserRepository.save(appUser);

    }

    @Override
    public void removeRoleFromUser(String username, String role) {

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
