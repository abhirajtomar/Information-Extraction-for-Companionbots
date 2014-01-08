package main.cleartk;

//package org.cleartk.examples.chunking;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.cleartk.classifier.CleartkSequenceAnnotator;
import org.cleartk.classifier.jar.DefaultSequenceDataWriterFactory;
import org.cleartk.classifier.jar.DirectoryDataWriterFactory;
import org.cleartk.classifier.jar.Train;
import org.cleartk.classifier.mallet.MalletCRFStringOutcomeDataWriter;
import org.cleartk.examples.chunking.NamedEntityChunker;
import org.cleartk.examples.chunking.TrainNamedEntityChunker.MASCTextFileFilter;
import org.cleartk.examples.chunking.TrainNamedEntityChunker.Options;
import org.cleartk.examples.chunking.util.MASCGoldAnnotator;
import org.cleartk.syntax.opennlp.PosTaggerAnnotator;
import org.cleartk.util.Options_ImplBase;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.kohsuke.args4j.Option;
import org.uimafit.factory.AggregateBuilder;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.TypeSystemDescriptionFactory;
import org.uimafit.pipeline.SimplePipeline;

public class Train_InfoAnnotation {
	
	public static class Options extends Options_ImplBase {
	    @Option(name = "--train-dir", usage = "The directory containing Manually Annotated files")
	    public File trainDirectory = new File(
	        "/usr/local/home/administrator/My Files/JAVA/workspace/featExt/resources/data/written");

	    @Option(name = "--model-dir", usage = "The directory where the model should be written")
	    public File modelDirectory = new File(
	        "/usr/local/home/administrator/My Files/JAVA/workspace/featExt/src/target");
	  }
	/*
	public static void main(String[] args) throws Exception {
		System.out.println("Just Inside main() of TrainInfoAnnotation ");
	    Options options = new Options();
	    options.parseOptions(args);
	    
	    TypeSystemDescription tsd =
	    		 TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(
	    		
	    		  "/usr/local/home/administrator/My Files/JAVA/workspace/featExt/ClauseTypeSystemDescriptor.xml");
	    
	 // Instantiate component
	                    AnalysisEngine ae =
	     AnalysisEngineFactory.createPrimitive(InfoAnnotator.class, tsd);
	    
	                    //JCas jcas = ae.newJCas();
	                  
	    
	                    // Test that injection works
	                    //ae.process(jcas);

	    // a reader that loads the URIs of the training files
	    CollectionReaderDescription reader = UriCollectionReader.getDescriptionFromDirectory(
	        options.trainDirectory,
	        InfoAnnotationXMLFileFilter.class,
	        null);
	    System.out.println("After initialising reader ");
	    // assemble the training pipeline
	    AggregateBuilder aggregate = new AggregateBuilder();
	    System.out.println("After initialising aggregate ");
	    // an annotator that loads the text from the training file URIs
	   // aggregate.add(UriToDocumentTextAnnotator.getDescription());

	    // an annotator that loads manual information type annotations (and tokens)
	    aggregate.add(InfoAnnotator.getDescription());
	    System.out.println("After Adding InfoAnnotator to aggregate ");
	    // an annotator that adds part-of-speech tags (so we can use them for features)
	   // aggregate.add(PosTaggerAnnotator.getDescription());

	    // our NamedEntityChunker annotator, configured to write Mallet CRF training data
	    aggregate.add(AnalysisEngineFactory.createPrimitiveDescription(
	        InfoClassifier.class,
	        CleartkSequenceAnnotator.PARAM_IS_TRAINING,
	        true,
	        DirectoryDataWriterFactory.PARAM_OUTPUT_DIRECTORY,
	        options.modelDirectory,
	        DefaultSequenceDataWriterFactory.PARAM_DATA_WRITER_CLASS_NAME,
	        MalletCRFStringOutcomeDataWriter.class));
	    

	    // run the pipeline over the training corpus
	    SimplePipeline.runPipeline(reader, aggregate.createAggregateDescription());

	    // train a Mallet CRF model on the training data
	    Train.main(options.modelDirectory);
	  }
	
	*/
	public static class InfoAnnotationXMLFileFilter implements IOFileFilter {
	    public boolean accept(File file) {
	      return file.getPath().endsWith(".xml");
	    }

	    public boolean accept(File dir, String name) {
	      return name.endsWith(".xml");
	    }
	  }
	

}
