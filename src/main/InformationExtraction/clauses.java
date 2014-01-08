package main.cleartk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

public class clauses {
	
	public static void findClauses(DepTree tree, String sentId) {
		
		//System.out.println("tree from clause class \n"+ tree);
		//System.out.println("Tree Size = " + tree.size());
		
		ArrayList<Integer> verbIndices = new ArrayList<Integer>();
		verbIndices = findVerbs(tree);
	//	System.out.println("Verb Locations : " + verbIndices);
		
		int numV = verbIndices.size();
	//	System.out.println(numV);
		
		ArrayList<ArrayList<Integer>> clauses = new ArrayList<ArrayList<Integer>>();
		
		for (int j = 0; j < numV; j++){
			int flag = 0;
			int verbId = verbIndices.get(j);
			for ( ArrayList<Integer> clause : clauses){
				if (verbId < Collections.max(clause)){
					flag = 1;
					break;
				}
			}
			
			if (flag == 1){
				continue;
			}
			
			ArrayList<Integer> clauseSet = new ArrayList<Integer>();
			
					
			clauseSet.add(verbId);
	//		System.out.println("Here is the initial Clause Set for this iteration :" + clauseSet);			
			DepNode verb = tree.get(verbId);
			
			if ( !(verb.deprel.equals("ROOT") || verb.deprel.equals("CONJ") || verb.deprel.equals("COORD"))){
				clauseSet.add(verb.headId);
			}
			
			ArrayList<Integer> added = new ArrayList<Integer>();
			
			added = clauseSet;
			
			while(added.size() != 0){
				int temp;
				ArrayList<Integer> nextAdditions = new ArrayList<Integer>();
				ArrayList<Integer> tempAdditions = new ArrayList<Integer>();
				
				
				for( int i = 0;i < added.size(); i++){
					temp = added.get(i);
					tempAdditions = relatedTokens(tree, temp);
					
					for( int tokenId : tempAdditions){
						nextAdditions.add(tokenId);
					}
				}
		//		System.out.println("Here are the next additions :" + nextAdditions + "for : " + added);
				for (int i = 0; i < nextAdditions.size(); i++){
					
					int tokenId = nextAdditions.get(i);
					if (clauseSet.contains(tokenId)){
						
						nextAdditions.remove(i);
						
					}
					else{
						clauseSet.add(tokenId);
					}
					
				}
				
				added = nextAdditions;
				
			}
			Collections.sort(clauseSet);
			clauses.add(clauseSet);
								
			
		}
//		System.out.println("Here are the clauses :" + clauses );
		
		StringBuilder parentSentence = new StringBuilder();
		for (int i = 1; i < tree.size(); i++ ){
			if( i == 1){
				parentSentence.append(tree.get(i).form);
			}
			else{
				parentSentence.append(" " + tree.get(i).form);
			}
						
		}
//		System.out.println("\nThe parent sentence: "+parentSentence.toString());
		
		ArrayList<String> clauseStrings = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> clauseEnds = new ArrayList<ArrayList<Integer>>();
		
		for(ArrayList<Integer> clause : clauses){
			ArrayList<Integer> tempEnds =  new ArrayList<Integer>();
			int max = Collections.max(clause);
			int min = Collections.min(clause);
			tempEnds.add(min);
			tempEnds.add(max);clauseEnds.add(tempEnds);
			
			StringBuilder clauseBuilder = new StringBuilder();
			for (int i = min; i <= max; i++){
				if( i == min){
					clauseBuilder.append(tree.get(i).form);
				}
				else{
					clauseBuilder.append(" " + tree.get(i).form);
				}				
				
			}
			
			clauseStrings.add(clauseBuilder.toString());
						
		}
		
	//	System.out.println("The Clauses are: \n");
		for(String clause : clauseStrings){
	//		System.out.println(clause);
		}
	//	System.out.println("\nThe Clauses Ends are: \n");
		for(ArrayList<Integer> ends : clauseEnds){
	//		System.out.println(ends);
		}
		
		int i = 1;
		for(ArrayList<Integer> ends : clauseEnds){
			
			while(i < ends.get(0)){
	//			System.out.print(tree.get(i).form + " ");
				i++;
				}
	//		System.out.print("$$");
			while( i >= ends.get(0) && i <= ends.get(1) ){
	//			System.out.print(tree.get(i).form + " ");
				i++;
			}
			
	//		System.out.print("$$");
			
		}
		
		try{
		String docnum = sentId.split("_s")[0].split("d")[1];
		System.out.println("DOCNUMBER="+docnum);
		String filepath= "/usr/local/home/administrator/My Files/BnB for MTurk/HITs/"+"HIT_"+docnum+".html";
	    FileWriter fstream = new FileWriter(filepath,true);
	    BufferedWriter fw = new BufferedWriter(fstream);
	    
	    String xmlfilepath= "/usr/local/home/administrator/My Files/BnB for MTurk/XMLs/"+"HIT_"+docnum+".xml";
	    FileWriter xmlfstream = new FileWriter(xmlfilepath,true);
	    BufferedWriter xmlfw = new BufferedWriter(xmlfstream);
	    
	    String pyfilepath= "/usr/local/home/administrator/My Files/BnB for MTurk/Py/"+"HIT_"+docnum+".txt";
	    FileWriter pyfstream = new FileWriter(pyfilepath,true);
	    BufferedWriter pyfw = new BufferedWriter(pyfstream);
	    
	    fw.write("<br/>");
	    
	    //Code for generating xml file for clauses
	    
	    
	    String clauseVerb,clauseObj,clauseSbj,clauseObjPOS,clauseSbjPOS;
	    int verbIndex,sbjIndex,objIndex;
	    for(i = 0; i < clauseStrings.size(); i++){
	    	String clauseId = sentId + "_c" + (i+1);
	    	
	    	//Finding the verb in the clause
	    	verbIndex = featureSearch(tree, clauseEnds.get(i).get(0), clauseEnds.get(i).get(1), "Verb" );
	    	if (verbIndex != -1 ){
	    		clauseVerb = tree.get(verbIndex).lemma;	   
	    		if(clauseVerb.equals("'s")){
	    			clauseVerb = "is";
	    		}
	    	}
	    	else {
	    		clauseVerb = "";
	    	}
	    	// finding the Subject of the clause
	    	sbjIndex = featureSearch(tree, clauseEnds.get(i).get(0), clauseEnds.get(i).get(1), "SBJ" );
	    	if (sbjIndex != -1 ){
	    		clauseSbj = tree.get(sbjIndex).form;	
	    		clauseSbjPOS = tree.get(sbjIndex).pos;
	    	}
	    	else {
	    		clauseSbj = "";
	    		clauseSbjPOS = "";
	    	}
	    	
	    	//Finding the Object of the clause
	    	objIndex = featureSearch(tree, clauseEnds.get(i).get(0), clauseEnds.get(i).get(1), "OBJ" );
	    	if (objIndex != -1 ){
	    		clauseObj = tree.get(objIndex).form;	
	    		clauseObjPOS = tree.get(objIndex).pos;	    		
	    	}
	    	else {
	    		clauseObj = "";
	    		clauseObjPOS = "";
	    	}
	    	
	    	
	    	xmlfw.write("<clause id = \""+ clauseId + "\" Verb =\""+ clauseVerb +"\" SBJ =\""+ clauseSbj +"\" sbjPOS = \""+ clauseSbjPOS +"\" OBJ =\""+ clauseObj +"\" objPOS = \""+ clauseObjPOS + "\" >"  );
	    	xmlfw.write("\n\t<text>");
	    	xmlfw.write(clauseStrings.get(i));
	    	xmlfw.write("</text>\n");
	    	xmlfw.write("</clause>\n");
	    	
	    }
		
		i = 1;
		int j = 1;
		for(ArrayList<Integer> ends : clauseEnds){
			int flag = 0;
			
			if(i < ends.get(0)){
				fw.write("<p>");
				flag = 1;
			}
			
			
			while(i < ends.get(0)){
				fw.write(tree.get(i).form + " ");
				i++;
				}
			
			if (flag == 1){
				fw.write("</p>");
			}
			String clauseId = sentId + "_c" + j;
			
			//for generating text file with clause text and id
			pyfw.write(clauseId + ":" + clauseStrings.get(j-1));
			pyfw.write("\n");
			
			
			
			fw.write("<p id = \"" + clauseId + "\" class=\"clause\" onClick=\"$('#" + clauseId + "_Menu').slideDown('slow',function(){});\" >");
			
			while( i >= ends.get(0) && i <= ends.get(1) ){
				fw.write(tree.get(i).form + " ");
				i++;
			}
			
			fw.write("</p>");
			fw.write("<div id=\""+ clauseId +  "_Menu\" class=\"menu\" onClick=\"$('#" + clauseId +  "_Menu').hide('slow');\">");
			fw.write("<p>Click a button to assign the corresponding label to the text segment. To hide this menu, click anywhere on the menu.</p><br /> <br />");
			fw.write("<button onClick=\"javascript:test2('"+ clauseId +"','preference','" + clauseId + "');return false;\">Preference</button>");
			fw.write("<button onClick=\"javascript:test2('"+ clauseId +"','activity','" + clauseId +"');return false;\">Activity</button>");
			fw.write("<button onClick=\"javascript:test2('"+ clauseId +"','health','" + clauseId +"');return false;\">Health</button>");
			fw.write("<button onClick=\"javascript:test2('"+ clauseId +"','people','" + clauseId + "');return false;\">People</button>");
			fw.write("<button onClick=\"javascript:test2('"+ clauseId +"','none','" + clauseId + "');return false;\">Remove Label</button>");
			fw.write("</div>\n");
			fw.write("<p><input class='textbox' type='text' id='"+ clauseId +"_box' name ='"+ clauseId + "_box' value='" +clauseId +":none' /></p>");
			//<p><input class="textbox" type="text" id="s1_c1_box" name="s1_c1_box" value="s1_c1:none" /></p>
		
			j++;
		}
		int flag = 0;
		
		if (i <= tree.size() - 1){
			fw.write("<p>");
			flag = 1;
		}
		
		while( i <= tree.size() - 1){
			fw.write(tree.get(i).form + " ");
			i++;			
		}
		
		if (flag == 1){
			fw.write("</p>");
			//fw.write("<br/>");
		}
		
		fw.close();
		xmlfw.close();
		pyfw.close();
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		  }
		  
		
		
	}
	
