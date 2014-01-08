package main.cleartk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.cleartk.examples.chunking.util.MASCGoldAnnotator;
import org.cleartk.token.type.Sentence;
import org.cleartk.token.type.Token;
import org.cleartk.util.ViewURIUtil;
import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.TypeSystemDescriptionFactory;

import featExt.Clause;

import main.featExt.src.InfoAnnotation;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;




public class InfoAnnotator extends JCasAnnotator_ImplBase{
	
	public static AnalysisEngineDescription getDescription() throws ResourceInitializationException {
		TypeSystemDescription tsd =	 TypeSystemDescriptionFactory.createTypeSystemDescription();
		//AnalysisEngine ae = AnalysisEngineFactory.createPrimitive(InfoAnnotator.class, tsd);
	    return AnalysisEngineFactory.createPrimitiveDescription(InfoAnnotator.class,tsd);
	  }
	
	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException{
		//System.out.println(" InfoAnnotator just inside process()");
		File textFile = new File(ViewURIUtil.getURI(jCas));
	    String prefix = textFile.getPath().replaceAll("[.]txt$", "");
	    File xmlFile = new File(prefix);
	    
	    
	    
	   
	    
	    String filepath= "/usr/local/home/administrator/My Files/JAVA/workspace/featExt/results.txt";
	    
	    HashMap<String,String> clauseLabels = new HashMap<String,String>();
	  //  System.out.println(" InfoAnnotator after creating results hashmap ");
	    BufferedReader br = null;
		try {
			//System.out.println(" InfoAnnotator just inside try for br");
			 br = new BufferedReader(new FileReader(filepath));
			// System.out.println(" InfoAnnotator inside try for br after initialising br with filepath");
			
			
			String line;
			String[] splitLine;
			String id,label;
			while ((line = br.readLine()) != null){
				splitLine = line.split(",");
				//System.out.println(line);
				id = splitLine[1].split(":")[1];
				label = splitLine[2].split(":")[1];
				clauseLabels.put(id, label);
				//System.out.println(id + label);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    SAXBuilder builder = new SAXBuilder();
	    System.out.println(" InfoAnnotator before document builder");
	    try {
	    	 
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> clauseList = rootNode.getChildren("clause");
			String clauseId,clauseText,clauseLabel,clauseVerb,clauseObj,clauseSbj,clauseObjPOS,clauseSbjPOS;
			for (int i = 0; i < clauseList.size(); i++) {
	 
			   Element clause = (Element) clauseList.get(i);
			   System.out.println(clause.getAttributeValue("id"));
			   
			   
			   clauseId = clause.getAttributeValue("id");
			   clauseLabel = clauseLabels.get(clauseId);
			   clauseVerb = clause.getAttributeValue("Verb");
			   clauseObj = clause.getAttributeValue("OBJ");
			   clauseObjPOS = clause.getAttributeValue("objPOS");
			   clauseSbj = clause.getAttributeValue("SBJ");
			   clauseSbjPOS = clause.getAttributeValue("sbjPOS");
			   clauseText = clause.getText();
			   
			   Clause clauseInfo = new Clause(jCas);
			   
			   clauseInfo.setId(clauseId);
			   clauseInfo.setLabel(clauseLabel);
			  // clauseInfo.setClauseText(clauseText);
			   clauseInfo.setRoot(clauseVerb);
			   //clauseInfo.setObject(clauseObj);
			   //clauseInfo.setObjPOS(clauseObjPOS);
			   //clauseInfo.setSubject(clauseSbj);
			   //clauseInfo.setSbjPOS(clauseSbjPOS);
			   
			   clauseInfo.addToIndexes();
			   
			   
			}
	 
		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		  }
		
	    
	    try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
