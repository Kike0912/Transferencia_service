package com.bootcamp.bancodigital.neonautas.neofundz.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @Column(name = "Id_Cliente", length = 8, nullable = false)
    private String idCliente;

    @Column(name = "Nombre_Completo", length = 60, nullable = false)
    private String nombreCompleto;

    @Column(name = "Cedula_Unica", length = 10, nullable = false, unique = true)
    private String cedulaUnica;

    @Column(name = "Correo", length = 30, nullable = false)
    private String correo;

    @Column(name = "Genero_neo", length = 10, nullable = false)
    private String generoNeo;

    @Column(name = "estado_Cliente_neo", length = 10, nullable = false)
    private String estadoClienteNeo;

    // Relación inversa (opcional, pero útil a veces)
    // @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    // private List<Cuenta> cuentas;

    // Puedes agregar la relación con Cliente_login si la necesitas
}