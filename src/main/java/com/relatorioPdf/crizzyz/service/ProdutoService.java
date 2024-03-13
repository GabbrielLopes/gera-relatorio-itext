package com.relatorioPdf.crizzyz.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    public List<String> dividirTexto(String texto, float larguraMaxima) {
        List<String> partes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        String[] palavras = texto.split("\\s+"); // Divide o texto em palavras usando espaços como delimitador

        for (String palavra : palavras) {
            float larguraPalavra = getWidth(palavra); // Suponha que você tenha um método para obter a largura da palavra

            if (sb.length() == 0) {
                sb.append(palavra);
            } else if (getWidth(sb.toString() + " " + palavra) <= larguraMaxima) {
                sb.append(" ").append(palavra);
            } else {
                partes.add(sb.toString());
                sb = new StringBuilder(palavra);
            }
        }

        if (sb.length() > 0) {
            partes.add(sb.toString());
        }

        return partes;
    }

    // Suponha que você tenha um método para obter a largura de uma palavra
    public static float getWidth(String palavra) {
        // Implemente a lógica para obter a largura da palavra
        return palavra.length() * 8; // Exemplo simplificado: largura é o comprimento da palavra multiplicado por 8 (apenas para fins de ilustração)
    }
}
