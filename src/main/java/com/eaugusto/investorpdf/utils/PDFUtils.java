package com.eaugusto.investorpdf.utils;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFUtils {

	public static String getTextFromPDF(File file) {
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;
		
		try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");) {
			
			PDFParser parser = new PDFParser(randomAccessFile);
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(5);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				pdDoc.close();
			} catch (IOException e) {
			}
		}
		
		return parsedText;
	}
	
}