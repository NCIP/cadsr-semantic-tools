package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Used by UML Loader to indicate a new DataType Event. (such as java.lang.String)
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewDataTypeEvent implements LoaderEvent {
    private String name;

    public NewDataTypeEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
