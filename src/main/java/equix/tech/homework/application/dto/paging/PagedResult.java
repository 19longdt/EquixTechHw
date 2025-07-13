package equix.tech.homework.application.dto.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagedResult<T> {
    private List<T> content;
    private Integer totalElements;
}
