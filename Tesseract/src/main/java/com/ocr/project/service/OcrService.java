package com.ocr.project.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class OcrService {
	
	public String startProcess(List<MultipartFile> files) throws IOException {
		int number = 1;
		for (MultipartFile multiPartFile : files) {
			File file = convertMultiPartFileToFile(multiPartFile);
			ITesseract ocr = new Tesseract();
			ocr.setDatapath("C:\\Anandan\\Sutheesh POC\\Tess4J\\tessdata");
			try {
				String extractedText = ocr.doOCR(file);
				writeIntoExcel(extractedText,number);
				++number;
				System.out.println(extractedText);
				return "Success";
			} catch (TesseractException e) {
				e.printStackTrace();
			}
		}
		return "Error while reading image";
	}
	
	private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(multipartFile.getBytes());
		fos.close();
		return file;
	}
	
	private void writeIntoExcel(String text,int number) throws IOException {
		String excelFileName = "C:/Anandan/Sutheesh POC/"+number+"output.xlsx";
		Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("PDF Text");

        String[] lines = text.split("\\r?\\n");
        int rowNum = 0;
        for (String line : lines) {
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(0);
            cell.setCellValue(line);
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFileName)) {
            workbook.write(outputStream);
        }catch (Exception e) {
			e.printStackTrace();
		}
        workbook.close();
	}
	
	public String getExcel(List<MultipartFile> files) throws IOException {
		int number = 1;
		for (MultipartFile multiPartFile : files) {
			File file = convertMultiPartFileToFile(multiPartFile);
			ITesseract ocr = new Tesseract();
			ocr.setDatapath("C:\\Anandan\\Sutheesh POC\\Tess4J\\tessdata");
			ocr.setLanguage("eng");
			try {
				String extractedText = ocr.doOCR(file);
				writeIntoExcel(extractedText,number);
				++number;
				System.out.println(extractedText);
				return "Success";
			} catch (TesseractException e) {
				e.printStackTrace();
			}
		}
		return "Error while reading image";
	}
}
