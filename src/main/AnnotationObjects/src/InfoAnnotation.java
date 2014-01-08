

/* First created by JCasGen Thu Apr 25 10:21:01 CDT 2013 */
package main.featExt.src;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Jun 17 16:20:45 CDT 2013
 * XML source: /usr/local/home/administrator/My Files/JAVA/workspace/featExt/src/main/cleartk/InfoAnnotation.xml
 * @generated */
public class InfoAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(InfoAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected InfoAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public InfoAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public InfoAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public InfoAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: Label

  /** getter for Label - gets 
   * @generated */
  public String getLabel() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Label == null)
      jcasType.jcas.throwFeatMissing("Label", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Label);}
    
  /** setter for Label - sets  
   * @generated */
  public void setLabel(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Label == null)
      jcasType.jcas.throwFeatMissing("Label", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Label, v);}    
   
    
  //*--------------*
  //* Feature: score

  /** getter for score - gets 
   * @generated */
  public double getScore() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_score);}
    
  /** setter for score - sets  
   * @generated */
  public void setScore(double v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_score, v);}    
   
    
  //*--------------*
  //* Feature: InfoChunk

  /** getter for InfoChunk - gets The actual chunk of text
   * @generated */
  public String getInfoChunk() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_InfoChunk == null)
      jcasType.jcas.throwFeatMissing("InfoChunk", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_InfoChunk);}
    
  /** setter for InfoChunk - sets The actual chunk of text 
   * @generated */
  public void setInfoChunk(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_InfoChunk == null)
      jcasType.jcas.throwFeatMissing("InfoChunk", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_InfoChunk, v);}    
   
    
  //*--------------*
  //* Feature: Root

  /** getter for Root - gets Dominant verb in the chunk
   * @generated */
  public String getRoot() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Root == null)
      jcasType.jcas.throwFeatMissing("Root", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Root);}
    
  /** setter for Root - sets Dominant verb in the chunk 
   * @generated */
  public void setRoot(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Root == null)
      jcasType.jcas.throwFeatMissing("Root", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Root, v);}    
   
    
  //*--------------*
  //* Feature: Object

  /** getter for Object - gets Object of the chunk
   * @generated */
  public String getObject() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Object == null)
      jcasType.jcas.throwFeatMissing("Object", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Object);}
    
  /** setter for Object - sets Object of the chunk 
   * @generated */
  public void setObject(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Object == null)
      jcasType.jcas.throwFeatMissing("Object", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Object, v);}    
   
    
  //*--------------*
  //* Feature: ObjectPOS

  /** getter for ObjectPOS - gets POS tag of the object
   * @generated */
  public String getObjectPOS() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_ObjectPOS == null)
      jcasType.jcas.throwFeatMissing("ObjectPOS", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_ObjectPOS);}
    
  /** setter for ObjectPOS - sets POS tag of the object 
   * @generated */
  public void setObjectPOS(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_ObjectPOS == null)
      jcasType.jcas.throwFeatMissing("ObjectPOS", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_ObjectPOS, v);}    
   
    
  //*--------------*
  //* Feature: Subject

  /** getter for Subject - gets 
   * @generated */
  public String getSubject() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Subject == null)
      jcasType.jcas.throwFeatMissing("Subject", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Subject);}
    
  /** setter for Subject - sets  
   * @generated */
  public void setSubject(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Subject == null)
      jcasType.jcas.throwFeatMissing("Subject", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Subject, v);}    
   
    
  //*--------------*
  //* Feature: SubjectPOS

  /** getter for SubjectPOS - gets 
   * @generated */
  public String getSubjectPOS() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_SubjectPOS == null)
      jcasType.jcas.throwFeatMissing("SubjectPOS", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_SubjectPOS);}
    
  /** setter for SubjectPOS - sets  
   * @generated */
  public void setSubjectPOS(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_SubjectPOS == null)
      jcasType.jcas.throwFeatMissing("SubjectPOS", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_SubjectPOS, v);}    
   
    
  //*--------------*
  //* Feature: ClauseText

  /** getter for ClauseText - gets 
   * @generated */
  public String getClauseText() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_ClauseText == null)
      jcasType.jcas.throwFeatMissing("ClauseText", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_ClauseText);}
    
  /** setter for ClauseText - sets  
   * @generated */
  public void setClauseText(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_ClauseText == null)
      jcasType.jcas.throwFeatMissing("ClauseText", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_ClauseText, v);}    
   
    
  //*--------------*
  //* Feature: Id

  /** getter for Id - gets 
   * @generated */
  public String getId() {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "main.featExt.src.InfoAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Id);}
    
  /** setter for Id - sets  
   * @generated */
  public void setId(String v) {
    if (InfoAnnotation_Type.featOkTst && ((InfoAnnotation_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "main.featExt.src.InfoAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((InfoAnnotation_Type)jcasType).casFeatCode_Id, v);}    
  }

    