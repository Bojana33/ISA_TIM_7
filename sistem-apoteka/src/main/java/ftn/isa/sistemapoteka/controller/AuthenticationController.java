package ftn.isa.sistemapoteka.controller;

import ftn.isa.sistemapoteka.dto.ChangePasswordAfterFirstLoginDTO;
import ftn.isa.sistemapoteka.dto.UserDTO;
import ftn.isa.sistemapoteka.model.*;
import ftn.isa.sistemapoteka.security.auth.JwtAuthenticationRequest;
import ftn.isa.sistemapoteka.service.impl.CustomUserDetailsService;
import ftn.isa.sistemapoteka.exception.ResourceConflictException;
import ftn.isa.sistemapoteka.security.TokenUtils;
import ftn.isa.sistemapoteka.service.impl.UserServiceImpl;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private UserServiceImpl userService;

    private TokenUtils tokenUtils;

    private AuthenticationManager authenticationManager;

    private CustomUserDetailsService userDetailsService;


    @Autowired
    public AuthenticationController(TokenUtils tokenUtils,
                                    AuthenticationManager authenticationManager,
                                    CustomUserDetailsService userDetailsService,
                                    UserServiceImpl userService){
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView loginForm(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return new ModelAndView("login");
    }

    //endpoint za logovanje
    @PostMapping("/login-submit")
    public ModelAndView login_submit(@Valid @ModelAttribute UserDTO user, HttpServletRequest request) throws Exception{

        User u = this.userService.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (u.getEnabled()==false){
            throw new Exception("Your account is not activated, please check your email!");
        }

        if (u == null) {
            throw new Exception("User with this credentials doesn't exist.");
        }

        HttpSession session = request.getSession();
        session.setAttribute("email", user.getEmail());

        if (u.getIsFirstLogin()){
            u.setIsFirstLogin(false);
            return new ModelAndView("redirect:/auth/change-password");
        }
        if (u instanceof Patient){
            return new ModelAndView("redirect:/auth/patient/home");
        } else if(u instanceof SystemAdministrator){
            return new ModelAndView("redirect:/auth/sys-admin/home");
        } else {
            return new ModelAndView("redirect:/auth/home");
        }
    }

    @GetMapping("/change-password")
    public ModelAndView changePasswordForm(Model model){
        ChangePasswordAfterFirstLoginDTO changePasswordAfterFirstLoginDTO = new ChangePasswordAfterFirstLoginDTO();
        model.addAttribute(changePasswordAfterFirstLoginDTO);
        return new ModelAndView("change-password");
    }

    @PostMapping("/change-password/submit")
    public ModelAndView cps(@ModelAttribute("changePasswordAfterFirstLoginDTO")@Valid ChangePasswordAfterFirstLoginDTO changePasswordAfterFirstLoginDTO, HttpServletRequest request, BindingResult bindingResult){

        User user = this.userService.findByEmail(request.getSession().getAttribute("email").toString());
        this.userService.changePasswordAfterFirstLogin(user,changePasswordAfterFirstLoginDTO);
        if (bindingResult.hasErrors()){
            return new ModelAndView("redirect:/auth/change-password");
        }
        if (user instanceof Patient){
            return new ModelAndView("redirect:/auth/patient/home");
        } else if(user instanceof SystemAdministrator){
            return new ModelAndView("redirect:/auth/sys-admin/home");
        } else {
            return new ModelAndView("redirect:/auth/home");
        }
    }

    @GetMapping("/sys-admin/home")
    public ModelAndView sysAdminHome(HttpServletRequest request, Model model){
        User user = this.userService.findByEmail(request.getSession().getAttribute("email").toString());
        model.addAttribute(user);
        return new ModelAndView("sys-admin-home");
    }

    @GetMapping("/patient/home")
    public ModelAndView patientHome(){
        return new ModelAndView("patient-home");
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
//    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
//    @PreAuthorize("hasAnyRole('PATIENT','SYS_ADMIN','PHARMACIST','PHARMACY_ADMIN','DERMATOLOGIST','SUPPLIER')")
//    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
//        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
//
//        Map<String, String> result = new HashMap<>();
//        result.put("result", "success");
//        return ResponseEntity.accepted().body(result);
//    }
//
//    static class PasswordChanger {
//        public String oldPassword;
//        public String newPassword;
//    }

}
