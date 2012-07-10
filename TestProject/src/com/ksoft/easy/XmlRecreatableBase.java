package com.ksoft.easy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;

import com.ksoft.serialization.Deserializer;
import com.ksoft.serialization.XmlRecreatable;

public abstract class XmlRecreatableBase extends EasySerializableBase implements XmlRecreatable {
    /**
     * Empty constructor that does nothing.
     */
    public XmlRecreatableBase() {}
    
    public XmlRecreatableBase(Deserializer deserializer) throws IOException {
        super(deserializer);
    }
    
    /**
     * Used as the base implementation of creating objects from xml. Will parse the XML and set
     * attributes/objects by calling {@link #setAttribute(int, String)} and
     * {@link #setObject(int, XmlPullParser)}.
     * 
     * @param parser
     *            XmlPullParser from which to get data for this class.
     */
    public XmlRecreatableBase(XmlPullParser parser) throws XmlPullParserException, IOException {
        for (int i = parser.getAttributeCount(); i > 0; /* done below */) {
            setAttribute(parser.getAttributeName(--i).hashCode(), parser.getAttributeValue(i));
        }
        int outsideDepth = parser.getDepth();
        
        int state = parser.getEventType();
        while (state != XmlPullParser.END_TAG || parser.getDepth() != outsideDepth) {
            if ((state = parser.next()) != XmlPullParser.START_TAG) {
                continue;
            }
            setObject(parser.getName().hashCode(), parser);
        }
    }
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public final void writeToParser(XmlSerializer output) throws IOException {
        HashMap map = new HashMap();
        
        String name = this.getClass().getName();
        output.startTag("", name);
        getAttributes(map);
        for (Map.Entry<String, String> ent : ((HashMap<String, String>) map).entrySet()) {
            output.attribute("", ent.getKey(), ent.getValue());
        }
        map.clear();
        
        getObjects(map);
        for (Map.Entry<String, XmlRecreatable> ent : ((HashMap<String, XmlRecreatable>) map)
                .entrySet()) {
            if (ent.getValue() != null) {
                output.startTag("", ent.getKey());
                ent.getValue().writeToParser(output);
                output.endTag("", ent.getKey());
            }
        }
        map.clear();
        
        getObjectArrays(map);
        for (Map.Entry<String, XmlRecreatable[]> ent : ((HashMap<String, XmlRecreatable[]>) map)
                .entrySet()) {
            XmlRecreatable[] val = ent.getValue();
            
            if (val != null) {
                output.startTag("", ent.getKey());
                output.startTag("", "array");
                
                for (XmlRecreatable x : val) {
                    x.writeToParser(output);
                }
                
                output.endTag("", "array");
                output.endTag("", ent.getKey());
            }
        }
        
        output.endTag("", name);
    }
    
    /**
     * Should be overridden in subclasses so that if the hash of the field name matches a field they
     * own they will set that value, else they should bubble up the value to the superclass.
     * 
     * @param hash
     *            Hash of the attribute name in the xml file.
     * @param value
     *            Value of the attribute in the xml file.
     */
    protected void setAttribute(int hash, String value) {
        Log.w("XmlRecreatableBase", "Hash in xml file not captured by superclass: "
                                    + hash + ", value: \"" + value + "\"");
    }
    
    /**
     * Should be overridden in subclasses so that if the hash of the field name matches a field they
     * own they will set that value by parsing the xml file at the current position, else they
     * should bubble up the value to the superclass. The position of the XmlPullParser will be at
     * the point in the file that is at the opening tag of the field name, meaning that at least one
     * parser.next() call must be called before data should be extracted. The position of the parser
     * at the end of the method is expected to be at the end tag of the
     * 
     * @param hash
     *            Hash of the attribute name in the xml file.
     * @param value
     *            Value of the attribute in the xml file.
     */
    protected void setObject(int hash, XmlPullParser parser) {
        Log.w("XmlRecreatableBase",
                "Hash in xml file not captured by superclass: "
                        + hash
                        + ", line: "
                        + parser.getLineNumber()
                        + ". XML recreation of this object may be incorrect because of incorrect alignment.");
    }
    
    /**
     * Should be overridden in subclasses such that all fields that are primitives, arrays of
     * primitives, strings, and arrays of strings are saved as fieldName:value pairs in this
     * mapping.
     * 
     * Subclasses should call their super-class' method in order to make sure their values are saved
     * too.
     * 
     * @param map
     *            Map to save the fields described above.
     */
    protected void getAttributes(HashMap<String, String> map) {
        // Base does nothing.
    }
    
    /**
     * Should be overridden in subclasses such that all fields that are Objects are saved as
     * [Classname].[fieldName]:value pairs in this mapping. Note that only objects that implement
     * {@link XmlRecreatable} can be saved in this way. A value can be null, but note that in this
     * case that pair is simply skipped in the current implementation.
     * 
     * Subclasses should call their super-class' method in order to make sure their values are saved
     * too.
     * 
     * @param map
     *            Map to save the fields described above.
     */
    protected void getObjects(HashMap<String, XmlRecreatable> map) {
        // Base does nothing.
    }
    
    /**
     * Should be overridden in subclasses such that all fields that are Object arrays are saved as
     * [Classname].[fieldName]:value pairs in this mapping. Note that only arrays that implement
     * {@link XmlRecreatable} can be saved in this way. A value can be null, but note that in this
     * case that pair is simply skipped in the current implementation.
     * 
     * Subclasses should call their super-class' method in order to make sure their values are saved
     * too.
     * 
     * @param map
     *            Map to save the fields described above.
     */
    protected void getObjectArrays(HashMap<String, XmlRecreatable[]> map) {
        // Base does nothing.
    }
    
    /**
     * Represents a creator that can be used as an EasySerializable creator and an XmlRecreatable
     * creator. This base class doesn't actually do anything, but it helps with creating the CREATOR
     * field in subclasses.
     * 
     * @author Kevin
     * 
     * @param <T>
     */
    public abstract static class CreatorBase<T> extends EasySerializableBase.CreatorBase<T>
            implements
                XmlRecreatable.Creator<T> {
        
        // Still need to implement these in subclass!
        @Override
        public abstract T[] newArray(int size);
        
        @Override
        public abstract T createFromDeserializer(Deserializer deserializer) throws IOException;
        
        @Override
        public abstract T createFromParser(XmlPullParser parser) throws XmlPullParserException,
                IOException;
    }
}
