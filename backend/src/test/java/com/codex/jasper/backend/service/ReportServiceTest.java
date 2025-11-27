package com.codex.jasper.backend.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codex.jasper.backend.model.ReportRequest;
import java.io.IOException;
import java.time.LocalDate;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Pruebas unitarias del servicio de generación de informes.
 */
@SpringBootTest
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    void generateReportShouldReturnPdfWithContent() throws IOException {
        ReportRequest request = new ReportRequest(
                "DOCUMENTO DE INSTALACIÓN",
                "PRODUCCIÓN",
                "PCM",
                "Alarmado Tráfico Internacional",
                "5.4.0",
                "Telefónica Móviles España",
                LocalDate.of(2025, 11, 10),
                "1.0",
                "TME");

        byte[] pdfBytes = reportService.generateReport(request);

        assertNotNull(pdfBytes, "El PDF generado no debe ser nulo");
        assertTrue(new String(pdfBytes).startsWith("%PDF"), "El contenido debe comenzar con la cabecera PDF");

        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            assertArrayEquals(new int[]{1}, new int[]{document.getNumberOfPages()}, "El PDF debería tener una página");
            String text = new PDFTextStripper().getText(document);
            assertTrue(text.contains("DOCUMENTO DE INSTALACIÓN"));
            assertTrue(text.contains("Telefónica Móviles España"));
            assertTrue(text.contains("1.0"));
        }
    }
}