	public static ArrayList<Integer> findVerbs(DepTree tree){
		
		int i;
		ArrayList<Integer> verbIndices = new ArrayList<Integer>();
		DepNode node ;
		for ( i = 1; i < tree.size(); i++ ){
			
			node = tree.get(i);
			
			if (node.pos.equals("VB") || node.pos.equals("VBD") || node.pos.equals("VBG") || node.pos.equals("VBN") || node.pos.equals("VBP") || node.pos.equals("VBZ") ){
				if ( !node.deprel.equals("VC")){
					verbIndices.add(i);
				}
			}
						
		}
		
		return verbIndices;
	}
	
	public static ArrayList<Integer> relatedTokens(DepTree tree, int head){
		
		ArrayList<Integer> relatedTokenIds = new ArrayList<Integer>();
		DepNode tempNode;
		
		int colonPosition = colonSearch(tree);
		
		
		for ( int i = colonPosition + 1; i < tree.size(); i++ ){
			
			if (i == head){
				continue;
			}
			
			else{
				tempNode = tree.get(i);
				if(tempNode.headId == head){
					
					if (tempNode.deprel.equals("COORD")){
						
						if(tempNode.pos.equals("CC") && tree.get(i+1).deprel.equals("CONJ") && tree.get(i+1).headId == i){
							relatedTokenIds.add(i);
						}
												
					}
					else if (!(tempNode.deprel.equals("P") || tempNode.deprel.equals("APPO")))
						relatedTokenIds.add(i);
				}
				
			}
			
		}
		
		return relatedTokenIds;
		
	}
	public static int colonSearch (DepTree tree){
		int colon = 0;
		for (int i = 1;i < tree.size(); i++){
			if (tree.get(i).form.equals(":")){
				colon = i;
				break;
			}
		}
		
		return colon;
	}
	
	public static int featureSearch(DepTree tree, int begin, int end, String feat){
		
		int featPosition = -1;
		for(int i = begin; i <= end; i++) {
			DepNode temp = tree.get(i);
			if (feat.equals("Verb")){
				if (temp.pos.equals("VB") || temp.pos.equals("VBD") || temp.pos.equals("VBG") || temp.pos.equals("VBN") || temp.pos.equals("VBP") || temp.pos.equals("VBZ")){
					featPosition = i;
					break;
				}				
			}
			else{
				if (temp.deprel.equals(feat)){
					featPosition = i;
					break;
				}
			}
		}
		
		return featPosition;
	}

}
