package com.example.qrcode.controller;


import com.example.qrcode.dto.FileDB;
import com.example.qrcode.services.FileServices;
import com.example.qrcode.utils.QRGenerator;
import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private FileServices fileServices;

    public FileController(FileServices fileServices) {
        this.fileServices = fileServices;
    }

    @GetMapping(value = "/download/qr")
    public ResponseEntity<?> downloadFile(@RequestParam("fileName") String fileName)
            throws IOException, WriterException {
        byte[] fileQR = QRGenerator.getQRCodeImage("www.google.com", 250, 250);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(fileQR));
    }

    @GetMapping(value = "/download/ticket")
    public ResponseEntity<?> downloadETicket(@RequestParam("fileName") String fileName)
            throws IOException, WriterException, JRException {

        FileDB fileDB = fileServices.getInvoice();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(fileDB.getData()));
    }
}
