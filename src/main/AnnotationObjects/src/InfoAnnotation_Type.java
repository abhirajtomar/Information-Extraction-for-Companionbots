
/* First created by JCasGen Thu Apr 25 10:21:01 CDT 2013 */
package main.featExt.src;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Mon Jun 17 16:20:45 CDT 2013
 * @generated */
public class InfoAnnotation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (InfoAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = InfoAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new InfoAnnotation(addr, InfoAnnotation_Type.this);
  			   InfoAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new InfoAnnotation(addr, InfoAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = InfoAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("main.featExt.src.InfoAnnotation");
 
  /** @generated */
  final Feature casFeat_Label;
  /** @generated */
  final int     casFeatCode_Label;
  /** @generated */ 
  public String getLabel(int addr) {
        if (featOkTst && casFeat_Label == null)
      jcas.throwFeatMissing("Label", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Label);
  }
  /** @generated */    
  public void setLabel(int addr, String v) {
        if (featOkTst && casFeat_Label == null)
      jcas.throwFeatMissing("Label", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Label, v);}
    
  
 
  /** @generated */
  final Feature casFeat_score;
  /** @generated */
  final int     casFeatCode_score;
  /** @generated */ 
  public double getScore(int addr) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_score);
  }
  /** @generated */    
  public void setScore(int addr, double v) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_score, v);}
    
  
 
  /** @generated */
  final Feature casFeat_InfoChunk;
  /** @generated */
  final int     casFeatCode_InfoChunk;
  /** @generated */ 
  public String getInfoChunk(int addr) {
        if (featOkTst && casFeat_InfoChunk == null)
      jcas.throwFeatMissing("InfoChunk", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_InfoChunk);
  }
  /** @generated */    
  public void setInfoChunk(int addr, String v) {
        if (featOkTst && casFeat_InfoChunk == null)
      jcas.throwFeatMissing("InfoChunk", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_InfoChunk, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Root;
  /** @generated */
  final int     casFeatCode_Root;
  /** @generated */ 
  public String getRoot(int addr) {
        if (featOkTst && casFeat_Root == null)
      jcas.throwFeatMissing("Root", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Root);
  }
  /** @generated */    
  public void setRoot(int addr, String v) {
        if (featOkTst && casFeat_Root == null)
      jcas.throwFeatMissing("Root", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Root, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Object;
  /** @generated */
  final int     casFeatCode_Object;
  /** @generated */ 
  public String getObject(int addr) {
        if (featOkTst && casFeat_Object == null)
      jcas.throwFeatMissing("Object", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Object);
  }
  /** @generated */    
  public void setObject(int addr, String v) {
        if (featOkTst && casFeat_Object == null)
      jcas.throwFeatMissing("Object", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Object, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ObjectPOS;
  /** @generated */
  final int     casFeatCode_ObjectPOS;
  /** @generated */ 
  public String getObjectPOS(int addr) {
        if (featOkTst && casFeat_ObjectPOS == null)
      jcas.throwFeatMissing("ObjectPOS", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ObjectPOS);
  }
  /** @generated */    
  public void setObjectPOS(int addr, String v) {
        if (featOkTst && casFeat_ObjectPOS == null)
      jcas.throwFeatMissing("ObjectPOS", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_ObjectPOS, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Subject;
  /** @generated */
  final int     casFeatCode_Subject;
  /** @generated */ 
  public String getSubject(int addr) {
        if (featOkTst && casFeat_Subject == null)
      jcas.throwFeatMissing("Subject", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Subject);
  }
  /** @generated */    
  public void setSubject(int addr, String v) {
        if (featOkTst && casFeat_Subject == null)
      jcas.throwFeatMissing("Subject", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Subject, v);}
    
  
 
  /** @generated */
  final Feature casFeat_SubjectPOS;
  /** @generated */
  final int     casFeatCode_SubjectPOS;
  /** @generated */ 
  public String getSubjectPOS(int addr) {
        if (featOkTst && casFeat_SubjectPOS == null)
      jcas.throwFeatMissing("SubjectPOS", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_SubjectPOS);
  }
  /** @generated */    
  public void setSubjectPOS(int addr, String v) {
        if (featOkTst && casFeat_SubjectPOS == null)
      jcas.throwFeatMissing("SubjectPOS", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_SubjectPOS, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ClauseText;
  /** @generated */
  final int     casFeatCode_ClauseText;
  /** @generated */ 
  public String getClauseText(int addr) {
        if (featOkTst && casFeat_ClauseText == null)
      jcas.throwFeatMissing("ClauseText", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ClauseText);
  }
  /** @generated */    
  public void setClauseText(int addr, String v) {
        if (featOkTst && casFeat_ClauseText == null)
      jcas.throwFeatMissing("ClauseText", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_ClauseText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Id;
  /** @generated */
  final int     casFeatCode_Id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_Id == null)
      jcas.throwFeatMissing("Id", "main.featExt.src.InfoAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_Id == null)
      jcas.throwFeatMissing("Id", "main.featExt.src.InfoAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Id, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public InfoAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Label = jcas.getRequiredFeatureDE(casType, "Label", "uima.cas.String", featOkTst);
    casFeatCode_Label  = (null == casFeat_Label) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Label).getCode();

 
    casFeat_score = jcas.getRequiredFeatureDE(casType, "score", "uima.cas.Double", featOkTst);
    casFeatCode_score  = (null == casFeat_score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_score).getCode();

 
    casFeat_InfoChunk = jcas.getRequiredFeatureDE(casType, "InfoChunk", "uima.cas.String", featOkTst);
    casFeatCode_InfoChunk  = (null == casFeat_InfoChunk) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_InfoChunk).getCode();

 
    casFeat_Root = jcas.getRequiredFeatureDE(casType, "Root", "uima.cas.String", featOkTst);
    casFeatCode_Root  = (null == casFeat_Root) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Root).getCode();

 
    casFeat_Object = jcas.getRequiredFeatureDE(casType, "Object", "uima.cas.String", featOkTst);
    casFeatCode_Object  = (null == casFeat_Object) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Object).getCode();

 
    casFeat_ObjectPOS = jcas.getRequiredFeatureDE(casType, "ObjectPOS", "uima.cas.String", featOkTst);
    casFeatCode_ObjectPOS  = (null == casFeat_ObjectPOS) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ObjectPOS).getCode();

 
    casFeat_Subject = jcas.getRequiredFeatureDE(casType, "Subject", "uima.cas.String", featOkTst);
    casFeatCode_Subject  = (null == casFeat_Subject) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Subject).getCode();

 
    casFeat_SubjectPOS = jcas.getRequiredFeatureDE(casType, "SubjectPOS", "uima.cas.String", featOkTst);
    casFeatCode_SubjectPOS  = (null == casFeat_SubjectPOS) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_SubjectPOS).getCode();

 
    casFeat_ClauseText = jcas.getRequiredFeatureDE(casType, "ClauseText", "uima.cas.String", featOkTst);
    casFeatCode_ClauseText  = (null == casFeat_ClauseText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ClauseText).getCode();

 
    casFeat_Id = jcas.getRequiredFeatureDE(casType, "Id", "uima.cas.String", featOkTst);
    casFeatCode_Id  = (null == casFeat_Id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Id).getCode();

  }
}



    