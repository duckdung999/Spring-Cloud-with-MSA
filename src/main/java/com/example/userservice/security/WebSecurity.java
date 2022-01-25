package com.example.userservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {



    @Override
    //httpSecurity는 인가(authorization)쪽과 관련된 항수
    protected void configure(HttpSecurity http) throws Exception {
        // disable하지 않으면 csrf토큰이 없는 사용자로부터는 get이외의 통신을 할수 없게 된다.
        // 다른 토큰을 이용해서 통신한다면 굳이 필요 X
        http.csrf().disable();
        // /users/** 경로로 들어오는 request에 대한 authorization 검증이 들어간다.
        http.authorizeRequests().antMatchers("/users/**").permitAll();


    }
}
