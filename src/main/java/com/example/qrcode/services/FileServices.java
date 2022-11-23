package com.example.qrcode.services;

import com.example.qrcode.dto.FileDB;
import com.example.qrcode.utils.JasperUtil;
import com.example.qrcode.utils.QRGenerator;
import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

@Service
public class FileServices {

    private JasperUtil jasperUtil;

    public FileServices(JasperUtil jasperUtil) {
        this.jasperUtil = jasperUtil;
    }

    public FileDB getInvoice() throws IOException, WriterException, JRException {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("passenger", "Sophie Amundsen");
        parameters.put("QR", new ByteArrayInputStream(
                QRGenerator.getQRCodeImage("https://api.coinpaprika.com/v1/coins/btc-bitcoin", 400, 400)));

        JasperReport jspReport = jasperUtil.setJasperReport("/eticket.jrxml");
        JasperPrint jspPrint = jasperUtil.setJasperPrint(jspReport, parameters);

        FileDB fileDB = new FileDB();
        fileDB.setData(jasperUtil.toPdf(jspPrint));
        fileDB.setFilename("E-ticket");

        return fileDB;
    }
}
