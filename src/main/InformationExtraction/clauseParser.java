package main.cleartk;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
//import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.cleartk.classifier.Feature;
import org.cleartk.classifier.feature.extractor.simple.TypePathExtractor;
import org.cleartk.ne.type.NamedEntity;
import org.cleartk.ne.type.NamedEntityMention;
import org.cleartk.srl.type.Predicate;
import org.cleartk.srl.type.SemanticArgument;
import org.cleartk.syntax.constituent.type.TreebankNode;
import org.cleartk.syntax.dependency.clear.ClearParserUtil;
import org.cleartk.syntax.dependency.type.DependencyNode;
import org.cleartk.syntax.dependency.type.DependencyRelation;
import org.cleartk.syntax.dependency.type.TopDependencyNode;
import org.cleartk.token.type.Sentence;
import org.cleartk.token.type.Token;
import org.cleartk.util.UIMAUtil;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;
import org.uimafit.descriptor.TypeCapability;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.ConfigurationParameterFactory;
import org.uimafit.factory.TypeSystemDescriptionFactory;
import org.uimafit.util.JCasUtil;

import clear.dep.DepNode;
import clear.dep.DepTree;
import clear.dep.srl.SRLHead;
import clear.parse.AbstractSRLParser;
import clear.reader.AbstractReader;
//import cleartk.ClearParserSRL.Span;
import edu.stanford.nlp.dcoref.CorefChain;
//import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.BeginIndexAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.EndIndexAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


@TypeCapability(inputs = {
	    "org.cleartk.token.type.Token:pos",
	    "org.cleartk.token.type.Token:lemma",
	    "org.cleartk.syntax.dependency.type.DependencyNode" })
public class clauseParser extends JCasAnnotator_ImplBase  {
	
	@Deprecated
	  public static TypeSystemDescription TYPE_SYSTEM_DESCRIPTION = TypeSystemDescriptionFactory.createTypeSystemDescription("org.cleartk.TypeSystem");

	  // TODO: change this
	  //public static final String DEFAULT_MODEL_FILE_NAME = "en_srl_ontonotes.jar";

	  public static final String PARAM_PARSER_MODEL_FILE_NAME = ConfigurationParameterFactory.createConfigurationParameterName(
			  clauseParser.class,
	      "parserModelFileName");
	  
	 	  @ConfigurationParameter//(
	     // description = "This parameter provides the file name of the semantic role labeler model required by the factory method provided by ClearParserUtil.")
	  private String parserModelFileName = "test.jar";

	  private AbstractSRLParser parser;
	  private StanfordCoreNLP processor;

	  public static AnalysisEngineDescription getDescription() throws ResourceInitializationException 
	  {
	    return AnalysisEngineFactory.createPrimitiveDescription(
	        clauseParser.class,
	        TYPE_SYSTEM_DESCRIPTION);
	  }
	  
	  public static AnalysisEngineDescription getDescription(String modelFileName)
		      throws ResourceInitializationException {
		    return AnalysisEngineFactory.createPrimitiveDescription(
		    		clauseParser.class,
		        TYPE_SYSTEM_DESCRIPTION,
		        PARAM_PARSER_MODEL_FILE_NAME,
		        modelFileName);
		    }
	  
	  @Override
	  public void initialize(UimaContext context) throws ResourceInitializationException 
	  {
	    super.initialize(context);
	    
	       
	    Properties properties = new Properties();
	    properties.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

	    processor = new StanfordCoreNLP(properties);
	    
	    try {
	    	
	    	 /*URL parserModelURL = this.parserModelFileName == null
	          ? ClearParserSRL.class.getResource(DEFAULT_MODEL_FILE_NAME): new File(this.parserModelFileName).toURI().toURL();
	     System.out.println(parserModelURL);*/
	    //URL parserModelURL = new File(this.parserModelFileName).toURI().toURL();
	    	parser = ClearParserUtil.createSRLParser(new File(this.parserModelFileName).toURI().toURL().openStream());
        } 
	    catch (MalformedURLException e) {
	      System.out.println(e);
	      throw new ResourceInitializationException(e);
	     
	    } catch (IOException e) {
	      System.out.println(e);
	      throw new ResourceInitializationException(e);
	    
	    }
	    //this.processor = new StanfordCoreNLP();
	    
	  }
	  
	  @Override
	  public void process(JCas jCas) throws AnalysisEngineProcessException 
	  {  
		int sentNumber = 0;		  	  
	    for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) 
	    {
	    	
	      List<Token> tokens = JCasUtil.selectCovered(jCas, Token.class, sentence);
	      	      
	      DepTree tree = new DepTree();

	      // Build map between CAS dependency node and id for later creation of
	      // ClearParser dependency node/tree
	      Map<DependencyNode, Integer> depNodeToID = new HashMap<DependencyNode, Integer>();
	      int nodeId = 1;
	      for (DependencyNode depNode : JCasUtil.selectCovered(jCas, DependencyNode.class, sentence)) 
	      {
	        if (depNode instanceof TopDependencyNode) {
	          depNodeToID.put(depNode, 0);
	        } else {
	          depNodeToID.put(depNode, nodeId);
	          nodeId++;
	        }
	      }

	      // Initialize Token / Sentence info for the ClearParser Semantic Role Labeler
	      for (int i = 0; i < tokens.size(); i++) 
	      {
	        Token token = tokens.get(i);
	        //System.out.println("token" + token);

	        // Determine HeadId
	        DepNode node = new DepNode();
	        DependencyNode casDepNode = JCasUtil.selectCovered(jCas, DependencyNode.class, token).get(0);
	        DependencyRelation headRelation = (DependencyRelation) casDepNode.getHeadRelations().get(0);
	        //System.out.println("headRelation"+ headRelation.getRelation());
	        
	        DependencyNode head = headRelation.getHead();
	        int headId = depNodeToID.get(head);
	        
	        //System.out.println("headID"+ headId);

	        // Populate Dependency Node / Tree information
	        node.id = i + 1;
	        node.form = token.getCoveredText();
	        node.pos = token.getPos();
	        node.lemma = token.getLemma();
	        node.setHead(headId, headRelation.getRelation(), 0);
	        tree.add(node);
	      }
	      System.out.println("tree \n"+ tree);
	      int i = 0;
	      /*for (i = 1;i <= 10; i++){
	    	  DepNode node = tree.get(i);
	    	  
	    	  
	    	  if (node.form.equals("animals")){
	    		  int node_head = node.getHeadId();
	    		  System.out.println(node.pos);
	    		  System.out.println(node.deprel);
	    		  System.out.println(node.headId);
	    		  System.out.println("Head of this node" + node_head);
	    		  System.out.println(tree.get(node_head).form + "\t" + tree.get(node_head).pos );
	    	  }
	    	  else {
	    		  continue;
	    	  }
	    	  //System.out.println(node.form);
	    	  //System.out.println("Tree node" + i + " = "+ tree.get(i));
	      }*/
	      
	      	sentNumber++; 
	      	String sentId = "s" + sentNumber ;
			clauses.findClauses(tree, sentId);
		
	     // TypePathExtractor extractor = new TypePathExtractor(Token.class,"dep/head/pos");
	     
	    }
	     
	  }

}













