package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Used by uml loader to indicate a new stereotype event
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewStereotypeEvent implements LoaderEvent {
    private String name;

    public NewStereotypeEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
