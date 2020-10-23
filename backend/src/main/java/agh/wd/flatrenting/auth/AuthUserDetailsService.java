package agh.wd.flatrenting.auth;

import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthUserDetailsService implements UserDetailsService {

    private UserService userService;
    //TODO: make another service for storing JUST auth data

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {

        User user = userService.findByUserName(userName).orElseThrow(
                () -> new UsernameNotFoundException("No user found with username: "+ userName)
        );

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return  new org.springframework.security.core.userdetails.User
                (user.getEmail(),
                        passwordEncoder.encode(user.getPasswordHash()),
                        enabled,
                        accountNonExpired,
                        credentialsNonExpired,
                        accountNonLocked,
                        getAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
