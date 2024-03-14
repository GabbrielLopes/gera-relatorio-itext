package com.relatorioPdf.crizzyz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadoDTO {

    private String atributo;
    private boolean inserido;

    public DadoDTO(String atributo) {
        this.atributo = atributo;
    }
}
