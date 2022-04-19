package com.example.jwt.security;

import com.example.jwt.exception.AuthenExceptionHandler;
import com.example.jwt.jwt.AuthenFilter;
import com.example.jwt.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenExceptionHandler authenExceptionHandler; //injection spring boot

    @Autowired
    AuthenFilter authenFilter;

    @Autowired
    UserSecurityService userSecurityService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(passwordEncoder());
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // cross origin resource sharing
        http
                .cors().and()
                .authorizeRequests()
                .antMatchers("/login","/signup").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenExceptionHandler)
                .and()
                .addFilterBefore(authenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
