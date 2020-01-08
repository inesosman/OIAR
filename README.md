# **OIAR** (**O**ntology **I**ntegration with **A**lignment **R**euse)
OIAR is an approach for creating a new ontology resulting from a simple merge of multiple ontologies using pairwise alignments between them. It automatically customizes and merges multiple large ontologies in a holistic way and in very short times.

Let's merge the three ontologies of the **Large Biomedical Ontologies** OAEI track. We will merge them using reference alignments between all possible ontology pairs. This will ensure a complete semantic interoperability between them. All tests were performed with a confidence threshold equal to 0.0, so we kept all correspondences / cells of the input alignments.


## Input Alignments (FMA-NCI + SNOMED-NCI + FMA-SNOMED)

Here are three equivalence correspondences extracted from the three "Large Biomedical Ontologies" OAEI reference alignments. They match the __Abdominal_lymph_node__ class (from [FMA](https://github.com/inesosman/OIAR/blob/master/Input/FMA2.owl)) to other classes (from [NCI](https://github.com/inesosman/OIAR/blob/master/Input/NCI2.owl) and [SNOMED](https://github.com/inesosman/OIAR/blob/master/Input/SNOMED1.owl)).

### FMA-NCI

The first correspondence (in [FMA-NCI](https://github.com/inesosman/OIAR/blob/master/Input/FMA2NCI.rdf)) matches the __Abdominal_lymph_node__ class (from FMA) to the __Intra-abdominal_Lymph_Node__ class (from NCI):

![FMA-NCI alignment](https://github.com/inesosman/OIAR/blob/master/Figures/cell111.png)

### FMA-SNOMED

The second correspondence (in [FMA-SNOMED](https://github.com/inesosman/OIAR/blob/master/Input/FMA2SNOMED.rdf)) matches the __Abdominal_lymph_node__ class (from FMA) to the __Abdominal_lymph_node_group__ class (from SNOMED):

![FMA-SNOMED alignment](https://github.com/inesosman/OIAR/blob/master/Figures/cell222.png)

The third correspondence (in [FMA-SNOMED](https://github.com/inesosman/OIAR/blob/master/Input/FMA2SNOMED.rdf)) matches the __Abdominal_lymph_node__ class (from FMA) to the __Abdominal_lymph_node_structure__ class (from SNOMED):

![FMA-SNOMED alignment](https://github.com/inesosman/OIAR/blob/master/Figures/cell333.png)



## Input Ontologies (FMA + NCI + SNOMED)

### * FMA

Here is the definition/description of __Abdominal_lymph_node__ class in its original ontology ([FMA](https://github.com/inesosman/OIAR/blob/master/Input/FMA2.owl) (Ont1)) :

![Abdominal_lymph_node](https://github.com/inesosman/OIAR/blob/master/Figures/22.png)


## Output Merged Ontology

The above-mentioned correspondences will lead to addition of three equivalence axioms between the __Abdominal_lymph_node__ class (from FMA (Ont1)) and its three matched classes __Intra-abdominal_Lymph_Node__ (from NCI (Ont2)), __Abdominal_lymph_node_group__ (from SNOMED (Ont3)), and __Abdominal_lymph_node_structure__ (from SNOMED (Ont3)). In other words, these correspondences will lead to three bridging axioms added to the description of the class __Abdominal_lymph_node__. For this example, we chose to give our future merged ontology the following IRI : "http://integration". 


The following figures show the __Abdominal_lymph_node__ class in our output ontology that resulted from the merging of the three _LargeBio_ ontologies. Axioms framed in red are the added bridging axioms.


You can view and download our merged ontologies from the [Output folder](https://github.com/inesosman/OIAR/blob/master/Output).


### * Non-Refactored Version

![MergedClass](https://github.com/inesosman/OIAR/blob/master/Figures/4.png)

For the non-customized version, axioms are identical to the original ones, and bridging axioms are added to them.
You can download and view this [non-refactored merged ontology](https://github.com/inesosman/OIAR/blob/master/Output/IntegratedOntology_WithoutRefact.owl) from the Output folder.

### * Refactored Version

![RefactoredMergedClass](https://github.com/inesosman/OIAR/blob/master/Figures/3.png)

For the customized version, axioms are like the original ones, except that the IRIs of the all mentioned entities are customized and bridge axioms are added to them.
You can download and view this [refactored merged ontology](https://github.com/inesosman/OIAR/blob/master/Output/IntegratedOntology_WithoutRefact.owl) from the Output folder.

## Conclusion

* Our final ontology is complete, in the sense that it retains all entities, axioms and hierarchies from the input ontologies.
* Running OIAR for merging the *Large Biomedical Ontologies* does not exceed **one minute**.
