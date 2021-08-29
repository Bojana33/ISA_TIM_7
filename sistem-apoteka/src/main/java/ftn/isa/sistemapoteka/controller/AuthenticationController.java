package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.dto.UserDTO;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private UserServiceImpl userService;


    @Autowired
    public AuthenticationController(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView loginForm(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return new ModelAndView("login");
    }

//    //endpoint za logovanje
//    @PostMapping("/login-submit")
//    public ModelAndView login_submit(@Valid @ModelAttribute UserDTO user, HttpServletRequest request) throws Exception{
//
//        User u = this.userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
//
//        if (u.getEnabled()==false){
//            throw new Exception("Your account is not activated, please check your email!");
//        }
//
//        if (u == null) {
//            throw new Exception("User with this credentials doesn't exist.");
//        }
//
//
//        if (u.getIsFirstLogin()){
//            u.setIsFirstLogin(false);
//            return new ModelAndView("redirect:/auth/change-password");
//        }
//        if (u instanceof Patient){
//            return new ModelAndView("redirect:/user/patient/home");
//        } else if(u instanceof SystemAdministrator){
//            return new ModelAndView("redirect:/user/sys-admin/home");
//        } else if (u instanceof Supplier){
//            return new ModelAndView("redirect:/user/supplier/home");
//        }
//        else {
//            return new ModelAndView("redirect:/auth/home");
//        }
//    }

    @GetMapping("/change-password")
    public ModelAndView changePasswordForm(Model model){
        ChangePasswordAfterFirstLoginDTO changePasswordAfterFirstLoginDTO = new ChangePasswordAfterFirstLoginDTO();
        model.addAttribute(changePasswordAfterFirstLoginDTO);
        return new ModelAndView("change-password");
    }

    @PostMapping("/change-password/submit")
    public ModelAndView cps(@ModelAttribute("changePasswordAfterFirstLoginDTO")@Valid ChangePasswordAfterFirstLoginDTO changePasswordAfterFirstLoginDTO, Authentication auth, BindingResult bindingResult){

        User user = this.userService.findByEmail(auth.getName());
        this.userService.changePasswordAfterFirstLogin(user,changePasswordAfterFirstLoginDTO);
        if (bindingResult.hasErrors()){
            return new ModelAndView("redirect:/auth/change-password");
        }
        if (user instanceof Patient){
            return new ModelAndView("redirect:/user/patient/home");
        } else if(user instanceof SystemAdministrator){
            return new ModelAndView("redirect:/user/sys-admin/home");
        } else if(user instanceof Supplier) {
            return new ModelAndView("redirect:/user/supplier/home");
        } else if (user instanceof Dermatologist) {
            return new ModelAndView("redirect:/user/dermatologist/home");
        } else if (user instanceof Pharmacist) {
            return new ModelAndView("redirect:/user/pharmacist/home");
        } else {
            return new ModelAndView("redirect:/auth/home");
        }
    }


    @GetMapping("/home")
    public ModelAndView home(){
        return new ModelAndView("home");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return new ModelAndView("redirect:/auth/home");
    }

    // Endpoint za registraciju novog korisnika
    @GetMapping("/signup")
    public ModelAndView registrationForm(Model model){
        UserRequest userRequest = new UserRequest();
        model.addAttribute(userRequest);
        return new ModelAndView("registration");
    }

    @PostMapping("/signup/submit")
    public ModelAndView addUser(@ModelAttribute("userRequest") @Valid UserRequest userRequest, BindingResult result) {

        User existUser = this.userService.findByEmail(userRequest.getEmail());
        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "Email already exists");
        }
        if (result.hasErrors()){
            return new ModelAndView("redirect:/auth/signup");
        }
        this.userService.savePatient(userRequest);


        return new ModelAndView("redirect:/auth/home");
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        return userService.confirmToken(token);
    }

}
