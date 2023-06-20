package com.yangnan.crm.security.config;

import com.yangnan.crm.security.exception.SimpleAccessDeniedHandler;
import com.yangnan.crm.security.exception.SimpleAuthenticationEntryPoint;
import com.yangnan.crm.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.yangnan.crm.security.filter.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Resource
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        // 这是配置的关键，决定哪些接口开启防护，哪些接口绕过防护
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //授权
                .authorizeRequests()
                // 登陆接口肯定是不需要认证的、permitAll登录未登录都额可以访问
                .antMatchers("/rbac/user/login").permitAll()
                .antMatchers("/favicon.ico", "/swagger-resources/**",
                        "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                //token校验过滤器加入过滤器链中
                //TokenAuthenticationFilter放到UsernamePasswordAuthenticationFilter的前面，这样做就是为了除了登录的时候去查询数据库外，其他时候都用token进行认证。
                .addFilterBefore(new TokenAuthenticationFilter(redisTemplate), CustomUsernamePasswordAuthenticationFilter.class)
                .addFilter(new CustomUsernamePasswordAuthenticationFilter(authenticationManager, redisTemplate))
        ;

        //配置异常处理
        http.exceptionHandling()
                .accessDeniedHandler(new SimpleAccessDeniedHandler())
                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint());

        return http.build();
    }
}