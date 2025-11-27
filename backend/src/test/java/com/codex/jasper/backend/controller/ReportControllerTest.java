package com.codex.jasper.backend.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
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
        body.put("nombreCliente", "Cliente Test");
        body.put("fechaInforme", LocalDate.of(2024, 5, 20));
        body.put("importeTotal", new BigDecimal("2500.50"));

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
