package com.unip.aps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorCsv {

    public static List<Incendio> carregarDados(String caminhoArquivo) {
        List<Incendio> listaIncendios = new ArrayList<>();


        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            if (new File("incendios_2024.csv").exists()) {
                caminhoArquivo = "incendios_2024.csv";
            } else if (new File("incendios_2024.csv").exists()) {
                caminhoArquivo = "incendios_2024.csv";
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String cabecalho = br.readLine();
            if (cabecalho == null) return listaIncendios;

            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] colunas = linha.split(",");

                try {
                    if (colunas.length >= 9) {
                        String data = colunas[4].trim();
                        String municipio = colunas[7].trim();
                        String estado = colunas[6].trim();
                        String bioma = colunas[8].trim();
                        double riscoFogo = Double.parseDouble(colunas[2].trim().replace(",", "."));

                        listaIncendios.add(new Incendio(data, municipio, estado, bioma, riscoFogo));
                    }
                } catch (Exception e) {
                    //ignora linhas com erro
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
        }

        return listaIncendios;
    }
}