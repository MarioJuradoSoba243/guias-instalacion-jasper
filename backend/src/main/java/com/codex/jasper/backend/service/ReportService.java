package com.codex.jasper.backend.service;

import com.codex.jasper.backend.model.ReportRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de compilar la plantilla Jasper y rellenarla con los datos recibidos del formulario.
 */
@Service
public class ReportService {

    private static final String TEMPLATE_PATH = "reports/plantilla.jrxml";

    /**
     * Genera el informe PDF a partir de los datos proporcionados por el usuario.
     *
     * @param request datos capturados desde el frontend
     * @return PDF generado en formato binario
     */
    public byte[] generateReport(ReportRequest request) {
        try (InputStream template = new ClassPathResource(TEMPLATE_PATH).getInputStream()) {
            JasperReport compiledTemplate = JasperCompileManager.compileReport(template);
            Map<String, Object> parameters = buildParameters(request);
            JasperPrint filledReport = JasperFillManager.fillReport(compiledTemplate, parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(filledReport);
        } catch (IOException | JRException e) {
            throw new ReportGenerationException("Error al generar el informe Jasper", e);
        }
    }

    private Map<String, Object> buildParameters(ReportRequest request) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nombreCliente", request.getNombreCliente());
        parameters.put("fechaInforme", Date.from(request.getFechaInforme().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        parameters.put("importeTotal", request.getImporteTotal() != null ? request.getImporteTotal() : BigDecimal.ZERO);
        return parameters;
    }
}
