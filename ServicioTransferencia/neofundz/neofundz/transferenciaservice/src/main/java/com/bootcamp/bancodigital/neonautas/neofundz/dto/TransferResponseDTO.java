package com.bootcamp.bancodigital.neonautas.neofundz.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponseDTO {

    private String idCuentaOrigen;
    private String nombreClienteOrigen;
    private String idCuentaDestino;
    private String nombreClienteDestino;
    private BigDecimal montoTransferido; // Monto que se intentó transferir
    private String descripcion;         // Descripción proporcionada en la solicitud
    private String idTransaccion;       // ID único de la transacción (del SP)
    private String idMovimientoOrigen;  // ID del movimiento de débito generado
}