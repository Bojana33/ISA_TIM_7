package ftn.isa.sistemapoteka.dto;

import ftn.isa.sistemapoteka.fieldMatch.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match!")
public class ChangePasswordAfterFirstLoginDTO {
    private String newPassword;
    private String confirmNewPassword;
}
