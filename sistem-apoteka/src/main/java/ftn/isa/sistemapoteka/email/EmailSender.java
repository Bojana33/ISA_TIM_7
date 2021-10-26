package ftn.isa.sistemapoteka.email;

public interface EmailSender {

    void send(String to, String email);
    void sendEmail(String to, String body, String topic);
}
