End2EndTestCase junit test output:

INFO UMLDefaults.initParams(144) | List of packages that will be processed: 
INFO UMLDefaults.initParams(173) | No Filter, all packages will be processed.
DEBUG DAOAccessor.init(56) | Loading DataElementDAO bean
DEBUG DAOAccessor.init(60) | Loading AlternateNameDAO bean
DEBUG DAOAccessor.init(64) | Loading AdminComponentDAO bean
DEBUG DAOAccessor.init(68) | Loading DataElementConceptDAO bean
DEBUG DAOAccessor.init(73) | Loading VDDAO bean
DEBUG DAOAccessor.init(77) | Loading PropertyDAO bean
DEBUG DAOAccessor.init(81) | Loading ObjectClassDAO bean
DEBUG DAOAccessor.init(85) | Loading ObjectClassRelationshipDAO bean
DEBUG DAOAccessor.init(89) | Loading CSDAO bean
DEBUG DAOAccessor.init(93) | Loading CSIDAO bean
DEBUG DAOAccessor.init(97) | Loading CSIDAO bean
DEBUG DAOAccessor.init(101) | Loading ConceptDAO bean
DEBUG DAOAccessor.init(105) | Loading ContextDAO bean
DEBUG DAOAccessor.init(109) | Loading CDDAO bean
DEBUG DAOAccessor.init(113) | Loading RepresentationDAO bean
DEBUG DAOAccessor.init(117) | Loading LoaderDAO bean
INFO UMLDefaults.initClassifications(253) | Project CS Existed:
INFO UserPreferences.setUsePrivateApi(74) | Will be using the private api from now on
INFO XMIParser2.setFilterClassAndPackages(319) | no filter specified
INFO UMLDefaultHandler.newPackage(78) | Package: 
INFO UMLDefaultHandler.newPackage(78) | Package: gov
INFO UMLDefaultHandler.newPackage(78) | Package: gov.nih
INFO UMLDefaultHandler.newPackage(78) | Package: gov.nih.nci
INFO UMLDefaultHandler.newPackage(78) | Package: gov.nih.nci.training
INFO UMLDefaultHandler.newPackage(78) | Package: gov.nih.nci.training.domain
DEBUG XMIParser2.doClass(594) | CLASS: gov.nih.nci.training.domain.XMIClass1
DEBUG XMIParser2.doClass(595) | CLASS PACKAGE: gov.nih.nci.training.domain
INFO UMLDefaultHandler.newClass(329) | Class: gov.nih.nci.training.domain.XMIClass1
INFO UMLDefaultHandler.newAttribute(403) | Attribute: gov.nih.nci.training.domain.XMIClass1.Att1
DEBUG UMLDefaultHandler.newAttribute(435) | DEC LONG_NAME: XMIClass1:Att1
DEBUG UMLDefaultHandler.newAttribute(485) | DE LONG_NAME: XMIClass1:Att1 char
DEBUG XMIParser2.parse(270) | parsing took: 222 ms
DEBUG ConceptPersister.persist(73) | ConceptPersister.persist()
DEBUG ConceptPersister.persist(83) | concept name: C86945
INFO ConceptPersister.persist(144) | Concept C86945 already existed 
DEBUG ConceptPersister.persist(83) | concept name: C85585
DEBUG AbstractHttpInvokerRequestExecutor.executeRequest(131) | Sending HTTP invoker request for service at [http://lexevsapi60.nci.nih.gov/lexevsapi60/http/applicationService], with size 717
DEBUG AuthenticationSimpleHttpInvokerRequestExecutor.prepareConnection(88) | Unable to set BASIC authentication header as SecurityContext did not provide valid Authentication: null
INFO LexEVSProxyHelperImpl.isInitialized(146) | DLB calling remotely: org.LexGrid.LexBIG.Impl.codednodeset.SingleLuceneIndexCodedNodeSet.restrictToMatchingProperties
DEBUG AbstractHttpInvokerRequestExecutor.executeRequest(131) | Sending HTTP invoker request for service at [http://lexevsapi60.nci.nih.gov/lexevsapi60/http/applicationService], with size 2576
DEBUG AuthenticationSimpleHttpInvokerRequestExecutor.prepareConnection(88) | Unable to set BASIC authentication header as SecurityContext did not provide valid Authentication: null
INFO LexEVSProxyHelperImpl.isInitialized(142) | DLB calling locally: org.LexGrid.LexBIG.Impl.codednodeset.SingleLuceneIndexCodedNodeSet.restrictToStatus
INFO LexEVSProxyHelperImpl.isInitialized(146) | DLB calling remotely: org.LexGrid.LexBIG.Impl.codednodeset.SingleLuceneIndexCodedNodeSet.resolve
DEBUG AbstractHttpInvokerRequestExecutor.executeRequest(131) | Sending HTTP invoker request for service at [http://lexevsapi60.nci.nih.gov/lexevsapi60/http/applicationService], with size 4538
DEBUG AuthenticationSimpleHttpInvokerRequestExecutor.prepareConnection(88) | Unable to set BASIC authentication header as SecurityContext did not provide valid Authentication: null
INFO LexEVSProxyHelperImpl.isInitialized(146) | DLB calling remotely: org.LexGrid.LexBIG.Impl.helpers.DefaultCodeToReturnResolver.buildResolvedConceptReference
DEBUG AbstractHttpInvokerRequestExecutor.executeRequest(131) | Sending HTTP invoker request for service at [http://lexevsapi60.nci.nih.gov/lexevsapi60/http/applicationService], with size 1783
DEBUG AuthenticationSimpleHttpInvokerRequestExecutor.prepareConnection(88) | Unable to set BASIC authentication header as SecurityContext did not provide valid Authentication: null
INFO ConceptPersister.persist(139) | Created Concept: 
INFO LogUtil.logAc(45) | --ID: DF212B42-EEE1-5995-E040-BB89A7B443CC
INFO LogUtil.logAc(46) | -- Long_Name: Day Times Kilogram per Milliliter
INFO LogUtil.logAc(47) | -- Preferred_Name: C85585
INFO LogUtil.logAc(48) | -- Version: 1
INFO LogUtil.logAc(49) | -- Workflow Status: RELEASED
INFO LogUtil.logAc(50) | -- Preferred_Definition: Days times kilograms per milliliter.
INFO LogUtil.logAc(51) | -- Public ID: null
DEBUG ObjectClassPersister.persist(83) | gov.nih.nci.training.domain.XMIClass1
INFO ObjectClassPersister.persist(173) | Object Class Existed: 
INFO LogUtil.logAc(45) | --ID: DE7D5921-85A5-54E9-E040-BB89A7B46C53
INFO LogUtil.logAc(46) | -- Long_Name: Address
INFO LogUtil.logAc(47) | -- Preferred_Name: C86945
INFO LogUtil.logAc(48) | -- Version: 1
INFO LogUtil.logAc(49) | -- Workflow Status: RELEASED
INFO LogUtil.logAc(50) | -- Preferred_Definition: A formal speech or report.
INFO LogUtil.logAc(51) | -- Public ID: 3636100
INFO ObjectClassPersister.persist(185) | public ID: 3636100
INFO PersisterUtil.addAlternateDefinition(237) | Alternate Definition Test C104437 already existed
INFO PersisterUtil.addAlternateDefinition(250) | Linked Alternate Definition to Package
INFO PersisterUtil.addAlternateName(70) | Alternate Name gov.nih.nci.training.domain.XMIClass1 already existed
INFO PersisterUtil.addAlternateName(83) | Linked Alternate Name to Package
INFO PersisterUtil.addAlternateName(70) | Alternate Name XMIClass1 already existed
INFO PersisterUtil.addAlternateName(83) | Linked Alternate Name to Package
INFO PersisterUtil.addPackageClassification(514) | Attaching Package Classification
INFO PersisterUtil.addPackageClassification(523) | Added package gov.nih.nci.training.domain to Address
DEBUG PropertyPersister.persist(67) | Att1
DEBUG PropertyPersister.persist(68) | C85585
DEBUG PropertyPersister.persist(118) | property: Day Times Kilogram per Milliliter
DEBUG PropertyPersister.persist(124) | Saving Property with C85585v1.0 in context NCIP
INFO PropertyPersister.persist(130) | Created Property: 
INFO LogUtil.logAc(45) | --ID: DF2137C5-C10A-F6F7-E040-BB89A7B46229
INFO LogUtil.logAc(46) | -- Long_Name: Day Times Kilogram per Milliliter
INFO LogUtil.logAc(47) | -- Preferred_Name: C85585
INFO LogUtil.logAc(48) | -- Version: 1
INFO LogUtil.logAc(49) | -- Workflow Status: RELEASED
INFO LogUtil.logAc(50) | -- Preferred_Definition: Days times kilograms per milliliter.
INFO LogUtil.logAc(51) | -- Public ID: 3636200
INFO PropertyPersister.persist(145) | -- Public ID: 3636200
INFO PersisterUtil.addPackageClassification(514) | Attaching Package Classification
INFO PersisterUtil.addPackageClassification(523) | Added package gov.nih.nci.training.domain to Day Times Kilogram per Milliliter
DEBUG DECPersister.persist(67) | decs... 
DEBUG DECPersister.persist(123) | dec name: XMIClass1:Att1
INFO DECPersister.persist(164) | Created Data Element Concept: 
INFO PersisterUtil.addAlternateName(107) | Added Alternate Name XMIClass1:Att1 to Address Day Times Kilogram per Milliliter 
INFO PersisterUtil.addAlternateDefinition(276) | Added new Alternate Definition DF213C27-7D73-52E4-E040-BB89A7B47981 with definition Attribute1 to Address Day Times Kilogram per Milliliter
INFO LogUtil.logAc(45) | --ID: DF2139A2-F133-A7B9-E040-BB89A7B466BF
INFO LogUtil.logAc(46) | -- Long_Name: Address Day Times Kilogram per Milliliter
INFO LogUtil.logAc(47) | -- Preferred_Name: 3636100v1.0:3636200v1.0
INFO LogUtil.logAc(48) | -- Version: 1
INFO LogUtil.logAc(49) | -- Workflow Status: RELEASED
INFO LogUtil.logAc(50) | -- Preferred_Definition: A formal speech or report._Days times kilograms per milliliter.
INFO LogUtil.logAc(51) | -- Public ID: 3636201
INFO DECPersister.persist(195) | -- Public ID: 3636201
INFO DECPersister.persist(196) | -- Object Class (long_name): Address
INFO DECPersister.persist(199) | -- Property (long_name): Day Times Kilogram per Milliliter
INFO PersisterUtil.addPackageClassification(514) | Attaching Package Classification
INFO PersisterUtil.addPackageClassification(523) | Added package gov.nih.nci.training.domain to Address Day Times Kilogram per Milliliter
DEBUG DEPersister.persist(62) | des...
DEBUG DEPersister.persist(123) | Creating DE: Address Day Times Kilogram per Milliliter java.lang.Character
INFO DEPersister.persist(140) | Created Data Element: 
INFO LogUtil.logAc(45) | --ID: DF213E0E-D68E-7BB1-E040-BB89A7B4042D
INFO LogUtil.logAc(46) | -- Long_Name: Address Day Times Kilogram per Milliliter java.lang.Character
INFO LogUtil.logAc(47) | -- Preferred_Name: 3636201v1.0:2467195v1.0
INFO LogUtil.logAc(48) | -- Version: 1
INFO LogUtil.logAc(49) | -- Workflow Status: RELEASED
INFO LogUtil.logAc(50) | -- Preferred_Definition: A formal speech or report._Days times kilograms per milliliter._Generic value domain for a primitive java dataype that is a single character (16-bit Unicode character).
INFO LogUtil.logAc(51) | -- Public ID: null
INFO DEPersister.persist(159) | -- Value Domain (Preferred_Name): java.lang.Character
INFO PersisterUtil.addPackageClassification(514) | Attaching Package Classification
INFO PersisterUtil.addPackageClassification(523) | Added package gov.nih.nci.training.domain to Address Day Times Kilogram per Milliliter java.lang.Character
INFO PersisterUtil.addAlternateName(107) | Added Alternate Name gov.nih.nci.training.domain.XMIClass1.Att1 to Address Day Times Kilogram per Milliliter java.lang.Character 
INFO PersisterUtil.addAlternateName(116) | Linked Alternate Name to Package
INFO PersisterUtil.addAlternateName(107) | Added Alternate Name XMIClass1:Att1 to Address Day Times Kilogram per Milliliter java.lang.Character 
INFO PersisterUtil.addAlternateName(116) | Linked Alternate Name to Package
INFO PersisterUtil.addAlternateDefinition(276) | Added new Alternate Definition DF214176-08B2-BB39-E040-BB89A7B41502 with definition Attribute1 to Address Day Times Kilogram per Milliliter java.lang.Character
INFO PersisterUtil.addAlternateDefinition(286) | Linked Alternate Definition to Package

