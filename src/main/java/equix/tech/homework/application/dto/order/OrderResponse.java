package equix.tech.homework.application.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import equix.tech.homework.common.Constants;
import equix.tech.homework.domain.enums.OrderSide;
import equix.tech.homework.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String symbol;
    private BigDecimal price;
    private Integer quantity;
    private OrderSide side;
    private OrderStatus status;
    @JsonFormat(pattern = Constants.DATE_TIME_FORMAT)
    private ZonedDateTime createdTime;
}
