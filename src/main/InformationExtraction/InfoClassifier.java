package main.cleartk;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.classifier.CleartkSequenceAnnotator;
import org.cleartk.classifier.Feature;
import org.cleartk.classifier.Instances;
import org.cleartk.classifier.chunking.BIOChunking;
import org.cleartk.classifier.feature.extractor.CleartkExtractor;
import org.cleartk.classifier.feature.extractor.CleartkExtractor.Following;
import org.cleartk.classifier.feature.extractor.CleartkExtractor.Preceding;
import org.cleartk.classifier.feature.extractor.simple.CharacterCategoryPatternExtractor;
import org.cleartk.classifier.feature.extractor.simple.CharacterCategoryPatternExtractor.PatternType;
import org.cleartk.classifier.feature.extractor.simple.CombinedExtractor;
import org.cleartk.classifier.feature.extractor.simple.CoveredTextExtractor;
import org.cleartk.classifier.feature.extractor.simple.SimpleFeatureExtractor;
import org.cleartk.classifier.feature.extractor.simple.TypePathExtractor;
import org.cleartk.ne.type.NamedEntityMention;
import org.cleartk.token.type.Sentence;
import org.cleartk.token.type.Token;
import org.uimafit.util.JCasUtil;

import featExt.Clause;

import main.featExt.src.InfoAnnotation;

public class InfoClassifier extends CleartkSequenceAnnotator<String> {
	
	  private SimpleFeatureExtractor extractor;

	  @Override
	  public void initialize(UimaContext context) throws ResourceInitializationException {
	    super.initialize(context);

	    // the token feature extractor: text, char pattern (uppercase, digits, etc.), and part-of-speech
	    this.extractor = new CombinedExtractor(
	        //new CoveredTextExtractor(),
	
	        new TypePathExtractor(InfoAnnotation.class, "Root"),
	    	new TypePathExtractor(InfoAnnotation.class, "Subject"),
	    	new TypePathExtractor(InfoAnnotation.class, "Object"),
	    	new TypePathExtractor(InfoAnnotation.class, "SubjectPOS"),
	    	new TypePathExtractor(InfoAnnotation.class, "ObjectPOS")	    	
	    	
	    	);
	    
	  }

	  @Override
	  public void process(JCas jCas) throws AnalysisEngineProcessException {
		  
		  
		  List<List<Feature>> featureLists = new ArrayList<List<Feature>>();
		  List<String> clauseOutcomes = new ArrayList<String>();
		  List<Clause> clauses = new ArrayList<Clause>();
		  
	        for (Clause clause : JCasUtil.select(jCas, Clause.class)) {
	        	clauses.add(clause);
	        	List<Feature> features = new ArrayList<Feature>();
	        	
	          // extract features for each clause
	        	features.addAll(this.extractor.extract(jCas, clause));
	        	featureLists.add(features);
	          
	          // during training, convert InfoAnnotation in the CAS into expected classifier outcomes
	          if (this.isTraining()) {
	            // extract the human annotated InfoAnnotation labels
	        	  clauseOutcomes.add(clause.getLabel());
	          }	       
	        }
	        
	     // for training, write instances to the data write
	      if (this.isTraining()) {
	    	  this.dataWriter.write(Instances.toInstances(clauseOutcomes, featureLists));
	      }
	      // for classification, set the token part of speech tags from the classifier outcomes.
	      else {
	        List<String> outcomes = this.classifier.classify(featureLists);
	        for (int i = 0; i < clauses.size(); i++){
	        	clauses.get(i).setLabel(outcomes.get(i));
	        	
	        }  
	      }      
	      
	      
	 }
}
