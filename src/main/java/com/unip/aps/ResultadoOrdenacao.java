package com.unip.aps;

public class ResultadoOrdenacao {
    private String nomeAlgoritmo;
    private long comparacoes;
    private long trocas;
    private long tempoExecucaoMs;

    public ResultadoOrdenacao(String nomeAlgoritmo, long comparacoes, long trocas, long tempoExecucaoMs) {
        this.nomeAlgoritmo = nomeAlgoritmo;
        this.comparacoes = comparacoes;
        this.trocas = trocas;
        this.tempoExecucaoMs = tempoExecucaoMs;
    }

    public String getNomeAlgoritmo() { return nomeAlgoritmo; }
    public long getComparacoes() { return comparacoes; }
    public long getTrocas() { return trocas; }
    public long getTempoExecucaoMs() { return tempoExecucaoMs; }

    @Override
    public String toString() {
        return String.format("Algoritmo: %s | Comparações: %,d | Trocas: %,d | Tempo: %d ms",
                nomeAlgoritmo, comparacoes, trocas, tempoExecucaoMs);
    }

    public long getTempoMs() {
        return tempoExecucaoMs;
    }
}
