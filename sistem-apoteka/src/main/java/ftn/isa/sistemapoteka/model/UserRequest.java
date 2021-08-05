package ftn.isa.sistemapoteka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String residence;

    private String city;

    private String state;

    private String phoneNumber;
}
