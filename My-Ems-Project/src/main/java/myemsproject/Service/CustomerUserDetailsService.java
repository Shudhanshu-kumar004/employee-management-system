package myemsproject.Service;

import myemsproject.Entity.Users;
import myemsproject.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String emailId)
            throws UsernameNotFoundException {

        Users users = usersRepository.findByEmailId(emailId);

        if (users == null) {
            throw new UsernameNotFoundException(
                    "User not found with email: " + emailId
            );
        }

        List<GrantedAuthority> authorityList = List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + users.getRole().name()
                )
        );

        return new User(
                users.getEmailId(),
                users.getPassword(),
                authorityList
        );
    }
}
