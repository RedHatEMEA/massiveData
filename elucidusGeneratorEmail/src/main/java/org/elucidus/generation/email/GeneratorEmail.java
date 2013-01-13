package org.elucidus.generation.email;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.elucidus.currency.Item;
import org.elucidus.exceptions.GenerationException;
import org.elucidus.generation.base.BaseGenerator;
     
public class GeneratorEmail extends BaseGenerator {
	private List<Item> attrs = new Vector<Item>();

	@Override
	public List<Item> generate(InputStream inputStream) throws GenerationException {
		attrs.clear();
		
		Session s = Session.getDefaultInstance(new Properties());  
		
		try {
			MimeMessage mm = new MimeMessage(s, inputStream);
			
			parseHeaders(mm);
			parseContent(mm);  
		} catch (MessagingException | IOException e) {
			throw new GenerationException(e);
		}
		 
		return attrs;
	}
	
	private void parseHeaders(MimeMessage message) throws MessagingException {
		Item itemHeaders = new Item();
		
		itemHeaders.addString("email.headers.subject", message.getSubject());   
		itemHeaders.addString("email.headers.encoding", this.sanitizeValue(message.getEncoding()));
		itemHeaders.addString("email.headers.sender", this.sanitizeValue(message.getSender()));
		itemHeaders.addString("email.headers.sendDate", this.sanitizeValue(message.getSentDate()));     
		itemHeaders.addString("email.headers.receivedDate", this.sanitizeValue(message.getReceivedDate()));
		itemHeaders.addString("email.headers.contentType", this.sanitizeValue(message.getContentType()));
		
		attrs.add(itemHeaders);   
	}
	 
	private void parseContent(MimeMessage message) throws MessagingException, IOException {
		Item itemContent = new Item();
		    
		itemContent.addString("email.content.md5sum", this.sanitizeValue(message.getContentMD5()));
		itemContent.addString("email.content", this.sanitizeValue(message.getContent()));
		 
		attrs.add(itemContent);
	}
}
