package car_rental.api.user;

import car_rental.api.car.CarRepository;
import car_rental.api.client.ClientRepository;
import car_rental.api.promotionCode.PromotionCodeRepository;
import car_rental.api.rents.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, CarRepository.class, ClientRepository.class, PromotionCodeRepository.class, RentRepository.class})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private  CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        }

     @Override
     protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers("**/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll();
    }

/*    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/signin")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((req, res, auth) -> {res.sendRedirect("/home");})
                .failureUrl("login?error='incorrect login or password'")
                .failureHandler((req, res, auth) -> { }).permitAll()
                .and()
                .exceptionHandling( (req) -> {
                    req.accessDeniedPage("/home?message=Acces denied");
                })
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    req.getSession().setAttribute("message", "You are logout.");
                    res.sendRedirect("/home");
                }).permitAll();

        httpSecurity.headers().frameOptions().disable();
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
