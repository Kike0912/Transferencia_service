package com.bootcamp.bancodigital.neonautas.neofundz.repository;

import com.bootcamp.bancodigital.neonautas.neofundz.model.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, String> { // ID es String

    /**
     * Busca el primer movimiento que coincida con la cuenta, ID de transacción
     * y tipo de movimiento. Útil para encontrar el movimiento de débito
     * después de una transferencia exitosa.
     *
     * @param idCuenta ID de la cuenta (origen en este caso)
     * @param idTransaccion ID de la transacción devuelto por el SP
     * @param tipoMovimientoNeo Tipo de movimiento ('DB' para débito)
     * @return Optional<Movimientos>
     */
    Optional<Movimientos> findFirstByCuentaIdCuentaAndIdTransaccionAndTipoMovimientoNeo(
            String idCuenta, String idTransaccion, String tipoMovimientoNeo);
    
    Optional<Movimientos> findTopByCuentaIdCuentaAndTipoMovimientoNeoOrderByFechaMovimientoDescIdMovimientosDesc(String idCuenta, String tipoMovimientoNeo);

     // Alternativa si necesitas el más reciente por si acaso (aunque idTransaccion debería ser suficiente)
     // Optional<Movimientos> findTopByCuentaIdCuentaAndIdTransaccionAndTipoMovimientoNeoOrderByFechaMovimientoDescIdMovimientosDesc(
     //       String idCuenta, String idTransaccion, String tipoMovimientoNeo);
}