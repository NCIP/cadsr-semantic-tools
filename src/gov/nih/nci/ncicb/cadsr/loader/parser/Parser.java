package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.loader.event.LoaderListener;


public interface Parser {
    public void setListener(LoaderListener listener);

    public void parse(String filename);
}
