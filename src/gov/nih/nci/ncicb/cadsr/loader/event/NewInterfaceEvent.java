package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Used by UML Loader to indicate a new interface was found.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewInterfaceEvent implements LoaderEvent {
    private String name;

    public NewInterfaceEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
