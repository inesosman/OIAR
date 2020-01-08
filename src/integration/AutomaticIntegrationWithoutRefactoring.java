package integration;

import java.io.File;

import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.Cell;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLOntologyMerger;

import fr.inrialpes.exmo.align.parser.AlignmentParser;

public class AutomaticIntegrationWithoutRefactoring {

	public static void main(String[] args) throws OWLException, UnsupportedEncodingException, AlignmentException {

		System.setProperty("jdk.xml.entityExpansionLimit", "0");

		ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
		long chrono1 = bean.getCurrentThreadCpuTime();

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory datafactory = manager.getOWLDataFactory();

		/*******************************************************************************************************************************************************/

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
		ontologiesFiles.add("Input/FMA2.owl");   /** FMA3 is the whole FMA */
		ontologiesFiles.add("Input/NCI1.owl");   /** NCI3 is the whole NCI */
		ontologiesFiles.add("Input/SNOMED1.owl");   /** SNOMED3 is the extended SNOMED */

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

		/*********************************************************************************************************************************************************/

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

		/****************************************************************************************************************************************/

		/** parsing the entities of the input ontologies */

		HashSet<String> classes = new HashSet<String>();
		int totalPredictedClasses = 0;
		totalPredictedClasses = getOntologiesClasses(totalPredictedClasses, classes, datafactory, ontologiesSet);


		HashSet<String> objectProps = new HashSet<String>();
		int totalPredictedObjectProperties = 0;
		totalPredictedObjectProperties = getOntologiesObjectProperties(totalPredictedObjectProperties, objectProps, datafactory, ontologiesSet);


		HashSet<String> dataProps = new HashSet<String>();
		int totalPredictedDataProperties = 0;
		totalPredictedDataProperties = getOntologiesDataProperties(totalPredictedDataProperties, dataProps, datafactory, ontologiesSet);


		HashSet<String> individuals = new HashSet<String>();
		int totalPredictedIndividuals = 0;
		totalPredictedIndividuals = getOntologiesIndividuals(totalPredictedIndividuals, individuals, datafactory, ontologiesSet);

		/******************************************************************************************************************************************************/		

		int totalPredictedAxioms = 0;
		totalPredictedAxioms = getNumberOfLogicalAxioms(totalPredictedAxioms, ontologiesSet);
		Set<OWLAxiom> integratedOntologyAxioms = new HashSet<OWLAxiom>();

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
			//nbOfBridgingAxioms = nbOfBridgingAxioms + createBridgingAxiomsUsingRepairedFilteredAlignmentsSoftVersion(datafactory, integratedOntologyAxioms, alignment, threshold, classes, objectProps, dataProps, individuals, redundantEntities);
			//nbOfBridgingAxioms = nbOfBridgingAxioms + createBridgingAxiomsUsingRepairedFilteredAlignmentsHardVersion(datafactory, integratedOntologyAxioms, alignment, threshold, classes, objectProps, dataProps, individuals, redundantEntities);
		}

		System.out.println("\n ===> There are in total : "+ nbOfBridgingAxioms+ " correspondences");

		/*************************************************************************************************************************************************************************************************/

		String integratedOntologyIRI = "http://integration";
		IRI newIRI = IRI.create(integratedOntologyIRI);

		/** aggregating/concatenating/composing the loaded input ontologies */
		OWLOntologyMerger merger = new OWLOntologyMerger(manager);
		OWLOntology integratedOntology = merger.createMergedOntology(manager, newIRI);

		manager.addAxioms(integratedOntology, integratedOntologyAxioms);
		/** you can uncomment this method to remove the disjointClasses axioms (which are the cause of future unsatisfiabilities) from the integrated ontology */
		//removeAllDisjointAxioms(manager, integratedOntology);
		
