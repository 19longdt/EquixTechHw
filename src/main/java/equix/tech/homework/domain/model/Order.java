package equix.tech.homework.domain.model;

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
@NoArgsConstructor
public class Order {
    private Long id;
    private String symbol;
    private BigDecimal price;
    private Integer quantity;
    private OrderSide side;
    private OrderStatus status;
    private ZonedDateTime createdTime;
}
