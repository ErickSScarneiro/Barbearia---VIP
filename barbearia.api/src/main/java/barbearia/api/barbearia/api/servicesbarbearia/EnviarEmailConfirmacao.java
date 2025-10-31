package barbearia.api.barbearia.api.servicesbarbearia;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EnviarEmailConfirmacao {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String para, String assunto, String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(para);
        email.setSubject(assunto);
        email.setText(mensagem);
        email.setFrom("seuemail@gmail.com");

        mailSender.send(email);
    }
}