		manager.saveOntology(integratedOntology, new RDFXMLOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact.owl")));		
		//manager.saveOntology(mergedOntology, new OWLXMLOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact.owl")));
		//manager.saveOntology(mergedOntology, new OWLFunctionalSyntaxOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact.owl")));	
		//manager.addOntologyStorer(new DLSyntaxOntologyStorer());
		//manager.saveOntology(mergedOntology, new DLSyntaxOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact.owl")));
		//manager.addOntologyStorer(new OWLTutorialSyntaxOntologyStorer());
		//manager.saveOntology(mergedOntology, new OWLTutorialSyntaxOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact.owl")));
		//manager.saveOntology(mergedOntology, new TurtleOntologyFormat(), IRI.create(new File("Output/IntegratedOntology_WithoutRefact.owl")));

		System.out.println("\n\n --> The integrated ontology is created\n");

		/******************************************************************************************************************************************************/		

		long chrono2 = bean.getCurrentThreadCpuTime();
		long runtime = chrono2-chrono1; 

		System.out.println("\n ****** Running the program took : " + runtime / 1E6+ " ms"); // CPU runtime in milliseconds
		System.out.println(" ****** Running the program took : " + runtime / 1E9+ " s"); // CPU runtime in seconds

		/******************************************************************************************************************************************************/		

		checkNumberOfEntitiesOfIntegratedOntology(integratedOntology, totalPredictedClasses, totalPredictedObjectProperties, totalPredictedDataProperties, totalPredictedIndividuals, totalPredictedAxioms, redundantEntities, nbOfBridgingAxioms);
		OntologyConsistencyAndCoherence.verify(integratedOntology);

		System.clearProperty("jdk.xml.entityExpansionLimit");
	}


	public static int getOntologiesClasses(int x, HashSet<String> classes, OWLDataFactory df, ArrayList<OWLOntology> ontologiesSet) throws OWLException{

		for(OWLOntology ontology_n : ontologiesSet){
			x = x + getOntologyClasses(classes, ontology_n, df);
		}
		System.out.println("===> The predicted total number of classes of these ontologies is "+ x + "\n\n");

		return x;
	}

	public static int getOntologyClasses(HashSet<String> classes, OWLOntology ont, OWLDataFactory df)
			throws OWLException {

		int x = 0;
		for (OWLClass cls : ont.getClassesInSignature()) {

			if (!cls.isOWLThing()) {
				x++;
				classes.add(cls.getIRI().toString());
			}
		}
		
		System.out.println("- The parsing of classes of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is done");
		System.out.println("---> The number of classes of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is " + x + "\n");

		return x;
	}
	
	public static int getOntologiesObjectProperties(int x, HashSet<String> objProps, OWLDataFactory df,
			ArrayList<OWLOntology> ontologiesSet) throws OWLException {

		System.out.println("");

		for (OWLOntology ontology_n : ontologiesSet) {
			x = x + getOntologyObjectProperties(objProps,ontology_n, df);
		}
		System.out.println("===> The predicted total number of object properties of these ontologies is "+ x +"\n\n");

		return x;
	}
	
	public static int getOntologyObjectProperties(HashSet<String> objProps, OWLOntology ont, OWLDataFactory df)
			throws OWLException {

		int x = 0;
		for (OWLObjectProperty objProp : ont.getObjectPropertiesInSignature()) {
			if (!objProp.isOWLTopObjectProperty()) {
				x++;
				objProps.add(objProp.getIRI().toString());
			}
		}
		System.out.println("- The parsing of object properties of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is done");
		System.out.println("---> The number of object properties of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is "+ x +"\n");

		return x;
	}
	
	public static int getOntologiesDataProperties(int x, HashSet<String> dataProps, OWLDataFactory df,
			ArrayList<OWLOntology> ontologiesSet) throws OWLException {

		System.out.println("");

		for (OWLOntology ontology_n : ontologiesSet) {
			x = x + getOntologyDataProperties(dataProps, ontology_n, df);
		}
		System.out.println("===> The predicted total number of datatype properties of these ontologies is "+ x +"\n\n");

		return x;
	}
	
