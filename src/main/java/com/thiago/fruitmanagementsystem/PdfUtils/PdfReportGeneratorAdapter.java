package com.thiago.fruitmanagementsystem.PdfUtils;

import com.itextpdf.text.DocumentException;
import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import com.thiago.fruitmanagementsystem.Utils.PdfUtils;

import java.io.IOException;
import java.util.List;

public class PdfReportGeneratorAdapter implements ReportGenerator {

    @Override
    public byte[] generate(List<HistoricoResponseDTO> dados) throws IOException, DocumentException {
        return PdfUtils.generatePdfStream(dados);
    }

}
