package main.cleartk;



import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.apache.uima.collection.CollectionReader;
import org.cleartk.syntax.dependency.clear.ClearParser;
import org.cleartk.syntax.opennlp.PosTaggerAnnotator;
import org.cleartk.syntax.opennlp.SentenceAnnotator;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.factory.AggregateBuilder;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.pipeline.SimplePipeline;


public class featureExtraction {
	
		@SuppressWarnings("deprecation")
		public static void main(String[] args) throws Exception {
		
	    // Create reader	
		

	    // Create aggregate engine
	    AggregateBuilder builder = new AggregateBuilder();
	    builder.add(UriToDocumentTextAnnotator.getDescription());
	    builder.add(SentenceAnnotator.getDescription());
	    builder.add(TokenAnnotator.getDescription());
	    builder.add(PosTaggerAnnotator.getDescription());
	    builder.add(LemmaAnnotator.getDescription());
	    builder.add(ClearParser.getDescription());
	    
	    //builder.add(StanfordCoreNLPAnnotator.getClassifierDescription(StanfordCoreNLPAnnotator.DEFAULT_MODEL));
	    builder.add(AnalysisEngineFactory.createPrimitiveDescription(
	        ClearParserSRL.class,
	        "",
	        ""));
	    String myDirectoryPath = "/usr/local/home/administrator/My Files/BnB for MTurk/Text/One Third Text/";
	   // File dir = new File(myDirectoryPath);
	   
	    for (int i= 4; i<=4;i++){
	    //String in_file = "inp.txt";
	    	//String filename = child.getName().split(".")[0];
	    	for (int j =1;j<=3;j++){
	    		//System.out.println("File to be processed-" + FilenameUtils.getBaseName(child.getName()));
	    		CollectionReader reader = UriCollectionReader.getCollectionReaderFromFiles(Arrays.asList(new File(myDirectoryPath +i+"_"+j+".txt" )));
	    			// Run pipeline
	    		SimplePipeline.runPipeline(reader, builder.createAggregateDescription());
	    	}
	    }
	}	

}
