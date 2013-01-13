package elucidusFilterPdf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import dummyFilter.ElucidusFileFilter;

public class FilterPdf implements ElucidusFileFilter {
	private final Map<String, String> fileAttributes = new HashMap<String, String>();
	private File fileToFilter;

	private PDDocument pdfDoc;

	private void addFilesystemAttributes() {
		this.fileAttributes.put("filesystem.filename", this.fileToFilter.getAbsolutePath());
		this.fileAttributes.put("filesystem.sizeBytes", Long.toString(this.fileToFilter.length()));
	}

	private void addHeaderAttributes() {
		this.fileAttributes.put("pdfDocument.header.author", this.pdfDoc.getDocumentInformation().getAuthor());
		this.fileAttributes.put("pdfDocument.header.title", this.sanitizeValue(this.pdfDoc.getDocumentInformation().getTitle()));
		this.fileAttributes.put("pdfDocument.header.pdfStandardVersion", Float.toString(this.pdfDoc.getDocument().getVersion()));
		this.fileAttributes.put("pdfDocument.header.modificationTimeUtc", this.docInfoMtimeToIso8601(this.pdfDoc.getDocumentInformation()));
		// etc
	}

	private void addPageContent() {
		try {
			PDFTextStripper stripper = new PDFTextStripper();

			for (int i = 0; i < this.pdfDoc.getNumberOfPages(); i++) {
				stripper.setStartPage(i);
				stripper.setEndPage(i);

				this.fileAttributes.put("pdfDocument.pages." + i + ".content", this.sanitizeValue(stripper.getText(this.pdfDoc)));
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

	public Map<String, String> filterFile(File fileToFilter) {
		this.fileToFilter = fileToFilter;

		this.addFilesystemAttributes();

		try {
			this.loadPdf();
		} catch (IOException e) {
			System.out.println("This document cannot be indexed:" + e);
		}

		this.addHeaderAttributes();
		this.addPageContent();

		this.unloadPdf();

		return this.fileAttributes;
	}

	private void loadPdf() throws IOException {
		this.pdfDoc = PDDocument.load(this.fileToFilter);
	}

	private String sanitizeValue(String value) {
		if (value == null) {
			return "";
		} else {
			return value.trim();
		}
	}

	private void unloadPdf() {
		try {
			this.pdfDoc.close();
		} catch (IOException e) {
			System.out.println("Could not cleanly close the PDF");
		}
	}

}