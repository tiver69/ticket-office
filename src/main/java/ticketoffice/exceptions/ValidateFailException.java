package ticketoffice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Thrown to indicate that request has been passed with illegal argument:
 * errorKeyMessage contains key of bundle-message to display on error page.
 */
@Setter
@Getter
@AllArgsConstructor
public class ValidateFailException extends IllegalArgumentException {
    String errorKeyMessage;
}
