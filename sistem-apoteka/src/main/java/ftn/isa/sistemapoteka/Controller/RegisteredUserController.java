package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;
}
