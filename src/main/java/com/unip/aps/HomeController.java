package com.unip.aps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = "text/html;charset=UTF-8")
    public String index(
            @RequestParam(name = "criterio", defaultValue = "data") String criterio,
            @RequestParam(name = "ano", defaultValue = "2024") String ano) {

        String caminhoCsv = "incendios_" + ano + ".csv";

        File file = new File(caminhoCsv);
        if (!file.exists()) {
            if (new File("incendios_" + ano + ".csv").exists()) {
                caminhoCsv = "incendios_" + ano + ".csv";
            }
        }

        List<Incendio> dados = LeitorCsv.carregarDados(caminhoCsv);

        List<ResultadoOrdenacao> resultados = new ArrayList<>();
        if (!dados.isEmpty()) {
            resultados.add(AlgoritmosOrdenacao.bubbleSort(dados, criterio));
            resultados.add(AlgoritmosOrdenacao.selectionSort(dados, criterio));
            resultados.add(AlgoritmosOrdenacao.insertionSort(dados, criterio));
            resultados.add(AlgoritmosOrdenacao.mergeSort(dados, criterio));
            resultados.add(AlgoritmosOrdenacao.quickSort(dados, criterio));
        }

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html lang=\"pt\">");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("<title>MONITOR // INPE - Análise de Ordenação</title>");
        html.append("<style>");
        html.append("@import url('https://fonts.googleapis.com/css2?family=Share+Tech+Mono&family=Inter:wght@400;600&display=swap');");
        html.append("body { background-color: #0b0f19; color: #c9d1d9; font-family: 'Inter', sans-serif; margin: 0; padding: 30px; }");
        html.append(".wrapper { max-width: 1000px; margin: auto; background: #131d31; border: 1px solid #1f3055; border-radius: 6px; padding: 25px; box-shadow: 0 8px 24px rgba(0,0,0,0.6); }");
        html.append(".header-top { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #1f3055; padding-bottom: 15px; margin-bottom: 20px; }");
        html.append(".logo-tag { font-family: 'Share Tech Mono', monospace; color: #3fb950; font-size: 1.2rem; letter-spacing: 1px; }");
        html.append(".status-badge { background: rgba(63, 185, 80, 0.15); color: #3fb950; border: 1px solid #3fb950; padding: 4px 10px; font-size: 0.8rem; border-radius: 4px; font-family: monospace; }");
        html.append("h1 { color: #f0f6fc; font-size: 1.5rem; margin: 0 0 5px 0; }");
        html.append(".meta-info { font-family: 'Share Tech Mono', monospace; color: #8b949e; font-size: 0.95rem; margin-bottom: 20px; }");
        html.append(".toolbar { display: flex; gap: 10px; margin-bottom: 20px; align-items: center; flex-wrap: wrap; }");
        html.append(".toolbar span { font-size: 0.85rem; text-transform: uppercase; letter-spacing: 1px; color: #8b949e; margin-right: 5px; font-weight: 600; }");
        html.append(".btn-crit, .btn-ano { text-decoration: none; padding: 8px 16px; background: #1f3055; color: #c9d1d9; border-radius: 4px; font-size: 0.85rem; font-family: monospace; transition: all 0.2s; border: 1px solid #30363d; }");
        html.append(".btn-crit:hover, .btn-ano:hover { background: #30363d; color: #fff; }");
        html.append(".btn-crit.active { background: #238636; color: #fff; border-color: #2ea043; font-weight: bold; }");
        html.append(".btn-ano.active { background: #a371f7; color: #fff; border-color: #8957e5; font-weight: bold; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 10px; background: #0d1117; border-radius: 4px; overflow: hidden; }");
        html.append("th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #21262d; font-size: 0.95rem; }");
        html.append("th { background-color: #161b22; color: #8b949e; font-family: 'Share Tech Mono', monospace; text-transform: uppercase; font-size: 0.85rem; letter-spacing: 0.5px; }");
        html.append("td { font-family: 'Share Tech Mono', monospace; color: #e6edf3; }");
        html.append("tr:hover td { background-color: rgba(177, 186, 196, 0.03); }");
        html.append(".highlight { color: #58a6ff; font-weight: bold; }");
        html.append(".fast { color: #3fb950; font-weight: bold; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        html.append("<div class=\"wrapper\">");
        html.append("<div class=\"header-top\">");
        html.append("<div>");
        html.append("<h1>SISTEMA DE PROCESSAMENTO DE FOCOS DE INCÊNDIO</h1>");
        html.append("<div class=\"meta-info\">MÓDULO DE AVALIAÇÃO DE DESEMPENHO DE ORDENAÇÃO // INPE DATASET</div>");
        html.append("</div>");
        html.append("<div class=\"status-badge\">STATUS: OPERACIONAL</div>");
        html.append("</div>");

        html.append("<div class=\"meta-info\">");
        html.append("REGISTOS INDEXADOS: <span class=\"highlight\">").append(String.format("%,d", dados.size()).replace(',', '.')).append("</span> | ");
        html.append("DATASET ATIVO: <span class=\"highlight\">INPE (").append(ano).append(")</span>");
        html.append("</div>");

        html.append("<div class=\"toolbar\">");
        html.append("<span>DATASET:</span>");
        html.append("<a href=\"/?ano=2023&criterio=").append(criterio).append("\" class=\"btn-ano ").append(ano.equals("2023") ? "active" : "").append("\">[ 2023 ]</a>");
        html.append("<a href=\"/?ano=2024&criterio=").append(criterio).append("\" class=\"btn-ano ").append(ano.equals("2024") ? "active" : "").append("\">[ 2024 ]</a>");
        html.append("</div>");

        html.append("<div class=\"toolbar\">");
        html.append("<span>CRITÉRIO:</span>");
        html.append("<a href=\"/?ano=").append(ano).append("&criterio=data\" class=\"btn-crit ").append(criterio.equals("data") ? "active" : "").append("\">[ DATA ]</a>");
        html.append("<a href=\"/?ano=").append(ano).append("&criterio=bioma\" class=\"btn-crit ").append(criterio.equals("bioma") ? "active" : "").append("\">[ BIOMA ]</a>");
        html.append("<a href=\"/?ano=").append(ano).append("&criterio=municipio\" class=\"btn-crit ").append(criterio.equals("municipio") ? "active" : "").append("\">[ MUNICÍPIO ]</a>");
        html.append("<a href=\"/?ano=").append(ano).append("&criterio=risco\" class=\"btn-crit ").append(criterio.equals("risco") ? "active" : "").append("\">[ RISCO / LAT ]</a>");
        html.append("</div>");

        html.append("<table>");
        html.append("<thead><tr><th>Algoritmo</th><th>Comparações</th><th>Trocas / Escritas</th><th>Tempo de Execução</th></tr></thead>");
        html.append("<tbody>");
        for (ResultadoOrdenacao res : resultados) {
            String tempoClass = res.getTempoMs() <= 10 ? "fast" : "";
            html.append("<tr>");
            html.append("<td><span class=\"highlight\">").append(res.getNomeAlgoritmo()).append("</span></td>");
            html.append("<td>").append(String.format("%,d", res.getComparacoes()).replace(',', '.')).append("</td>");
            html.append("<td>").append(String.format("%,d", res.getTrocas()).replace(',', '.')).append("</td>");
            html.append("<td><span class=\"").append(tempoClass).append("\">").append(res.getTempoMs()).append(" ms</span></td>");
            html.append("</tr>");
        }
        html.append("</tbody>");
        html.append("</table>");

        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
}