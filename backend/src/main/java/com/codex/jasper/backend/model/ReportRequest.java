package com.codex.jasper.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa los datos recibidos desde el formulario web para generar el informe Jasper.
 */
public class ReportRequest {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotNull(message = "La fecha del informe es obligatoria")
    private LocalDate fechaInforme;

    @NotNull(message = "El importe total es obligatorio")
    @PositiveOrZero(message = "El importe total debe ser mayor o igual a cero")
    private BigDecimal importeTotal;

    public ReportRequest() {
    }

    public ReportRequest(String nombreCliente, LocalDate fechaInforme, BigDecimal importeTotal) {
        this.nombreCliente = nombreCliente;
        this.fechaInforme = fechaInforme;
        this.importeTotal = importeTotal;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public LocalDate getFechaInforme() {
        return fechaInforme;
    }

    public void setFechaInforme(LocalDate fechaInforme) {
        this.fechaInforme = fechaInforme;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
}
