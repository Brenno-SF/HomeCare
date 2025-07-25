package com.eng.homecare.services;

import com.eng.homecare.entities.Appointment;
import com.eng.homecare.entities.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("brennosantosflorencio@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    public void sendAssessmentEmail(Assessment assessment){
        String emailBody = """
            Olá %s,
    
            Você recebeu uma nova avaliação de %s!
    
            🌟 Nota: %.1f
            📝 Título: %s
            💬 Comentário: %s
    
                        Acesse a plataforma para visualizar mais detalhes.
    
            Atenciosamente,  
            Equipe HomeCare
        """.formatted(
                    assessment.getProfessional().getUser().getName(),
                    assessment.getPatient().getUser().getName(),
                    assessment.getStars(),
                    assessment.getTitle(),
                    assessment.getDescription()
            );
        sendSimpleEmail(
                assessment.getProfessional().getUser().getEmail(),
                "Nova Avaliação recebida",
                emailBody
        );
    }
    public void sendConfirmAppointment(Appointment appointment){
        String emailBody = """
            Olá %s,
        
            Sua consulta com o(a) Dr(a). %s foi confirmada com sucesso!
        
            📅 Data: %s
            🕒 Horário: das %s às %s
            📝 Observações: %s
        
            Por favor, esteja disponível no horário agendado. Se precisar remarcar ou cancelar, utilize a plataforma com antecedência.
        
            Agradecemos por usar o HomeCare.  
            Equipe HomeCare
        """.formatted(
                appointment.getPatient().getUser().getName(),
                appointment.getProfessional().getUser().getName(),
                appointment.getDate().toString(),
                appointment.getStartTime().toString(),
                appointment.getEndTime().toString(),
                appointment.getObs() == null || appointment.getObs().isBlank() ? "Nenhuma observação registrada." : appointment.getObs()
        );
        sendSimpleEmail(
                appointment.getPatient().getUser().getEmail(),
                "Sua consulta foi confirmada!",
                emailBody
        );
    }
}
