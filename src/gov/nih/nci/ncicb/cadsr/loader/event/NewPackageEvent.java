package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Used by UML Loader to indicate a new Package was found
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewPackageEvent implements LoaderEvent {
    private String name;

    public NewPackageEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
