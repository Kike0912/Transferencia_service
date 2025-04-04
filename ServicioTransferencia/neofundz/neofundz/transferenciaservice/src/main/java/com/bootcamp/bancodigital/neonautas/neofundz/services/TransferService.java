package com.bootcamp.bancodigital.neonautas.neofundz.services;

import com.bootcamp.bancodigital.neonautas.neofundz.dto.TransferRequestDTO;
import com.bootcamp.bancodigital.neonautas.neofundz.dto.TransferResponseDTO;
import com.bootcamp.bancodigital.neonautas.neofundz.model.Cuenta;
import com.bootcamp.bancodigital.neonautas.neofundz.model.Movimientos; // Asegúrate que esté importado
import com.bootcamp.bancodigital.neonautas.neofundz.repository.CuentaRepository;
import com.bootcamp.bancodigital.neonautas.neofundz.repository.MovimientosRepository;
import com.bootcamp.bancodigital.neonautas.neofundz.repository.TransferRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import java.time.LocalDateTime; // Descomenta si 'fechaMovimiento' en Movimientos es LocalDateTime y lo usas para ordenar

@Service // Anotación de servicio
@RequiredArgsConstructor // Inyección de dependencias vía constructor (Lombok)
@Slf4j // Para logging
public class TransferService {

    private final TransferRepository transferRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientosRepository movimientosRepository; // Repositorio para buscar el movimiento

