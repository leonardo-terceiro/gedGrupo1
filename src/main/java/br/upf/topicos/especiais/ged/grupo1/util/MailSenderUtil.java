package br.upf.topicos.especiais.ged.grupo1.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSenderUtil {

	public static void sendMail(String emailTo, String eventName, String userName, byte[] fileBytes) {
		
		Session session = Session.getDefaultInstance(getProperties(), getAuthentication());
		session.setDebug(true);
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("leonardo.terceiro.teste@gmail.com"));
			Address[] mailTo = InternetAddress.parse(emailTo);
			message.setRecipients(Message.RecipientType.TO, mailTo);
			message.setSubject("Diploma de Particiapação do Evento " + eventName);
			String msg = "Caro(a) " + userName + ", \n \n" + "Segue abaixo o Diploma de Participação no Evento " + eventName + ". \n \n" + "Qualquer dúvida entre em contato através do email upf@support.br!";
			
			MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
			mimeBodyPart1.setText(msg);
			
			File file = readFile(fileBytes, eventName, userName);
			MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
			mimeBodyPart2.attachFile(file);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart1);
			multipart.addBodyPart(mimeBodyPart2);
			
			message.setContent(multipart);
			
			Transport.send(message);
			
			System.out.println("Email Enviado!!!");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static File readFile(byte[] fileBytes, String eventName, String userName) throws IOException {

		File file = File.createTempFile(userName + "_" + eventName, ".pdf");
		System.out.println("File name -> " + file.getName());
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);
		
		FileOutputStream fos = new FileOutputStream(file);
        int data;
        while ((data = byteArrayInputStream.read()) != -1) {
            char ch = (char) data;
            fos.write(ch);
        }
        fos.flush();
        fos.close();
        byteArrayInputStream.close();
		
		return file;
	}

	private static Authenticator getAuthentication() {
		return new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication()
	           {
	                 return new PasswordAuthentication("leonardo.terceiro.teste@gmail.com",
	                 "#teste1234");
	           }
		};
	}

	private static Properties getProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");
		return props;
	}
	
}
