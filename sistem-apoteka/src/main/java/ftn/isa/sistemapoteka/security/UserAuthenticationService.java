package ftn.isa.sistemapoteka.security;

import ftn.isa.sistemapoteka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static ftn.isa.sistemapoteka.model.UserRole.*;

@Service
public class UserAuthenticationService
        implements AuthenticationProvider {
    private final UserRepository userRepository;

    @Autowired
    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        System.out.println("test");
        Authentication retVal = null;
        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        if (auth != null) {
            String email = auth.getName();
            String password = auth.getCredentials().toString();
            System.out.println("email: " + email);
            System.out.println("password: " + password);

            if (userRepository.findByEmail(email).getUserRole() == SYS_ADMIN) {
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_SYS_ADMIN"));

                retVal = new UsernamePasswordAuthenticationToken(
                        email, "", grantedAuths
                );
            } else if (userRepository.findByEmail(email).getUserRole() == SUPPLIER) {
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_SUPPLIER"));

                retVal = new UsernamePasswordAuthenticationToken(
                        email, "", grantedAuths
                );
            } else if (userRepository.findByEmail(email).getUserRole() == PATIENT) {
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_PATIENT"));

                retVal = new UsernamePasswordAuthenticationToken(
                        email, "", grantedAuths
                );
            }
        } else {
            System.out.println("invalid login");
            retVal = new UsernamePasswordAuthenticationToken(
                    null, null, grantedAuths
            );
            System.out.println("bad Login");
        }

        System.out.println("return login info");
        return retVal;
    }

    @Override
    public boolean supports(Class<?> tokenType) {
        return tokenType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
