package com.bootcamp.bancodigital.neonautas.neofundz.repository;

import com.bootcamp.bancodigital.neonautas.neofundz.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, String> { // ID es String

    // Asegura cargar el cliente asociado para obtener el nombre eficientemente
    @Query("SELECT cu FROM Cuenta cu LEFT JOIN FETCH cu.cliente WHERE cu.idCuenta = :idCuenta")
    Optional<Cuenta> findByIdWithCliente(@Param("idCuenta") String idCuenta);
}