package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.cadsr.domain.ClassificationScheme;
import gov.nih.nci.cadsr.domain.ComponentConcept;
import gov.nih.nci.cadsr.domain.ConceptualDomain;
import gov.nih.nci.cadsr.domain.Context;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.ObjectClass;
import gov.nih.nci.cadsr.domain.Protocol;
import gov.nih.nci.cadsr.domain.Representation;
import gov.nih.nci.cadsr.domain.ValueDomain;
import gov.nih.nci.cadsr.umlproject.domain.Project;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Layer to the EVS external API.
 * 
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class CadsrPublicApiModule implements CadsrModule {

	private static ApplicationService service = null;

	private Logger logger = Logger.getLogger(CadsrPrivateApiModule.class
			.getName());

	public CadsrPublicApiModule() {
		try {
			service = ApplicationServiceProvider
					.getApplicationService("CadsrServiceInfo");
		} catch (Exception e) {
			logger.error("Can't get cadsr publicAPI, contact support");
			e.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public CadsrPublicApiModule(String serviceURL) {
		if (serviceURL == null) {
			logger.error("caDSR Public API not initialized, please initialize it first.");
		}
		try {
			service = ApplicationServiceProvider
					.getApplicationService("CadsrServiceInfo");
		} catch (Exception e) {
			logger.error("Can't get cadsr publicAPI, contact support");
			e.printStackTrace();
		} // end of try-catch
	}

	public boolean isPublic() {
		return true;
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.Context> getAllContexts() {

		try {
			gov.nih.nci.cadsr.domain.Context searchContext = new gov.nih.nci.cadsr.domain.Context();
			// List listResult = new ArrayList(new
			// HashSet(service.search(gov.nih.nci.cadsr.domain.Context.class.getName(),
			// searchContext)));
			List listResult = service.search(
					gov.nih.nci.cadsr.domain.Context.class.getName(),
					searchContext);

			return CadsrTransformer.contextListPublicToPrivate(listResult);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // end of try-catch
	}

	public gov.nih.nci.ncicb.cadsr.domain.Concept findConceptByCode(String code) {
		try {
			gov.nih.nci.cadsr.domain.Concept searchConcept = new gov.nih.nci.cadsr.domain.Concept();
			searchConcept.setPreferredName(code);

			// List listResult = new ArrayList(new
			// HashSet(service.search(gov.nih.nci.cadsr.domain.Concept.class.getName(),
			// searchConcept)));
			List listResult = service.search(
					gov.nih.nci.cadsr.domain.Concept.class.getName(),
					searchConcept);

			if (listResult != null && listResult.size() == 1) {
				gov.nih.nci.cadsr.domain.Concept con = (gov.nih.nci.cadsr.domain.Concept) listResult
						.get(0);
				return CadsrTransformer.conceptPublicToPrivate(con);
			} else
				return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // end of try-catch
	}

	public Collection<String> getAllDatatypes() {
		DetachedCriteria datatypeCriteria = DetachedCriteria.forClass(
				gov.nih.nci.cadsr.domain.ValueDomain.class, "vd");
		datatypeCriteria.setProjection(Projections.distinct(Projections
				.projectionList().add(Projections.property("vd.datatypeName"),
						"datatypeName")));
		try {
			List listResult = service.query(datatypeCriteria);

			return listResult;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // end of try-catch
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme> findClassificationScheme(
			Map<String, Object> queryFields) throws Exception {
		return findClassificationScheme(queryFields, null);
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme> findClassificationScheme(
			Map<String, Object> queryFields, List<String> eager)
			throws Exception {

		DetachedCriteria detachedCrit = null;
		gov.nih.nci.cadsr.domain.ClassificationScheme searchCS = new gov.nih.nci.cadsr.domain.ClassificationScheme();

		buildExample(searchCS, queryFields);

		// List listResult = new ArrayList(new
		// HashSet(service.search(gov.nih.nci.cadsr.domain.ClassificationScheme.class.getName(),
		// searchCS)));
		// List listResult =
		// service.search(gov.nih.nci.cadsr.domain.ClassificationScheme.class.getName(),
		// searchCS);

		if (searchCS.publicID != null) {
			detachedCrit = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.ClassificationScheme.class).add(
					Property.forName("publicID").eq(searchCS.getPublicID()));
		} else {
			detachedCrit = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.ClassificationScheme.class).add(
					Property.forName("longName").like(
							searchCS.getLongName() + "%"));
		}
		detachedCrit.setFetchMode("context", FetchMode.EAGER);
		detachedCrit.setFetchMode("ClassSchemeClassSchemeItemCollection",
				FetchMode.EAGER);

		List<ClassificationScheme> cs = (List<ClassificationScheme>) (List<?>) service
				.query(detachedCrit);

		return CadsrTransformer.csListPublicToPrivate(cs);
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.ObjectClass> findObjectClass(
			Map<String, Object> queryFields) throws Exception {

		gov.nih.nci.cadsr.domain.ObjectClass searchOC = new gov.nih.nci.cadsr.domain.ObjectClass();

		buildExample(searchOC, queryFields);

		// List listResult = new ArrayList(new
		// HashSet(service.search(gov.nih.nci.cadsr.domain.ObjectClass.class.getName(),
		// searchOC)));
		List listResult = service.search(
				gov.nih.nci.cadsr.domain.ObjectClass.class.getName(), searchOC);

		return CadsrTransformer.ocListPublicToPrivate(listResult);
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.ValueDomain> findValueDomain(
			Map<String, Object> queryFields) throws Exception {

		gov.nih.nci.cadsr.domain.ValueDomain vd = new gov.nih.nci.cadsr.domain.ValueDomain();

		DetachedCriteria detachedCrit = null;
		buildExample(vd, queryFields);

		// List listResult = new ArrayList(new
		// HashSet(service.search(gov.nih.nci.cadsr.domain.ValueDomain.class.getName(),
		// vd)));

		if (vd.getPublicID() != null) {
			detachedCrit = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.ValueDomain.class).add(
					Property.forName("publicID").eq(vd.getPublicID())); // .add(
																		// Property.forName("version").eq(vd.getVersion()));
		} else {
			detachedCrit = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.ValueDomain.class).add(
							Property.forName("longName").like(
									vd.getLongName() + "%"));
			System.out.println("VD Longname ="+vd.getLongName());
		}
		detachedCrit.setFetchMode("context", FetchMode.EAGER);
		detachedCrit.setFetchMode("conceptualDomain", FetchMode.EAGER);
		detachedCrit.setFetchMode("represention", FetchMode.EAGER);
		detachedCrit.setFetchMode("conceptualDomain.context", FetchMode.EAGER);
		detachedCrit.setFetchMode("represention.context", FetchMode.EAGER);

		List<gov.nih.nci.cadsr.domain.ValueDomain> resultListVD = (List<gov.nih.nci.cadsr.domain.ValueDomain>) (List<?>) service.query(detachedCrit);

		return CadsrTransformer.vdListPublicToPrivate(resultListVD);
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.Property> findProperty(
			Map<String, Object> queryFields) throws Exception {

		gov.nih.nci.cadsr.domain.Property searchProp = new gov.nih.nci.cadsr.domain.Property();

		buildExample(searchProp, queryFields);

		// List listResult = new ArrayList(new
		// HashSet(service.search(gov.nih.nci.cadsr.domain.Property.class.getName(),
		// searchProp)));
		List listResult = service.search(
				gov.nih.nci.cadsr.domain.Property.class.getName(), searchProp);

		return CadsrTransformer.propListPublicToPrivate(listResult);
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain> findConceptualDomain(
			Map<String, Object> queryFields) throws Exception {

		gov.nih.nci.cadsr.domain.ConceptualDomain searchCD = new gov.nih.nci.cadsr.domain.ConceptualDomain();
		gov.nih.nci.cadsr.domain.ConceptualDomain cdTemp = null;
		DetachedCriteria detachedCrit = null;
		buildExample(searchCD, queryFields);

		// List listResult = new ArrayList(new
		// HashSet(service.search(gov.nih.nci.cadsr.domain.ConceptualDomain.class,
		// searchCD)));
		if (searchCD.getPublicID() != null) {
			detachedCrit = DetachedCriteria
					.forClass(gov.nih.nci.cadsr.domain.ConceptualDomain.class)
					.add(Property.forName("publicID")
							.eq(searchCD.getPublicID()))
					.add(Property.forName("version").eq(searchCD.getVersion()));
		} else {
			detachedCrit = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.ClassificationScheme.class).add(
					Property.forName("longName").like(
							searchCD.getLongName() + "%"));
		}
		detachedCrit.setFetchMode("context", FetchMode.EAGER);

		List<gov.nih.nci.cadsr.domain.ConceptualDomain> cds = (List<gov.nih.nci.cadsr.domain.ConceptualDomain>) (List<?>) service
				.query(detachedCrit);

		return CadsrTransformer.cdListPublicToPrivate(cds);
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.Representation> findRepresentation(
			Map<String, Object> queryFields) throws Exception {

		gov.nih.nci.cadsr.domain.Representation searchRep = new gov.nih.nci.cadsr.domain.Representation();

		DetachedCriteria detachedCrit = null;

		buildExample(searchRep, queryFields);

		// List listResult = new ArrayList(new
		// HashSet(service.search(gov.nih.nci.cadsr.domain.Representation.class,
		// searchRep)));
		if (searchRep.publicID != null) {
			detachedCrit = DetachedCriteria
					.forClass(gov.nih.nci.cadsr.domain.Representation.class)
					.add(Property.forName("publicID").eq(
							searchRep.getPublicID()))
					.add(Property.forName("version").eq(searchRep.getVersion()));
		} else {
			detachedCrit = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.Representation.class).add(
					Property.forName("longName").eq(
							searchRep.getLongName() + "%"));
		}
		detachedCrit.setFetchMode("context", FetchMode.EAGER);

		List<Representation> reps = (List<Representation>) (List<?>) service
				.query(detachedCrit);

		return CadsrTransformer.repListPublicToPrivate(reps);
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> findDataElement(
			Map<String, Object> queryFields) throws Exception {

		gov.nih.nci.cadsr.domain.DataElement searchDE = new gov.nih.nci.cadsr.domain.DataElement();
		gov.nih.nci.cadsr.domain.DataElement deTemp = null;

		buildExample(searchDE, queryFields);

		// List listResult = new ArrayList(new
		// HashSet(service.search(gov.nih.nci.cadsr.domain.DataElement.class,
		// searchDE)));

		DetachedCriteria detachedCrit = DetachedCriteria
				.forClass(DataElement.class)
				.add(Property.forName("publicID").eq(searchDE.getPublicID()))
				.add(Property.forName("version").eq(searchDE.getVersion()));
		detachedCrit.setFetchMode("context", FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain", FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.context", FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.conceptualDomain",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.represention", FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.conceptualDomain.context",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.represention.context",
				FetchMode.EAGER);

		detachedCrit.setFetchMode("dataElementConcept", FetchMode.EAGER);
		detachedCrit
				.setFetchMode("dataElementConcept.context", FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.objectClass",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.property",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.conceptualDomain",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.objectClass.context",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.property.context",
				FetchMode.EAGER);
		detachedCrit.setFetchMode(
				"dataElementConcept.conceptualDomain.context", FetchMode.EAGER);

		List<DataElement> results = (List<DataElement>) (List<?>) service
				.query(detachedCrit);

		return CadsrTransformer.deListPublicToPrivate(results);
	}

	private void buildExample(Object o, Map<String, Object> queryFields) {
		for (String s : queryFields.keySet()) {
			Object field = queryFields.get(s);
			if (s.equals("publicId")) {
				s = "publicID";
				if (field instanceof String)
					field = new Long((String) field);
			} else if (s.equals("workflowStatus")) {
				s = "workflowStatusName";
			}
			if (field instanceof String) {
				String sField = (String) field;
				field = sField.replace('%', '*');
			}

			try {
				if (s.startsWith("context.")) {
					gov.nih.nci.cadsr.domain.Context context = new gov.nih.nci.cadsr.domain.Context();
					context.setName((String) field);
					Method m = o.getClass().getMethod("setContext",
							gov.nih.nci.cadsr.domain.Context.class);
					m.invoke(o, context);
				} else {
					Method m = o.getClass().getMethod(
							"set" + StringUtil.upperFirst(s), field.getClass());
					m.invoke(o, field);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} // end of try-catch
		}
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> findDEByClassifiedAltName(
			gov.nih.nci.ncicb.cadsr.domain.AlternateName altName,
			gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem csCsi)
			throws Exception {

		DetachedCriteria deCriteria = DetachedCriteria.forClass(
				gov.nih.nci.cadsr.domain.DataElement.class, "de");

		DetachedCriteria subCriteria = deCriteria
				.createCriteria("designationCollection");
		subCriteria.add(Expression.eq("name", altName.getName()));
		subCriteria.add(Expression.eq("type", altName.getType()));

		subCriteria.createCriteria("designationClassSchemeItemCollection")
				.createCriteria("classSchemeClassSchemeItem")
				.add(Expression.eq("id", csCsi.getId()));

		List listResult = service.query(deCriteria,
				gov.nih.nci.cadsr.domain.DataElement.class.getName());

		if (listResult.size() > 0) {
			return CadsrTransformer.deListPublicToPrivate(listResult);
		} else
			return new ArrayList();
	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.ObjectClass> findOCByClassifiedAltName(
			gov.nih.nci.ncicb.cadsr.domain.AlternateName altName,
			gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem csCsi)
			throws Exception {

		DetachedCriteria ocCriteria = DetachedCriteria.forClass(
				gov.nih.nci.cadsr.domain.ObjectClass.class, "oc");

		DetachedCriteria subCriteria = ocCriteria
				.createCriteria("designationCollection");
		subCriteria.add(Expression.eq("name", altName.getName()));
		subCriteria.add(Expression.eq("type", altName.getType()));

		subCriteria.createCriteria("designationClassSchemeItemCollection")
				.createCriteria("classSchemeClassSchemeItem")
				.add(Expression.eq("id", csCsi.getId()));

		List listResult = service.query(ocCriteria,
				gov.nih.nci.cadsr.domain.ObjectClass.class.getName());

		if (listResult.size() > 0) {
			return CadsrTransformer.ocListPublicToPrivate(listResult);
		} else
			return new ArrayList();
	}

	// I will remove the following method which needs to be tested but is never
	// used.
	//
	// /**
	// * Returns a collection containing the DataElement that has the
	// ObjectClass
	// * and Property specified by the given concepts, and the Value Domain
	// * specified by the given long name.
	// */
	// public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement>
	// findDataElement(Concept[] ocConcepts, Concept[] propConcepts,
	// String vdLongName) throws Exception {
	//
	// int i = 0;
	// StringBuffer ocNames = new StringBuffer();
	// for(Concept concept : ocConcepts) {
	// if (i++ > 0) ocNames.append(":");
	// ocNames.append(concept.getPreferredName());
	// }
	//
	// int j = 0;
	// StringBuffer propNames = new StringBuffer();
	// for(Concept concept : propConcepts) {
	// if (j++ > 0) propNames.append(":");
	// propNames.append(concept.getPreferredName());
	// }
	//
	// DetachedCriteria criteria = DetachedCriteria.forClass(
	// gov.nih.nci.cadsr.domain.DataElement.class, "de");
	//
	// DetachedCriteria decCriteria =
	// criteria.createCriteria("dataElementConcept");
	//
	// criteria.createCriteria("valueDomain").
	// add(Expression.eq("longName", vdLongName));
	//
	// decCriteria.createCriteria("objectClass")
	// .createCriteria("conceptDerivationRule")
	// .add(Expression.eq("name", ocNames.toString()));
	//
	// decCriteria.createCriteria("property")
	// .createCriteria("conceptDerivationRule")
	// .add(Expression.eq("name", propNames.toString()));
	//
	// ArrayList<gov.nih.nci.cadsr.domain.DataElement> results =
	// new ArrayList(service.query(criteria));
	//
	// // we found all results where condR.name matches, now check that
	// Comp.Concept.value is empty
	// ListIterator<gov.nih.nci.cadsr.domain.DataElement> it =
	// results.listIterator();
	// while(it.hasNext()) {
	// gov.nih.nci.cadsr.domain.DataElement _de = it.next();
	// for(gov.nih.nci.cadsr.domain.ComponentConcept comp :
	// _de.getDataElementConcept().getObjectClass().getConceptDerivationRule().getComponentConceptCollection())
	// {
	// if(!StringUtil.isEmpty(comp.getValue())) {
	// it.remove();
	// continue;
	// }
	// }
	// for(gov.nih.nci.cadsr.domain.ComponentConcept comp :
	// _de.getDataElementConcept().getProperty().getConceptDerivationRule().getComponentConceptCollection())
	// {
	// if(!StringUtil.isEmpty(comp.getValue())) {
	// it.remove();
	// continue;
	// }
	// }
	// }
	//
	//
	// return CadsrTransformer.deListPublicToPrivate(results);
	// }

	/**
	 * Returns a list containing DataElements that are good candidates for the
	 * given class/attribute names. Currently, it searches for DataElements
	 * which have "class:attribute" as an alternate name (designation).
	 */
	public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> suggestDataElement(
			String className, String attrName) throws Exception {

		// for now, just search on this, but we can do more searches in the
		// future
		final String altName = className + ":" + attrName;

		DetachedCriteria detachedCrit = DetachedCriteria.forClass(
				gov.nih.nci.cadsr.domain.DataElement.class, "de");
		detachedCrit.createCriteria("designationCollection").add(
				Expression.eq("name", altName));
		detachedCrit.setFetchMode("context", FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain", FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.context", FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.conceptualDomain",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.represention", FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.conceptualDomain.context",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("valueDomain.represention.context",
				FetchMode.EAGER);

		detachedCrit.setFetchMode("dataElementConcept", FetchMode.EAGER);
		detachedCrit
				.setFetchMode("dataElementConcept.context", FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.objectClass",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.property",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.conceptualDomain",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.objectClass.context",
				FetchMode.EAGER);
		detachedCrit.setFetchMode("dataElementConcept.property.context",
				FetchMode.EAGER);
		detachedCrit.setFetchMode(
				"dataElementConcept.conceptualDomain.context", FetchMode.EAGER);

		List results = service.query(detachedCrit);
		return CadsrTransformer.deListPublicToPrivate(results);

	}

	public List<gov.nih.nci.ncicb.cadsr.domain.PermissibleValue> getPermissibleValues(
			gov.nih.nci.ncicb.cadsr.domain.ValueDomain vd) throws Exception {

		List<gov.nih.nci.cadsr.domain.PermissibleValue> result = new ArrayList<gov.nih.nci.cadsr.domain.PermissibleValue>();

		DetachedCriteria criteria = DetachedCriteria.forClass(
				gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue.class,
				"vdPv");

		criteria.createCriteria("enumeratedValueDomain").add(
				Expression.eq("id", vd.getId()));
		criteria.setFetchMode("permissibleValue", FetchMode.EAGER);
		criteria.setFetchMode("permissibleValue.valueMeaning", FetchMode.EAGER);

		List vdPvs = service.query(criteria);

		for(int i=0;i<vdPvs.size();i++){
	//	for (Object o : vdPvs) {

			gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue vdPv = (gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue) vdPvs.get(i);
			result.add(vdPv.getPermissibleValue());
		}

		return new ArrayList(CadsrTransformer.pvListPublicToPrivate(result));
	}

	public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(
			gov.nih.nci.ncicb.cadsr.domain.ObjectClass oc) {
		ArrayList<gov.nih.nci.ncicb.cadsr.domain.Concept> result = new ArrayList<gov.nih.nci.ncicb.cadsr.domain.Concept>();

		if (StringUtil.isEmpty(oc.getPublicId()))
			return result;

		try {
			gov.nih.nci.cadsr.domain.ObjectClass searchOC = new gov.nih.nci.cadsr.domain.ObjectClass();
			searchOC.setPublicID(new Long(oc.getPublicId()));
			searchOC.setVersion(oc.getVersion());

			DetachedCriteria detachedCrit = DetachedCriteria.forClass(
					ObjectClass.class).add(
					Property.forName("publicID").eq(searchOC.getPublicID()));
			detachedCrit.setFetchMode("context", FetchMode.EAGER);
			detachedCrit.setFetchMode("conceptDerivationRule", FetchMode.EAGER);
			detachedCrit.setFetchMode(
					"conceptDerivationRule.componentConceptCollection",
					FetchMode.EAGER);
			detachedCrit.setFetchMode(
					"conceptDerivationRule.componentConceptCollection.concept",
					FetchMode.EAGER);
			detachedCrit
					.setFetchMode(
							"conceptDerivationRule.componentConceptCollection.concept.context",
							FetchMode.EAGER);

			List<ObjectClass> ocs = (List<ObjectClass>) (List<?>) service
					.query(detachedCrit);

			// List ocs =
			// service.search(gov.nih.nci.cadsr.domain.ObjectClass.class.getName(),
			// searchOC);

			if (ocs.size() != 1)
				return result;
			gov.nih.nci.cadsr.domain.ObjectClass resOc = null;

			for (int i = 0; i < ocs.size(); i++)
				resOc = (gov.nih.nci.cadsr.domain.ObjectClass) ocs.get(i);
			Collection<gov.nih.nci.cadsr.domain.ComponentConcept> comps = resOc
					.getConceptDerivationRule().getComponentConceptCollection();

			ArrayList<gov.nih.nci.cadsr.domain.ComponentConcept> sortedComps = sortCompConcepts(comps);

			// for(gov.nih.nci.cadsr.domain.ComponentConcept comp : sortedComps)
			// {
			for (int i = 0; i < sortedComps.size(); i++) {
				gov.nih.nci.cadsr.domain.Concept conc = sortedComps.get(i)
						.getConcept();
				gov.nih.nci.ncicb.cadsr.domain.Concept concept = DomainObjectFactory
						.newConcept();
				CadsrTransformer.acPublicToPrivate(concept, conc);
				result.add(0, concept);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // end of try-catch

		return result;

	}

	public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(
			gov.nih.nci.ncicb.cadsr.domain.Property prop) {
		ArrayList<gov.nih.nci.ncicb.cadsr.domain.Concept> result = new ArrayList<gov.nih.nci.ncicb.cadsr.domain.Concept>();

		if (StringUtil.isEmpty(prop.getPublicId()))
			return result;

		try {
			gov.nih.nci.cadsr.domain.Property searchProp = new gov.nih.nci.cadsr.domain.Property();
			searchProp.setPublicID(new Long(prop.getPublicId()));
			searchProp.setVersion(prop.getVersion());

			DetachedCriteria detachedCrit = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.Property.class).add(
					Property.forName("publicID").eq(searchProp.getPublicID()));
			detachedCrit.setFetchMode("context", FetchMode.EAGER);
			detachedCrit.setFetchMode("conceptDerivationRule", FetchMode.EAGER);
			detachedCrit.setFetchMode(
					"conceptDerivationRule.componentConceptCollection",
					FetchMode.EAGER);
			detachedCrit.setFetchMode(
					"conceptDerivationRule.componentConceptCollection.concept",
					FetchMode.EAGER);
			detachedCrit
					.setFetchMode(
							"conceptDerivationRule.componentConceptCollection.concept.context",
							FetchMode.EAGER);

			List<gov.nih.nci.cadsr.domain.Property> props = (List<gov.nih.nci.cadsr.domain.Property>) (List<?>) service
					.query(detachedCrit);

			// List props =
			// service.search(gov.nih.nci.cadsr.domain.Property.class.getName(),
			// searchProp);

			gov.nih.nci.cadsr.domain.Property resProp = null;
			if (props.size() != 1)
				return result;

			for (int i = 0; i < props.size(); i++)
				resProp = (gov.nih.nci.cadsr.domain.Property) props.get(i);
			Collection<gov.nih.nci.cadsr.domain.ComponentConcept> comps = resProp
					.getConceptDerivationRule().getComponentConceptCollection();

			ArrayList<gov.nih.nci.cadsr.domain.ComponentConcept> sortedComps = sortCompConcepts(comps);

			// for(gov.nih.nci.cadsr.domain.ComponentConcept comp : sortedComps)
			// {
			for (int i = 0; i < sortedComps.size(); i++) {
				gov.nih.nci.cadsr.domain.Concept conc = sortedComps.get(i)
						.getConcept();
				gov.nih.nci.ncicb.cadsr.domain.Concept concept = DomainObjectFactory
						.newConcept();
				CadsrTransformer.acPublicToPrivate(concept, conc);
				result.add(0, concept);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // end of try-catch

		return result;
	}

	private ArrayList<gov.nih.nci.cadsr.domain.ComponentConcept> sortCompConcepts(
			Collection<gov.nih.nci.cadsr.domain.ComponentConcept> compConcepts) {
		ArrayList<gov.nih.nci.cadsr.domain.ComponentConcept> compsList = new ArrayList<gov.nih.nci.cadsr.domain.ComponentConcept>(
				compConcepts);

		Collections.sort(compsList,
				new Comparator<gov.nih.nci.cadsr.domain.ComponentConcept>() {
					public int compare(ComponentConcept o1, ComponentConcept o2) {
						return o1.getDisplayOrder() - o2.getDisplayOrder();
					}
				});

		return compsList;
	}

	public boolean matchDEToPropertyConcepts(
			gov.nih.nci.ncicb.cadsr.domain.DataElement de, String[] conceptCodes)
			throws Exception {

		if (StringUtil.isEmpty(de.getPublicId()))
			return false;

		gov.nih.nci.cadsr.domain.DataElement searchDE = new gov.nih.nci.cadsr.domain.DataElement();
		searchDE.setPublicID(new Long(de.getPublicId()));
		searchDE.setVersion(de.getVersion());

		List results = service.search(
				gov.nih.nci.cadsr.domain.DataElement.class.getName(), searchDE);

		if (results.size() == 0) {
			logger.error("Can't find CDE : " + de.getPublicId() + " v "
					+ de.getVersion() + "\\n Please contact support");
			return false;
		}

		gov.nih.nci.cadsr.domain.DataElement resultDE = (gov.nih.nci.cadsr.domain.DataElement) results
				.get(0);

		gov.nih.nci.cadsr.domain.ConceptDerivationRule conDR = resultDE
				.getDataElementConcept().getProperty()
				.getConceptDerivationRule();

		Collection compConcepts = conDR.getComponentConceptCollection();
		if (compConcepts.size() != conceptCodes.length)
			return false;

		Iterator it = compConcepts.iterator();
		while (it.hasNext()) {
			ComponentConcept comp = (ComponentConcept) it.next();
			if (!conceptCodes[comp.getDisplayOrder()].equals(comp.getConcept()
					.getPreferredName()))
				return false;
		}

		return true;

	}

	public Collection<gov.nih.nci.ncicb.cadsr.domain.Representation> findPreferredRepTerms() {

		try {
			DetachedCriteria repTermCriteria = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.Representation.class, "repTerm");

			repTermCriteria
					.add(Expression.eq("workflowStatusName", "RELEASED"));
			repTermCriteria
					.add(Expression.eq("registrationStatus", "Standard"));

			List listResult = service.query(repTermCriteria,
					gov.nih.nci.cadsr.domain.Representation.class.getName());

			if (listResult.size() > 0) {
				return CadsrTransformer.repListPublicToPrivate(listResult);
			} else
				return new ArrayList();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // end of try-catch

	}

	public List<gov.nih.nci.ncicb.cadsr.domain.DataElement> findDEByOCConcept(
			gov.nih.nci.ncicb.cadsr.domain.Concept concept) {

		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.DataElement.class, "de");

			criteria.createCriteria("dataElementConcept")
					.createCriteria("objectClass")
					.createCriteria("conceptDerivationRule")
					.createCriteria("componentConceptCollection")
					.add(Expression.eq("primaryFlag", "Yes"))
					.createCriteria("concept")
					.add(Expression.eq("preferredName",
							concept.getPreferredName()));

			List results = service.query(criteria);

			return new ArrayList<gov.nih.nci.ncicb.cadsr.domain.DataElement>(
					CadsrTransformer.deListPublicToPrivate(results));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // end of try-catch
	}

	public List<gov.nih.nci.ncicb.cadsr.domain.DataElement> findDEByOCConcepts(
			gov.nih.nci.ncicb.cadsr.domain.Concept[] concepts) {

		try {
			int i = 0;
			StringBuffer ocNames = new StringBuffer();
			for (Concept concept : concepts) {
				if (i++ > 0)
					ocNames.append(":");
				ocNames.append(concept.getPreferredName());
			}

			DetachedCriteria criteria = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.DataElement.class, "de");

			criteria.createCriteria("dataElementConcept")
					.createCriteria("objectClass")
					.createCriteria("conceptDerivationRule")
					.add(Expression.eq("name", ocNames.toString()));

			List results = service.query(criteria);

			return new ArrayList<gov.nih.nci.ncicb.cadsr.domain.DataElement>(
					CadsrTransformer.deListPublicToPrivate(results));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} // end of try-catch

	}

	public List<gov.nih.nci.ncicb.cadsr.domain.AlternateName> getAlternateNames(
			gov.nih.nci.ncicb.cadsr.domain.AdminComponent ac) {
		try {
			gov.nih.nci.cadsr.domain.AdministeredComponent searchAC = new gov.nih.nci.cadsr.domain.AdministeredComponent();
			searchAC.setId(ac.getId());

			List results = service.search(
					gov.nih.nci.cadsr.domain.AdministeredComponent.class
							.getName(), searchAC);

			if (results.size() == 0)
				return new ArrayList();

			else {

				// return
				// CadsrTransformer.anListPublicToPrivate(((gov.nih.nci.cadsr.domain.AdministeredComponent)results.get(0)).getDesignationCollection());
				return null;
			}

		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}

	}

	public List<gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship> findOCR(
			gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship ocr) {
		try {

			DetachedCriteria criteria = DetachedCriteria.forClass(
					gov.nih.nci.cadsr.domain.ObjectClassRelationship.class,
					"ocr");

			if (!StringUtil.isEmpty(ocr.getSourceRole()))
				criteria.add(Expression.eq("sourceRole", ocr.getSourceRole()));
			if (!StringUtil.isEmpty(ocr.getTargetRole()))
				criteria.add(Expression.eq("targetRole", ocr.getTargetRole()));

			criteria.add(Expression.eq("direction", ocr.getDirection()))
					.add(Expression.eq("sourceLowMultiplicity",
							ocr.getSourceLowCardinality()))
					.add(Expression.eq("sourceHighMultiplicity",
							ocr.getSourceHighCardinality()))
					.add(Expression.eq("targetLowMultiplicity",
							ocr.getTargetLowCardinality()))
					.add(Expression.eq("targetHighMultiplicity",
							ocr.getTargetHighCardinality()));

			criteria.createCriteria("sourceObjectClass").add(
					Expression.eq("id", ocr.getSource().getId()));

			criteria.createCriteria("targetObjectClass").add(
					Expression.eq("id", ocr.getTarget().getId()));

			Collection results = service.query(criteria);

			if (results.size() == 0)
				return new ArrayList();

			else {
				// return new
				// ArrayList<gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship>(CadsrTransformer.ocrListPublicToPrivate(results));
				return null;
			}

		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Project> getAllProjects() {

		DetachedCriteria projectCriteria = DetachedCriteria
				.forClass(Project.class);

		projectCriteria.addOrder(Order.asc("longName").ignoreCase());

		DetachedCriteria csCriteria = projectCriteria
				.createCriteria("classificationScheme");

		csCriteria.add(Expression.eq("workflowStatusName", "RELEASED"));
		List results = null;
		try {
			results = service.query(projectCriteria);
		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		} // end of try-catch

		return results;

	}

	public static void main(String[] args) {
		CadsrPublicApiModule testModule = new CadsrPublicApiModule(
				"http://cabio.nci.nih.gov/cacore32/http/remoteService");
		try {

			// System.out.println("Test Find CS");
			// {
			// Map<String, Object> queryFields = new HashMap<String, Object>();
			// queryFields.put(CadsrModule.LONG_NAME,
			// "Transcription Annotation Prioritization and Screening System");
			// queryFields.put(CadsrModule.VERSION, 1f);

			// Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme>
			// list = testModule.findClassificationScheme(queryFields);

			// System.out.println(list.size());
			// for(gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme o : list)
			// {
			// System.out.println(o.getPreferredName());
			// System.out.println(o.getPublicId());
			// }

			// }

			// System.out.println("Test Find VD");
			// {
			// Map<String, Object> queryFields = new HashMap<String, Object>();
			// queryFields.put(CadsrModule.LONG_NAME, "java.lang.*");

			// Collection<gov.nih.nci.ncicb.cadsr.domain.ValueDomain> list =
			// testModule.findValueDomain(queryFields);

			// System.out.println(list.size());
			// for(gov.nih.nci.ncicb.cadsr.domain.ValueDomain o : list) {
			// System.out.println(o.getPreferredName());
			// System.out.println(o.getPublicId());
			// }

			// }

			// System.out.println("Test Find DE");
			// {
			// Map<String, Object> queryFields = new HashMap<String, Object>();
			// queryFields.put(CadsrModule.LONG_NAME, "Patient*");

			// Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> list =
			// testModule.findDataElement(queryFields);

			// System.out.println(list.size());
			// for(gov.nih.nci.ncicb.cadsr.domain.DataElement o : list) {
			// System.out.println(o.getPreferredName());
			// System.out.println(o.getPublicId());
			// }

			// }

			// System.out.println("Test matchDE");
			// gov.nih.nci.ncicb.cadsr.domain.DataElement de =
			// DomainObjectFactory.newDataElement();
			// de.setPublicId("2533339");
			// String[] conceptCodes = new String[] {"C43821", "C16423"};
			// // test that should return true
			// if(testModule.matchDEToPropertyConcepts(de, conceptCodes))
			// System.out.println("ok");
			//
			// conceptCodes = new String[] {"C16423", "C43821"};
			// // test that should return false
			// if(!testModule.matchDEToPropertyConcepts(de, conceptCodes))
			// System.out.println("ok");
			//

			// System.out.println("Test find OC Concepts");
			// gov.nih.nci.ncicb.cadsr.domain.ObjectClass oc =
			// DomainObjectFactory.newObjectClass();
			// oc.setPublicId("2241624");
			// // oc.setPublicId("2557779");
			// oc.setVersion(1f);
			// List<gov.nih.nci.ncicb.cadsr.domain.Concept> concepts =
			// testModule.getConcepts(oc);
			// for(gov.nih.nci.ncicb.cadsr.domain.Concept con : concepts) {
			// System.out.println(con.getLongName());
			// }

			// testModule.getAllDatatypes();

			// Collection<Representation> reps =
			// testModule.findPreferredRepTerms();
			// for(Representation rep : reps) {
			// System.out.println(rep.getLongName());
			// }

			// gov.nih.nci.ncicb.cadsr.domain.Concept concept =
			// DomainObjectFactory.newConcept();
			// concept.setPreferredName("C16612");

			// System.out.println("find " + concept.getPreferredName());
			// Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> des =
			// testModule.findDEByOCConcept(concept);
			// for(gov.nih.nci.ncicb.cadsr.domain.DataElement de : des) {
			// System.out.println(de.getLongName());
			// }

			List<Project> result = testModule.getAllProjects();
			for (Project p : result) {
				System.out.println(p.getLongName() + "-- WS: "
						+ p.getClassificationScheme().getWorkflowStatusName()
						+ "-- Context : "
						+ p.getClassificationScheme().getContext().getName());
			}

			// TEST find DE by concept codes and VD.

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
