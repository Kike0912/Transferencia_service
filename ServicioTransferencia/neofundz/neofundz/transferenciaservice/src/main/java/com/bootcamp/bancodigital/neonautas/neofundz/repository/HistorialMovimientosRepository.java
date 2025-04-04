package com.bootcamp.bancodigital.neonautas.neofundz.repository;

import com.bootcamp.bancodigital.neonautas.neofundz.model.HistorialMovimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMovimientosRepository extends JpaRepository<HistorialMovimientos, String> {
    // Puedes agregar métodos de consulta específicos si los necesitas
}