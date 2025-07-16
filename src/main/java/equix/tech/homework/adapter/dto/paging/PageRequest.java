package equix.tech.homework.adapter.dto.paging;

import equix.tech.homework.common.Messages;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageRequest {
    @Min(value = 0, message = Messages.INVALID_PAGE)
    private Integer page;

    @Min(value = 1, message = Messages.INVALID_SIZE)
    private Integer size;
}
