package com.gens.notificacao.controller;

import com.gens.notificacao.business.EmailService;
import com.gens.notificacao.infraestructure.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class NotificacaoController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefasDTO tarefasDTO) {

        emailService.enviarEmail(tarefasDTO);

        return ResponseEntity.ok().build();
    }
}
