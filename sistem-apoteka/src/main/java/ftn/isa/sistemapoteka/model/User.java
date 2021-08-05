package ftn.isa.sistemapoteka.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "user_type")
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private String firstName;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private String lastName;

    @Column
    @NotEmpty(message = "This field can not be empty")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "This field can not be empty")
    //@JsonIgnore
    private String password;

    @Column
    private String residence;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String phoneNumber;

    @Column
    private boolean enabled = false;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public void setPassword(String password) {
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
        this.password = password;
    }

    public User(@NotEmpty(message = "This field can not be empty") String firstName, @NotEmpty(message = "This field can not be empty") String username, @NotEmpty(message = "This field can not be empty") String lastName, @NotEmpty(message = "This field can not be empty") String email, @NotEmpty(message = "This field can not be empty") String password, String residence, String city, String state, String phoneNumber, boolean enabled, Timestamp lastPasswordResetDate, List<Authority> authorities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.residence = residence;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
    }
    public User(@NotEmpty(message = "This field can not be empty") String firstName, @NotEmpty(message = "This field can not be empty") String lastName, @NotEmpty(message = "This field can not be empty") String email, @NotEmpty(message = "This field can not be empty") String password, String residence, String city, String state, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.residence = residence;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
    }
}
