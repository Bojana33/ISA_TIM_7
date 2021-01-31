package ftn.isa.sistemapoteka.Controller;

import ftn.isa.sistemapoteka.Service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("registeredUsers")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;
}
