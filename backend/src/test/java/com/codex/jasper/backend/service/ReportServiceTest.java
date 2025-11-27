package com.codex.jasper.backend.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codex.jasper.backend.model.ReportRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
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
        ReportRequest request = new ReportRequest("Cliente Demo", LocalDate.of(2024, 1, 1), new BigDecimal("1234.56"));

        byte[] pdfBytes = reportService.generateReport(request);

        assertNotNull(pdfBytes, "El PDF generado no debe ser nulo");
        assertTrue(new String(pdfBytes).startsWith("%PDF"), "El contenido debe comenzar con la cabecera PDF");

        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            assertArrayEquals(new int[]{1}, new int[]{document.getNumberOfPages()}, "El PDF debería tener una página");
        }
    }
}
