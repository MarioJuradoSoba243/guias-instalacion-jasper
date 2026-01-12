package com.codex.jasper.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Representa los datos recibidos desde el formulario web para construir la portada del documento.
 */
public class ReportRequest {

    @NotBlank(message = "El título del documento es obligatorio")
    private String documentoTitulo;

    @NotBlank(message = "El entorno es obligatorio")
    private String entorno;

    @NotBlank(message = "La plataforma es obligatoria")
    private String plataforma;

    @NotBlank(message = "La funcionalidad es obligatoria")
    private String funcionalidad;

    @NotBlank(message = "La versión es obligatoria")
    private String version;

    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatario;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "La revisión es obligatoria")
    private String revision;

    @NotBlank(message = "El cliente es obligatorio")
    private String cliente;

    public ReportRequest() {
    }

    public ReportRequest(String documentoTitulo, String entorno, String plataforma, String funcionalidad,
                         String version, String destinatario, LocalDate fecha, String revision, String cliente) {
        this.documentoTitulo = documentoTitulo;
        this.entorno = entorno;
        this.plataforma = plataforma;
        this.funcionalidad = funcionalidad;
        this.version = version;
        this.destinatario = destinatario;
        this.fecha = fecha;
        this.revision = revision;
        this.cliente = cliente;
    }

    public String getDocumentoTitulo() {
        return documentoTitulo;
    }

    public void setDocumentoTitulo(String documentoTitulo) {
        this.documentoTitulo = documentoTitulo;
    }

    public String getEntorno() {
        return entorno;
    }

    public void setEntorno(String entorno) {
        this.entorno = entorno;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(String funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
