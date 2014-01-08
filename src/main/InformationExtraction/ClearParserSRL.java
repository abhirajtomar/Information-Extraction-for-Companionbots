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
public class ClearParserSRL extends JCasAnnotator_ImplBase 
{	public  static String DocId;
	
	@Deprecated
	  public static TypeSystemDescription TYPE_SYSTEM_DESCRIPTION = TypeSystemDescriptionFactory.createTypeSystemDescription("org.cleartk.TypeSystem");

	  // TODO: change this
	  //public static final String DEFAULT_MODEL_FILE_NAME = "en_srl_ontonotes.jar";

	  public static final String PARAM_PARSER_MODEL_FILE_NAME = ConfigurationParameterFactory.createConfigurationParameterName(
	      ClearParserSRL.class,
	      "parserModelFileName");
	  
	 	  @ConfigurationParameter//(
	     // description = "This parameter provides the file name of the semantic role labeler model required by the factory method provided by ClearParserUtil.")
	  private String parserModelFileName = "test.jar";

	  private AbstractSRLParser parser;
	  private StanfordCoreNLP processor;

	  public static AnalysisEngineDescription getDescription() throws ResourceInitializationException 
	  {
	    return AnalysisEngineFactory.createPrimitiveDescription(
	        ClearParserSRL.class,
	        TYPE_SYSTEM_DESCRIPTION);
	  }

	  public static AnalysisEngineDescription getDescription(String modelFileName)
	      throws ResourceInitializationException {
	    return AnalysisEngineFactory.createPrimitiveDescription(
	        ClearParserSRL.class,
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
		//String DocId = "-1";
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
	      if(tree.get(1).form.equals("DocID"))	{
	    	   DocId = tree.get(2).form;
	    	   System.out.println("DOCID="+ DocId);
	    	  continue;
	      }
	      
	      //System.out.println("tree \n"+ tree);
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
	      	String sentId = "d" + DocId +"_s" + sentNumber ;
			clauses.findClauses(tree, sentId);
		
	     // TypePathExtractor extractor = new TypePathExtractor(Token.class,"dep/head/pos");
	      
	  
	      
	      /*tree.setPredicates(AbstractReader.LANG_EN);

	      // Run the SRL
	      parser.parse(tree);
	      

	      // Convert ClearParser SRL output to CAS types
	      extractSRLInfo(jCas, tokens, tree);
	      */
	    }
	      /*
	      
	      //stanford coref 
	      
	   // map from spans to named entity mentions
	      Map<Span, NamedEntityMention> spanMentionMap = new HashMap<Span, NamedEntityMention>();
	      for (NamedEntityMention mention : JCasUtil.select(jCas, NamedEntityMention.class)) {
	        spanMentionMap.put(new Span(mention.getBegin(), mention.getEnd()), mention);
	      }

	      // add mentions for all entities identified by the coreference system
	      
	      
	      List<NamedEntity> entities = new ArrayList<NamedEntity>();
	      List<List<Token>> sentenceTokens = new ArrayList<List<Token>>();
	      for (Sentence sentence1 : JCasUtil.select(jCas, Sentence.class)) {
	        sentenceTokens.add(JCasUtil.selectCovered(jCas, Token.class, sentence1));
	      }
	      Annotation document = processor.process(jCas.getDocumentText());
		  	      
	      Map<Integer, CorefChain> corefChains = document.get(CorefChainAnnotation.class);
	        	  
	      System.out.println(corefChains);
	            
	      for (CorefChain chain : corefChains.values()) 
	      {
	        List<NamedEntityMention> mentions = new ArrayList<NamedEntityMention>();
	        for (CorefMention corefMention : chain.getMentionsInTextualOrder()) 
	        {

	          // figure out the character span of the token
	          List<Token> tokens1 = sentenceTokens.get(corefMention.sentNum - 1);
	          int begin = tokens1.get(corefMention.startIndex - 1).getBegin();
	          int end = tokens1.get(corefMention.endIndex - 2).getEnd();

	          // use an existing named entity mention when possible; otherwise create a new one
	          NamedEntityMention mention = spanMentionMap.get(new Span(begin, end));
	          if (mention == null) {
	            mention = new NamedEntityMention(jCas, begin, end);
	            mention.addToIndexes();
	          }
	          mentions.add(mention);
	          
	        }

	        // create an entity for the mentions
	        Collections.sort(mentions, new Comparator<NamedEntityMention>() {
	          @Override
	          public int compare(NamedEntityMention m1, NamedEntityMention m2) {
	            return m1.getBegin() - m2.getBegin();
	          }
	        });

	        // create mentions and add them to entity
	        NamedEntity entity = new NamedEntity(jCas);
	        entity.setMentions(new FSArray(jCas, mentions.size()));
	        int index = 0;
	        for (NamedEntityMention mention : mentions) {
	          mention.setMentionedEntity(entity);
	          entity.setMentions(index, mention);
	          index += 1;
	        }
	        entities.add(entity);
	      }
	      for (NamedEntityMention mention : JCasUtil.select(jCas, NamedEntityMention.class)) {
	          if (mention.getMentionedEntity() == null) {
	            NamedEntity entity = new NamedEntity(jCas);
	            entity.setMentions(new FSArray(jCas, 1));
	            entity.setMentions(0, mention);
	            mention.setMentionedEntity(entity);
	            entity.getMentions();
	            entities.add(entity);
	          }
	        }
	      // sort entities by document order
	      Collections.sort(entities, new Comparator<NamedEntity>() 
	      {
	        @Override
	        public int compare(NamedEntity o1, NamedEntity o2) {
	          return getFirstBegin(o1) - getFirstBegin(o2);
	        }

	        private int getFirstBegin(NamedEntity entity) {
	          int min = Integer.MAX_VALUE;
	          for (NamedEntityMention mention : JCasUtil.select(
	              entity.getMentions(),
	              NamedEntityMention.class)) {
	            if (mention.getBegin() < min) {
	              min = mention.getBegin();
	            }
	          }
	          return min;
	        }
	      });

	      // add entities to document
	      for (NamedEntity entity : entities) {
	        entity.addToIndexes();
	        
	      }*/
	  }

