package com.jobis.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Scrap API 호출 응답")
@Data
@AllArgsConstructor
public class ScrapResponse {
    @Schema(description = "상태", example = "success")
    String status;
    @Schema(description = "메세지", example = "성공적으로 저장하였습니다.")
    String message;
}
