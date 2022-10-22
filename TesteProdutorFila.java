package br.com.ifsp.jms.jms;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {


	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection("lucas","activemq"); 
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//FILA
		Destination fila = (Destination) context.lookup("LOG");
		
		MessageProducer producer = session.createProducer(fila);
		
		
		//TÓPICO
		Destination topico = (Destination) context.lookup("TOPIC");
		
		MessageProducer producer2 = session.createProducer(topico);
		
		//LISTA PRINCIPAL
		List<String> lista = new ArrayList<String>();
		lista.add("ERR");
		lista.add("WARN");
		lista.add("DEBUG");
		lista.add("");
		
		//LISTA CASO NÃO TENHA ERRO
		List<String> lista2 = new ArrayList<String>();
		lista2.add("DIA_DAS_CRIANCAS");
		lista2.add("NATAL");
		lista2.add("PÁSCOA");
		lista2.add("DIA_DAS_MAES");
		
		
		Random random = new Random(System.currentTimeMillis());
		
		
		for (int i = 0; i < 1000; i++) {
			
			int numero = random.nextInt(4);
			int prioridade = 0;
			int holiday = 0;
			
			String msg = (String) lista.get(numero);
			
			if(msg=="ERR") {
				prioridade = 9;
			} else if (msg=="DEBUG") {
				prioridade = 4;
			} else if (msg=="WARN") {
				prioridade = 1;
			} else {
				holiday = 1;	
			}
				
			
			if(holiday == 0) {
				Message message = session.createTextMessage( msg + " | ERRO | Apache ActiveMQ 5.17.2 (localhost, ID:Mac-mini-de-IFSP.local-49701-1443131721783-0:1) is starting");
				producer.send(message,DeliveryMode.NON_PERSISTENT,prioridade,300000);
			} else {
				int numero2 = random.nextInt(4);
				String msg2 = (String) lista.get(numero2);
				Message message2 = session.createTextMessage( msg2 + " | FERIADO | Apache ActiveMQ 5.17.2 (localhost, ID:Mac-mini-de-IFSP.local-49701-1443131721783-0:1) is starting");
				producer2.send(message2);
			}
			
		}
		

				
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}