package ma.enset.hospitalapp.security.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.enset.hospitalapp.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private Accountservice accountservice;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AppUser appUser = accountservice.loadUserByUsername(username);
        if(appUser == null) throw new UsernameNotFoundException(String.format("User %s not found",username));

        String[] roles = appUser.getRoles().stream().map(u ->u.getRole()).toArray(String[]::new);
        UserDetails userDetails= User.
                withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles).build();
        return userDetails;
    }
}
