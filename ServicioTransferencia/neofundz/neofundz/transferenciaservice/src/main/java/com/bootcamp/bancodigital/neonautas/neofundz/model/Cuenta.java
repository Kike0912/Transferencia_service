package com.bootcamp.bancodigital.neonautas.neofundz.model;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Entity
@Table(name = "cuenta")
@Data
@NoArgsConstructor
public class Cuenta {

    @Id
    @Column(name = "Id_Cuenta", length = 8, nullable = false)
    private String idCuenta;

    // Relación ManyToOne con Cliente
    @ManyToOne(fetch = FetchType.LAZY) // LAZY es generalmente preferido
    @JoinColumn(name = "Id_Cliente", nullable = false) // Nombre de la FK en la tabla Cuenta
    private Cliente cliente;

    @Column(name = "Numero_Cuenta", length = 12, nullable = false, unique = true)
    private String numeroCuenta;

    @Column(name = "Saldo", precision = 10, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Column(name = "Tipo_de_Cuenta_neo", length = 10, nullable = false)
    private String tipoDeCuentaNeo;

    @Column(name = "Estado_de_Cuenta_neo", length = 10, nullable = false)
    private String estadoDeCuentaNeo;

    // Relación inversa con Movimientos (opcional)
    // @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
    // private List<Movimientos> movimientos;

    // Puedes agregar la relación con Tarjeta si la necesitas
}