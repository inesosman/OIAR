package integration;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.Cell;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import fr.inrialpes.exmo.align.parser.AlignmentParser;

public class IntegrationWithoutRefactoring {


	public static void main(String[] args) throws OWLException, AlignmentException, IOException {


		System.setProperty("jdk.xml.entityExpansionLimit", "0");

		ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
		long chrono1 = bean.getCurrentThreadCpuTime();

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory datafactory = manager.getOWLDataFactory();

		/**********************************************************************************************************************/

		ArrayList<String> ontologiesFiles = new ArrayList<String>();
		ArrayList<OWLOntology> ontologiesSet = new ArrayList<OWLOntology>();

		/** selecting and entering input ontologies to be merged */

		/** Conference base */		
		//ontologiesFiles.add("Input/cmt.owl");
		//ontologiesFiles.add("Input/conference.owl");
		//ontologiesFiles.add("Input/confOf.owl");
		//ontologiesFiles.add("Input/edas.owl");
		//ontologiesFiles.add("Input/ekaw.owl");
		//ontologiesFiles.add("Input/iasted.owl");
		//ontologiesFiles.add("Input/sigkdd.owl");

		/** Anatomy base */
		//ontologiesFiles.add("Input/human.owl");
		//ontologiesFiles.add("Input/mouse.owl");     

		/** LargeBio base */
                ontologiesFiles.add("Input/FMA2.owl"); /** FMA3 is the whole FMA */
                ontologiesFiles.add("Input/NCI1.owl"); /** NCI3 is the whole NCI */
                ontologiesFiles.add("Input/SNOMED1.owl"); /** SNOMED3 is the extended SNOMED */

		/** Disease Phenotype base */
		//ontologiesFiles.add("Input/hp.owl");
		//ontologiesFiles.add("Input/mp.owl");
		//ontologiesFiles.add("Input/mesh.owl");
		//ontologiesFiles.add("Input/omim.owl");
		//ontologiesFiles.add("Input/doid.owl");
		//ontologiesFiles.add("Input/ordo.owl");
		
		for(int g=0; g<ontologiesFiles.size(); g++){
			ontologiesSet.add(manager.loadOntologyFromOntologyDocument(new File(ontologiesFiles.get(g))));
		}

		/******************************************************************************************************************************************************************/

		/** selecting and entering input reference alignments to be used in the merge process */

		double threshold = 0.0;
		ArrayList<String> alignmentsFiles = new ArrayList<String>();

		/** Conference base */
		//alignmentsFiles.add("Input/cmt-conference.rdf");
		//alignmentsFiles.add("Input/cmt-confOf.rdf");
		//alignmentsFiles.add("Input/cmt-edas.rdf");
		//alignmentsFiles.add("Input/cmt-ekaw.rdf");
		//alignmentsFiles.add("Input/cmt-iasted.rdf");
		//alignmentsFiles.add("Input/cmt-sigkdd.rdf");
		//alignmentsFiles.add("Input/conference-confOf.rdf");
		//alignmentsFiles.add("Input/conference-edas.rdf");
		//alignmentsFiles.add("Input/conference-ekaw.rdf");
		//alignmentsFiles.add("Input/conference-iasted.rdf");
		//alignmentsFiles.add("Input/conference-sigkdd.rdf");
		//alignmentsFiles.add("Input/confOf-edas.rdf");
		//alignmentsFiles.add("Input/confOf-ekaw.rdf");
		//alignmentsFiles.add("Input/confOf-iasted.rdf");
		//alignmentsFiles.add("Input/confOf-sigkdd.rdf");
		//alignmentsFiles.add("Input/edas-ekaw.rdf");
		//alignmentsFiles.add("Input/edas-iasted.rdf");
		//alignmentsFiles.add("Input/edas-sigkdd.rdf");
		//alignmentsFiles.add("Input/ekaw-iasted.rdf");
		//alignmentsFiles.add("Input/ekaw-sigkdd.rdf");
		//alignmentsFiles.add("Input/iasted-sigkdd.rdf");

		/** Anatomy base */
		//alignmentsFiles.add("Input/human-mouse.rdf");

		/** Largebio base */
                alignmentsFiles.add("Input/FMA2NCI.rdf");
                alignmentsFiles.add("Input/FMA2SNOMED.rdf");
                alignmentsFiles.add("Input/SNOMED2NCI.rdf");
		
		/** Disease Phenotype base */ 
		//alignmentsFiles.add("Input/HP_MP.rdf");
		//alignmentsFiles.add("Input/HP_RH-MESH.rdf");
		//alignmentsFiles.add("Input/HP_OMIM.rdf");
		//alignmentsFiles.add("Input/DOID_ORDO.rdf");

		/*******************************************************************************************************************************************/

		String integratedOntologyIRI = "http://integration";
		OWLOntology integratedOntology = manager.createOntology(IRI.create(integratedOntologyIRI));
		Set<OWLAxiom> integratedOntologyAxioms = new HashSet<OWLAxiom>();

		/***********************************************************************************************************************************/

		/* Ontology entities can be classes/concepts, object properties, data type properties, individuals/instances,
		 * annotation properties, data types, and anonymous individuals.
		 */

		HashSet<String> classes = new HashSet<String>();
		int totalPredictedClasses = 0;
		/** you can enter this method and comment the parsing of disjointClasses axioms (which are the cause of future unsatisfiabilities in the integrated ontology) */
		totalPredictedClasses = createRefactoredAxiomsOfParsedClasses(totalPredictedClasses, integratedOntologyAxioms, classes, datafactory, ontologiesSet);


		HashSet<String> objectProps = new HashSet<String>();
		int totalPredictedObjectProperties = 0;
		totalPredictedObjectProperties = createRefactoredAxiomsOfParsedObjectProperties(totalPredictedObjectProperties, integratedOntologyAxioms, objectProps, datafactory, ontologiesSet);


		HashSet<String> dataProps = new HashSet<String>();
		int totalPredictedDataProperties = 0;
		totalPredictedDataProperties = createRefactoredAxiomsOfParsedDataProperties(totalPredictedDataProperties, integratedOntologyAxioms, dataProps, datafactory, ontologiesSet);


		HashSet<String> individuals = new HashSet<String>();
		int totalPredictedIndividuals = 0;
		totalPredictedIndividuals = createRefactoredAxiomsOfParsedIndividuals(totalPredictedIndividuals, integratedOntologyAxioms, individuals, datafactory, ontologiesSet);


		createAxiomsOfParsedSubPropertyChainOfAxioms(integratedOntologyAxioms, datafactory, ontologiesSet);
		createAxiomsOfParsedHasKeyAxioms(integratedOntologyAxioms, datafactory, ontologiesSet);
		createAxiomsOfParsedNonBuiltInAnnotationProperties(integratedOntologyAxioms, datafactory, ontologiesSet);
		createAxiomsOfParsedDataTypes(integratedOntologyAxioms, datafactory, ontologiesSet);
		createAxiomsOfParsedAnonymousIndividuals(integratedOntologyAxioms, datafactory, ontologiesSet);

		/***************************************************************************************************************************************************/

		int totalPredictedAxioms = 0;
		totalPredictedAxioms = getNumberOfPredictedLogicalAxioms(totalPredictedAxioms, ontologiesSet);

		/********************************************************************************************************************************************************************/

		/** if this module was commented, then OIA2R would just aggregate the input ontologies */
		/** choose one of the four following methods (if you uncomment one, comment the others, and vice versa) */
		/** the filtering soft version keeps the higher-multiplicity correspondences having equal confidence values */
		/** while the filtering hard version only keeps a single correspondence from the higher-multiplicity correspondences, even if they have exactly equal confidence values */
		
		HashSet<String> redundantEntities = new HashSet<String>();
		int nbOfBridgingAxioms = 0;
		for (String alignment : alignmentsFiles) {
			nbOfBridgingAxioms = nbOfBridgingAxioms + createBridgingAxiomsUsingOriginalAlignments(datafactory, integratedOntologyAxioms, alignment, threshold, classes, objectProps, dataProps, individuals);
			//nbOfBridgingAxioms = nbOfBridgingAxioms + createBridgingAxiomsUsingFilteredAlignments_SoftVersion(datafactory, integratedOntologyAxioms, alignment, threshold, classes, objectProps, dataProps, individuals, redundantEntities);
			//nbOfBridgingAxioms = nbOfBridgingAxioms + createBridgingAxiomsUsingFilteredAlignments_HardVersion(datafactory, integratedOntologyAxioms, alignment, threshold, classes, objectProps, dataProps, individuals, redundantEntities);
			//nbOfBridgingAxioms = nbOfBridgingAxioms + createBridgingAxiomsUsingRepairedAlignments(datafactory, integratedOntologyAxioms, alignment, threshold, classes, objectProps, dataProps, individuals, redundantEntities);
			//nbOfBridgingAxioms = nbOfBridgingAxioms + createBridgingAxiomsUsingRepairedFilteredAlignments_SoftVersion(datafactory, integratedOntologyAxioms, alignment, threshold, classes, objectProps, dataProps, individuals, redundantEntities);
			//nbOfBridgingAxioms = nbOfBridgingAxioms + createBridgingAxiomsUsingRepairedFilteredAlignments_HardVersion(datafactory, integratedOntologyAxioms, alignment, threshold, classes, objectProps, dataProps, individuals, redundantEntities);
		}

		System.out.println("\n ===> There are in total : " + nbOfBridgingAxioms + " correspondences");

		/*****************************************************************************************************************************************************/		

		manager.addAxioms(integratedOntology, integratedOntologyAxioms);

		manager.saveOntology(integratedOntology, new RDFXMLOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact2.owl")));
		//manager.saveOntology(mergedOntology, new OWLXMLOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact2.owl")));
		//manager.saveOntology(mergedOntology, new OWLFunctionalSyntaxOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact2.owl")));	
		//manager.addOntologyStorer(new DLSyntaxOntologyStorer());
		//manager.saveOntology(mergedOntology, new DLSyntaxOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact2.owl")));
		//manager.addOntologyStorer(new OWLTutorialSyntaxOntologyStorer());
		//manager.saveOntology(mergedOntology, new OWLTutorialSyntaxOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact2.owl")));
		//manager.saveOntology(mergedOntology, new TurtleOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact2.owl")));

		System.out.println("\n\n\n --> The refactored integrated ontology is created\n");


		/**************************************************************************************************************************************/

		long chrono2 = bean.getCurrentThreadCpuTime();
		long runtime = chrono2-chrono1;

		System.out.println("\n ****** Running the program took : " + runtime/1E6+" ms"); // CPU runtime in milliseconds
		System.out.println(" ****** Running the program took : " + runtime/1E9+" s"); // CPU runtime in seconds		

		/*********************************************************************************************************************************************/

		checkNumberOfEntitiesOfIntegratedOntology(integratedOntology, totalPredictedClasses, totalPredictedObjectProperties, totalPredictedDataProperties, totalPredictedIndividuals, totalPredictedAxioms, redundantEntities, nbOfBridgingAxioms);
		OntologyConsistencyAndCoherence.verify(integratedOntology);

		System.clearProperty("jdk.xml.entityExpansionLimit");

	}


	public static int createRefactoredAxiomsOfParsedClasses(int x, Set<OWLAxiom> axioms, HashSet<String> classes, OWLDataFactory df, ArrayList<OWLOntology> ontologiesSet) throws OWLException{

		for(OWLOntology ontology_n : ontologiesSet){
			x = x + classesModule(classes, axioms, ontology_n, df);
		}
		System.out.println("===> The predicted number of classes of these ontologies is "+ x+ "\n\n");

		return x;
	}

	public static int classesModule(HashSet<String> classes, Set<OWLAxiom> axioms, OWLOntology ont, OWLDataFactory df)
			throws OWLException {

		int x = 0;
		for (OWLClass cls : ont.getClassesInSignature()) {

			if (!cls.isOWLThing()) {
				x++;
				classes.add(cls.getIRI().toString());

				OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(cls);
				axioms.add(ax);

				extractAndCreateClassAnnotations(cls, axioms, df, cls, ont); //labels, comments, and non built-in annotations
				extractAndCreateSuperClassesOfClass(axioms, cls, df, ont, cls);
				extractAndCreateEquivalentClassesOfClass(df, cls, axioms, cls, ont);
				/** if you don't want to include disjoint axioms between classes, comment the following instruction */
				extractAndCreateDisjointClassesOfClass(cls, axioms, df, cls, ont);
			}
			//} else {
			//OWLDeclarationAxiom ax = datafact.getOWLDeclarationAxiom(cls);
			//axioms.add(ax);
			//}
		}

		System.out.println("- The parsing and the creation of classes of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" are done");
		System.out.println("---> The number of classes of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is " + x+ "\n");

		return x;
	}


	public static void extractAndCreateClassAnnotations(OWLClass cls, Set<OWLAxiom> axioms, OWLDataFactory datafact, OWLClass concept, OWLOntology ont) {

		for (OWLAnnotation annotation : concept.getAnnotations(ont)) {

			OWLAnnotationProperty prop = annotation.getProperty();
			OWLAnnotationValue value = annotation.getValue();

			OWLAnnotationAssertionAxiom ax = datafact.getOWLAnnotationAssertionAxiom(prop, cls.getIRI(), value);
			axioms.add(ax);
		}
	}

	public static void extractAndCreateSuperClassesOfClass(Set<OWLAxiom> axioms, OWLClass cls, OWLDataFactory df, OWLOntology ont,
			OWLClass concept) {

		for (OWLClassExpression clsExpression : concept.getSuperClasses(ont)) {

			OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(cls, getClassExpressions(clsExpression, df));
			axioms.add(axiom);
		}
	}

	public static OWLClassExpression getClassExpressions(OWLClassExpression classExpr, OWLDataFactory df) {

		OWLClassExpression owlClassRestriction = null;

		//if(!classExpr.isAnonymous()){
		//else if(classExpr.equals(ClassExpressionType.OWL_CLASS)){
		if (classExpr instanceof OWLClass) {
			owlClassRestriction = classExpr;
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_SOME_VALUES_FROM)){
		//else if (classExpr instanceof OWLObjectSomeValuesFromImpl) {
		else if (classExpr instanceof OWLObjectSomeValuesFrom) {
			OWLObjectSomeValuesFrom expression = (OWLObjectSomeValuesFrom)classExpr;

			OWLObjectPropertyExpression objProp = expression.getProperty();
			OWLClassExpression exp = expression.getFiller();

			owlClassRestriction = df.getOWLObjectSomeValuesFrom(getObjectPropertyExpressions(objProp, df), getClassExpressions(exp, df));			
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_ALL_VALUES_FROM)){
		//else if (classExpr instanceof OWLObjectAllValuesFromImpl) {
		else if (classExpr instanceof OWLObjectAllValuesFrom) {
			OWLObjectAllValuesFrom expression = (OWLObjectAllValuesFrom)classExpr;

			OWLObjectPropertyExpression objProp = expression.getProperty();
			OWLClassExpression exp = expression.getFiller();

			owlClassRestriction = df.getOWLObjectAllValuesFrom(getObjectPropertyExpressions(objProp, df), getClassExpressions(exp, df));
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_MIN_CARDINALITY)){
		//else if (classExpr instanceof OWLObjectMinCardinalityImpl) {
		else if(classExpr instanceof OWLObjectMinCardinality){
			OWLObjectMinCardinality exp = (OWLObjectMinCardinality)classExpr;

			OWLObjectPropertyExpression objProp = exp.getProperty();
			OWLClassExpression range = exp.getFiller();
			int card = exp.getCardinality();

			owlClassRestriction = df.getOWLObjectMinCardinality(card, getObjectPropertyExpressions(objProp, df), getClassExpressions(range, df));
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_MAX_CARDINALITY)){
		//else if (classExpr instanceof OWLObjectMaxCardinalityImpl) {
		else if(classExpr instanceof OWLObjectMaxCardinality){
			OWLObjectMaxCardinality exp = (OWLObjectMaxCardinality)classExpr;

			OWLObjectPropertyExpression objProp = exp.getProperty();
			OWLClassExpression range = exp.getFiller();
			int card = exp.getCardinality();

			owlClassRestriction = df.getOWLObjectMaxCardinality(card, getObjectPropertyExpressions(objProp, df), getClassExpressions(range, df));
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_EXACT_CARDINALITY)){
		//else if (classExpr instanceof OWLObjectExactCardinalityImpl) {
		else if(classExpr instanceof OWLObjectExactCardinality){
			OWLObjectExactCardinality exp = (OWLObjectExactCardinality)classExpr;

			OWLObjectPropertyExpression objProp = exp.getProperty();
			OWLClassExpression range = exp.getFiller();
			int card = exp.getCardinality();

			owlClassRestriction = df.getOWLObjectExactCardinality(card, getObjectPropertyExpressions(objProp, df), getClassExpressions(range, df));
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_HAS_VALUE)){
		//else if (classExpr instanceof OWLObjectHasValueImpl) {
		else if(classExpr instanceof OWLObjectHasValue){
			OWLObjectHasValue exp = (OWLObjectHasValue)classExpr;

			OWLObjectPropertyExpression objProp = exp.getProperty();
			OWLIndividual ind = exp.getValue();

			owlClassRestriction = df.getOWLObjectHasValue(getObjectPropertyExpressions(objProp, df), getIndividuals(ind, df));
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_HAS_SELF)){
		//else if (classExpr instanceof OWLObjectHasSelfImpl) {
		else if(classExpr instanceof OWLObjectHasSelf){
			OWLObjectHasSelf exp = (OWLObjectHasSelf)classExpr;

			OWLObjectPropertyExpression objProp = exp.getProperty();

			owlClassRestriction = df.getOWLObjectHasSelf(getObjectPropertyExpressions(objProp, df));	
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_COMPLEMENT_OF)){
		//else if (classExpr instanceof OWLObjectComplementOfImpl) {
		else if(classExpr instanceof OWLObjectComplementOf){
			OWLObjectComplementOf exp = (OWLObjectComplementOf)classExpr;

			OWLClassExpression complement = exp.getOperand();

			owlClassRestriction = df.getOWLObjectComplementOf(getClassExpressions(complement, df));
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_UNION_OF)){
		//else if (classExpr instanceof OWLObjectUnionOfImpl) {
		else if (classExpr instanceof OWLObjectUnionOf) {
			OWLObjectUnionOf union = (OWLObjectUnionOf)classExpr;

			Set<OWLClassExpression> set = new HashSet<OWLClassExpression>();
			for(OWLClassExpression exp : union.getOperands()){
				set.add(getClassExpressions(exp, df));
			}
			owlClassRestriction = df.getOWLObjectUnionOf(set);
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_INTERSECTION_OF)){
		//else if (classExpr instanceof OWLObjectIntersectionOfImpl) {
		if (classExpr instanceof OWLObjectIntersectionOf) {
			OWLObjectIntersectionOf intersect = (OWLObjectIntersectionOf)classExpr;

			Set<OWLClassExpression> set = new HashSet<OWLClassExpression>();
			for(OWLClassExpression exp : intersect.getOperands()){
				set.add(getClassExpressions(exp, df));
			}
			owlClassRestriction = df.getOWLObjectIntersectionOf(set);
		}


		//else if(classExpr.equals(ClassExpressionType.OBJECT_ONE_OF)){
		//else if (classExpr instanceof OWLObjectOneOfImpl) {
		else if (classExpr instanceof OWLObjectOneOf) {
			OWLObjectOneOf exp = (OWLObjectOneOf)classExpr;

			Set<OWLIndividual> set = new HashSet<OWLIndividual>();
			for(OWLIndividual ind : exp.getIndividuals()){
				set.add(getIndividuals(ind, df));
			}
			owlClassRestriction = df.getOWLObjectOneOf(set);
		}


		//else if(classExpr.equals(ClassExpressionType.DATA_MIN_CARDINALITY)){
		//else if (classExpr instanceof OWLDataMinCardinalityImpl) {
		else if(classExpr instanceof OWLDataMinCardinality){
			OWLDataMinCardinality exp = (OWLDataMinCardinality)classExpr;

			OWLDataPropertyExpression dataProp = exp.getProperty();
			OWLDataRange range = exp.getFiller();
			int card = exp.getCardinality();

			owlClassRestriction = df.getOWLDataMinCardinality(card, getDataPropertyExpressions(dataProp, df), getDataRanges(range, df));
		}


		//else if(classExpr.equals(ClassExpressionType.DATA_MAX_CARDINALITY)){
		//else if (classExpr instanceof OWLDataMaxCardinalityImpl) {
		else if(classExpr instanceof OWLDataMaxCardinality){
			OWLDataMaxCardinality exp = (OWLDataMaxCardinality)classExpr;

			OWLDataPropertyExpression dataProp = exp.getProperty();
			OWLDataRange range = exp.getFiller();
			int card = exp.getCardinality();

			owlClassRestriction = df.getOWLDataMaxCardinality(card, getDataPropertyExpressions(dataProp, df), getDataRanges(range, df));
		}


		//else if(classExpr.equals(ClassExpressionType.DATA_EXACT_CARDINALITY)){
		//else if (classExpr instanceof OWLDataExactCardinalityImpl) {
		else if(classExpr instanceof OWLDataExactCardinality){
			OWLDataExactCardinality exp = (OWLDataExactCardinality)classExpr;

			OWLDataPropertyExpression dataProp = exp.getProperty();
			OWLDataRange range = exp.getFiller();
			int card = exp.getCardinality();

			owlClassRestriction = df.getOWLDataExactCardinality(card, getDataPropertyExpressions(dataProp, df), getDataRanges(range, df));
		}


		//else if(classExpr.equals(ClassExpressionType.DATA_HAS_VALUE)){
		//else if (classExpr instanceof OWLDataHasValueImpl) {
		else if(classExpr instanceof OWLDataHasValue){
			OWLDataHasValue exp = (OWLDataHasValue)classExpr;

			OWLDataPropertyExpression dataProp = exp.getProperty();
			OWLLiteral literal = exp.getValue();

			owlClassRestriction = df.getOWLDataHasValue(getDataPropertyExpressions(dataProp, df), literal);
		}


		//else if(classExpr.equals(ClassExpressionType.DATA_ALL_VALUES_FROM)){
		//else if (classExpr instanceof OWLDataAllValuesFromImpl) {
		else if(classExpr instanceof OWLDataAllValuesFrom){
			OWLDataAllValuesFrom exp = (OWLDataAllValuesFrom)classExpr;

			OWLDataPropertyExpression dataProp = exp.getProperty();
			OWLDataRange range = exp.getFiller();

			owlClassRestriction = df.getOWLDataAllValuesFrom(getDataPropertyExpressions(dataProp, df), getDataRanges(range, df));
		}


		//else if(classExpr.equals(ClassExpressionType.DATA_SOME_VALUES_FROM)){
		//else if (classExpr instanceof OWLDataSomeValuesFromImpl) {
		else if(classExpr instanceof OWLDataSomeValuesFrom){
			OWLDataSomeValuesFrom exp = (OWLDataSomeValuesFrom)classExpr;

			OWLDataPropertyExpression dataProp = exp.getProperty();
			OWLDataRange range = exp.getFiller();

			owlClassRestriction = df.getOWLDataSomeValuesFrom(getDataPropertyExpressions(dataProp, df), getDataRanges(range, df));
		}

		return owlClassRestriction;
	}


	public static OWLObjectPropertyExpression getObjectPropertyExpressions(OWLObjectPropertyExpression propExpr, OWLDataFactory df) {

		OWLObjectPropertyExpression owlObjectPropertyRestriction = null;

		//if(!propExpr.isAnonymous()){
		if (propExpr instanceof OWLObjectProperty) {
				owlObjectPropertyRestriction = propExpr;
		}

		//else if (propExpr instanceof OWLObjectInverseOfImpl) {
		else if(propExpr instanceof OWLObjectInverseOf){
			OWLObjectInverseOf exp = (OWLObjectInverseOf)propExpr;

			OWLObjectPropertyExpression inverseProp = exp.getInverse();

			owlObjectPropertyRestriction = df.getOWLObjectInverseOf(getObjectPropertyExpressions(inverseProp, df));		
		}

		return owlObjectPropertyRestriction;
	}


	public static OWLDataPropertyExpression getDataPropertyExpressions(OWLDataPropertyExpression dataExpr, OWLDataFactory df) {

		OWLDataPropertyExpression owlDataPropertyRestriction = null;

		//if(!dataExpr.isAnonymous()){
		if (dataExpr instanceof OWLDataProperty) {
			owlDataPropertyRestriction = dataExpr;		
		}

		return owlDataPropertyRestriction;
	}

	public static OWLIndividual getIndividuals(OWLIndividual individual, OWLDataFactory df) {

		OWLIndividual owlIndividual = individual;

		//if(individual.isNamed()){
		if (individual instanceof OWLNamedIndividual) {
			owlIndividual = individual;
		}

		return owlIndividual;
	}

	public static OWLDataRange getDataRanges(OWLDataRange dataRange, OWLDataFactory df) {

		OWLDataRange owlDataRangeRestriction = null;

		//if(dataRange.isDatatype()) {
		if (dataRange instanceof OWLDatatype) {
			owlDataRangeRestriction = dataRange;
		}

		//else if (dataRange instanceof OWLDataOneOfImpl) {
		else if(dataRange instanceof OWLDataOneOf){
			OWLDataOneOf exp = (OWLDataOneOf)dataRange;

			Set<OWLLiteral> set = new HashSet<OWLLiteral>();
			for(OWLLiteral literal : exp.getValues()){
				set.add(literal);
			}
			owlDataRangeRestriction = df.getOWLDataOneOf(set);
		}

		//else if (dataRange instanceof OWLDataIntersectionOfImpl) {
		else if(dataRange instanceof OWLDataIntersectionOf){
			OWLDataIntersectionOf exp = (OWLDataIntersectionOf)dataRange;

			Set<OWLDataRange> set = new HashSet<OWLDataRange>();
			for(OWLDataRange range : exp.getOperands()){
				set.add(getDataRanges(range, df));
			}
			owlDataRangeRestriction = df.getOWLDataIntersectionOf(set);
		}

		//else if (dataRange instanceof OWLDataUnionOfImpl) {
		else if(dataRange instanceof OWLDataUnionOf){
			OWLDataUnionOf exp = (OWLDataUnionOf)dataRange;

			Set<OWLDataRange> set = new HashSet<OWLDataRange>();
			for(OWLDataRange range : exp.getOperands()){
				set.add(getDataRanges(range, df));
			}
			owlDataRangeRestriction = df.getOWLDataUnionOf(set);
		}

		//else if (dataRange instanceof OWLDataComplementOfImpl) {
		else if(dataRange instanceof OWLDataComplementOf){
			OWLDataComplementOf exp = (OWLDataComplementOf)dataRange;

			OWLDataRange complementRange = exp.getDataRange();
			owlDataRangeRestriction = df.getOWLDataComplementOf(getDataRanges(complementRange, df));
		}

		else if (dataRange instanceof OWLDatatypeRestriction) {
			OWLDatatypeRestriction exp = (OWLDatatypeRestriction)dataRange;

			OWLDatatype dataType = exp.getDatatype();

			Set<OWLFacetRestriction> set = new HashSet<OWLFacetRestriction>();
			for(OWLFacetRestriction facetRestriction : exp.getFacetRestrictions()){
				set.add(facetRestriction);
				//OWLFacet facet = facetRestriction.getFacet();
				//OWLLiteral literal = facetRestriction.getFacetValue();
			}
			owlDataRangeRestriction = df.getOWLDatatypeRestriction(dataType, set);
		}

		return owlDataRangeRestriction;
	}


	public static void extractAndCreateEquivalentClassesOfClass(OWLDataFactory df, OWLClass clsA, Set<OWLAxiom> axioms, OWLClass concept, OWLOntology ont) {

		for (OWLClassExpression clsExpression : concept.getEquivalentClasses(ont)) {

			OWLEquivalentClassesAxiom axiom = df.getOWLEquivalentClassesAxiom(clsA, getClassExpressions(clsExpression, df));
			axioms.add(axiom);
		}
	}

	public static void extractAndCreateDisjointClassesOfClass(OWLClass cls, Set<OWLAxiom> axioms, OWLDataFactory df, OWLClass concept, OWLOntology ont) {

		for (OWLClassExpression clsExpression : concept.getDisjointClasses(ont)) {
			OWLDisjointClassesAxiom axiom = df.getOWLDisjointClassesAxiom(cls, getClassExpressions(clsExpression, df));
			axioms.add(axiom);		
		}
	}

	public static int createRefactoredAxiomsOfParsedObjectProperties(int x, Set<OWLAxiom> axioms, HashSet<String> objectProps, OWLDataFactory df,
			ArrayList<OWLOntology> ontologiesSet) throws OWLException {
		System.out.println("");

		for (OWLOntology ontology_n : ontologiesSet) {
			x = x + objectPropertiesModule(objectProps, axioms, ontology_n, df);
		}
		System.out.println("===> The predicted number of object properties of these ontologies is "+ x+"\n\n");

		return x;
	}

	public static int objectPropertiesModule(HashSet<String> objectProps, Set<OWLAxiom> axioms, OWLOntology ont, OWLDataFactory df)
			throws OWLException {

		int x = 0;
		for (OWLObjectProperty objectProperty : ont.getObjectPropertiesInSignature()) {

			if (!objectProperty.isOWLTopObjectProperty()) {
				x++;
				objectProps.add(objectProperty.getIRI().toString());

				OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(objectProperty);
				axioms.add(ax);

				extractAndCreateObjectPropertyAnnotations(axioms, objectProperty, ont, df, objectProperty);  //labels, comments, and non built-in annotations
				extractAndCreateObjectPropertyDomains(objectProperty, axioms, df, ont, objectProperty);
				extractAndCreateObjectPropertyRanges(objectProperty, axioms, df, ont, objectProperty);
				extractAndCreateObjectPropertyTypes(objectProperty, axioms, df, ont, objectProperty);
				extractAndCreateSuperPropertiesOfObjectProperty(axioms, objectProperty, df, ont, objectProperty);
				extractAndCreateInversePropertiesOfObjectProperty(axioms, objectProperty, df, ont, objectProperty);
				extractAndCreateDisjointPropertiesOfObjectProperty(axioms, objectProperty, df, ont, objectProperty);
				extractAndCreateEquivalentPropertiesOfObjectProperty(axioms, objectProperty, df, ont, objectProperty);
			}
			//} else {
			//OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(objectProperty);
			//axioms.add(ax);
			//}
		}

		System.out.println("- The parsing and the creation of object properties of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" are done");
		System.out.println("---> The number of object properties of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is " + x+"\n");

		return x;
	}

	public static void extractAndCreateObjectPropertyAnnotations(Set<OWLAxiom> axioms, OWLObjectProperty objProp, OWLOntology ont,
			OWLDataFactory datafact, OWLObjectProperty objectProp) {

		for (OWLAnnotation annotation : objectProp.getAnnotations(ont)) {

			OWLAnnotationProperty prop = annotation.getProperty();
			OWLAnnotationValue value = annotation.getValue();

			OWLAnnotationAssertionAxiom ax = datafact.getOWLAnnotationAssertionAxiom(prop, objProp.getIRI(), value);
			axioms.add(ax);
		}
	}

	public static void extractAndCreateObjectPropertyDomains(OWLObjectProperty objProp, Set<OWLAxiom> axioms, OWLDataFactory df, OWLOntology ont,
			OWLObjectProperty objectProperty) {

		for (OWLClassExpression clsExpression : objectProperty.getDomains(ont)) {

			OWLObjectPropertyDomainAxiom ax = df.getOWLObjectPropertyDomainAxiom(objProp, getClassExpressions(clsExpression, df));
			axioms.add(ax);				
		}
	}

	public static void extractAndCreateObjectPropertyRanges(OWLObjectProperty objectProp, Set<OWLAxiom> axioms, OWLDataFactory df, OWLOntology ont,
			OWLObjectProperty obj) {

		for (OWLClassExpression clsExpression : obj.getRanges(ont)) {

			OWLObjectPropertyRangeAxiom ax = df.getOWLObjectPropertyRangeAxiom(objectProp, getClassExpressions(clsExpression, df));
			axioms.add(ax);
		}
	}

	public static void extractAndCreateObjectPropertyTypes(OWLObjectProperty objProp, Set<OWLAxiom> axioms, OWLDataFactory df, OWLOntology ont,
			OWLObjectProperty prop) {

		if (prop.isSymmetric(ont)) {
			OWLSymmetricObjectPropertyAxiom ax = df
					.getOWLSymmetricObjectPropertyAxiom(objProp);
			axioms.add(ax);		}

		if (prop.isAsymmetric(ont)) {
			OWLAsymmetricObjectPropertyAxiom ax = df
					.getOWLAsymmetricObjectPropertyAxiom(objProp);
			axioms.add(ax);		}

		if (prop.isReflexive(ont)) {
			OWLReflexiveObjectPropertyAxiom ax = df
					.getOWLReflexiveObjectPropertyAxiom(objProp);
			axioms.add(ax);		}

		if (prop.isTransitive(ont)) {
			OWLTransitiveObjectPropertyAxiom ax = df
					.getOWLTransitiveObjectPropertyAxiom(objProp);
			axioms.add(ax);		}

		if (prop.isIrreflexive(ont)) {
			OWLIrreflexiveObjectPropertyAxiom ax = df
					.getOWLIrreflexiveObjectPropertyAxiom(objProp);
			axioms.add(ax);		}

		if (prop.isFunctional(ont)) {
			OWLFunctionalObjectPropertyAxiom ax = df
					.getOWLFunctionalObjectPropertyAxiom(objProp);
			axioms.add(ax);		}

		if (prop.isInverseFunctional(ont)) {
			OWLInverseFunctionalObjectPropertyAxiom ax = df
					.getOWLInverseFunctionalObjectPropertyAxiom(objProp);
			axioms.add(ax);
		}	
	}

	public static void extractAndCreateSuperPropertiesOfObjectProperty(Set<OWLAxiom> axioms, OWLObjectProperty objProperty, OWLDataFactory df, OWLOntology ont,
			OWLObjectProperty objProp) {

		for (OWLObjectPropertyExpression propExpression : objProp.getSuperProperties(ont)) {

			OWLSubObjectPropertyOfAxiom ax = df.getOWLSubObjectPropertyOfAxiom(objProperty, getObjectPropertyExpressions(propExpression, df));
			axioms.add(ax);
		}
	}	

	public static void extractAndCreateInversePropertiesOfObjectProperty(Set<OWLAxiom> axioms, OWLObjectProperty objectProp, OWLDataFactory df, OWLOntology ont,
			OWLObjectProperty obj) {

		for (OWLObjectPropertyExpression objPropExpression : obj.getInverses(ont)) {

			OWLInverseObjectPropertiesAxiom inv = df.getOWLInverseObjectPropertiesAxiom(objectProp,
					getObjectPropertyExpressions(objPropExpression, df));
			axioms.add(inv);			
		}
	}

	public static void extractAndCreateDisjointPropertiesOfObjectProperty(Set<OWLAxiom> axioms, OWLObjectProperty objProperty, OWLDataFactory df, OWLOntology ont,
			OWLObjectProperty prop) {

		for (OWLObjectPropertyExpression objectProp : prop.getDisjointProperties(ont)) {

			OWLDisjointObjectPropertiesAxiom disjAx = df.getOWLDisjointObjectPropertiesAxiom(objProperty,
					getObjectPropertyExpressions(objectProp, df));
			axioms.add(disjAx);	
		}
	}

	public static void extractAndCreateEquivalentPropertiesOfObjectProperty(Set<OWLAxiom> axioms, OWLObjectProperty objProp, OWLDataFactory df, OWLOntology ont,
			OWLObjectProperty objectProp) {

		for (OWLObjectPropertyExpression propExpression :  objectProp.getEquivalentProperties(ont)) {

			OWLEquivalentObjectPropertiesAxiom inv = df.getOWLEquivalentObjectPropertiesAxiom(objProp, getObjectPropertyExpressions(propExpression, df));
			axioms.add(inv);
		}
	}

	public static int createRefactoredAxiomsOfParsedDataProperties(int x, Set<OWLAxiom> axioms, HashSet<String> dataProps, OWLDataFactory df,
			ArrayList<OWLOntology> ontologiesSet) throws OWLException {
		System.out.println("");

		for (OWLOntology ontology_n : ontologiesSet) {
			x = x + dataPropertiesModule(dataProps, axioms, ontology_n, df);
		}
		System.out.println("===> The predicted number of data properties of these ontologies is "+ x+"\n\n");

		return x;
	}

	public static int dataPropertiesModule(HashSet<String> dataProps, Set<OWLAxiom> axioms, OWLOntology ont, OWLDataFactory df)
			throws OWLException {

		int x = 0;
		for (OWLDataProperty dataProperty : ont.getDataPropertiesInSignature()) {

			if (!dataProperty.isOWLTopDataProperty()) {
				x++;
				dataProps.add(dataProperty.getIRI().toString());

				OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(dataProperty);
				axioms.add(ax);

				extractAndCreateDataPropertyAnnotations(dataProperty, axioms, dataProperty, ont, df);  //labels, comments, and non built-in annotations
				extractAndCreateDataPropertyDomains(dataProperty, axioms, df, dataProperty, ont);
				extractAndCreateDataPropertyRanges(dataProperty, axioms, df, dataProperty, ont);
				extractAndCreateDataPropertyTypes(dataProperty, axioms, df, dataProperty, ont);
				extractAndCreateSuperPropertiesOfDataProperty(dataProperty, axioms, df, dataProperty, ont);
				extractAndCreateEquivalentPropertiesOfDataProperty(dataProperty, axioms, df, dataProperty, ont);
				extractAndCreateDisjointPropertiesOfDataProperty(dataProperty, axioms, df, dataProperty, ont);
			}

			//} else {
			//OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(dataProperty);
			//axioms.add(ax);
			//}
		}

		System.out.println("- The parsing and the creation of data properties of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" are done");
		System.out.println("---> The number of data properties of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is " + x+"\n");

		return x;
	}

	public static void extractAndCreateDataPropertyAnnotations(OWLDataProperty dataProp, Set<OWLAxiom> axioms, OWLDataProperty dataProperty,
			OWLOntology ont, OWLDataFactory df) {

		for (OWLAnnotation annotation : dataProperty.getAnnotations(ont)) {

			OWLAnnotationProperty prop = annotation.getProperty();
			OWLAnnotationValue value = annotation.getValue();

			OWLAnnotationAssertionAxiom ax = df.getOWLAnnotationAssertionAxiom(prop, dataProp.getIRI(), value);
			axioms.add(ax);
		}
	}

	public static void extractAndCreateDataPropertyDomains(OWLDataProperty dataProperty, Set<OWLAxiom> axioms, OWLDataFactory df, OWLDataProperty dataProp, OWLOntology ont) {

		for (OWLClassExpression clsExpression : dataProp.getDomains(ont)) {

			OWLDataPropertyDomainAxiom ax = df.getOWLDataPropertyDomainAxiom(dataProperty, getClassExpressions(clsExpression, df));
			axioms.add(ax);
		}
	}

	public static void extractAndCreateDataPropertyRanges(OWLDataProperty dataProp, Set<OWLAxiom> axioms, OWLDataFactory df, OWLDataProperty dataProperty, OWLOntology ont) {

		for (OWLDataRange range : dataProperty.getRanges(ont)) {

			OWLDataPropertyRangeAxiom ax = df.getOWLDataPropertyRangeAxiom(dataProp, getDataRanges(range, df));
			axioms.add(ax);
		}
	}

	public static void extractAndCreateDataPropertyTypes(OWLDataProperty dataProp, Set<OWLAxiom> axioms, OWLDataFactory df, OWLDataProperty dataProperty, OWLOntology ont) {

		if (dataProperty.isFunctional(ont)) {
			OWLFunctionalDataPropertyAxiom typeAx = df.getOWLFunctionalDataPropertyAxiom(dataProp);
			axioms.add(typeAx);	
		}
	}

	public static void extractAndCreateSuperPropertiesOfDataProperty(OWLDataProperty dataProp, Set<OWLAxiom> axioms, OWLDataFactory df, OWLDataProperty dataProperty, OWLOntology ont) {

		for (OWLDataPropertyExpression dataPropExpr : dataProperty.getSuperProperties(ont)) {

			OWLSubDataPropertyOfAxiom ax = df.getOWLSubDataPropertyOfAxiom(dataProp, getDataPropertyExpressions(dataPropExpr, df));
			axioms.add(ax);
		}
	}

	public static void extractAndCreateEquivalentPropertiesOfDataProperty(OWLDataProperty dataProp, Set<OWLAxiom> axioms, OWLDataFactory df, OWLDataProperty dataProperty, OWLOntology ont) {

		for (OWLDataPropertyExpression equiv : dataProperty.getEquivalentProperties(ont)) {

			OWLEquivalentDataPropertiesAxiom equivAx = df.getOWLEquivalentDataPropertiesAxiom(dataProp, getDataPropertyExpressions(equiv, df));
			axioms.add(equivAx);
		}
	}

	public static void extractAndCreateDisjointPropertiesOfDataProperty(OWLDataProperty dataProp, Set<OWLAxiom> axioms, OWLDataFactory df, OWLDataProperty dataProperty, OWLOntology ont) {

		for (OWLDataPropertyExpression dataPropExpr : dataProperty.getDisjointProperties(ont)) {

			OWLDisjointDataPropertiesAxiom disjAx = df.getOWLDisjointDataPropertiesAxiom(dataProp, getDataPropertyExpressions(dataPropExpr, df));
			axioms.add(disjAx);
		}
	}

	public static int createRefactoredAxiomsOfParsedIndividuals(int x, Set<OWLAxiom> axioms, HashSet<String> individuals, OWLDataFactory df,
			ArrayList<OWLOntology> ontologiesSet) {
		System.out.println("");

		for (OWLOntology ontology_n : ontologiesSet) {
			x = x + individualsModule(individuals, axioms, ontology_n, df);
		}
		System.out.println("===> The predicted number of individuals of these ontologies is "+ x +"\n\n");

		return x;
	}

	public static int individualsModule(HashSet<String> individuals, Set<OWLAxiom> axioms, OWLOntology ont, OWLDataFactory df){

		int k = 0;
		for (OWLNamedIndividual individual : ont.getIndividualsInSignature()) {
			k++;

			individuals.add(individual.getIRI().toString());
			OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(individual);
			axioms.add(ax);

			extractAndCreateIndividualAnnotations(individual, axioms, individual, ont, df);   //labels, comments, and non built-in annotations
			extractAndCreateSameAsIndividualsOfIndividual(individual, axioms, df, individual, ont);
			extractAndCreateDifferentIndividualsOfIndividual(axioms, individual, df, individual, ont); 
			extractAndCreateClassAssertions(individual, axioms, df, individual, ont);
			extractAndCreateObjectPropertyAssertionsOfIndividual(individual, axioms, df, individual, ont);
			extractAndCreateNegativeObjectPropertyAssertionsOfIndividual(individual, axioms, df, individual, ont); 
			extractAndCreateDataPropertyAssertionsOfIndividual(individual, axioms, df, individual, ont);
			extractAndCreateNegativeDataPropertyAssertionsOfIndividual(individual, axioms, df, individual, ont);
		}

		System.out.println("- The parsing and the creation of individuals of \"" + ont.getOntologyID().getOntologyIRI().getFragment() + "\" are done");
		System.out.println("---> The number of individuals of \""+ ont.getOntologyID().getOntologyIRI().getFragment()  +"\" is " + k+"\n");

		return k;
	}

	public static void extractAndCreateIndividualAnnotations(OWLNamedIndividual instance, Set<OWLAxiom> axioms, OWLNamedIndividual individual, OWLOntology ont,
			OWLDataFactory df) {

		for (OWLAnnotation annotation : individual.getAnnotations(ont)) {

			OWLAnnotationProperty prop = annotation.getProperty();
			OWLAnnotationValue value = annotation.getValue();

			OWLAnnotationAssertionAxiom ax = df.getOWLAnnotationAssertionAxiom(prop, instance.getIRI(), value);
			axioms.add(ax);
		}
	}

	public static void extractAndCreateSameAsIndividualsOfIndividual(OWLNamedIndividual instance, Set<OWLAxiom> axioms, OWLDataFactory df, OWLNamedIndividual individual, OWLOntology ont) {

		for (OWLIndividual same : individual.getSameIndividuals(ont)) {

			OWLSameIndividualAxiom sameAx = df.getOWLSameIndividualAxiom(instance, getIndividuals(same, df));
			axioms.add(sameAx);
		}
	}

	public static void extractAndCreateDifferentIndividualsOfIndividual(Set<OWLAxiom> axioms, OWLNamedIndividual instance, OWLDataFactory df, OWLNamedIndividual individual, OWLOntology ont) {

		Collection<OWLIndividual> diffIndividuals = individual.getDifferentIndividuals(ont);
		if(!diffIndividuals.isEmpty()){

			Set<OWLIndividual> set = new HashSet<OWLIndividual>();

			for (OWLIndividual indiv : diffIndividuals) {
				set.add(getIndividuals(indiv, df));
			}
			set.add(instance);

			OWLDifferentIndividualsAxiom diffAx = df.getOWLDifferentIndividualsAxiom(set);
			axioms.add(diffAx);
		}
	}

	public static void extractAndCreateClassAssertions(OWLNamedIndividual instance, Set<OWLAxiom> axioms, OWLDataFactory df, OWLNamedIndividual individual, OWLOntology ont) {

		for (OWLClassAssertionAxiom classAssertion : ont.getClassAssertionAxioms(individual)) {
			for (OWLClass cls : classAssertion.getClassesInSignature()) {

				OWLClassAssertionAxiom classAssertionAXiom = df.getOWLClassAssertionAxiom(getClassExpressions(cls, df), instance);
				axioms.add(classAssertionAXiom);
			}
		}
	}

	public static void extractAndCreateObjectPropertyAssertionsOfIndividual(OWLNamedIndividual instance, Set<OWLAxiom> axioms, OWLDataFactory df,
			OWLNamedIndividual individual, OWLOntology ont) {

		Map<OWLObjectPropertyExpression, Set<OWLIndividual>> map = individual.getObjectPropertyValues(ont);
		for (Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry : map.entrySet()) {
			if (!entry.getValue().isEmpty()) {

				OWLObjectPropertyExpression objectProp = getObjectPropertyExpressions(entry.getKey(), df);

				for (OWLIndividual indiv : entry.getValue()) {

					OWLObjectPropertyAssertionAxiom propertyAssertionAxiom = df
							.getOWLObjectPropertyAssertionAxiom(objectProp, instance, getIndividuals(indiv, df));
					axioms.add(propertyAssertionAxiom);
				}
			}
		}
	}

	public static void extractAndCreateNegativeObjectPropertyAssertionsOfIndividual(OWLNamedIndividual instance, Set<OWLAxiom> axioms, OWLDataFactory df,
			OWLNamedIndividual individual, OWLOntology ont) {

		Map<OWLObjectPropertyExpression, Set<OWLIndividual>> map = individual.getNegativeObjectPropertyValues(ont);
		for (Entry<OWLObjectPropertyExpression, Set<OWLIndividual>> entry : map.entrySet()) {
			if (!entry.getValue().isEmpty()) {

				OWLObjectPropertyExpression objectProp = getObjectPropertyExpressions(entry.getKey(), df);

				for (OWLIndividual indiv : entry.getValue()) {

					OWLObjectPropertyAssertionAxiom negPropAssertionAxiom = df
							.getOWLObjectPropertyAssertionAxiom(objectProp, instance, getIndividuals(indiv, df));
					axioms.add(negPropAssertionAxiom);
				}
			}
		}
	}

	public static void extractAndCreateDataPropertyAssertionsOfIndividual(OWLNamedIndividual instance, Set<OWLAxiom> axioms, OWLDataFactory df,
			OWLNamedIndividual individual, OWLOntology ont) {

		Map<OWLDataPropertyExpression, Set<OWLLiteral>> map = individual.getDataPropertyValues(ont);
		for (Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : map.entrySet()) {
			if (!entry.getValue().isEmpty()) {

				for (OWLLiteral literal : entry.getValue()) {

					OWLDataPropertyAssertionAxiom dataPropertyAssertionAxiom = df
							.getOWLDataPropertyAssertionAxiom(getDataPropertyExpressions(entry.getKey(), df), instance, literal);
					axioms.add(dataPropertyAssertionAxiom);
				}
			}
		}
	}

	public static void extractAndCreateNegativeDataPropertyAssertionsOfIndividual(OWLNamedIndividual instance, Set<OWLAxiom> axioms, OWLDataFactory df,
			OWLNamedIndividual individual, OWLOntology ont) {

		Map<OWLDataPropertyExpression, Set<OWLLiteral>> map = individual.getNegativeDataPropertyValues(ont);
		for (Entry<OWLDataPropertyExpression, Set<OWLLiteral>> entry : map.entrySet()) {
			if (!entry.getValue().isEmpty()) {

				for (OWLLiteral literal : entry.getValue()) {

					OWLDataPropertyAssertionAxiom negDataPropAssertionAxiom = df
							.getOWLDataPropertyAssertionAxiom(getDataPropertyExpressions(entry.getKey(), df), instance, literal);
					axioms.add(negDataPropAssertionAxiom);
				}
			}
		}
	}

	public static void createAxiomsOfParsedSubPropertyChainOfAxioms(Set<OWLAxiom> axioms, OWLDataFactory df, ArrayList<OWLOntology> ontologiesSet) throws OWLException{

		for(OWLOntology ontology_n : ontologiesSet){
			parsingSubPropertyChains(axioms, ontology_n, df);
		}
	}

	public static void parsingSubPropertyChains(Set<OWLAxiom> axioms, OWLOntology ont, OWLDataFactory df){

		Set<OWLSubPropertyChainOfAxiom> set = ont.getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF);
		for(OWLSubPropertyChainOfAxiom chain : set){

			List<OWLObjectPropertyExpression> list = chain.getPropertyChain();
			OWLObjectPropertyExpression supObjProp = chain.getSuperProperty();

			List<OWLObjectPropertyExpression> modifiedList = new ArrayList<OWLObjectPropertyExpression>();
			for(OWLObjectPropertyExpression obExp : list){
				modifiedList.add(getObjectPropertyExpressions(obExp, df));
			}

			if(chain.isAnnotated()){
				Set<OWLAnnotation> annotation = chain.getAnnotations();
				OWLSubPropertyChainOfAxiom ax = df.getOWLSubPropertyChainOfAxiom(modifiedList, getObjectPropertyExpressions(supObjProp, df), annotation);
				axioms.add(ax);
			}else{
				OWLSubPropertyChainOfAxiom ax = df.getOWLSubPropertyChainOfAxiom(modifiedList, getObjectPropertyExpressions(supObjProp, df));
				axioms.add(ax);
			}			
		}
	}

	public static void createAxiomsOfParsedHasKeyAxioms(Set<OWLAxiom> axioms, OWLDataFactory df, ArrayList<OWLOntology> ontologiesSet) throws OWLException{

		for(OWLOntology ontology_n : ontologiesSet){
			parsingHasKey(axioms, ontology_n, df);
		}
	}

	public static void parsingHasKey(Set<OWLAxiom> axioms, OWLOntology ont, OWLDataFactory df){

		Set<OWLHasKeyAxiom> setKey = ont.getAxioms(AxiomType.HAS_KEY);
		for(OWLHasKeyAxiom key : setKey){

			OWLClassExpression clsExp = key.getClassExpression();
			OWLClassExpression ModifiedClsExp = getClassExpressions(clsExp, df);
			Set<OWLObjectPropertyExpression> objExpSet = key.getObjectPropertyExpressions();
			Set<OWLDataPropertyExpression> dataExpSet = key.getDataPropertyExpressions();


			Set<OWLObjectPropertyExpression> modifiedObjExpSet = new HashSet<OWLObjectPropertyExpression>();
			for(OWLObjectPropertyExpression objExp : objExpSet){
				modifiedObjExpSet.add(getObjectPropertyExpressions(objExp, df));
			}
			Set<OWLDataPropertyExpression> modifiedDataExpSet = new HashSet<OWLDataPropertyExpression>();
			for(OWLDataPropertyExpression dataExp : dataExpSet){
				modifiedDataExpSet.add(getDataPropertyExpressions(dataExp, df));
			}

			if(!key.isAnnotated()){
				OWLHasKeyAxiom haskey = df.getOWLHasKeyAxiom(ModifiedClsExp, modifiedObjExpSet);
				OWLHasKeyAxiom haskeyy = df.getOWLHasKeyAxiom(ModifiedClsExp, modifiedDataExpSet);
				axioms.add(haskey);
				axioms.add(haskeyy);
			}else{
				Set<OWLAnnotation> annotations = key.getAnnotations();
				OWLHasKeyAxiom haskey = df.getOWLHasKeyAxiom(ModifiedClsExp, modifiedObjExpSet, annotations);
				OWLHasKeyAxiom haskeyy = df.getOWLHasKeyAxiom(ModifiedClsExp, modifiedDataExpSet, annotations);
				axioms.add(haskey);
				axioms.add(haskeyy);
			}
		}
	}

	public static void createAxiomsOfParsedNonBuiltInAnnotationProperties(Set<OWLAxiom> axioms, OWLDataFactory df, ArrayList<OWLOntology> ontologiesSet) throws OWLException{

		for(OWLOntology ontology_n : ontologiesSet){
			for (OWLAnnotationProperty annoProp : ontology_n.getAnnotationPropertiesInSignature()){

				if(!annoProp.isBuiltIn()){
					OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(annoProp);
					axioms.add(ax);

					extractAndCreateAnnotationPropertyAnnotations(annoProp, axioms, ontology_n, df);
					extractAndCreateSuperPropertiesOfAnnotationProperty(annoProp, axioms, df, ontology_n);
					extractAndCreateAnnotationPropertyDomains(annoProp, axioms, df, ontology_n);
					extractAndCreateAnnotationPropertyRanges(annoProp, axioms, df, ontology_n);
				}
			}
		}
	}

	public static void extractAndCreateAnnotationPropertyAnnotations(OWLAnnotationProperty prop, Set<OWLAxiom> axioms, OWLOntology ont,
			OWLDataFactory df) {

		for (OWLAnnotation annotation : prop.getAnnotations(ont)) {

			OWLAnnotationProperty AnnoProp = annotation.getProperty();
			OWLAnnotationValue value = annotation.getValue();

			OWLAnnotationAssertionAxiom ax = df.getOWLAnnotationAssertionAxiom(AnnoProp, prop.getIRI(), value);
			axioms.add(ax);
		}
	}

	public static void extractAndCreateSuperPropertiesOfAnnotationProperty(OWLAnnotationProperty annotationProperty, Set<OWLAxiom> axioms, OWLDataFactory df, OWLOntology ont) {

		for (OWLAnnotationProperty superProp : annotationProperty.getSuperProperties(ont)) {

			OWLSubAnnotationPropertyOfAxiom ax = df.getOWLSubAnnotationPropertyOfAxiom(annotationProperty, superProp);
			axioms.add(ax);
		}
	}

	public static void extractAndCreateAnnotationPropertyDomains(OWLAnnotationProperty prop, Set<OWLAxiom> axioms, OWLDataFactory df,
			OWLOntology ont) {

		for (OWLAnnotationPropertyDomainAxiom domain : ont.getAnnotationPropertyDomainAxioms(prop)) {

			OWLAnnotationPropertyDomainAxiom axiom = df.getOWLAnnotationPropertyDomainAxiom(prop, domain.getDomain());
			axioms.add(axiom);
		}
	}

	public static void extractAndCreateAnnotationPropertyRanges(OWLAnnotationProperty annotationProp, Set<OWLAxiom> axioms, OWLDataFactory df,
			OWLOntology ont) {

		for (OWLAnnotationPropertyRangeAxiom range : ont.getAnnotationPropertyRangeAxioms(annotationProp)) {

			OWLAnnotationPropertyDomainAxiom ax = df.getOWLAnnotationPropertyDomainAxiom(annotationProp, range.getRange());
			axioms.add(ax);
		}
	}

	public static void createAxiomsOfParsedDataTypes(Set<OWLAxiom> axioms, OWLDataFactory df, ArrayList<OWLOntology> ontologiesSet) throws OWLException{

		for(OWLOntology ontology_n : ontologiesSet){
			for(OWLDatatype type : ontology_n.getDatatypesInSignature()){

				OWLDeclarationAxiom ax = df.getOWLDeclarationAxiom(type);
				axioms.add(ax);
			}			
		}
	}

	public static void createAxiomsOfParsedAnonymousIndividuals(Set<OWLAxiom> axioms, OWLDataFactory df, ArrayList<OWLOntology> onts) {

		for (OWLOntology ont : onts) {

			for (OWLAnonymousIndividual individual : ont.getAnonymousIndividuals()) {
				for (OWLAnnotationAssertionAxiom assertion : ont.getAnnotationAssertionAxioms(individual)) {

					//OWLAnnotation anno = assertion.getAnnotation();
					OWLAnnotationProperty prop = assertion.getProperty();
					OWLAnnotationSubject sub = assertion.getSubject();
					OWLAnnotationValue value = assertion.getValue();
					Set<OWLAnnotation> annotations = assertion.getAnnotations();

					if(!assertion.isAnnotated()){
						OWLAnnotationAssertionAxiom x = df.getOWLAnnotationAssertionAxiom(prop, sub, value);
						//OWLAnnotationAssertionAxiom x = df.getOWLAnnotationAssertionAxiom(sub, anno);
						axioms.add(x);
					}else{
						OWLAnnotationAssertionAxiom x = df.getOWLAnnotationAssertionAxiom(prop, sub, value, annotations);
						//OWLAnnotationAssertionAxiom x = df.getOWLAnnotationAssertionAxiom(sub, anno, annotations);
						axioms.add(x);
					}
				}
			}	
		}
	}

	public static int getNumberOfPredictedLogicalAxioms(int x, ArrayList<OWLOntology> ontologiesSet) throws OWLException{

		System.out.println("");

		for(OWLOntology ontology_n : ontologiesSet){
			int size = ontology_n.getLogicalAxiomCount();
			x = x + size;
			System.out.println("---> The number of axioms of \""+ ontology_n.getOntologyID().getOntologyIRI().getFragment()  +"\" is " + size);
		}	
		System.out.println("\n===> The predicted total number of axioms of these ontologies is "+ x +"\n\n");

		return x;
	}

	public static int createBridgingAxiomsUsingOriginalAlignments(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objectProps,
			HashSet<String> dataProps, HashSet<String> individuals) throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold);  // al.cut( "hard", threshold );

		int eq = 0, eq1 = 0, eq2 = 0, sub1 = 0, sub2 = 0, disj = 0;

		Enumeration<Cell> enume = alignment.getElements();
		while (enume.hasMoreElements()) {
			Cell cell = enume.nextElement();

			String entity1 = cell.getObject1AsURI().toString();
			String entity2 = cell.getObject2AsURI().toString();

			String relation = cell.getRelation().getRelation();

			if(relation.equals("=") || relation.equals("?")){
				eq++;
				if(relation.equals("=")){
					eq1++;	
				}else{
					eq2++;
				}
				createEquivalentBridgingAxioms(entity1, entity2, classes, objectProps, dataProps, individuals, axioms, datafactory);	
			}
			else{
				if(relation.equals("<")){
					sub1++;
					createSubsumptionBridgingAxioms(entity1, entity2, classes, objectProps, dataProps, individuals, axioms, datafactory);
				}
				else {
					if(relation.equals(">")){
						sub2++;
						createSubsumptionBridgingAxioms(entity2, entity1, classes, objectProps, dataProps, individuals, axioms, datafactory);
					}
					else{
						if(relation.equals("%")){
							disj++;
							createDisjointnessBridgingAxioms(entity1, entity2, classes, objectProps, dataProps, individuals, axioms, datafactory);
						}
					}
				}
			}

		}

		System.out.println("  --> There are "+ eq + " equivalences that we will use :");
		System.out.println("     --> "+ eq1 + " equivalences (=) ");
		System.out.println("     --> "+ eq2 + " unknown equivalences (?)");

		System.out.println("  --> There are "+ sub1 + " subsumptions (<)");
		System.out.println("  --> There are "+ sub2 + " subsumptions (>)");
		System.out.println("  --> There are "+ disj + " disjunctions (%)");

		System.out.println("==> The creation of \"Bridging\" axioms from the alignment \"" + alignmentFile + "\" is done\n\n");

		int effectiveNbCells = eq + sub1 + sub2 + disj;
		return effectiveNbCells;
	}	

	public static void createEquivalentBridgingAxioms(String entity1, String entity2, HashSet<String> classes, HashSet<String> objectProps, HashSet<String> dataProps, HashSet<String> individuals, Set<OWLAxiom> axioms, OWLDataFactory datafactory){

		if (classes.contains(entity1) && classes.contains(entity2)) {

			OWLClass clsA = datafactory.getOWLClass(IRI.create(entity1));
			OWLClass clsB = datafactory.getOWLClass(IRI.create(entity2));

			OWLEquivalentClassesAxiom axiom = datafactory.getOWLEquivalentClassesAxiom(clsA, clsB);
			axioms.add(axiom);
		} else {

			if (objectProps.contains(entity1) && objectProps.contains(entity2)) {

				OWLObjectProperty prop1 = datafactory
						.getOWLObjectProperty(IRI.create(entity1));

				OWLObjectProperty prop2 = datafactory
						.getOWLObjectProperty(IRI.create(entity2));

				OWLEquivalentObjectPropertiesAxiom ax = datafactory.getOWLEquivalentObjectPropertiesAxiom(prop1, prop2);
				axioms.add(ax);
			} else {

				if (dataProps.contains(entity1) && dataProps.contains(entity2)) {

					OWLDataProperty prop1 = datafactory
							.getOWLDataProperty(IRI.create(entity1));

					OWLDataProperty prop2 = datafactory
							.getOWLDataProperty(IRI.create(entity2));

					OWLEquivalentDataPropertiesAxiom ax = datafactory.getOWLEquivalentDataPropertiesAxiom(prop1, prop2);
					axioms.add(ax);
				} else {

					if (individuals.contains(entity1) && individuals.contains(entity2)) {

						OWLNamedIndividual indiv1 = datafactory
								.getOWLNamedIndividual(IRI.create(entity1));

						OWLNamedIndividual indiv2 = datafactory
								.getOWLNamedIndividual(IRI.create(entity2));

						OWLSameIndividualAxiom ax = datafactory.getOWLSameIndividualAxiom(indiv1, indiv2);
						axioms.add(ax);
					}
				}
			}
		}
	}

	public static void createSubsumptionBridgingAxioms(String entity1, String entity2, HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps, HashSet<String> instance_num, Set<OWLAxiom> axioms, OWLDataFactory datafactory){

		if (classes.contains(entity1) && classes.contains(entity2)) {

			OWLClass clsA = datafactory.getOWLClass(IRI.create(entity1));
			OWLClass clsB = datafactory.getOWLClass(IRI.create(entity2));

			OWLSubClassOfAxiom axiom = datafactory.getOWLSubClassOfAxiom(clsA, clsB);
			axioms.add(axiom);
		} else {

			if (objProps.contains(entity1) && objProps.contains(entity2)) {

				OWLObjectProperty prop1 = datafactory
						.getOWLObjectProperty(IRI.create(entity1));

				OWLObjectProperty prop2 = datafactory
						.getOWLObjectProperty(IRI.create(entity2));

				OWLSubObjectPropertyOfAxiom ax = datafactory.getOWLSubObjectPropertyOfAxiom(prop1, prop2);
				axioms.add(ax);
			} else {

				if (dataProps.contains(entity1) && dataProps.contains(entity2)) {

					OWLDataProperty prop1 = datafactory
							.getOWLDataProperty(IRI.create(entity1));

					OWLDataProperty prop2 = datafactory
							.getOWLDataProperty(IRI.create(entity2));

					OWLSubDataPropertyOfAxiom ax = datafactory.getOWLSubDataPropertyOfAxiom(prop1, prop2);
					axioms.add(ax);
				}
			}
		}
	}

	public static void createDisjointnessBridgingAxioms(String entity1, String entity2, HashSet<String> classes, HashSet<String> objectProps, HashSet<String> dataProps, HashSet<String> individuals, Set<OWLAxiom> axioms, OWLDataFactory datafactory){

		if (classes.contains(entity1) && classes.contains(entity2)) {

			OWLClass clsA = datafactory.getOWLClass(IRI.create(entity1));
			OWLClass clsB = datafactory.getOWLClass(IRI.create(entity2));

			OWLDisjointClassesAxiom axiom = datafactory.getOWLDisjointClassesAxiom(clsA, clsB);
			axioms.add(axiom);
		} else {

			if (objectProps.contains(entity1) && objectProps.contains(entity2)) {

				OWLObjectProperty prop1 = datafactory
						.getOWLObjectProperty(IRI.create(entity1));

				OWLObjectProperty prop2 = datafactory
						.getOWLObjectProperty(IRI.create(entity2));

				OWLDisjointObjectPropertiesAxiom ax = datafactory.getOWLDisjointObjectPropertiesAxiom(prop1, prop2);
				axioms.add(ax);
			} else {

				if (dataProps.contains(entity1) && dataProps.contains(entity2)) {

					OWLDataProperty prop1 = datafactory
							.getOWLDataProperty(IRI.create(entity1));

					OWLDataProperty prop2 = datafactory
							.getOWLDataProperty(IRI.create(entity2));

					OWLDisjointDataPropertiesAxiom ax = datafactory.getOWLDisjointDataPropertiesAxiom(prop1, prop2);
					axioms.add(ax);
				} else {

					if (individuals.contains(entity1) && individuals.contains(entity2)) {

						OWLNamedIndividual indiv1 = datafactory
								.getOWLNamedIndividual(IRI.create(entity1));

						OWLNamedIndividual indiv2 = datafactory
								.getOWLNamedIndividual(IRI.create(entity2));

						OWLDifferentIndividualsAxiom ax = datafactory.getOWLDifferentIndividualsAxiom(indiv1, indiv2);
						axioms.add(ax);
					}
				}
			}
		}
	}

	public static int createBridgingAxiomsUsingFilteredAlignments_SoftVersion(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objectProps,
			HashSet<String> dataProps, HashSet<String> individuals, HashSet<String> redundantEntities)
					throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold); // al.cut( "hard", threshold );

