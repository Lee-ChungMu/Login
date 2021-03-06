package com.kosa.login.config;


import com.kosa.login.security.filter.ApiCheckFilter;
import com.kosa.login.security.filter.ApiLoginFilter;
import com.kosa.login.security.handler.ApiLoginFailHandler;
import com.kosa.login.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApiCheckFilter apiCheckFilter(){
        return new ApiCheckFilter("/boards/**/*", jwtUtil());
    }

    @Bean
    public JWTUtil jwtUtil(){
        return new JWTUtil();
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception{
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager());

        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
        return apiLoginFilter;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sample/all", "/signUp").permitAll();//로그인 없이 접근 가능
                //.antMatchers("/sample/member").hasRole("USER");
                //.antMatchers("/signUp").anonymous()
//                .and()
//                .formLogin()
//                .and()
//                .csrf().disable()//로그인 창;
//                .logout();
                        //.antMatchers("/sample/admin").hasRole("ADMIN");

        http.formLogin();//상위 접근권한 접근 문제시 로그인 화면으로 되돌아옴
        http.csrf().disable();
        http.logout();


        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }



}
