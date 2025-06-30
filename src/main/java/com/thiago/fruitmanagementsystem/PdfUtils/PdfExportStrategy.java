package com.thiago.fruitmanagementsystem.PdfUtils;

import com.itextpdf.text.DocumentException;
import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import com.thiago.fruitmanagementsystem.Utils.PdfUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component("pdf")
public class PdfExportStrategy implements ExportStrategy {
    @Override
    public byte[] export(List<HistoricoResponseDTO> data) throws IOException, DocumentException {
        return PdfUtils.generatePdfStream(data);
    }
}