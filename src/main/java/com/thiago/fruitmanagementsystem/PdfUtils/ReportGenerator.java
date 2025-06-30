package com.thiago.fruitmanagementsystem.PdfUtils;

import com.itextpdf.text.DocumentException;
import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;

import java.io.IOException;
import java.util.List;

public interface ReportGenerator {
    byte[] generate(List<HistoricoResponseDTO> dados) throws IOException, DocumentException;
}