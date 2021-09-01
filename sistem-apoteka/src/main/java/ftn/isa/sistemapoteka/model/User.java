package ftn.isa.sistemapoteka.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "user_type")
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
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
    private Boolean enabled = false;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Column
    private UserRole userRole;

    @Column
    private Boolean isFirstLogin = false;


    public User(@NotEmpty(message = "This field can not be empty") String firstName, @NotEmpty(message = "This field can not be empty") String username, @NotEmpty(message = "This field can not be empty") String lastName, @NotEmpty(message = "This field can not be empty") String email, @NotEmpty(message = "This field can not be empty") String password, String residence, String city, String state, String phoneNumber, boolean enabled, Timestamp lastPasswordResetDate) {
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

    public User(@NotEmpty(message = "This field can not be empty") String firstName, @NotEmpty(message = "This field can not be empty") String lastName, @NotEmpty(message = "This field can not be empty") String email, @NotEmpty(message = "This field can not be empty") String password, String residence, String city, String state, String phoneNumber, Boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.residence = residence;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
    }

}