	  /**
	   * Converts the output from the ClearParser Semantic Role Labeler to the ClearTK Predicate and
	   * SemanticArgument Types.
	   * 
	   * @param jCas
	   * @param tokens
	   *          - In order list of tokens
	   * @param tree
	   *          - DepdendencyTree output by ClearParser SRLPredict
	   */
	  private void extractSRLInfo(JCas jCas, List<Token> tokens, DepTree tree) {
	    Map<Integer, Predicate> headIdToPredicate = new HashMap<Integer, Predicate>();
	    Map<Predicate, List<SemanticArgument>> predicateArguments = new HashMap<Predicate, List<SemanticArgument>>();

	    // Start at node 1, since node 0 is considered the head of the sentence
	    for (int i = 1; i < tree.size(); i++) {
	      // Every ClearParser parserNode will contain an srlInfo field.
	      DepNode parserNode = tree.get(i);
	      Token token = tokens.get(i - 1);
	      if (parserNode.srlInfo == null) {
	        continue;
	      }

	      if (parserNode.srlInfo.isPredicate()) {
	        int headId = i;
	        if (!headIdToPredicate.containsKey(headId)) {
	          // We have not encountered this predicate yet, so create it
	          Predicate pred = this.createPredicate(jCas, parserNode.srlInfo.rolesetId, token);
	          headIdToPredicate.put(headId, pred);
	        }
	      } else {
	        for (SRLHead head : parserNode.srlInfo.heads) {
	          Predicate predicate;

	          // Determine which predicate this argument belongs to
	          if (!headIdToPredicate.containsKey(head.headId)) {
	            // The predicate hasn't been encountered, so create it
	            Token headToken = tokens.get(head.headId - 1);
	            predicate = this.createPredicate(jCas, parserNode.srlInfo.rolesetId, headToken);
	            headIdToPredicate.put(head.headId, predicate);
	          } else {
	            predicate = headIdToPredicate.get(head.headId);
	          }

	          // Append this argument to the predicate's list of arguments
	          if (!predicateArguments.containsKey(predicate)) {
	            predicateArguments.put(predicate, new ArrayList<SemanticArgument>());
	          }
	          List<SemanticArgument> argumentList = predicateArguments.get(predicate);

	          // Create the semantic argument and store for later link creation
	          SemanticArgument argument = createArgument(jCas, head, token);
	          argumentList.add(argument);
	          }
	      }
	    }

	    // Store Arguments in Predicate
	    for (Map.Entry<Predicate, List<SemanticArgument>> entry : predicateArguments.entrySet()) {
	      Predicate predicate = entry.getKey();
	      List<SemanticArgument> arguments = entry.getValue();
	      predicate.setArguments(UIMAUtil.toFSArray(jCas, arguments));
	      }
	  }

