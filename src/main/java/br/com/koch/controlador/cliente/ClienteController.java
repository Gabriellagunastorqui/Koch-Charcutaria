package br.com.koch.controlador.cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @GetMapping("/login")
    public String login() {
        return "cliente/login";
    }

    @GetMapping("/cadastro")       // era: @getMapping (G minúsculo)
    public String cadastro() {     // era: puplic string (typos)
        return "cliente/cadastro"; // era: retrun (invertido) e fora das chaves
    }
}