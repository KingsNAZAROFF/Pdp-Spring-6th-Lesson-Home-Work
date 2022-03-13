package uz.pdp.pdpspring6thlessonhomework.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.pdpspring6thlessonhomework.entity.User;
import uz.pdp.pdpspring6thlessonhomework.entity.enums.Role;
import uz.pdp.pdpspring6thlessonhomework.payload.ApiResponse;
import uz.pdp.pdpspring6thlessonhomework.payload.LoginDto;
import uz.pdp.pdpspring6thlessonhomework.payload.RegisterDto;
import uz.pdp.pdpspring6thlessonhomework.repository.RoleRepository;
import uz.pdp.pdpspring6thlessonhomework.repository.UserRepository;
import uz.pdp.pdpspring6thlessonhomework.security.JwtProvider;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse registerUser(RegisterDto registerDto){

        boolean byEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (byEmail){
            return new ApiResponse("Bunday email ro'yxatdan o'tgan",false);
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setRole(Role.WORKER);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        return new ApiResponse("Muvaffaqiyatli ro'yxatdan o'tdizngiz",true);


    }
    public ApiResponse registerDirector(RegisterDto registerDto){

        boolean byEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (byEmail){
            return new ApiResponse("Bunday email ro'yxatdan o'tgan",false);
        }
        boolean existsByRole = userRepository.existsByRole(Role.DIRECTOR);

        if (existsByRole){
            return new ApiResponse("Direktor ro'yxatdan o'tgan",false);
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setRole(Role.DIRECTOR);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        return new ApiResponse("Muvaffaqiyatli ro'yxatdan o'tdizngiz",true);



    }





    public ApiResponse login (LoginDto loginDto) {
        try {


            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRole());

            return new ApiResponse("Token = ",true,token);
        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse("Parol yoki login xato",false);

        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optionalUser = userRepository.findByEmail(username);
//        if (optionalUser.isPresent()){
//            return optionalUser.get();
//        }
//        throw  new UsernameNotFoundException(username +" topilmadi");
        return (UserDetails) userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + "user topilmadi ! ! !"));
    }
}