	  private Predicate createPredicate(JCas jCas, String rolesetId, Token token) {
	    Predicate pred = new Predicate(jCas, token.getBegin(), token.getEnd());
	    pred.setFrameSet(rolesetId);
	    pred.addToIndexes();
	    System.out.println("Predicate: Begin-"+ pred.getBegin() + "  End-" + pred.getEnd());
	    return pred;
	  }

	  private SemanticArgument createArgument(JCas jCas, SRLHead head, Token token) {
	    SemanticArgument argument = new SemanticArgument(jCas, token.getBegin(), token.getEnd());
	    argument.setLabel(head.label);
	    argument.addToIndexes();
	    System.out.println("argument: Begin-"+ argument.getBegin() + "  End-" + argument.getEnd() + "-" + argument.getLabel());
	    return argument;
	  }
	  
	  private FSArray addTreebankNodeChildrenToIndexes(
		      TreebankNode parent,
		      JCas jCas,
		      List<CoreLabel> tokenAnns,
		      Tree tree) {
		    Tree[] childTrees = tree.children();

		    // collect all children (except leaves, which are just the words - POS tags are pre-terminals in
		    // a Stanford tree)
		    List<TreebankNode> childNodes = new ArrayList<TreebankNode>();
		    for (Tree child : childTrees) {
		      if (!child.isLeaf()) {

		        // set node attributes and add children (mutual recursion)
		        TreebankNode node = new TreebankNode(jCas);
		        node.setParent(parent);
		        this.addTreebankNodeToIndexes(node, jCas, child, tokenAnns);
		        childNodes.add(node);
		      }
		    }

		    // convert the child list into an FSArray
		    FSArray childNodeArray = new FSArray(jCas, childNodes.size());
		    for (int i = 0; i < childNodes.size(); ++i) {
		      childNodeArray.set(i, childNodes.get(i));
		    }
		    return childNodeArray;
		  }

		  private void addTreebankNodeToIndexes(
		      TreebankNode node,
		      JCas jCas,
		      Tree tree,
		      List<CoreLabel> tokenAnns) {
		    // figure out begin and end character offsets
		    CoreMap label = (CoreMap) tree.label();
		    CoreMap beginToken = tokenAnns.get(label.get(BeginIndexAnnotation.class));
		    CoreMap endToken = tokenAnns.get(label.get(EndIndexAnnotation.class) - 1);
		    int nodeBegin = beginToken.get(CharacterOffsetBeginAnnotation.class);
		    int nodeEnd = endToken.get(CharacterOffsetEndAnnotation.class);

		    // set span, node type, children (mutual recursion), and add it to the JCas
		    node.setBegin(nodeBegin);
		    node.setEnd(nodeEnd);
		    node.setNodeType(tree.value());
		    node.setChildren(this.addTreebankNodeChildrenToIndexes(node, jCas, tokenAnns, tree));
		    node.setLeaf(node.getChildren().size() == 0);
		    node.addToIndexes();
		  }

		  private static class Span {
		    public int begin;

		    public int end;

		    public Span(int begin, int end) {
		      this.begin = begin;
		      this.end = end;
		    }

		    public boolean equals(Object object) {
		      if (object instanceof Span) {
		        Span that = (Span) object;
		        return this.begin == that.begin && this.end == that.end;
		      } else {
		        return false;
		      }
		    }

		    public int hashCode() {
		      return Arrays.hashCode(new int[] { this.begin, this.end });
		    }
		  }

	
	
}