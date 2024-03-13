package com.relatorioPdf.crizzyz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListaRelatorio {
    private String cidade;
    private List<Hospital> hospitais;
    private String tipoConsulta;
}
