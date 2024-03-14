package com.relatorioPdf.crizzyz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicaDTO {

    private String bairro0;
    private String clinica;
    private String diretorTecnico;
    private String crm;

    private String clinica2;

    private String endereco;
    private String numero;
    private String bairro;
    private String cep;
    private String telefone;


    public HashMap<Integer, DadoDTO> template() {
        HashMap<Integer, DadoDTO> map = new HashMap<>();

        map.put(0, new DadoDTO(this.bairro0));
        map.put(1, new DadoDTO(this.clinica));
        map.put(2, new DadoDTO(this.diretorTecnico));
        map.put(3, new DadoDTO(this.endereco));
        map.put(4, new DadoDTO(this.numero));
        map.put(5, new DadoDTO(this.bairro));
        map.put(6, new DadoDTO(this.cep));
        map.put(7, new DadoDTO(this.telefone));

        return map;
    }

    public String getClinica2() {
        return "(" + clinica2 + ")";
    }

}
