package ma.enset.hospitalapp;

import ma.enset.hospitalapp.entities.Patient;
import ma.enset.hospitalapp.repository.PatientRepository;
import ma.enset.hospitalapp.security.service.Accountservice;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HospitalAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalAppApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"Mohamed",new Date(),false,42));
            patientRepository.save(new Patient(null,"Imane",new Date(),true,98));
            patientRepository.save(new Patient(null,"Yassine",new Date(),true,342));
            patientRepository.save(new Patient(null,"Laila",new Date(),false,123));
        };
    }

    //@Bean
    CommandLineRunner commandLineRunnerc(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            try {
                jdbcUserDetailsManager.loadUserByUsername("user11");
            } catch (Exception e) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user11").password(passwordEncoder.encode("1234")).roles("USER").build()
                );
            }

            try {
                jdbcUserDetailsManager.loadUserByUsername("user22");
            } catch (Exception e) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user22").password(passwordEncoder.encode("1234")).roles("USER").build()
                );
            }

            try {
                jdbcUserDetailsManager.loadUserByUsername("admin2");
            } catch (Exception e) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("admin2").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
                );
            }
        };
    }


    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(Accountservice accountservice){
        return args -> {
            accountservice.addNewRole("User");
            accountservice.addNewRole("Admin");
            accountservice.addUser("user1","1234","user1@gmail.com","1234");
            accountservice.addUser("user2","1234","user2@gmail.com","1234");
            accountservice.addUser("admin","1234","admin@gmail.com","1234");

            accountservice.addRoleToUser("user1","USER");
            accountservice.addRoleToUser("user2","USER");
            accountservice.addRoleToUser("admin","USER");
            accountservice.addRoleToUser("admin","ADMIN");



        };
    }



    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
