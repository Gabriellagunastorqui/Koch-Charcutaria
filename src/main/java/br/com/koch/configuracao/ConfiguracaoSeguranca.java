package br.com.koch.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfiguracaoSeguranca
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                        .requestMatchers("/login", "/cadastro").permitAll()
                        .requestMatchers("/cliente/login", "/cliente/cadastro").permitAll()
                        .requestMatchers("/painel/**").hasRole("ADMINISTRADOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/cliente/login")          // aponta para sua rota real
                        .loginProcessingUrl("/cliente/login") // onde o form faz POST
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/painel", true)
                        .failureUrl("/cliente/login?erro=true") // redireciona em falha
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/cliente/login?logout")
                        .invalidateHttpSession(true)   // limpa a sessão
                        .deleteCookies("JSESSIONID")   // remove o cookie
                        .permitAll()
                )
                .csrf(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}