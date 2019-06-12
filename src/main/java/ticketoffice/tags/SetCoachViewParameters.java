package ticketoffice.tags;

import lombok.Setter;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Executed class for custom "setCoachViewParameters" tag, set "rightSideRow" and "leftSideRow" attributes
 * to current page context according to input coach type number.
 * On jsp page this attributes may be used to display requested number of places rows on right
 * and lest side of corridor. In order to visualize place location difference between coach types.
 */
@Setter
public class SetCoachViewParameters extends SimpleTagSupport {

    private static Logger LOG = Logger.getLogger(SetCoachViewParameters.class);
    private Integer number;
    private int rightSideRow;
    private int leftSideRow;

    @Override
    public void doTag() throws JspException, IOException {
        if (number != null) {
            switch (number) {
                case 1:
                    rightSideRow = 2;
                    leftSideRow = 2;
                    break;
                case 2:
                    rightSideRow = 2;
                    leftSideRow = 3;
                    break;
                case 3:
                    rightSideRow = 0;
                    leftSideRow = 2;
                    break;
                case 4:
                    rightSideRow = 1;
                    leftSideRow = 2;
                    break;
                case 5:
                    rightSideRow = 0;
                    leftSideRow = 1;
                    break;
                default:
                    rightSideRow = 2;
                    leftSideRow = 2;
            }

            getJspContext().setAttribute("rightSideRow", rightSideRow);
            getJspContext().setAttribute("leftSideRow", leftSideRow);
            LOG.debug("Set up coach view for type#" + number);
        } else {
            LOG.error("Can't set up coach view");
        }
    }
}
