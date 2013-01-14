package org.elucidus.generation.pdf;

<<<<<<< HEAD
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
=======
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL; 
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> 3087b9ad4007e80104a6be1d41b86318bf02f052
import java.util.Vector;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.elucidus.currency.Item;
import org.elucidus.exceptions.GenerationException;
<<<<<<< HEAD
import org.elucidus.generation.base.BaseGenerator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class GeneratorPdf extends BaseGenerator {
=======
import org.elucidus.generation.IGenerator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class GeneratorPdf implements IGenerator {
>>>>>>> 3087b9ad4007e80104a6be1d41b86318bf02f052
	private final Vector<Item> pdfAttributes = new Vector<Item>(); 
	private File fileToFilter;

	private PDDocument pdfDoc;

	private void addFilesystemAttributes() {
		Item filesystemAttributes = new Item();
		
		if (fileToFilter != null && fileToFilter.exists()) {
			filesystemAttributes.addString("filesystem.filename", this.fileToFilter.getAbsolutePath());
			filesystemAttributes.addString("filesystem.sizeBytes", Long.toString(this.fileToFilter.length()));
		} else {  
			filesystemAttributes.addString("filesystem.fileExists", "false");
		} 
		
		pdfAttributes.add(filesystemAttributes);
	}

	private void addHeaderAttributes() {
		Item headerAttributes = new Item();
		 
		headerAttributes.addString("pdfDocument.header.author", this.sanitizeValue(this.pdfDoc.getDocumentInformation().getAuthor()));
		headerAttributes.addString("pdfDocument.header.title", this.sanitizeValue(this.pdfDoc.getDocumentInformation().getTitle()));
		headerAttributes.addString("pdfDocument.header.pdfStandardVersion", Float.toString(this.pdfDoc.getDocument().getVersion()));
		headerAttributes.addString("pdfDocument.header.modificationTimeUtc", this.docInfoMtimeToIso8601(this.pdfDoc.getDocumentInformation()));
		
		pdfAttributes.add(headerAttributes);
	}  

	private void addPageContent() {
		try {
			PDFTextStripper stripper = new PDFTextStripper();

			for (int i = 1; i <= this.pdfDoc.getNumberOfPages(); i++) {
				Item pageContent = new Item();
				
				stripper.setStartPage(i);
				stripper.setEndPage(i);

				pageContent.addString("pdfDocument.pages." + i + ".content", this.sanitizeValue(stripper.getText(this.pdfDoc)));
 				 
				pdfAttributes.add(pageContent);
			}
		} catch (IOException e) {
			System.out.println("Exception while stripping text from document: " + e);
		}
	}

	private String docInfoMtimeToIso8601(PDDocumentInformation pdDocumentInformation) {
		try {
			return new DateTime(pdDocumentInformation.getModificationDate(), DateTimeZone.UTC).toString();
		} catch (Exception e) {
			return "";
		}
	}

	private void loadPdf(InputStream is) throws IOException {
		this.pdfDoc = PDDocument.load(is);
	} 

<<<<<<< HEAD
=======
	private String sanitizeValue(String value) {
		if (value == null) {
			return "";
		} else {
			return value.trim(); 
		}
	}

>>>>>>> 3087b9ad4007e80104a6be1d41b86318bf02f052
	private void unloadPdf() {
		try {
			this.pdfDoc.close();
		} catch (IOException e) {
			System.out.println("Could not cleanly close the PDF");
<<<<<<< HEAD
		} 
=======
		}
>>>>>>> 3087b9ad4007e80104a6be1d41b86318bf02f052
	}

	@Override
	public List<Item> generate(InputStream inputStream) throws GenerationException {
		this.addFilesystemAttributes();

		try {
			this.loadPdf(inputStream);
		} catch (IOException e) {
			System.out.println("This document cannot be indexed:" + e);
		}

		this.addHeaderAttributes();
		this.addPageContent();

		this.unloadPdf();
		
		return pdfAttributes;
	}

	@Override
	public List<Item> generate(File inputFile) throws GenerationException {
		this.fileToFilter = inputFile;
		
		try {
			return generate(new FileInputStream(inputFile));
		} catch (FileNotFoundException e) {
			throw new GenerationException(e); 
		}  
	}
<<<<<<< HEAD
=======

	@Override
	public List<Item> generate(String inputString) throws GenerationException {
		return generate(new ByteArrayInputStream(inputString.getBytes(Charset.defaultCharset())));
	}    

	@Override
	public List<Item> generate(URL url) throws GenerationException {
		/* 
		 * This is bad interface method imo, but hey ho. Without any understanding
		 * of the URL protocol, exceptions cannot be caught and handled in a sensible way.  
		 */
		try {
			return generate(url.openStream()) ;
		} catch (IOException e) {
			throw new GenerationException(e);
		}
	} 
  
	@Override
	public void setParameter(String name, String value) throws GenerationException 
	{
	}

>>>>>>> 3087b9ad4007e80104a6be1d41b86318bf02f052
}