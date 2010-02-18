Packages.java.lang.System.out.println("Starting Bediako starting");


var parser = new Packages.gov.nih.nci.ncicb.cadsr.loader.parser.XMIParser2();
var handler = new Packages.gov.nih.nci.ncicb.cadsr.loader.event.UMLDefaultHandler();

var xmiFileName = "GF25444_41_112_1.xmi";
parser.setEventHandler(handler);

var file = new Packages.java.io.File(xmiFileName);

parser.parse(file.getAbsolutePath().replace("\\", "/"));


Packages.java.lang.System.out.println("Done Bediako done");