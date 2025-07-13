package equix.tech.homework.application.dto.order;

import equix.tech.homework.domain.enums.OrderSide;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

import static equix.tech.homework.common.Messages.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest implements Serializable {
    @NotBlank(message = SYMBOL_REQUIRED)
    @Pattern(regexp = "^[A-Z]{1,10}$", message = SYMBOL_FORMAT_INVALID)
    private String symbol;

    @NotNull(message = PRICE_REQUIRED)
    @DecimalMin(value = "0.01", message = PRICE_MIN)
    private BigDecimal price;

    @NotNull(message = QUANTITY_REQUIRED)
    @Min(value = 1, message = QUANTITY_MIN)
    private Integer quantity;

    @NotNull(message = SIDE_REQUIRED)
    private OrderSide side;
}
