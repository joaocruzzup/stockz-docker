package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.model.Transacao;
import br.com.catalisa.stockz.model.dto.EstoqueDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service

public class RelatorioService {
    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private TransacaoService transacaoService;

    public byte[] gerarRelatorioEstoquePDF(String conteudoRelatorio) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataAtualFormatada = LocalDateTime.now().format(formatter);

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();
        // Configuração fonte do cabaçelho
        Font fonteCabecalho = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);

        // Configuração do cabeçalho
        Paragraph nomeSoftware = new Paragraph("StockZ - Gerenciamento de Estoque", fonteCabecalho);
        Paragraph titulo = new Paragraph("Relatório de Estoque", fonteCabecalho);
        Paragraph dataGeracao = new Paragraph("Relatório gerado em " + dataAtualFormatada, fonteCabecalho);
        nomeSoftware.setAlignment(Element.ALIGN_CENTER);
        titulo.setAlignment(Element.ALIGN_CENTER);
        dataGeracao.setAlignment(Element.ALIGN_CENTER);
        document.add(nomeSoftware);
        document.add(titulo);
        document.add(dataGeracao);

        // Adicionando linha para separar o cabeçalho das demais informações
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0));
        document.add(Chunk.NEWLINE);
        document.add(lineSeparator);

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph(conteudoRelatorio));
        document.close();

        return outputStream.toByteArray();
    }

    public String gerarConteudoRelatorioEstoque() {
        List<EstoqueDTO> estoqueDTOList = estoqueService.listarTodos();

        StringBuilder conteudoRelatorio = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


        int i = 1;
        for (EstoqueDTO estoqueDTO : estoqueDTOList) {
            conteudoRelatorio.append("Produto: ").append(estoqueDTO.getProduto().getNome()).append("\n");
            conteudoRelatorio.append("Registrado em: ").append(estoqueDTO.getDataHoraRegistro().format(formatter)).append("\n");
            conteudoRelatorio.append("Quantidade em estoque: ").append(estoqueDTO.getQuantidade()).append("\n\n");

            List<Transacao> transacoes = transacaoService.buscarTransacoesPorProduto(estoqueDTO.getProduto());

            conteudoRelatorio.append("Transações:\n");
            for (Transacao transacao : transacoes) {
                conteudoRelatorio.append("- Data/Hora: ").append(transacao.getDataHora().format(formatter)).append("\n");
                conteudoRelatorio.append("  Tipo de transação: ").append(transacao.getTipoTransacao()).append("\n");
                conteudoRelatorio.append("  Quantidade movimentada: ").append(transacao.getQuantidade()).append("\n");
                String tipoUsuario = transacao.getTipoTransacao() == TipoTransacao.ENTRADA ? "fornecedor" : "comprador";
                conteudoRelatorio.append("  Nome do ").append(tipoUsuario).append(": ").append(transacao.getUsuario().getNome()).append("\n");
                conteudoRelatorio.append("  Email do ").append(tipoUsuario).append(": ").append(transacao.getUsuario().getEmail()).append("\n");
                conteudoRelatorio.append("\n");
            }

            conteudoRelatorio.append("\n");
            conteudoRelatorio.append("----------------------------------------------------");
            conteudoRelatorio.append("\n");
        }
        return conteudoRelatorio.toString();
    }
}
