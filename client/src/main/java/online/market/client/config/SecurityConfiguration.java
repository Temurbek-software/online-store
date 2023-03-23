package online.market.client.config;

import online.market.service.entity.CustomerService;
import online.market.service.entity.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CustomerService customerService;


    public SecurityConfiguration(PasswordEncoder passwordEncoder,
                                 @Lazy UserService userService,
                                 CustomerService customerService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.customerService = customerService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customerService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests().
                antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated();

        http
                .csrf().disable().cors().disable()
                .formLogin().failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout")//.deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();
    }

    public void configure(WebSecurity web) throws Exception {
        // web.ignoring().antMatchers("/resources/static/**").anyRequest();
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/fonts/**",
            "/image/**",
            "/mail/**",
            "/ico/**",
            "/",
            "/category",
            "/part-search",
            "/part-details",
            "/register",
            "/login",
            "/forget-password",
            "/about-us",
            "/contact-us",
            "/bookList-category",
            "/bookList-subcategory",
            "/book-details",
            "/authors",
            "/author/**",
            "/publisher",
            "/companies",
            "/company/**",
            "/contactUs",
            "/aboutUs",
            "/faq",
            "/privacy-policy",
            "/return-policy",
            "/terms-and-conditions",

            //api route
            "/models",
            "/my-account",

            //Secure pages
            /*
             "/view-cart",
             "/checkout",
             "/my-account",
            "/order-details",
            "/order-history",
            "/change-password",*/
    };

}
