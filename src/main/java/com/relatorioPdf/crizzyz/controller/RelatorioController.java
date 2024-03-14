package com.relatorioPdf.crizzyz.controller;

import com.itextpdf.io.IOException;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.AreaBreakType;
import com.relatorioPdf.crizzyz.model.Hospital;
import com.relatorioPdf.crizzyz.model.ListaRelatorio;
import com.relatorioPdf.crizzyz.service.ProdutoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
public class RelatorioController {
    String filePath = "C:\\pdf-teste\\relatorio.pdf";
//
//    @Autowired
//    private ProdutoService service;

    float yMax = 380;
    float xMax = 410;

    @GetMapping("/relatorio")
    public ResponseEntity gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        try {
            List<ListaRelatorio> listaRelatorios = listaProdutos();
            Color setGray = new DeviceRgb(84, 84, 84);

            Paragraph p = new Paragraph("RB24- -UNIPADRAO APARTAMENTO IND/FAMIL-491550220").setFontSize(6).setBold(); // var -
            p.setFixedPosition(10, 830, 700);
            document.add(p);

            p = new Paragraph("HOSPITAIS").setBold().setUnderline(); // fixo
            p.setFixedPosition(10, 805, 100);
            document.add(p);

            p = new Paragraph("(PRONTO-SOCORRO, HOSPITAIS, HOSPITAL-DIA ISOLADO E MATERNIDADE)").setUnderline(); // var -
            p.setFixedPosition(75, 805, 600);
            document.add(p);

            p = new Paragraph("GERAL").setBackgroundColor(Color.LIGHT_GRAY).setFontSize(11); // fixo
            p.setFixedPosition(10, 780, 175);
            document.add(p);


            // todo for da coluna, maximo 410 , inicia com 10 e itera de 200 em 200, gera 3 colunas.
            // todo iverter a ordem dos fors o for do x tem q  estar dentro do for do y na primeira linha do for do y
            // todo ja cria o for do x para criar a primeira coluna.
            // todo quando sair do for do y e entrar no x para criar outra coluna nao pode perder a referencia da lista para continuar
            // todo de onde parou.
            // todo esse for tem que ser pela lista de dados a cada iteracao dela no final subtrai 200 do y ate chegar
            // todo em 200 0 yMax o valor pode iniciar em 780 fixo nao precisa do for com o  yMax
            for (ListaRelatorio listaRelatorio : listaRelatorios) {
                float x;
                float y;
                for (x = 10; x <= xMax; ) {
                    for (y = 780; yMax <= y; ) {
                        int h = 1;
                        System.out.println(h);
                        p = new Paragraph(listaRelatorio.getTipoConsulta()).setBackgroundColor(setGray).setBold().setFontSize(11); // var - tipo consulta
                        p.setFixedPosition(x, y -= 30, 175);
                        document.add(p);

                        p = new Paragraph(listaRelatorio.getCidade()).setBold();
                        p.setFixedPosition(x, y -= 25, 175);
                        document.add(p);

                        List<Hospital> hospitais = listaRelatorio.getHospitais();

                        for (Hospital hospital : hospitais) {

                            //se atingir o tamanho maximo da pagina vai para a proxima a coluna na primeira linha
                            if (y < yMax){
                                x = 200;
                                y = 780;
                            }
                            montaHospital(x, y, hospital, p, document);
                        }
                    }
                }
                // todo essa linha gera uma nova pagina tudo que for add depois vai para proxima pagina.
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                x += 200;
            }

            document.close();
        } catch (Exception e) {
            System.out.println(e);
            document.close();
        }
        return null;
    }

    private void montaHospital(float x, float y, Hospital hospital, Paragraph p, Document document){
        int g = 1;
        p = new Paragraph(hospital.getBairro()).setFontSize(6); // var - bairro
        p.setFixedPosition(x, y -= 10, 175);
        document.add(p);

        p = new Paragraph(hospital.getNomeHospital() + g).setBold().setFontSize(8); // var - nome da
        p.setFixedPosition(x, y -= 15, 175);
        document.add(p);

        p = new Paragraph("Diretor Técnico: ").setBold().setFontSize(7); //fixo
        p.setFixedPosition(x, y -= 15, 175);
        document.add(p);

        p = new Paragraph(hospital.getDiretorTecnico()).setFontSize(8);
        p.setFixedPosition(x += 50, y, 175);
        document.add(p);

        p = new Paragraph(" ").setFontSize(8);
        p.setFixedPosition(x -= 50, y -= 10, 175);
        document.add(p);


        p = new Paragraph("(" + hospital.getDiretorTecnico() + ")").setFontSize(8);
        p.setFixedPosition(x, y -= 10, 175);
        document.add(p);

        p = new Paragraph(hospital.getEndereco() + hospital.getNumero()).setFontSize(6);
        p.setFixedPosition(x, y -= 10, 175);
        document.add(p);

        p = new Paragraph(hospital.getBairro() + hospital.getCep()).setFontSize(6);
        p.setFixedPosition(x, y -= 10, 175);
        document.add(p);

        p = new Paragraph("Telefone: " + hospital.getTelefone()).setFontSize(6);
        p.setFixedPosition(x, y -= 10, 175);
        document.add(p);
        g++;
    }

    private ArrayList<ListaRelatorio> listaProdutos() {
        ArrayList<ListaRelatorio> lista = new ArrayList<>();
        lista.add(new ListaRelatorio("SÃO JOSÉ DOS CAMPOS", listaHospitais(), "CONSULTA PSIQUIATRIA"));
        return lista;
    }

    private ArrayList<Hospital> listaHospitais() {
        ArrayList<Hospital> lista = new ArrayList<>();
        for(int i=0; i < new Random().nextInt(3); i++){
        lista.add(new Hospital("ODEILTON TADEU SOARES", "(12) 3944-9090",
                "Doutor Bezerra De Menezes, ", "700", "Jardim Terreo De Ouro",
                "CENTRO DE VALORIZAÇÃO DA VIDA", "CRM(68139)", "CEP:12229380"));
        }
        return lista;
    }
}