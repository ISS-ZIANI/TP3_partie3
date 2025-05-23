package ma.enset.hospitalapp.security.repository;

import ma.enset.hospitalapp.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByUsername(String username);

}