	public static int getOntologyDataProperties(HashSet<String> dataProps, OWLOntology ont, OWLDataFactory df)
			throws OWLException {

		int x = 0;
		for (OWLDataProperty dataProp : ont.getDataPropertiesInSignature()){
			if (!dataProp.isOWLTopDataProperty()) {
				x++;
				dataProps.add(dataProp.getIRI().toString());
			}
		}
		System.out.println("- The parsing of the data properties of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is done");
		System.out.println("---> The number of data properties of \""
				+ ont.getOntologyID().getOntologyIRI().getFragment() + "\" is "+ x +"\n");

		return x;
	}
	
	public static int getOntologiesIndividuals(int x, HashSet<String> instances, OWLDataFactory df,
			ArrayList<OWLOntology> ontologiesSet) {

		System.out.println("");

		for (OWLOntology ontology_n : ontologiesSet) {
			x = x + getOntologyIndividuals(instances, ontology_n, df);
		}
		System.out.println("===> The predicted total number of individuals of these ontologies is "+ x +"\n\n");

		return x;
	}
	
	public static int getOntologyIndividuals(HashSet<String> instances, OWLOntology ont, OWLDataFactory fac){

		int k = 0;
		for (OWLNamedIndividual ind : ont.getIndividualsInSignature()) {
			k++;
			instances.add(ind.getIRI().toString());
			///System.out.println(k + ". " + ind.getIRI().getFragment());
		}

		System.out.println("- The parsing of the individuals of \"" + ont.getOntologyID().getOntologyIRI().getFragment() + "\" is done");
		System.out.println("---> The number of individuals of \""+ ont.getOntologyID().getOntologyIRI().getFragment()  +"\" is " + k+"\n");

		return k;
	}
	
	public static int getNumberOfLogicalAxioms(int x, ArrayList<OWLOntology> ontologiesSet) throws OWLException{

		System.out.println("");

		for(OWLOntology ontology_N : ontologiesSet){
			int size = ontology_N.getLogicalAxiomCount();
			x = x + size;
			System.out.println("---> The number of axioms of \""+ ontology_N.getOntologyID().getOntologyIRI().getFragment()  +"\" is " + size);
		}	
		System.out.println("\n===> The predicted total number of axioms of these ontologies is "+ x +"\n\n");

		return x;
	}
	
