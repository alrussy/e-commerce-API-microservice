package product_app.model.dto.error_dto;

import java.time.LocalDateTime;

public record ErrorResponse(String msg , String Status , LocalDateTime date) {

}