		HashMap<String, HashSet<String>> equivHash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> equivHash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> equivHashConf = new HashMap<String, String>();
		HashMap<String, String> equivHashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> is_a_Hash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> is_a_Hash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> is_a_HashConf = new HashMap<String, String>();
		HashMap<String, String> is_a_HashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> reverse_is_a_Hash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> reverse_is_a_Hash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> reverse_is_HashConf = new HashMap<String, String>();
		HashMap<String, String> reverse_is_a_HashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> disjHash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> disjHash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> disjHashConf = new HashMap<String, String>();
		HashMap<String, String> disjHashConf2 = new HashMap<String, String>();


		int eq = 0, eq1 = 0, eq2 = 0, sub1 = 0, sub2 = 0, disj = 0, newEq = 0, newSub1 = 0, newSub2 = 0, newDisj = 0;

		Enumeration<Cell> enume = alignment.getElements();
		while (enume.hasMoreElements()) {
			Cell cell = enume.nextElement();

			String entity1 = cell.getObject1AsURI().toString();
			String entity2 = cell.getObject2AsURI().toString();

			redundantEntities.add(entity1);
			redundantEntities.add(entity2);

			String relation = cell.getRelation().getRelation();

			if(relation.equals("=") || relation.equals("?")){
				eq++;
				if(relation.equals("=")){
					eq1++;
				}else{
					eq2++;
				}
				filterCellsHavingSameSources_Soft(equivHash, equivHashConf, cell, entity1, entity2);			
			}
			else {
				if(relation.equals("<")){
					sub1++;
					filterCellsHavingSameSources_Soft(is_a_Hash, is_a_HashConf, cell, entity1, entity2);			
				}
				else{
					if(relation.equals(">")){
						sub2++;
						filterCellsHavingSameSources_Soft(reverse_is_a_Hash, reverse_is_HashConf, cell, entity1, entity2);
					}
					else{
						if(relation.equals("%")){
							disj++;
							filterCellsHavingSameSources_Soft(disjHash, disjHashConf, cell, entity1, entity2);
						}
					}
				}
			}

		}

