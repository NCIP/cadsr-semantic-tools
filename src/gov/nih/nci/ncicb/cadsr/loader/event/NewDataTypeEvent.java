package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewDataTypeEvent implements LoaderEvent {
    private String name;

    public NewDataTypeEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
