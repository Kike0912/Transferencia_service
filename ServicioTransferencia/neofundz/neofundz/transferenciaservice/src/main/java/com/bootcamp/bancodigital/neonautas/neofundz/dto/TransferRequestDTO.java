package com.bootcamp.bancodigital.neonautas.neofundz.dto;


import jakarta.validation.constraints.*; // Para las anotaciones de validación
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) para la solicitud de transferencia.
 * Contiene los datos necesarios para iniciar una transferencia entre cuentas.
 */
@Data               // Genera getters, setters, toString, equals, hashCode (Lombok)
@NoArgsConstructor  // Genera un constructor sin argumentos (Lombok)
@AllArgsConstructor // Genera un constructor con todos los campos (Lombok)
public class TransferRequestDTO {

    /**
     * Identificador único de la cuenta desde la que se debitará el monto.
     * Debe tener exactamente 8 caracteres y no puede estar vacío.
     */
    @NotBlank(message = "El ID de la cuenta origen no puede estar vacío")
    @Size(min = 8, max = 8, message = "El ID de la cuenta origen debe tener 8 caracteres")
    private String idCuentaOrigen;

    /**
     * Identificador único de la cuenta a la que se acreditará el monto.
     * Debe tener exactamente 8 caracteres y no puede estar vacío.
     */
    @NotBlank(message = "El ID de la cuenta destino no puede estar vacío")
    @Size(min = 8, max = 8, message = "El ID de la cuenta destino debe tener 8 caracteres")
    private String idCuentaDestino;

    /**
     * El monto de dinero a transferir.
     * No puede ser nulo y debe ser un valor positivo (mayor o igual a 0.01).
     */
    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", inclusive = true, message = "El monto debe ser mayor o igual a 0.01")
    private BigDecimal monto;

    /**
     * Descripción o motivo de la transferencia.
     * No puede estar vacío y tiene una longitud máxima de 50 caracteres.
     */
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 50, message = "La descripción no puede exceder los 50 caracteres")
    private String descripcion;
}