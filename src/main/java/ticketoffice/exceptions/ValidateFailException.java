package ticketoffice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ValidateFailException extends IllegalArgumentException {
    String errorKeyMessage;
}
