package com.codex.jasper.backend.service;

/**
 * Excepción de dominio para errores durante la generación del informe Jasper.
 */
public class ReportGenerationException extends RuntimeException {

    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
