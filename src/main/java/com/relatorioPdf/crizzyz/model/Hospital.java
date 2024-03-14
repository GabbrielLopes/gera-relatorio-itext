package com.relatorioPdf.crizzyz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hospital {
    private String diretorTecnico;
    private String telefone;
    private String endereco;
    private String numero;
    private String bairro;
    private String nomeClinica;
    private String crm;
    private String cep;
}