	public static int createBridgingAxiomsUsingOriginalAlignments(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objProps,
			HashSet<String> dataProps, HashSet<String> instances) throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- There are " + alignment.nbCells() + " cells in \""+ alignmentFile+"\"");

		alignment.cut(threshold); // al.cut( "hard", threshold );

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
				createEquivalentBridgingAxioms(entity1, entity2, classes, objProps, dataProps, instances, datafactory, axioms);
			}
			else{
				if(relation.equals("<")){
					sub1++;
					createSubsumptionBridgingAxioms(entity1, entity2, classes, objProps, dataProps, instances, datafactory, axioms); 
				}
				else {
					if(relation.equals(">")){
						sub2++;
						createSubsumptionBridgingAxioms(entity2, entity1, classes, objProps, dataProps, instances, datafactory, axioms); 
					}
					else{
						if(relation.equals("%")){
							disj++;
							createDisjointnessBridgingAxioms(entity1, entity2, classes, objProps, dataProps, instances, datafactory, axioms);
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

		System.out.println("==> The creation of the \"Bridging\" axioms from the alignment \"" + alignmentFile + "\" is done\n\n");
	
		int effectiveNbCells = eq + sub1 + sub2 + disj;
		return effectiveNbCells;
	}
	
	public static void createEquivalentBridgingAxioms(String entity1, String entity2, HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps, HashSet<String> instances, OWLDataFactory datafactory, Set<OWLAxiom> axioms){

		if (classes.contains(entity1) && classes.contains(entity2)) {
			
			OWLClass clsA = datafactory.getOWLClass(IRI.create(entity1));
			OWLClass clsB = datafactory.getOWLClass(IRI.create(entity2));

			OWLEquivalentClassesAxiom axiom = datafactory.getOWLEquivalentClassesAxiom(clsA, clsB);
			axioms.add(axiom);
		} else {

			if (objProps.contains(entity1) && objProps.contains(entity2)) {

				OWLObjectProperty prop1 = datafactory.getOWLObjectProperty(IRI.create(entity1));
				OWLObjectProperty prop2 = datafactory.getOWLObjectProperty(IRI.create(entity2));

				OWLEquivalentObjectPropertiesAxiom eq = datafactory.getOWLEquivalentObjectPropertiesAxiom(prop1, prop2);
				axioms.add(eq);
			} else {

				if (dataProps.contains(entity1) && dataProps.contains(entity2)) {

					OWLDataProperty prop1 = datafactory.getOWLDataProperty(IRI.create(entity1));
					OWLDataProperty prop2 = datafactory.getOWLDataProperty(IRI.create(entity2));

					OWLEquivalentDataPropertiesAxiom ax = datafactory.getOWLEquivalentDataPropertiesAxiom(prop1, prop2);
					axioms.add(ax);
				} else {

					if (instances.contains(entity1) && instances.contains(entity2)) {

						OWLNamedIndividual indiv1 = datafactory.getOWLNamedIndividual(IRI.create(entity1));
						OWLNamedIndividual indiv2 = datafactory.getOWLNamedIndividual(IRI.create(entity2));

						OWLSameIndividualAxiom sameAx = datafactory.getOWLSameIndividualAxiom(indiv1, indiv2);
						axioms.add(sameAx);
					}
				}
			}
		}
	}

	public static void createSubsumptionBridgingAxioms(String entity1, String entity2, HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps, HashSet<String> instances, OWLDataFactory datafactory, Set<OWLAxiom> axioms){

		if (classes.contains(entity1) && classes.contains(entity2)) {

			OWLClass clsA = datafactory.getOWLClass(IRI.create(entity1));
			OWLClass clsB = datafactory.getOWLClass(IRI.create(entity2));

			OWLSubClassOfAxiom axiom = datafactory.getOWLSubClassOfAxiom(clsA, clsB);
			axioms.add(axiom);
		} else {

			if (objProps.contains(entity1) && objProps.contains(entity2)) {

				OWLObjectProperty prop1 = datafactory.getOWLObjectProperty(IRI.create(entity1));
				OWLObjectProperty prop2 = datafactory.getOWLObjectProperty(IRI.create(entity2));

				OWLSubObjectPropertyOfAxiom eq = datafactory.getOWLSubObjectPropertyOfAxiom(prop1, prop2);
				axioms.add(eq);
			} else {

				if (dataProps.contains(entity1) && dataProps.contains(entity2)) {

					OWLDataProperty prop1 = datafactory.getOWLDataProperty(IRI.create(entity1));
					OWLDataProperty prop2 = datafactory.getOWLDataProperty(IRI.create(entity2));

					OWLSubDataPropertyOfAxiom ax = datafactory.getOWLSubDataPropertyOfAxiom(prop1, prop2);
					axioms.add(ax);
				}
			}
		}
	}

	public static void createDisjointnessBridgingAxioms(String entity1, String entity2, HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps, HashSet<String> instances, OWLDataFactory datafactory, Set<OWLAxiom> axioms){

		if (classes.contains(entity1) && classes.contains(entity2)) {

			OWLClass clsA = datafactory.getOWLClass(IRI.create(entity1));
			OWLClass clsB = datafactory.getOWLClass(IRI.create(entity2));

			OWLDisjointClassesAxiom axiom = datafactory.getOWLDisjointClassesAxiom(clsA, clsB);
			axioms.add(axiom);
		} else {

			if (objProps.contains(entity1) && objProps.contains(entity2)) {

				OWLObjectProperty prop1 = datafactory.getOWLObjectProperty(IRI.create(entity1));
				OWLObjectProperty prop2 = datafactory.getOWLObjectProperty(IRI.create(entity2));

				OWLDisjointObjectPropertiesAxiom eq = datafactory.getOWLDisjointObjectPropertiesAxiom(prop1, prop2);
				axioms.add(eq);
			} else {

				if (dataProps.contains(entity1) && dataProps.contains(entity2)) {

					OWLDataProperty prop1 = datafactory.getOWLDataProperty(IRI.create(entity1));
					OWLDataProperty prop2 = datafactory.getOWLDataProperty(IRI.create(entity2));

					OWLDisjointDataPropertiesAxiom ax = datafactory.getOWLDisjointDataPropertiesAxiom(prop1, prop2);
					axioms.add(ax);
				} else {
					if (instances.contains(entity1) && instances.contains(entity2)) {

						OWLNamedIndividual indiv1 = datafactory.getOWLNamedIndividual(IRI.create(entity1));
						OWLNamedIndividual indiv2 = datafactory.getOWLNamedIndividual(IRI.create(entity2));

						OWLDifferentIndividualsAxiom sameAx = datafactory.getOWLDifferentIndividualsAxiom(indiv1, indiv2);
						axioms.add(sameAx);
					}
				}
			}
		}
	}
	
	public static int createBridgingAxiomsUsingFilteredAlignments_SoftVersion(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps,
			HashSet<String> instances, HashSet<String> redundantEntities) throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold); // alignment.cut( "hard", threshold );

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
						filterCellsHavingSameSources_Soft(reverse_is_a_Hash, reverse_is_HashConf, cell, entity1,
								entity2);			
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

		int effectiveNbCells = createBridgingAxiomsFromFilteredAlignment(datafactory, axioms, alignmentFile, classes, objProps, dataProps, instances, equivHash2,
				is_a_Hash2, reverse_is_a_Hash2, disjHash2, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);
		
		getRedundantEntities(redundantEntities, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2);
		
		return effectiveNbCells;
	}
	
	public static int createBridgingAxiomsUsingFilteredAlignments_HardVersion(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps,
			HashSet<String> instances, HashSet<String> redundantEntities) throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold); // alignment.cut( "hard", threshold );

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
						filterCellsHavingSameSources_Hard(reverse_is_a_Hash, reverse_is_HashConf, cell, entity1,
								entity2);			
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

		int effectiveNbCells = createBridgingAxiomsFromFilteredAlignment(datafactory, axioms, alignmentFile, classes, objProps, dataProps, instances, equivHash2,
				is_a_Hash2, reverse_is_a_Hash2, disjHash2, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);
		
		getRedundantEntities(redundantEntities, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2);
		
		return effectiveNbCells;
	}
	
	public static void filterCellsHavingSameSources_Soft(HashMap<String, HashSet<String>> equivHash,
			HashMap<String, String> equivHashConf, Cell cell, String entity1, String entity2) {

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
	
	public static void filterCellsHavingSameSources_Hard(HashMap<String, HashSet<String>> equivHash,
			HashMap<String, String> equivHashConf, Cell cell, String entity1, String entity2) {

		if ((!equivHash.containsKey(entity1)) || (cell.getStrength() > Double.valueOf(equivHashConf.get(entity1)))){
			HashSet<String> set = new HashSet<String>();
			set.add(entity2);
			equivHash.put(entity1, set);
			equivHashConf.put(entity1, String.valueOf(cell.getStrength()));
		}
	}
	
	public static void filterCellsHavingSameTargets_Soft(HashMap<String, HashSet<String>> equivHash,
			HashMap<String, HashSet<String>> equivHash2, HashMap<String, String> equivHashConf, HashMap<String, String> equivHashConf2) {

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
	
	public static void filterCellsHavingSameTargets_Hard(HashMap<String, HashSet<String>> equivHash,
			HashMap<String, HashSet<String>> equivHash2, HashMap<String, String> equivHashConf, HashMap<String, String> equivHashConf2) {

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
	
	public static int createBridgingAxiomsFromFilteredAlignment(OWLDataFactory datafactory, Set<OWLAxiom> axioms, String fichier,
			HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps, HashSet<String> instances,
			HashMap<String, HashSet<String>> equivHash2, HashMap<String, HashSet<String>> is_a_Hash2,
			HashMap<String, HashSet<String>> reverse_is_a_Hash2, HashMap<String, HashSet<String>> disjHash2, int eq,
			int eq1, int eq2, int sub1, int sub2, int disj, int newEq, int newSub1, int newSub2, int newDisj) {


		for (Entry<String, HashSet<String>> entry : equivHash2.entrySet()) {
			for(String a : entry.getValue()){
				newEq++;
				createEquivalentBridgingAxioms(a, entry.getKey(), classes, objProps, dataProps, instances, datafactory, axioms); 
			}
		}

		for (Entry<String, HashSet<String>> entry : is_a_Hash2.entrySet()) {
			for(String a : entry.getValue()){
				newSub1++;
				createSubsumptionBridgingAxioms(a, entry.getKey(), classes, objProps, dataProps, instances, datafactory, axioms); 
			}
		}

		for (Entry<String, HashSet<String>> entry : reverse_is_a_Hash2.entrySet()) {
			for(String a : entry.getValue()){
				newSub2++;
				createSubsumptionBridgingAxioms(entry.getKey(), a, classes, objProps, dataProps, instances, datafactory, axioms);
			}
		}

		for (Entry<String, HashSet<String>> entry : disjHash2.entrySet()) {
			for(String a : entry.getValue()){
				newDisj++;
				createDisjointnessBridgingAxioms(a, entry.getKey(), classes, objProps, dataProps, instances, datafactory, axioms);
			}
		}

		displayCellsInformationOfFilteredAlignment(fichier, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);

		return (newEq + newSub1 + newSub2 + newDisj);
	}
	
	public static void displayCellsInformationOfFilteredAlignment(String alignmentFile, int eq, int eq1, int eq2, int sub1, int sub2, int disj,
			int newEq, int newSub1, int newSub2, int newDisj) {

		System.out.println("   --> " + eq + " equivalences :");
		System.out.println("       * " + eq1 + " equivalences (=)");
		System.out.println("       * " + eq2 + " equivalences (?)");
		System.out.println("   --> " + sub1 + " subsumptions <");
		System.out.println("   --> " + sub2 + " subsumptions >\n");
		System.out.println("   --> " + disj + " disjunctions %");

		System.out.println(" ==> After filtering, we will only use : " + (newEq + newSub1 + newSub2 + newDisj) + " cells");

		System.out.println("   --> There are " + newEq + " equivalences (= and ?)");
		System.out.println("   --> There are " + newSub1 + " subsumptions (<)");
		System.out.println("   --> There are " + newSub2 + " subsumptions (>)");
		System.out.println("   --> There are " + newDisj + " disjunctions (%)");

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
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objProps,
			HashSet<String> dataProps, HashSet<String> instances, HashSet<String> redundantEntities) throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- There are " + alignment.nbCells() + " cells in \""+ alignmentFile+"\"");

		alignment.cut(threshold); // al.cut( "hard", threshold );

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
				createEquivalentBridgingAxioms(entity1, entity2, classes, objProps, dataProps, instances, datafactory, axioms);
			}
			else {
				if(relation.equals("?")) {
					eq++;
					eq2++;
					redundantEntities.add(entity1);
					redundantEntities.add(entity2);
				}
				else{
					if(relation.equals("<")){
						sub1++;
						createSubsumptionBridgingAxioms(entity1, entity2, classes, objProps, dataProps, instances, datafactory, axioms); 
					}
					else {
						if(relation.equals(">")){
							sub2++;
							createSubsumptionBridgingAxioms(entity2, entity1, classes, objProps, dataProps, instances, datafactory, axioms); 
						}
						else{
							if(relation.equals("%")){
								disj++;
								createDisjointnessBridgingAxioms(entity1, entity2, classes, objProps, dataProps, instances, datafactory, axioms);
							}
						}
					}
				}
			}
		}

		System.out.println("  --> There are "+ eq + " equivalences :");
		System.out.println("     --> "+ eq1 + " equivalences (=) that we will use");
		System.out.println("     --> "+ eq2 + " unknown equivalences (?) that we won't use");

		System.out.println("  --> There are "+ sub1 + " subsumptions (<)");
		System.out.println("  --> There are "+ sub2 + " subsumptions (>)");
		System.out.println("  --> There are "+ disj + " disjunctions (%)");

		System.out.println("==> The creation of the \"Bridging\" axioms from the alignment \"" + alignmentFile + "\" is done\n\n");
	
		int effectiveNbCells = eq + sub1 + sub2 + disj;
		return effectiveNbCells;
	}

	public static int createBridgingAxiomsUsingRepairedFilteredAlignmentsSoftVersion(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps,
			HashSet<String> instances, HashSet<String> redundantEntities) throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold); // alignment.cut( "hard", threshold );

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
				if(relation.equals("?")) {
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

		int effectiveNbCells = createBridgingAxiomsFromRepairedFilteredAlignment(datafactory, axioms, alignmentFile, classes, objProps, dataProps, instances, equivHash2,
				is_a_Hash2, reverse_is_a_Hash2, disjHash2, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);
		
		getRedundantEntities(redundantEntities, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2);
		
		return effectiveNbCells;
	}

	public static int createBridgingAxiomsUsingRepairedFilteredAlignmentsHardVersion(OWLDataFactory datafactory, Set<OWLAxiom> axioms,
			String alignmentFile, double threshold, HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps,
			HashSet<String> instances, HashSet<String> redundantEntities) throws AlignmentException, UnsupportedEncodingException {

		AlignmentParser parser = new AlignmentParser(0);
		Alignment alignment = parser.parse(new File(alignmentFile).toURI());
		System.out.println("- The alignment " + alignmentFile + " has originally : " + alignment.nbCells() + " cells");

		alignment.cut(threshold); // alignment.cut( "hard", threshold );

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
				if(relation.equals("?")) {
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

		int effectiveNbCells = createBridgingAxiomsFromRepairedFilteredAlignment(datafactory, axioms, alignmentFile, classes, objProps, dataProps, instances, equivHash2,
				is_a_Hash2, reverse_is_a_Hash2, disjHash2, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);
		
		getRedundantEntities(redundantEntities, equivHash2, is_a_Hash2, reverse_is_a_Hash2, disjHash2);
		
		return effectiveNbCells;
	}
	
	public static int createBridgingAxiomsFromRepairedFilteredAlignment(OWLDataFactory datafactory, Set<OWLAxiom> axioms, String fichier,
			HashSet<String> classes, HashSet<String> objProps, HashSet<String> dataProps, HashSet<String> instances,
			HashMap<String, HashSet<String>> equivHash2, HashMap<String, HashSet<String>> is_a_Hash2,
			HashMap<String, HashSet<String>> reverse_is_a_Hash2, HashMap<String, HashSet<String>> disjHash2, int eq,
			int eq1, int eq2, int sub1, int sub2, int disj, int newEq, int newSub1, int newSub2, int newDisj) {


		for (Entry<String, HashSet<String>> entry : equivHash2.entrySet()) {
			for(String a : entry.getValue()){
				newEq++;
				createEquivalentBridgingAxioms(a, entry.getKey(), classes, objProps, dataProps, instances, datafactory, axioms); 
			}
		}

		for (Entry<String, HashSet<String>> entry : is_a_Hash2.entrySet()) {
			for(String a : entry.getValue()){
				newSub1++;
				createSubsumptionBridgingAxioms(a, entry.getKey(), classes, objProps, dataProps, instances, datafactory, axioms); 
			}
		}

		for (Entry<String, HashSet<String>> entry : reverse_is_a_Hash2.entrySet()) {
			for(String a : entry.getValue()){
				newSub2++;
				createSubsumptionBridgingAxioms(entry.getKey(), a, classes, objProps, dataProps, instances, datafactory, axioms);
			}
		}

		for (Entry<String, HashSet<String>> entry : disjHash2.entrySet()) {
			for(String a : entry.getValue()){
				newDisj++;
				createDisjointnessBridgingAxioms(a, entry.getKey(), classes, objProps, dataProps, instances, datafactory, axioms);
			}
		}

		displayCellsInformationOfRepairedFilteredAlignment(fichier, eq, eq1, eq2, sub1, sub2, disj, newEq, newSub1, newSub2, newDisj);

		return (newEq + newSub1 + newSub2 + newDisj);
	}
	
	public static void displayCellsInformationOfRepairedFilteredAlignment(String alignmentFile, int eq, int eq1, int eq2, int sub1, int sub2, int disj,
			int newEq, int newSub1, int newSub2, int newDisj) {

		System.out.println("   --> " + eq + " equivalences :");
		System.out.println("       * " + eq1 + " equivalences (=)");
		System.out.println("       * " + eq2 + " equivalences (?)");
		System.out.println("   --> " + sub1 + " subsumptions <");
		System.out.println("   --> " + sub2 + " subsumptions >\n");
		System.out.println("   --> " + disj + " disjunctions %");

		System.out.println(" ==> After filtering, we will only use : " + (newEq + newSub1 + newSub2 + newDisj) + " cells");

		System.out.println("   --> There are " + newEq + " equivalences (=)");
		System.out.println("   --> There are " + newSub1 + " subsumptions (<)");
		System.out.println("   --> There are " + newSub2 + " subsumptions (>)");
		System.out.println("   --> There are " + newDisj + " disjunctions (%)");

		System.out.println(" ==> The creation of \"Bridging\" axioms from the alignment \"" + alignmentFile + "\" is done\n\n");
	}
	
	private static void removeAllDisjointAxioms(OWLOntologyManager manager, OWLOntology integratedOntology) {
		
		Set<OWLDisjointClassesAxiom> DisjointAxiomsSet = integratedOntology.getAxioms(AxiomType.DISJOINT_CLASSES);
		manager.removeAxioms(integratedOntology, DisjointAxiomsSet);
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
			if(!prop.isOWLTopObjectProperty()){
				//System.out.println(nbObjProps + "/. " + prop.getIRI().getFragment());
				nbObjProps++;
			}
		}
		System.out.println("--> There are " + nbObjProps + " object properties in the integrated ontology");
		System.out.println("    ==> The predicted number is " + predictedNumberOfObjectProps);


		int nbDataProperties = 0;
		for(OWLDataProperty prop : integratedOntology.getDataPropertiesInSignature()){
			if(!prop.isOWLTopDataProperty()){
				//System.out.println(nbDataProperties + "/. " + prop);
				nbDataProperties++;
			}
		}
		System.out.println("--> There are " + nbDataProperties + " datatype properties in the integrated ontology");
		System.out.println("    ==> The predicted number is " + predictedNumberOfDataProps);


		int nbIndividuals = 0;
		for (OWLNamedIndividual inst : integratedOntology.getIndividualsInSignature()){
			//System.out.println(nbIndividuals + "/. " + inst);
			nbIndividuals++;
		}
		System.out.println("--> There are " + nbIndividuals + " individuals in the integrated ontology");
		System.out.println("    ==> The predicted number is " + predictedNumberOfIndividuals);


		System.out.println("--> There are " + integratedOntology.getLogicalAxiomCount() + " axioms in the integrated ontology");
		System.out.println("    ==> The predicted number is " + (predictedNumberOfAxioms + nbOfBridgingAxioms) + " : (" + predictedNumberOfAxioms + " + " + nbOfBridgingAxioms + ")");
	
	    System.out.println("--> There are " + redundantEntities.size() + " redundant classes (that are not linked)");
	}	
	
}
