package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.EstoqueDTO;
import br.com.catalisa.stockz.service.EstoqueService;
import br.com.catalisa.stockz.service.RelatorioService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping(path = "/api/estoque")
public class EstoqueController {
    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    ResponseEntity<List<EstoqueDTO>> listarTodos(){
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @GetMapping("/relatorio")
    public ResponseEntity<Resource> gerarRelatorioPDF() throws DocumentException {

        byte[] pdfContent = relatorioService.gerarRelatorioEstoquePDF(relatorioService.gerarConteudoRelatorioEstoque());

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdfContent));

        // Indificando download do pdf nos headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(resource);
    }
}
