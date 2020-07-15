package hu.flowacademy.homeproject.config;


import hu.flowacademy.homeproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

@Configuration
@Transactional
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        return userRepository.findFirstByUsername(name).orElseThrow(()-> new UsernameNotFoundException(name));
    }

}