		filterCellsHavingSameTargets_Soft(equivHash, equivHash2, equivHashConf, equivHashConf2);
		filterCellsHavingSameTargets_Soft(is_a_Hash, is_a_Hash2, is_a_HashConf, is_a_HashConf2);
		filterCellsHavingSameTargets_Soft(reverse_is_a_Hash, reverse_is_a_Hash2, reverse_is_HashConf, reverse_is_a_HashConf2);
		filterCellsHavingSameTargets_Soft(disjHash, disjHash2, disjHashConf, disjHashConf2);

		int effectiveNbCells = createBridgingAxiomsFromFilteredAlignment(datafactory, axioms, alignmentFile, classes, objectProps, dataProps,
				individuals, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);

		getRedundantEntities(redundantEntities, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2);

		return effectiveNbCells;
	}

	public static int createBridgingAxiomsUsingFilteredAlignments_HardVersion(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objectProps,
			HashSet<String> dataProps, HashSet<String> individuals, HashSet<String> redundantEntities)
					throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold); // al.cut( "hard", threshold );

		HashMap<String, HashSet<String>> equivHash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> equivHash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> equivHashConf = new HashMap<String, String>();
		HashMap<String, String> equivHashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> is_a_Hash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> is_a_Hash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> is_a_HashConf = new HashMap<String, String>();
		HashMap<String, String> is_a_HashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> reverse_is_a_Hash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> reverse_is_a_Hash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> reverse_is_HashConf = new HashMap<String, String>();
		HashMap<String, String> reverse_is_a_HashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> disjHash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> disjHash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> disjHashConf = new HashMap<String, String>();
		HashMap<String, String> disjHashConf2 = new HashMap<String, String>();


		int eq = 0, eq1 = 0, eq2 = 0, sub1 = 0, sub2 = 0, disj = 0, newEq = 0, newSub1 = 0, newSub2 = 0, newDisj = 0;

		Enumeration<Cell> enume = alignment.getElements();
		while (enume.hasMoreElements()) {
			Cell cell = enume.nextElement();

			String entity1 = cell.getObject1AsURI().toString();
			String entity2 = cell.getObject2AsURI().toString();

			redundantEntities.add(entity1);
			redundantEntities.add(entity2);

			String relation = cell.getRelation().getRelation();

			if(relation.equals("=") || relation.equals("?")){
				eq++;
				if(relation.equals("=")){
					eq1++;
				}else{
					eq2++;
				}
				filterCellsHavingSameSources_Hard(equivHash, equivHashConf, cell, entity1, entity2);			
			}
			else {
				if(relation.equals("<")){
					sub1++;
					filterCellsHavingSameSources_Hard(is_a_Hash, is_a_HashConf, cell, entity1, entity2);			
				}
				else{
					if(relation.equals(">")){
						sub2++;
						filterCellsHavingSameSources_Hard(reverse_is_a_Hash, reverse_is_HashConf, cell, entity1, entity2);
					}
					else{
						if(relation.equals("%")){
							disj++;
							filterCellsHavingSameSources_Hard(disjHash, disjHashConf, cell, entity1, entity2);
						}
					}
				}
			}

		}

		filterCellsHavingSameTargets_Hard(equivHash, equivHash2, equivHashConf, equivHashConf2);
		filterCellsHavingSameTargets_Hard(is_a_Hash, is_a_Hash2, is_a_HashConf, is_a_HashConf2);
		filterCellsHavingSameTargets_Hard(reverse_is_a_Hash, reverse_is_a_Hash2, reverse_is_HashConf, reverse_is_a_HashConf2);
		filterCellsHavingSameTargets_Hard(disjHash, disjHash2, disjHashConf, disjHashConf2);

		int effectiveNbCells = createBridgingAxiomsFromFilteredAlignment(datafactory, axioms, alignmentFile, classes, objectProps, dataProps,
				individuals, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);

		getRedundantEntities(redundantEntities, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2);

		return effectiveNbCells;
	}
	
	public static void filterCellsHavingSameSources_Soft(HashMap<String, HashSet<String>> equivHash, HashMap<String, String> equivHashConf,
			Cell cell, String entity1, String entity2) {

		if ((!equivHash.containsKey(entity1)) || (cell.getStrength() > Double.valueOf(equivHashConf.get(entity1)))){
			HashSet<String> set = new HashSet<String>();
			set.add(entity2);
			equivHash.put(entity1, set);
			equivHashConf.put(entity1, String.valueOf(cell.getStrength()));
		}
		else {
			if (cell.getStrength() == Double.valueOf(equivHashConf.get(entity1))){
				HashSet<String> set = equivHash.get(entity1);
				set.add(entity2);
				equivHash.put(entity1, set);
			}			
		}
	}

	public static void filterCellsHavingSameSources_Hard(HashMap<String, HashSet<String>> equivHash, HashMap<String, String> equivHashConf,
			Cell cell, String entity1, String entity2) {

		if ((!equivHash.containsKey(entity1)) || (cell.getStrength() > Double.valueOf(equivHashConf.get(entity1)))){
			HashSet<String> set = new HashSet<String>();
			set.add(entity2);
			equivHash.put(entity1, set);
			equivHashConf.put(entity1, String.valueOf(cell.getStrength()));
		}
	}
	
	public static void filterCellsHavingSameTargets_Soft(HashMap<String, HashSet<String>> equivHash, HashMap<String, HashSet<String>> equivHash2,
			HashMap<String, String> equivHashConf, HashMap<String, String> equivHashConf2) {

		for (Entry<String, HashSet<String>> entry : equivHash.entrySet()) {
			String b = entry.getKey();

			for(String a : entry.getValue()){

				if ((!equivHash2.containsKey(a)) || (Double.valueOf(equivHashConf.get(b)) > Double.valueOf(equivHashConf2.get(a)))){
					HashSet<String> set = new HashSet<String>();
					set.add(b);
					equivHash2.put(a, set);
					equivHashConf2.put(a, equivHashConf.get(b));
				} else {
					if(Double.valueOf(equivHashConf.get(b)).equals(Double.valueOf(equivHashConf2.get(a)))){
						HashSet<String> set = equivHash2.get(a);
						set.add(b);
						equivHash2.put(a, set);
					}
				}
			}
		}
	}

	public static void filterCellsHavingSameTargets_Hard(HashMap<String, HashSet<String>> equivHash, HashMap<String, HashSet<String>> equivHash2,
			HashMap<String, String> equivHashConf, HashMap<String, String> equivHashConf2) {

		for (Entry<String, HashSet<String>> entry : equivHash.entrySet()) {
			String b = entry.getKey();

			for(String a : entry.getValue()){

				if ((!equivHash2.containsKey(a)) || (Double.valueOf(equivHashConf.get(b)) > Double.valueOf(equivHashConf2.get(a)))){
					HashSet<String> set = new HashSet<String>();
					set.add(b);
					equivHash2.put(a, set);
					equivHashConf2.put(a, equivHashConf.get(b));
				}
			}
		}
	}
	
	public static int createBridgingAxiomsFromFilteredAlignment(OWLDataFactory datafactory, Set<OWLAxiom> axioms, String alignmentFile,
			HashSet<String> classes, HashSet<String> objectProps, HashSet<String> dataProps, HashSet<String> individuals,
			HashMap<String, HashSet<String>> equivHash2, HashMap<String, HashSet<String>> is_a_Hash2,
			HashMap<String, HashSet<String>> reverse_is_a_Hash2, HashMap<String, HashSet<String>> disjHash2, int eq,
			int eq1, int eq2, int sub1, int sub2, int disj, int newEq, int newSub1, int newSub2, int newDisj) {


		for (Entry<String, HashSet<String>> entry : equivHash2.entrySet()) {
			for(String a : entry.getValue()){
				newEq++;
				createEquivalentBridgingAxioms(a, entry.getKey(), classes, objectProps, dataProps, individuals, axioms, datafactory);
			}
		}

		for (Entry<String, HashSet<String>> entry : is_a_Hash2.entrySet()) {
			for(String a : entry.getValue()){
				newSub1++;
				createSubsumptionBridgingAxioms(a, entry.getKey(), classes, objectProps, dataProps, individuals, axioms, datafactory);
			}
		}

		for (Entry<String, HashSet<String>> entry : reverse_is_a_Hash2.entrySet()) {
			for(String a : entry.getValue()){
				newSub2++;
				createSubsumptionBridgingAxioms(entry.getKey(), a, classes, objectProps, dataProps, individuals, axioms, datafactory);
			}
		}

		for (Entry<String, HashSet<String>> entry : disjHash2.entrySet()) {
			for(String a : entry.getValue()){
				newDisj++;
				createDisjointnessBridgingAxioms(a, entry.getKey(), classes, objectProps, dataProps, individuals, axioms, datafactory);
			}
		}

		displayCellsInformationOfFilteredAlignment(alignmentFile, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);

		return  (newEq + newSub1 + newSub2 + newDisj);
	}

	public static void displayCellsInformationOfFilteredAlignment(String alignmentFile, int eq, int eq1, int eq2,
			int sub1, int sub2, int disj, int newEq, int newSub1, int newSub2, int newDisj) {

		System.out.println("   --> " + eq + " equivalences");
		System.out.println("       * " + eq1 + " equivalences (=)");
		System.out.println("       * " + eq2 + " equivalences (?)");
		System.out.println("   --> " + sub1 + " subsumptions (<)");
		System.out.println("   --> " + sub2 + " subsumptions (>)");
		System.out.println("   --> " + disj + " disjunctions (%)\n");


		System.out.println(" ==> After filtering, we will only use : " + (newEq + newSub1 + newSub2 + newDisj) + " cells");

		System.out.println("   --> There are " + newEq + " equivalences (= and ?)");
		System.out.println("   --> There are " + newSub1 + " subsumptions (<) ");
		System.out.println("   --> There are " + newSub2 + " subsumptions (>) ");
		System.out.println("   --> There are " + newDisj + " disjunctions (%) ");

		System.out.println(" ==> The creation of \"Bridging\" axioms from the alignment \"" + alignmentFile + "\" is done\n\n");
	}

	private static void getRedundantEntities(HashSet<String> redundantEntities,
			HashMap<String, HashSet<String>> equivHash2, HashMap<String, HashSet<String>> is_a_Hash2,
			HashMap<String, HashSet<String>> reverse_is_a_Hash2, HashMap<String, HashSet<String>> disjHash2) {

		for (Entry<String, HashSet<String>> entry : equivHash2.entrySet()) {
			if(redundantEntities.contains(entry.getKey())) {
				redundantEntities.remove(entry.getKey());
			}
			for(String st : entry.getValue()) {
				if(redundantEntities.contains(st)) {
					redundantEntities.remove(st);
				}
			}
		}
		for (Entry<String, HashSet<String>> entry : is_a_Hash2.entrySet()) {
			if(redundantEntities.contains(entry.getKey())) {
				redundantEntities.remove(entry.getKey());
			}
			for(String st : entry.getValue()) {
				if(redundantEntities.contains(st)) {
					redundantEntities.remove(st);
				}
			}
		}
		for (Entry<String, HashSet<String>> entry : reverse_is_a_Hash2.entrySet()) {
			if(redundantEntities.contains(entry.getKey())) {
				redundantEntities.remove(entry.getKey());
			}
			for(String st : entry.getValue()) {
				if(redundantEntities.contains(st)) {
					redundantEntities.remove(st);
				}
			}
		}
		for (Entry<String, HashSet<String>> entry : disjHash2.entrySet()) {
			if(redundantEntities.contains(entry.getKey())) {
				redundantEntities.remove(entry.getKey());
			}
			for(String st : entry.getValue()) {
				if(redundantEntities.contains(st)) {
					redundantEntities.remove(st);
				}
			}
		}
	}
	
	public static int createBridgingAxiomsUsingRepairedAlignments(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objectProps,
			HashSet<String> dataProps, HashSet<String> individuals, HashSet<String> redundantEntities) throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold);  // al.cut( "hard", threshold );

		int eq = 0, eq1 = 0, eq2 = 0, sub1 = 0, sub2 = 0, disj = 0;

		Enumeration<Cell> enume = alignment.getElements();
		while (enume.hasMoreElements()) {
			Cell cell = enume.nextElement();

			String entity1 = cell.getObject1AsURI().toString();
			String entity2 = cell.getObject2AsURI().toString();

			String relation = cell.getRelation().getRelation();

			if(relation.equals("=")){
				eq++;
				eq1++;	
				createEquivalentBridgingAxioms(entity1, entity2, classes, objectProps, dataProps, individuals, axioms, datafactory);	
			}
			else{
				if(relation.equals("?")){
					eq++;
					eq2++;
					redundantEntities.add(entity1);
					redundantEntities.add(entity2);
				}
				else{
					if(relation.equals("<")){
						sub1++;
						createSubsumptionBridgingAxioms(entity1, entity2, classes, objectProps, dataProps, individuals, axioms, datafactory);
					}
					else {
						if(relation.equals(">")){
							sub2++;
							createSubsumptionBridgingAxioms(entity2, entity1, classes, objectProps, dataProps, individuals, axioms, datafactory);
						}
						else{
							if(relation.equals("%")){
								disj++;
								createDisjointnessBridgingAxioms(entity1, entity2, classes, objectProps, dataProps, individuals, axioms, datafactory);
							}
						}
					}	
				}
			}
		}

		System.out.println("  --> There are " + eq + " equivalences :");
		System.out.println("     --> " + eq1 + " equivalences (=) that we will use");
		System.out.println("     --> " + eq2 + " unknown equivalences (?) that we won't use");

		System.out.println("  --> There are " + sub1 + " subsumptions (<)");
		System.out.println("  --> There are " + sub2 + " subsumptions (>)");
		System.out.println("  --> There are " + disj + " disjunctions (%)");

		System.out.println("==> The creation of \"Bridging\" axioms from the alignment \"" + alignmentFile + "\" is done\n\n");

		int effectiveNbCells = eq + sub1 + sub2 + disj;
		return effectiveNbCells;
	}

	
	public static int createBridgingAxiomsUsingRepairedFilteredAlignments_SoftVersion(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objectProps,
			HashSet<String> dataProps, HashSet<String> individuals, HashSet<String> redundantEntities)
					throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold); // al.cut( "hard", threshold );

		HashMap<String, HashSet<String>> equivHash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> equivHash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> equivHashConf = new HashMap<String, String>();
		HashMap<String, String> equivHashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> is_a_Hash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> is_a_Hash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> is_a_HashConf = new HashMap<String, String>();
		HashMap<String, String> is_a_HashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> reverse_is_a_Hash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> reverse_is_a_Hash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> reverse_is_HashConf = new HashMap<String, String>();
		HashMap<String, String> reverse_is_a_HashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> disjHash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> disjHash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> disjHashConf = new HashMap<String, String>();
		HashMap<String, String> disjHashConf2 = new HashMap<String, String>();


		int eq = 0, eq1 = 0, eq2 = 0, sub1 = 0, sub2 = 0, disj = 0, newEq = 0, newSub1 = 0, newSub2 = 0, newDisj = 0;

		Enumeration<Cell> enume = alignment.getElements();
		while (enume.hasMoreElements()) {
			Cell cell = enume.nextElement();

			String entity1 = cell.getObject1AsURI().toString();
			String entity2 = cell.getObject2AsURI().toString();

			redundantEntities.add(entity1);
			redundantEntities.add(entity2);

			String relation = cell.getRelation().getRelation();

			if(relation.equals("=")){
				eq++;
				eq1++;
				filterCellsHavingSameSources_Soft(equivHash, equivHashConf, cell, entity1, entity2);			
			}
			else {
				if(relation.equals("?")){
					eq++;
					eq2++;
				}
				else {
					if(relation.equals("<")){
						sub1++;
						filterCellsHavingSameSources_Soft(is_a_Hash, is_a_HashConf, cell, entity1, entity2);			
					}
					else{
						if(relation.equals(">")){
							sub2++;
							filterCellsHavingSameSources_Soft(reverse_is_a_Hash, reverse_is_HashConf, cell, entity1, entity2);
						}
						else{
							if(relation.equals("%")){
								disj++;
								filterCellsHavingSameSources_Soft(disjHash, disjHashConf, cell, entity1, entity2);
							}
						}
					}
				}
			}
		}

		filterCellsHavingSameTargets_Soft(equivHash, equivHash2, equivHashConf, equivHashConf2);
		filterCellsHavingSameTargets_Soft(is_a_Hash, is_a_Hash2, is_a_HashConf, is_a_HashConf2);
		filterCellsHavingSameTargets_Soft(reverse_is_a_Hash, reverse_is_a_Hash2, reverse_is_HashConf, reverse_is_a_HashConf2);
		filterCellsHavingSameTargets_Soft(disjHash, disjHash2, disjHashConf, disjHashConf2);

		int effectiveNbCells = createBridgingAxiomsFromRepairedFilteredAlignment(datafactory, axioms, alignmentFile, classes, objectProps, dataProps,
				individuals, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);

		getRedundantEntities(redundantEntities, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2);

		return effectiveNbCells;
	}
	
	public static int createBridgingAxiomsUsingRepairedFilteredAlignments_HardVersion(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objectProps,
			HashSet<String> dataProps, HashSet<String> individuals, HashSet<String> redundantEntities)
					throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold); // al.cut( "hard", threshold );

		HashMap<String, HashSet<String>> equivHash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> equivHash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> equivHashConf = new HashMap<String, String>();
		HashMap<String, String> equivHashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> is_a_Hash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> is_a_Hash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> is_a_HashConf = new HashMap<String, String>();
		HashMap<String, String> is_a_HashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> reverse_is_a_Hash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> reverse_is_a_Hash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> reverse_is_HashConf = new HashMap<String, String>();
		HashMap<String, String> reverse_is_a_HashConf2 = new HashMap<String, String>();

		HashMap<String, HashSet<String>> disjHash = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> disjHash2 = new HashMap<String, HashSet<String>>();
		HashMap<String, String> disjHashConf = new HashMap<String, String>();
		HashMap<String, String> disjHashConf2 = new HashMap<String, String>();


		int eq = 0, eq1 = 0, eq2 = 0, sub1 = 0, sub2 = 0, disj = 0, newEq = 0, newSub1 = 0, newSub2 = 0, newDisj = 0;

		Enumeration<Cell> enume = alignment.getElements();
		while (enume.hasMoreElements()) {
			Cell cell = enume.nextElement();

			String entity1 = cell.getObject1AsURI().toString();
			String entity2 = cell.getObject2AsURI().toString();

			redundantEntities.add(entity1);
			redundantEntities.add(entity2);

			String relation = cell.getRelation().getRelation();

			if(relation.equals("=")){
				eq++;
				eq1++;
				filterCellsHavingSameSources_Hard(equivHash, equivHashConf, cell, entity1, entity2);			
			}
			else {
				if(relation.equals("?")){
					eq++;
					eq2++;
				}
				else {
					if(relation.equals("<")){
						sub1++;
						filterCellsHavingSameSources_Hard(is_a_Hash, is_a_HashConf, cell, entity1, entity2);			
					}
					else{
						if(relation.equals(">")){
							sub2++;
							filterCellsHavingSameSources_Hard(reverse_is_a_Hash, reverse_is_HashConf, cell, entity1, entity2);
						}
						else{
							if(relation.equals("%")){
								disj++;
								filterCellsHavingSameSources_Hard(disjHash, disjHashConf, cell, entity1, entity2);
							}
						}
					}
				}
			}
		}

		filterCellsHavingSameTargets_Hard(equivHash, equivHash2, equivHashConf, equivHashConf2);
		filterCellsHavingSameTargets_Hard(is_a_Hash, is_a_Hash2, is_a_HashConf, is_a_HashConf2);
		filterCellsHavingSameTargets_Hard(reverse_is_a_Hash, reverse_is_a_Hash2, reverse_is_HashConf, reverse_is_a_HashConf2);
		filterCellsHavingSameTargets_Hard(disjHash, disjHash2, disjHashConf, disjHashConf2);

		int effectiveNbCells = createBridgingAxiomsFromRepairedFilteredAlignment(datafactory, axioms, alignmentFile, classes, objectProps, dataProps,
				individuals, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);

		getRedundantEntities(redundantEntities, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2);

		return effectiveNbCells;
	}

	
	public static int createBridgingAxiomsFromRepairedFilteredAlignment(OWLDataFactory datafactory, Set<OWLAxiom> axioms, String alignmentFile,
			HashSet<String> classes, HashSet<String> objectProps, HashSet<String> dataProps, HashSet<String> individuals,
			HashMap<String, HashSet<String>> equivHash2, HashMap<String, HashSet<String>> is_a_Hash2,
			HashMap<String, HashSet<String>> reverse_is_a_Hash2, HashMap<String, HashSet<String>> disjHash2, int eq,
			int eq1, int eq2, int sub1, int sub2, int disj, int newEq, int newSub1, int newSub2, int newDisj) {


		for (Entry<String, HashSet<String>> entry : equivHash2.entrySet()) {
			for(String a : entry.getValue()){
				newEq++;
				createEquivalentBridgingAxioms(a, entry.getKey(), classes, objectProps, dataProps, individuals, axioms, datafactory);
			}
		}

		for (Entry<String, HashSet<String>> entry : is_a_Hash2.entrySet()) {
			for(String a : entry.getValue()){
				newSub1++;
				createSubsumptionBridgingAxioms(a, entry.getKey(), classes, objectProps, dataProps, individuals, axioms, datafactory);
			}
		}

		for (Entry<String, HashSet<String>> entry : reverse_is_a_Hash2.entrySet()) {
			for(String a : entry.getValue()){
				newSub2++;
				createSubsumptionBridgingAxioms(entry.getKey(), a, classes, objectProps, dataProps, individuals, axioms, datafactory);
			}
		}

		for (Entry<String, HashSet<String>> entry : disjHash2.entrySet()) {
			for(String a : entry.getValue()){
				newDisj++;
				createDisjointnessBridgingAxioms(a, entry.getKey(), classes, objectProps, dataProps, individuals, axioms, datafactory);
			}
		}

		displayCellsInformationOfRepairedFilteredAlignment(alignmentFile, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);

		return  (newEq + newSub1 + newSub2 + newDisj);
	}

	public static void displayCellsInformationOfRepairedFilteredAlignment(String alignmentFile, int eq, int eq1, int eq2,
			int sub1, int sub2, int disj, int newEq, int newSub1, int newSub2, int newDisj) {

		System.out.println("   --> " + eq + " equivalences");
		System.out.println("       * " + eq1 + " equivalences (=)");
		System.out.println("       * " + eq2 + " equivalences (?)");
		System.out.println("   --> " + sub1 + " subsumptions (<)");
		System.out.println("   --> " + sub2 + " subsumptions (>)");
		System.out.println("   --> " + disj + " disjunctions (%)\n");


		System.out.println(" ==> After filtering, we will only use : " + (newEq + newSub1 + newSub2 + newDisj) + " cells");

		System.out.println("   --> There are " + newEq + " equivalences (=)");
		System.out.println("   --> There are " + newSub1 + " subsumptions (<) ");
		System.out.println("   --> There are " + newSub2 + " subsumptions (>) ");
		System.out.println("   --> There are " + newDisj + " disjunctions (%) ");

		System.out.println(" ==> The creation of \"Bridging\" axioms from the alignment \"" + alignmentFile + "\" is done\n\n");
	}
	
	public static void checkNumberOfEntitiesOfIntegratedOntology(OWLOntology integratedOntology, int predictedNumberOfClasses, int predictedNumberOfObjectProps, int predictedNumberOfDataProps, int predictedNumberOfIndividuals, int predictedNumberOfAxioms, HashSet<String> redundantEntities, int nbOfBridgingAxioms) {
		System.out.println("");

		int nbClasses = 0;
		for (OWLClass cls : integratedOntology.getClassesInSignature()){
			if(!cls.isOWLThing()){
				//System.out.println(nbClasses + "/. " + cls.getIRI().getFragment());
				nbClasses++;
			}
		}
		System.out.println("\n--> There are " + nbClasses + " classes in the integrated ontology");
		System.out.println("    ==> The predicted number is " + predictedNumberOfClasses);


		int nbObjProps = 0;
		for (OWLObjectProperty prop : integratedOntology.getObjectPropertiesInSignature()){
			//System.out.println(nbObjProps + "/. " + prop.getIRI().getFragment());
			nbObjProps++;
		}
		System.out.println("--> There are " + nbObjProps + " object properties in the integrated ontology");
		System.out.println("    ==> The predicted number is " + predictedNumberOfObjectProps);


		int  nbDataProperties = 0;
		for(OWLDataProperty prop : integratedOntology.getDataPropertiesInSignature()){
			//System.out.println(nbDataProperties + "/. " + prop);
			nbDataProperties++;
		}
		System.out.println("--> There are " + nbDataProperties + " data properties in the integrated ontology");
		System.out.println("    ==> The predicted number is " + predictedNumberOfDataProps);


		int nbIndividuals = 0;
		for (OWLNamedIndividual inst : integratedOntology.getIndividualsInSignature()){
			//System.out.println(nbIndividuals + "/. " + inst);
			nbIndividuals++;
		}
		System.out.println("--> There are " + nbIndividuals + " individuals in the integrated ontology");
		System.out.println("    ==> The predicted number is " + predictedNumberOfIndividuals);


		System.out.println("--> There are " + integratedOntology.getLogicalAxiomCount() + " axioms in the integrated ontology");
		System.out.println("    ==> The predicted number is " + (predictedNumberOfAxioms + nbOfBridgingAxioms) + " : ("+ predictedNumberOfAxioms + " + " + nbOfBridgingAxioms+ ")");

		System.out.println("--> There are " + redundantEntities.size() + " redundant classes (that are not linked)");
	}


}
