package com.unip.aps;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmosOrdenacao {


    private static int comparar(Incendio a, Incendio b, String criterio) {
        switch (criterio.toLowerCase()) {
            case "data":
                return a.getData().compareTo(b.getData());
            case "bioma":
                return a.getBioma().compareTo(b.getBioma());
            case "municipio":
                return a.getMunicipio().compareTo(b.getMunicipio());
            case "risco":
            default:
                return Double.compare(a.getRiscoFogo(), b.getRiscoFogo());
        }
    }

    public static ResultadoOrdenacao bubbleSort(List<Incendio> lista, String criterio) {
        List<Incendio> dados = new ArrayList<>(lista);
        long comparacoes = 0;
        long trocas = 0;
        long inicio = System.currentTimeMillis();

        int n = dados.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparacoes++;
                if (comparar(dados.get(j), dados.get(j + 1), criterio) > 0) {
                    Incendio temp = dados.get(j);
                    dados.set(j, dados.get(j + 1));
                    dados.set(j + 1, temp);
                    trocas++;
                }
            }
        }

        long fim = System.currentTimeMillis();
        return new ResultadoOrdenacao("Bubble Sort (" + criterio + ")", comparacoes, trocas, (fim - inicio));
    }

    public static ResultadoOrdenacao selectionSort(List<Incendio> lista, String criterio) {
        List<Incendio> dados = new ArrayList<>(lista);
        long comparacoes = 0;
        long trocas = 0;
        long inicio = System.currentTimeMillis();

        int n = dados.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparacoes++;
                if (comparar(dados.get(j), dados.get(minIndex), criterio) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Incendio temp = dados.get(i);
                dados.set(i, dados.get(minIndex));
                dados.set(minIndex, temp);
                trocas++;
            }
        }

        long fim = System.currentTimeMillis();
        return new ResultadoOrdenacao("Selection Sort (" + criterio + ")", comparacoes, trocas, (fim - inicio));
    }

    public static ResultadoOrdenacao insertionSort(List<Incendio> lista, String criterio) {
        List<Incendio> dados = new ArrayList<>(lista);
        long comparacoes = 0;
        long trocas = 0;
        long inicio = System.currentTimeMillis();

        int n = dados.size();
        for (int i = 1; i < n; i++) {
            Incendio chave = dados.get(i);
            int j = i - 1;

            while (j >= 0) {
                comparacoes++;
                if (comparar(dados.get(j), chave, criterio) > 0) {
                    dados.set(j + 1, dados.get(j));
                    trocas++;
                    j--;
                } else {
                    break;
                }
            }
            dados.set(j + 1, chave);
        }

        long fim = System.currentTimeMillis();
        return new ResultadoOrdenacao("Insertion Sort (" + criterio + ")", comparacoes, trocas, (fim - inicio));
    }

    public static ResultadoOrdenacao mergeSort(List<Incendio> lista, String criterio) {
        List<Incendio> dados = new ArrayList<>(lista);
        long[] metricas = {0, 0}; // {comparacoes, escritas}
        long inicio = System.currentTimeMillis();

        if (dados.size() > 1) {
            mergeSortDivide(dados, 0, dados.size() - 1, metricas, criterio);
        }

        long fim = System.currentTimeMillis();
        return new ResultadoOrdenacao("Merge Sort (" + criterio + ")", metricas[0], metricas[1], (fim - inicio));
    }

    private static void mergeSortDivide(List<Incendio> dados, int inicio, int fim, long[] metricas, String criterio) {
        if (inicio < fim) {
            int meio = inicio + (fim - inicio) / 2;
            mergeSortDivide(dados, inicio, meio, metricas, criterio);
            mergeSortDivide(dados, meio + 1, fim, metricas, criterio);
            mergeSortConquista(dados, inicio, meio, fim, metricas, criterio);
        }
    }

    private static void mergeSortConquista(List<Incendio> dados, int inicio, int meio, int fim, long[] metricas, String criterio) {
        List<Incendio> esquerda = new ArrayList<>(dados.subList(inicio, meio + 1));
        List<Incendio> direita = new ArrayList<>(dados.subList(meio + 1, fim + 1));

        int i = 0, j = 0, k = inicio;
        while (i < esquerda.size() && j < direita.size()) {
            metricas[0]++;
            if (comparar(esquerda.get(i), direita.get(j), criterio) <= 0) {
                dados.set(k++, esquerda.get(i++));
            } else {
                dados.set(k++, direita.get(j++));
            }
            metricas[1]++;
        }

        while (i < esquerda.size()) {
            dados.set(k++, esquerda.get(i++));
            metricas[1]++;
        }

        while (j < direita.size()) {
            dados.set(k++, direita.get(j++));
            metricas[1]++;
        }
    }

    public static ResultadoOrdenacao quickSort(List<Incendio> lista, String criterio) {
        List<Incendio> dados = new ArrayList<>(lista);
        long[] metricas = {0, 0}; // {comparacoes, trocas}
        long inicio = System.currentTimeMillis();

        if (!dados.isEmpty()) {
            quickSortParticiona(dados, 0, dados.size() - 1, metricas, criterio);
        }

        long fim = System.currentTimeMillis();
        return new ResultadoOrdenacao("Quick Sort (" + criterio + ")", metricas[0], metricas[1], (fim - inicio));
    }

    private static void quickSortParticiona(List<Incendio> dados, int baixo, int alto, long[] metricas, String criterio) {
        if (baixo < alto) {
            int pi = quickSortParticaoIndex(dados, baixo, alto, metricas, criterio);
            quickSortParticiona(dados, baixo, pi - 1, metricas, criterio);
            quickSortParticiona(dados, pi + 1, alto, metricas, criterio);
        }
    }

    private static int quickSortParticaoIndex(List<Incendio> dados, int baixo, int alto, long[] metricas, String criterio) {
        Incendio pivoObj = dados.get(alto);
        int i = (baixo - 1);

        for (int j = baixo; j < alto; j++) {
            metricas[0]++;
            if (comparar(dados.get(j), pivoObj, criterio) <= 0) {
                i++;
                Incendio temp = dados.get(i);
                dados.set(i, dados.get(j));
                dados.set(j, temp);
                metricas[1]++;
            }
        }

        Incendio temp = dados.get(i + 1);
        dados.set(i + 1, dados.get(alto));
        dados.set(alto, temp);
        metricas[1]++;

        return i + 1;
    }
}