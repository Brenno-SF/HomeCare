package com.eng.homecare.mapper;

import com.eng.homecare.entities.History;
import com.eng.homecare.request.HistoryRequestDTO;
import com.eng.homecare.response.HistoryResponseDTO;

public class HistoryMapper {
    public static History toEntity(HistoryRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        History history = new History();
        history.setHistory(dto.history());
        return history;
    }

    public static HistoryResponseDTO toDTO(History entity) {
        if (entity == null) {
            return null;
        }
        return new HistoryResponseDTO(entity.getHistory());
    }
}
