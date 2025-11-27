package com.codex.jasper.backend.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Pruebas de integración del controlador REST que expone el endpoint de generación de informes.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void generateReportEndpointShouldReturnPdf() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("documentoTitulo", "DOCUMENTO DE INSTALACIÓN");
        body.put("entorno", "PRODUCCIÓN");
        body.put("plataforma", "PCM");
        body.put("funcionalidad", "Alarmado Tráfico Internacional");
        body.put("version", "5.4.0");
        body.put("destinatario", "Telefónica Móviles España");
        body.put("fecha", LocalDate.of(2025, 11, 10));
        body.put("revision", "1.0");
        body.put("cliente", "TME");

        byte[] response = mockMvc.perform(post("/api/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(body)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", containsString(MediaType.APPLICATION_PDF_VALUE)))
                .andExpect(header().string("Content-Disposition", containsString("attachment")))
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        String responsePrefix = new String(response, 0, 4);
        org.junit.jupiter.api.Assertions.assertEquals("%PDF", responsePrefix);
    }
}
