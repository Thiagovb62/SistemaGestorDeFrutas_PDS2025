package com.thiago.fruitmanagementsystem.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.thiago.fruitmanagementsystem.Model.FrutaVendaResponseDTO;
import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class PdfUtils {
    public static byte[] generatePdfStream(List<HistoricoResponseDTO> historicos) throws DocumentException {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Create table with 3 columns
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Set column widths
            float[] columnWidths = {1f, 1f, 3f};
            table.setWidths(columnWidths);

            // Add table headers
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            PdfPCell cell = new PdfPCell(new Phrase("ID", boldFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Nome Barraca", boldFont));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Frutas Vendidas", boldFont));
            table.addCell(cell);

            // Add data rows
            for (HistoricoResponseDTO historico : historicos) {
                table.addCell(historico.id().toString());
                table.addCell(historico.barraca().nomeBarraca());
                StringBuilder frutasVendidas = new StringBuilder();
                for (FrutaVendaResponseDTO frutaVenda : historico.frutasVendidas()) {
                    frutasVendidas.append(frutaVenda.frutaVendida().nome())
                            .append(" - ")
                            .append(frutaVenda.frutaVendida().classificacao().getDescricao())
                            .append(" - ")
                            .append(frutaVenda.qtdEscolhida())
                            .append(" - ")
                            .append(frutaVenda.dataVenda().toString())
                            .append(" - ")
                            .append(String.format("valorVenda: %.2f", frutaVenda.contatotal()))
                            .append("\n");

                }
                table.addCell(frutasVendidas.toString());
            }

       // Adiciona uma célula vazia para ocupar as colunas anteriores
                PdfPCell emptyCell = new PdfPCell(new Phrase(""));
                emptyCell.setColspan(2);
                emptyCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(emptyCell);

                // Adiciona o total indexado à última coluna
                PdfPCell totalCell = new PdfPCell(new Phrase(
                    String.format("Total: %.2f", historicos.stream()
                        .flatMap(h -> h.frutasVendidas().stream())
                        .mapToDouble(FrutaVendaResponseDTO::contatotal)
                        .sum())
                ));
                totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(totalCell);

            document.add(table);
            document.close(); // Ensure the document is closed
            return outputStream.toByteArray();

    }

    public static void savePdfToFile(byte[] pdfBytes, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
        }
    }

}