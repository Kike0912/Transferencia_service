package com.bootcamp.bancodigital.neonautas.neofundz.repository;

import com.bootcamp.bancodigital.neonautas.neofundz.model.Cuenta; // Importa una entidad gestionada (necesario para Repository<T, ID>)
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.Repository; // Usamos Repository base ya que solo definimos un método específico
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

/**
 * Repositorio para operaciones de transferencia que involucran el Stored Procedure.
 */
public interface TransferRepository extends Repository<Cuenta, String> { // Extiende Repository<TuEntidad, TipoDeSuID>

 
    @Procedure(procedureName = "sp_RealizarTransferencia")
    void executeTransferencia( // <--- Devuelve void
        @Param("CuentaOrigen") String cuentaOrigen,
        @Param("CuentaDestino") String cuentaDestino,
        @Param("Monto") BigDecimal monto,
        @Param("Descripcion") String descripcion
    );

}