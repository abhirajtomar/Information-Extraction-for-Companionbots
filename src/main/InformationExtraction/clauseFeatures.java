package main.cleartk;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.syntax.dependency.clear.ClearParser;
import org.cleartk.syntax.dependency.type.DependencyNode;
import org.cleartk.syntax.dependency.type.DependencyRelation;
import org.cleartk.syntax.dependency.type.TopDependencyNode;
import org.cleartk.syntax.opennlp.PosTaggerAnnotator;
import org.cleartk.syntax.opennlp.SentenceAnnotator;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.token.type.Sentence;
import org.cleartk.token.type.Token;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.factory.AggregateBuilder;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.pipeline.SimplePipeline;
import org.uimafit.util.JCasUtil;

import clear.dep.DepNode;
import clear.dep.DepTree;

import main.featExt.src.InfoAnnotation;

public class clauseFeatures {
	
	public int extraction(InfoAnnotation clauseInfo) throws UIMAException, IOException {
		
		// Create reader	
					String in_file = "inp.txt";
				    CollectionReader reader = UriCollectionReader.getCollectionReaderFromFiles(Arrays.asList(new File(in_file)));
					//String reader = "I love apples and bananas.";
				    // Create aggregate engine
				    AggregateBuilder builder = new AggregateBuilder();
				    builder.add(UriToDocumentTextAnnotator.getDescription());
				    builder.add(SentenceAnnotator.getDescription());
				    builder.add(TokenAnnotator.getDescription());
				    builder.add(PosTaggerAnnotator.getDescription());
				    builder.add(LemmaAnnotator.getDescription());
				    builder.add(ClearParser.getDescription());
				   /* 
				    //builder.add(StanfordCoreNLPAnnotator.getClassifierDescription(StanfordCoreNLPAnnotator.DEFAULT_MODEL));
				    builder.add(AnalysisEngineFactory.createPrimitiveDescription(
				    		clauseParser.class,
				        "",
				        ""));
				*/
				   
				 // Run pipeline
				    SimplePipeline.runPipeline(reader, builder.createAggregateDescription());
				   
				    
				    return 0;			    
	}
	
	
	

}
