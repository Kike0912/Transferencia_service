package com.bootcamp.bancodigital.neonautas.neofundz.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "historial_movimientos")
@Data
@NoArgsConstructor
public class HistorialMovimientos {

    @Id
    @Column(name = "Id_historial_movimiento", length = 8, nullable = false)
    private String idHistorialMovimiento;

    // Relación ManyToOne con Movimientos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_movimiento", nullable = false)
    private Movimientos movimiento;

    @Column(name = "Tipo_Movimiento", length = 2, nullable = false)
    private String tipoMovimiento; // 'DB' o 'CR'

    @Column(name = "Monto_movimiento", precision = 10, scale = 2, nullable = false)
    private BigDecimal montoMovimiento;

    @Column(name = "Estado_Transaccion", length = 10, nullable = false)
    private String estadoTransaccion;

    @Column(name = "Fecha_Movimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaMovimiento;

    @Column(name = "Inicio_Movimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate inicioMovimiento;

    @Column(name = "Fin_Movimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate finMovimiento;
}