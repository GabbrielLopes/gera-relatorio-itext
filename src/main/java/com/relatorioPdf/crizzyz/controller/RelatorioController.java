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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class RelatorioController {
    String filePath = "src/main/resources/report.pdf";
//
//    @Autowired
//    private ProdutoService service;


    @GetMapping("/relatorio")
    public ResponseEntity gerarRelatorio() throws IOException, FileNotFoundException {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        try {
            List<ListaRelatorio> listaRelatorios = listaProdutos();
            Color gray = new DeviceRgb(84, 84, 84);

            montaCabecalho(document);

            Paragraph p;

            // altura min 780 -  largura começa em 10 e 610 largura maxima

            float yMax = 380;
            float xMax = 410;

            float x = 10;
            float y = 780;
            for (ListaRelatorio listaRelatorio : listaRelatorios) {

                p = new Paragraph(listaRelatorio.getTipoConsulta()).setBackgroundColor(gray).setBold().setFontSize(11); // var - tipo consulta
                p.setFixedPosition(x, y -= 30, 175);
                document.add(p);

                p = new Paragraph(listaRelatorio.getCidade()).setBold();
                p.setFixedPosition(x, y -= 25, 175);
                document.add(p);

                for (Hospital hospital : listaRelatorio.getHospitais()) {
                    //se atingir o tamanho maximo da pagina vai para a proxima a coluna na primeira linha
                    if (y <= 100) {
                        x += 200;
                        y = 780;
                    }
                    p = new Paragraph(hospital.getBairro()).setFontSize(6).setBold().setItalic(); // var - bairro
                    p.setFixedPosition(x, y -= 10, 175);
                    document.add(p);

                    p = new Paragraph(hospital.getNomeClinica()).setBold().setFontSize(8); // var - nome da
                    p.setFixedPosition(x, y -= 15, 175);
                    document.add(p);

                    p = new Paragraph("Diretor Técnico: ").setBold().setFontSize(7).setWidth(175); //fixo
                    p.setFixedPosition(x, y -= 15, 175);
                    document.add(p);

                    p = new Paragraph(hospital.getDiretorTecnico().toUpperCase() +
                            "(CRM " + hospital.getCrm() + ")").setFontSize(7).setWidth(125);
                    p.setFixedPosition(x += 50, y, 175);
                    document.add(p);

                    p = new Paragraph(" ").setFontSize(8);
                    p.setFixedPosition(x -= 50, y -= 10, 175);
                    document.add(p);


                    p = new Paragraph("(" + hospital.getNomeClinica() + ")").setFontSize(8);
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
                }
                if (y <= 100) {
                    x += 200;
                    y = 780;
                }

                if(x > 410){
                    // todo essa linha gera uma nova pagina tudo que for add depois vai para proxima pagina.
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                    x = 10;
                    y = 780;
                }
            }


            document.close();
            System.out.println("done");
        } catch (Exception e) {
            System.out.println(e);
            document.close();
        }
        return null;
    }

    private static void montaCabecalho(Document document) {
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
    }
    private ArrayList<ListaRelatorio> listaProdutos() {
        ArrayList<ListaRelatorio> lista = new ArrayList<>();
        for (int x = 0; x < 15; x++) {
            lista.add(new ListaRelatorio("SÃO JOSÉ DOS CAMPOS", listaHospitais(), "CONSULTA PSIQUIATRIA"));
        }
        return lista;
    }

    private ArrayList<Hospital> listaHospitais() {
        ArrayList<Hospital> lista = new ArrayList<>();
        int numeroAleatorio = (int) (Math.random() * 3);
        for (int i = 0; i < (numeroAleatorio == 0 ? 1 : numeroAleatorio); i++) {
            lista.add(new Hospital("ODEILTON TADEU SOARES", "(12) 3944-9090",
                    "Doutor Bezerra De Menezes, ", "700", "Jardim Terrão De Ouro",
                    "CENTRO DE VALORIZAÇÃO DA VIDA", "68139", "CEP:12229380"));
        }
        return lista;
    }
}