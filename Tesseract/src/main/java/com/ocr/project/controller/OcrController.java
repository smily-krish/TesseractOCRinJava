package com.ocr.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ocr.project.service.OcrService;

import net.sourceforge.tess4j.TesseractException;

@RestController
@RequestMapping("/ocr")
public class OcrController {
	
	@Autowired
	private OcrService ocrService;
	
	@GetMapping("/start")
	public void startOcrProcess(@RequestParam("files") List<MultipartFile> files) throws IOException, TesseractException {
		ocrService.startProcess(files);
	}

}
