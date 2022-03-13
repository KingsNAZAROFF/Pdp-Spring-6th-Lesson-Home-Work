package uz.pdp.pdpspring6thlessonhomework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.pdpspring6thlessonhomework.payload.ApiResponse;
import uz.pdp.pdpspring6thlessonhomework.payload.LoginDto;
import uz.pdp.pdpspring6thlessonhomework.payload.RegisterDto;
import uz.pdp.pdpspring6thlessonhomework.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/regDirector")
    public ResponseEntity<?> regDirector(RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerDirector(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PostMapping("/regWorker")
    public ResponseEntity<?> regWorker(RegisterDto registerDto){
        ApiResponse response = authService.registerUser(registerDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> singIn(LoginDto loginDto){
        ApiResponse response = authService.login(loginDto);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }
}
