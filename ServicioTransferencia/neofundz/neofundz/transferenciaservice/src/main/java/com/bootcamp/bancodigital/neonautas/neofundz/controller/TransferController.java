package com.bootcamp.bancodigital.neonautas.neofundz.controller;


import com.bootcamp.bancodigital.neonautas.neofundz.dto.TransferRequestDTO;
import com.bootcamp.bancodigital.neonautas.neofundz.dto.TransferResponseDTO;
import com.bootcamp.bancodigital.neonautas.neofundz.services.TransferService; // Importa la clase de servicio
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/neofundz-common") // Comenta o elimina esta línea
@RequestMapping("/transfers") // O usa solo la raíz para este controlador dentro del context path
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService; // Inyecta la clase de servicio directamente
    
   

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDTO> realizarTransferencia(
            @Valid @RequestBody TransferRequestDTO requestDTO) {
        // Las validaciones (@Valid) fallidas devolverán 400 Bad Request automáticamente
        // Los errores del servicio (DataAccessException, RuntimeException) devolverán 500 Internal Server Error
        TransferResponseDTO responseDTO = transferService.realizarTransferencia(requestDTO);
        return ResponseEntity.ok(responseDTO); // 200 OK
    }
}