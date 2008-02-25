package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Asynchronous EVS preferred name lookup. This query is can be scheduled to 
 * run asynchronously in its own thread. This is usually done with the help 
 * of an Executor. 
 * 
 * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
 */
public class AsyncConceptQuery implements Callable<String> {
    
    /** Global cache of concepts for known terms */
    private static final Map<String, Collection<Concept>> cache = 
        Collections.synchronizedMap(new HashMap());

    /** EVS access object */
    private static final EvsModule evsModule = new EvsModule();

    /** The term to lookup when running this query */
    private final String term;

    /** 
     * Create a query for the specified term, to be run later.
     * @param term
     */
    AsyncConceptQuery(String term) {
        this.term = term;
    }

    /**
     * Usually called by an executor when some thread is ready to run this query.
     */
    public String call() throws Exception {
        
        //final long tid = Thread.currentThread().getId();
        //System.err.println(tid + " async call (" + term + ")");
        final List<Concept> found = new ArrayList<Concept>();
        final List<String> options = new ArrayList<String>();
        final List<String> words = new ArrayList<String>();

        SemmConnUtility.evaluateString(term, options, words);

        for (final String s : options) {
          final List<Concept> result = (List<Concept>) searchEvs(s);
          if (!result.isEmpty() && found.isEmpty()) {
            found.addAll(0, result);
          }
        }

        if (found.isEmpty()) {
          for (final String s : words) {
            final List<Concept> result2 = (List<Concept>) searchEvs(s);
            if (!result2.isEmpty()) {
              found.addAll(0, result2);
            }
          }
        }

        for (final Concept con : found) {
          ElementsLists.getInstance().addElement(con);
        }
        
        final String name = ConceptUtil.preferredNameFromConcepts(found);
        //System.out.println("Concept Preferred Name:" + name);
        //System.err.println(tid + " async call (" + term + ") returned: " + name);
        return name;
    }

    /**
     * Find the CVS concepts for the specified word, first checking the local
     * cache and then, failing that, actually querying EVS.
     * 
     * @param word
     * @return EVS concepts
     */
    public Collection<Concept> searchEvs(String word) {
        
        if (cache.containsKey(word.toLowerCase())) {
            return cache.get(word.toLowerCase());
        }

        final Collection<EvsResult> evsResult = 
            evsModule.findBySynonym(word, false);
        
        final Collection<Concept> result = new ArrayList();
        for (final EvsResult er : evsResult) {
            if (er.getConcept().getPreferredName().equals(word)) {
                result.add(er.getConcept());
            }
        }

        if (result.isEmpty()) {
            for (final EvsResult er : evsResult) {
                result.add(er.getConcept());
            }
        }
        cache.put(word.toLowerCase(), result);
        //for(Concept c : result)
        //  System.out.println("Concept found: "  + c.getLongName());

        // if concept definition or source is empty, use default.
        for(Concept con : result) {
          if(StringUtil.isEmpty(con.getPreferredDefinition()))
            con.setPreferredDefinition(PropertyAccessor.getProperty("default.evs.definition"));
          if(StringUtil.isEmpty(con.getDefinitionSource()))
            con.setDefinitionSource(PropertyAccessor.getProperty("default.evs.definition.source"));
        }

        return result;
    }
}
