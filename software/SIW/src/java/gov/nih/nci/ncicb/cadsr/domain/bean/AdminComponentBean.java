/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package gov.nih.nci.ncicb.cadsr.domain.bean;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentContact;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentRegistration;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.Audit;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.Lifecycle;
import gov.nih.nci.ncicb.cadsr.domain.ReferenceDocument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class AdminComponentBean implements AdminComponent,
		Serializable {
	private String workflowStatus;
	private Context context;
	private String preferredDefinition;
	private Float version;
	private String longName;
	private String preferredName;
	private Audit audit;
	private Lifecycle lifecycle;
	private String origin;
	private String changeNote;
	private List<AlternateName> alternateNames;
	private List<Definition> definitions;
	private List<AdminComponentClassSchemeClassSchemeItem> acCsCsis;
	private String latestVersionIndicator;
	private String deletedIndicator;
	private List<ReferenceDocument> referenceDocuments;
	private List<AdminComponentContact> adminComponentContacts;
	protected String publicId;
	private boolean altNameRemoveDups;
	private boolean defRemoveDups;
	private boolean acCsCsiRemoveDups;
	private AdminComponentRegistration adminComponentRegistration;

	public AdminComponentBean() {
		this.alternateNames = new ArrayList();
		this.definitions = new ArrayList();

		this.acCsCsis = new ArrayList();

		this.latestVersionIndicator = "Yes";

		this.referenceDocuments = new ArrayList();

		this.adminComponentContacts = new ArrayList();

		this.altNameRemoveDups = false;
		this.defRemoveDups = false;
		this.acCsCsiRemoveDups = false;
	}

	public String getPreferredName() {
		return this.preferredName;
	}

	public String getLongName() {
		return this.longName;
	}

	public Float getVersion() {
		return this.version;
	}

	public String getPreferredDefinition() {
		return this.preferredDefinition;
	}

	public Context getContext() {
		return this.context;
	}

	public String getWorkflowStatus() {
		return this.workflowStatus;
	}

	public Audit getAudit() {
		return this.audit;
	}

	public Lifecycle getLifecycle() {
		return this.lifecycle;
	}

	public String getChangeNote() {
		return this.changeNote;
	}

	public String getOrigin() {
		return this.origin;
	}

	public List<AlternateName> getAlternateNames() {
		if (this.altNameRemoveDups) {
			this.altNameRemoveDups = false;
			if (this.alternateNames != null)
				this.alternateNames = new ArrayList(new HashSet(
						this.alternateNames));
		}
		return this.alternateNames;
	}

	public List<Definition> getDefinitions() {
		if (this.defRemoveDups) {
			this.defRemoveDups = false;
			if (this.definitions != null)
				this.definitions = new ArrayList(new HashSet(this.definitions));
		}
		return this.definitions;
	}

	public List<AdminComponentClassSchemeClassSchemeItem> getAcCsCsis() {
		if (this.acCsCsiRemoveDups) {
			this.acCsCsiRemoveDups = false;
			if (this.acCsCsis != null)
				this.acCsCsis = new ArrayList(new HashSet(this.acCsCsis));
		}
		return this.acCsCsis;
	}

	public String getLatestVersionIndicator() {
		return this.latestVersionIndicator;
	}

	public String getDeletedIndicator() {
		return this.deletedIndicator;
	}

	public List<ReferenceDocument> getReferenceDocuments() {
		return this.referenceDocuments;
	}

	public String getPublicId() {
		return this.publicId;
	}

	public void setPublicId(String newPublicId) {
		this.publicId = newPublicId;
	}

	public void setReferenceDocuments(
			List<ReferenceDocument> newReferenceDocuments) {
		this.referenceDocuments = newReferenceDocuments;
	}

	public void setDeletedIndicator(String newDeletedIndicator) {
		this.deletedIndicator = newDeletedIndicator;
	}

	public void setLatestVersionIndicator(String newLatestVersionIndicator) {
		this.latestVersionIndicator = newLatestVersionIndicator;
	}

	public void addCsCsi(ClassSchemeClassSchemeItem csCsi) {
		AdminComponentClassSchemeClassSchemeItem acCsCsi = new AdminComponentClassSchemeClassSchemeItemBean();

		acCsCsi.setAcId(getId());
		acCsCsi.setCsCsi(csCsi);

		if (this.acCsCsis == null)
			this.acCsCsis = new ArrayList(new HashSet(this.acCsCsis));
		this.acCsCsis.add(acCsCsi);
	}

	public void setAcCsCsis(
			List<AdminComponentClassSchemeClassSchemeItem> newAcCsCsis) {
		this.acCsCsis = newAcCsCsis;
		if (newAcCsCsis != null)
			this.acCsCsiRemoveDups = true;
	}

	public void addDefinition(Definition newDefinition) {
		((DefinitionBean) newDefinition).setAcId(getId());
		this.definitions.add(newDefinition);
	}

	public void addAlternateName(AlternateName newAlternateName) {
		((AlternateNameBean) newAlternateName).setAcId(getId());
		this.alternateNames.add(newAlternateName);
	}

	private void setAlternateNames(List<AlternateName> newAlternateNames) {
		this.alternateNames = newAlternateNames;
		if (newAlternateNames != null)
			this.altNameRemoveDups = true;
	}

	public void removeAlternateNames() {
		this.alternateNames = new ArrayList();
		this.defRemoveDups = false;
	}

	public void removeDefinitions() {
		this.definitions = new ArrayList();
		this.altNameRemoveDups = false;
	}

	public void setLifecycle(Lifecycle newLifecycle) {
		this.lifecycle = newLifecycle;
	}

	public void setAudit(Audit newAudit) {
		this.audit = newAudit;
	}

	public void setPreferredDefinition(String newPreferredDefinition) {
		this.preferredDefinition = newPreferredDefinition;
	}

	public void setContext(Context newContext) {
		this.context = newContext;
	}

	public void setVersion(Float newVersion) {
		this.version = newVersion;
	}

	public void setWorkflowStatus(String newWorkflowStatus) {
		this.workflowStatus = newWorkflowStatus;
	}

	public void setLongName(String newLongName) {
		this.longName = newLongName;
	}

	public void setPreferredName(String newPreferredName) {
		this.preferredName = newPreferredName;
	}

	public void setChangeNote(String newChangeNote) {
		this.changeNote = newChangeNote;
	}

	public void setOrigin(String newOrigin) {
		this.origin = newOrigin;
	}

	public abstract String getId();

	public abstract void setId(String paramString);

	private void setDefinitions(List<Definition> newDefinitions) {
		this.definitions = newDefinitions;
		if (newDefinitions != null)
			this.defRemoveDups = true;
	}

	public List<AdminComponentContact> getAdminComponentContacts() {
		return this.adminComponentContacts;
	}

	public void setAdminComponentContacts(List<AdminComponentContact> contacts) {
		this.adminComponentContacts = contacts;
	}

	public void addAdminComponentContact(AdminComponentContact contact) {
		contact.setAcId(getId());
		this.adminComponentContacts.add(contact);
	}

	public void setAdminComponentRegistration(AdminComponentRegistration acr) {
		this.adminComponentRegistration = acr;
	}

	public AdminComponentRegistration getAdminComponentRegistration() {
		return this.adminComponentRegistration;
	}
}