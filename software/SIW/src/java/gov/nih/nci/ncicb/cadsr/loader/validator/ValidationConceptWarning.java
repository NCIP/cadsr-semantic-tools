package gov.nih.nci.ncicb.cadsr.loader.validator;

/**
 * Contains cause and message for a Validation Warning. 
 * 
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 * @version 1.0
 */
public class ValidationConceptWarning extends ValidationWarning {

    public ValidationConceptWarning(String message, Object rootCause) {
        super(message, rootCause);
    }

}
