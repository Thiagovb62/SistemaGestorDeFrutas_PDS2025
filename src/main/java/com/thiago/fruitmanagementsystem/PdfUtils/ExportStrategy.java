package com.thiago.fruitmanagementsystem.PdfUtils;

import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import java.util.List;

public interface ExportStrategy {
    byte[] export(List<HistoricoResponseDTO> data) throws Exception;
}