    @Transactional // Mantenemos la transacción por si las lecturas post-SP deben ver los cambios del SP
    public TransferResponseDTO realizarTransferencia(TransferRequestDTO requestDTO) {
        log.info("Iniciando transferencia desde {} hacia {} por monto {} con descripción '{}'",
                 requestDTO.getIdCuentaOrigen(), requestDTO.getIdCuentaDestino(),
                 requestDTO.getMonto(), requestDTO.getDescripcion());

        // Declaramos las variables que obtendremos DESPUÉS de llamar al SP
        String idTransaccion;
        String idMovimientoOrigen;
        Movimientos movimientoDebito; // Variable para almacenar el movimiento encontrado

        try {
            // --- PASO 1: Llamar al Stored Procedure ---
            // Llamamos al método que devuelve void. No hay asignación aquí.
            log.debug("Llamando a sp_RealizarTransferencia para cuenta origen {}...", requestDTO.getIdCuentaOrigen());
            transferRepository.executeTransferencia(
                    requestDTO.getIdCuentaOrigen(),
                    requestDTO.getIdCuentaDestino(),
                    requestDTO.getMonto(),
                    requestDTO.getDescripcion()
            );
            // Si llegamos aquí, la llamada al SP no lanzó una DataAccessException (como un SIGNAL del SP o error de conexión).
            log.info("Llamada a sp_RealizarTransferencia completada sin DataAccessException.");


            // --- PASO 2: Buscar el Movimiento Resultante ---
           
            log.debug("Buscando el último movimiento de débito ('DB') para la cuenta {}", requestDTO.getIdCuentaOrigen());
            movimientoDebito = movimientosRepository
                    // *** ¡IMPORTANTE! Necesitas tener este método definido en MovimientosRepository ***
                    // Ajusta los nombres de campo 'FechaMovimiento' y 'IdMovimientos' si son diferentes en tu entidad Movimientos.
                    .findTopByCuentaIdCuentaAndTipoMovimientoNeoOrderByFechaMovimientoDescIdMovimientosDesc(
                        requestDTO.getIdCuentaOrigen(),
                        "DB" // Tipo de movimiento: Débito
                    )
                    .orElseThrow(() -> {
                        // ESTE ES UN ERROR CRÍTICO: El SP no falló explícitamente, pero no encontramos el movimiento esperado.
                        log.error("¡INCONSISTENCIA! SP ejecutado sin DataAccessException, pero NO se encontró el movimiento de débito esperado para la cuenta {}. " +
                                  "Posibles causas: Lógica interna del SP falló silenciosamente, delay en la transacción, error en criterios de búsqueda.",
                                  requestDTO.getIdCuentaOrigen());
                        return new RuntimeException("Error interno: No se pudo verificar el resultado de la transferencia en los movimientos.");
                    });


            // --- PASO 3: Extraer IDs del Movimiento Encontrado ---
            idTransaccion = movimientoDebito.getIdTransaccion();
            idMovimientoOrigen = movimientoDebito.getIdMovimientos();
            log.debug("Movimiento de débito encontrado (ID Movimiento: {}). ID de transacción asociado: {}", idMovimientoOrigen, idTransaccion);


            // --- PASO 4: Validar el ID de Transacción Obtenido ---
            // Verificamos que el ID de transacción recuperado del movimiento sea válido.
            if (idTransaccion == null || idTransaccion.trim().isEmpty()) {
                 log.error("¡INCONSISTENCIA DE DATOS! El movimiento de débito encontrado (ID Movimiento: {}) tiene un ID de transacción nulo o vacío. " +
                           "Verificar cómo el SP genera o asigna este ID en la tabla de movimientos.", idMovimientoOrigen);
                 // Lanzamos una excepción porque no podemos confiar en el resultado si falta el ID de transacción.
                 throw new RuntimeException("Error interno: Inconsistencia de datos después de la transferencia. No se encontró ID de transacción válido en el movimiento generado.");
            }

            // Si la validación pasa, el idTransaccion es considerado válido.
            log.info("Transferencia procesada y verificada a través del movimiento. ID de transacción: {}. ID Movimiento Débito: {}", idTransaccion, idMovimientoOrigen);


            // --- PASO 5: Obtener Detalles Adicionales (Nombres de Clientes) ---
            // Esta lógica no cambia, se ejecuta después de verificar la transferencia.
            log.debug("Buscando detalles de las cuentas {} y {}", requestDTO.getIdCuentaOrigen(), requestDTO.getIdCuentaDestino());
            Cuenta cuentaOrigen = cuentaRepository.findByIdWithCliente(requestDTO.getIdCuentaOrigen())
                    .orElseThrow(() -> new EntityNotFoundException("Cuenta origen no encontrada post-transferencia: " + requestDTO.getIdCuentaOrigen()));

            Cuenta cuentaDestino = cuentaRepository.findByIdWithCliente(requestDTO.getIdCuentaDestino())
                    .orElseThrow(() -> new EntityNotFoundException("Cuenta destino no encontrada post-transferencia: " + requestDTO.getIdCuentaDestino()));

            String nombreOrigen = (cuentaOrigen.getCliente() != null) ? cuentaOrigen.getCliente().getNombreCompleto() : "N/D";
            String nombreDestino = (cuentaDestino.getCliente() != null) ? cuentaDestino.getCliente().getNombreCompleto() : "N/D";


            // --- PASO 6: Construir y Devolver la Respuesta Exitosa ---
            // Usamos los datos de la solicitud original y los IDs obtenidos del movimiento.
            return TransferResponseDTO.builder()
                    .idCuentaOrigen(requestDTO.getIdCuentaOrigen())
                    .nombreClienteOrigen(nombreOrigen)
                    .idCuentaDestino(requestDTO.getIdCuentaDestino())
                    .nombreClienteDestino(nombreDestino)
                    .montoTransferido(requestDTO.getMonto())
                    .descripcion(requestDTO.getDescripcion())
                    .idTransaccion(idTransaccion) // <--- ID obtenido del movimiento
                    .idMovimientoOrigen(idMovimientoOrigen) // <--- ID del movimiento encontrado
                    .build();

        } catch (DataAccessException e) {
            // Captura errores de BD durante la llamada al SP o la búsqueda del movimiento.
            // Esto incluye errores SIGNAL del SP.
            String errorMessage = e.getMostSpecificCause().getMessage();
            log.error("Error de acceso a datos durante la operación de transferencia: {}. Causa Raíz: {}", e.getMessage(), errorMessage, e);
            // Devolver un mensaje genérico o intentar extraer el mensaje del SP si es posible/seguro.
            throw new RuntimeException("Fallo en la transferencia: " + errorMessage, e); // Resulta en 500 Internal Server Error
        } catch (EntityNotFoundException e) {
             // Captura errores si las cuentas no se encuentran DESPUÉS de la lógica principal (al buscar nombres).
             log.error("Error al buscar detalles de cuenta necesarios post-transferencia: {}", e.getMessage(), e);
             // Esto usualmente indica un problema interno, ya que las cuentas deberían existir si el SP funcionó.
             throw new RuntimeException("Error interno del servidor al recuperar detalles de la transferencia.", e); // 500
        } catch (RuntimeException e) {
            // Captura las excepciones que lanzamos explícitamente (Movimiento no encontrado, ID Transacción nulo).
            log.error("Error de lógica interna o validación fallida durante la transferencia: {}", e.getMessage(), e);
            throw e; // Relanzamos la excepción específica para que sea manejada por el handler global (resultará en 500).
        } catch (Exception e) {
             // Captura cualquier otro error inesperado no previsto.
             log.error("Error inesperado y no capturado específicamente durante el proceso de transferencia: {}", e.getMessage(), e);
             throw new RuntimeException("Error inesperado del servidor durante la transferencia.", e); // 500
        }
    }
}