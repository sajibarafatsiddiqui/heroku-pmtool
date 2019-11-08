package com.inferit.pmtool.security;

import com.inferit.pmtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class
SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    com.inferit.pmtool.security.JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;
    @Bean
    com.inferit.pmtool.security.JsonTokenFilter jsonTokenFilter(){return new com.inferit.pmtool.security.JsonTokenFilter();};
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(jsonAuthenticationEntryPoint).and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .headers().frameOptions().sameOrigin().and().authorizeRequests().antMatchers(
                       "/","favicon.ico","/**/*.png",
               "/**/*.jpg","/**/*.svg","/**/*.gif","/**/*.html","/**/*.css","/**/*.js","/api/users/**"
       ).permitAll().anyRequest().authenticated();
       http.addFilterBefore(jsonTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
