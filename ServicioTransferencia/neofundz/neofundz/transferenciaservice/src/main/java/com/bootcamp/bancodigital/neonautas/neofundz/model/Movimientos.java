package com.bootcamp.bancodigital.neonautas.neofundz.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate; // Usar LocalDate para DATE SQL


@Entity
@Table(name = "movimientos")
@Data
@NoArgsConstructor
public class Movimientos {

    @Id
    @Column(name = "Id_movimientos", length = 8, nullable = false)
    private String idMovimientos;

    // Relación ManyToOne con Cuenta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_cuenta", nullable = false)
    private Cuenta cuenta;

    @Column(name = "Tipo_Movimiento_neo", length = 2, nullable = false)
    private String tipoMovimientoNeo; // 'DB' o 'CR'

    @Column(name = "Monto_movimiento", precision = 10, scale = 2, nullable = false)
    private BigDecimal montoMovimiento;

    @Column(name = "Id_transaccion", length = 8, nullable = false)
    private String idTransaccion;

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
    
    

    // Relación inversa con Historial_Movimientos (opcional)
    // @OneToMany(mappedBy = "movimiento", fetch = FetchType.LAZY)
    // private List<HistorialMovimientos> historial;
}