package equix.tech.homework.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponse {
    private Object message;
    private String reason;
    private Boolean status;
    private Object data;
    private Integer count;

    public CommonResponse(Object message, String reason, Boolean status) {
        this.message = message;
        this.reason = reason;
        this.status = status;
    }
}
