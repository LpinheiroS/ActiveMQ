package br.com.ifsp.jms.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConsumidorTopic {
	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();

		ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
		Connection conexao = cf.createConnection("lucas","activemq");

		conexao.start();
		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topico = (Destination) context.lookup("TOPIC");
		MessageConsumer consumer = session.createConsumer(topico);
		
		Message message = consumer.receive();
		
		System.out.println("Recebendo msg: " + message);
		
		new Scanner(System.in).nextLine();
		
		session.close();
		conexao.close();    
		context.close();
	}
}