package com.example.demo.service;

import java.io.File;
import java.io.StringReader;
import java.net.URI;
import java.util.Locale;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.xml.transform.Source;
import javax.xml.transform.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentGeneratorService {
    private final TemplateEngine templateEngine;

    private final TransformerFactory tFactory = TransformerFactory.newInstance();

    public void generateDocument(HttpServletRequest request, HttpServletResponse response) {

        String data = RandomStringUtils.random(20, 0, 0, true, true, null).toUpperCase();

        Context context = new Context(Locale.getDefault(),
                Map.of("model", Map.of(
                        "title", "Barcode generation using APache FOP",
                        "titleLine1", "and Barcode 4J",
                        "date", "02.08.24",
                        "batch", "240922",
                        "code39Message", data)));

        String xmlTemplate = templateEngine.process("bc", context);

        try {
            response.setContentType("application/pdf");
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, response.getOutputStream());
            Transformer transformer = tFactory.newTransformer();
            Source src = new StreamSource(new StringReader(xmlTemplate));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
