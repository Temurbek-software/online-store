package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.payload.UserDto;
import online.market.service.entity.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * * This is Login controller where to sign in or sign out
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(Model model) {
        UserDto userDto1 = new UserDto();
        model.addAttribute("userDto", userDto1);
        return "/auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password,
                               Model model)
    {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        }
        catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid username or password");
            return "/auth/login";
        }
    }
}
