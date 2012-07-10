package com.ksoft.easy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.ksoft.serialization.Deserializer;
import com.ksoft.serialization.Serializer;
import com.ksoft.serialization.StaticDataReader;
import com.ksoft.serialization.XmlRecreatable;
import com.ksoft.serialization.XmlRecreatableBase;

public class TestXml extends XmlRecreatableBase {
    private int t1;
    private TestXml t2;
    private TestXml[] t3;
    
    @Override
    public void writeToSerializer(Serializer serializer) throws IOException {
        super.writeToSerializer(serializer);
        serializer.writeObject(t2);
        serializer.writeInt(t1);
        serializer.writeObjectArray(t3);
    }
    public TestXml(Deserializer deserializer) throws IOException {
        super(deserializer);
        t2 = deserializer.readObject(TestXml.CREATOR);
        t1 = deserializer.readInt();
        t3 = deserializer.readObjectArray(TestXml.CREATOR);
    }
    public TestXml getT2() {
        return t2;
    }
    public int getT1() {
        return t1;
    }
    public TestXml[] getT3() {
        return t3;
    }
    
    public void setT2(TestXml val) {
        t2 = val;
    }
    public void setT1(int val) {
        t1 = val;
    }
    public void setT3(TestXml[] val) {
        t3 = val;
    }
    
    @Override
    protected void getAttributes(HashMap<String, String> map) {
        super.getAttributes(map);
        map.put("t1", new StringBuilder().append(t1).toString());
    }
    @Override
    protected void getObjects(HashMap<String, XmlRecreatable> map) {
        super.getObjects(map);
        map.put("TestXml.t2", t2);
    }
    @Override
    protected void getObjectArrays(HashMap<String, XmlRecreatable[]> map) {
        super.getObjectArrays(map);
        map.put("TestXml.t3", t3);
    }
    public static final int HASH_t2 = -437307705;
    public static final int HASH_t1 = 3645;
    public static final int HASH_t3 = -437307704;
    
    @Override
    protected void setAttribute(int hash, String value) {
        switch (hash) {
            case HASH_t1 :
                t1 = Integer.parseInt(value);
                break;
            default :
                super.setAttribute(hash, value);
                break;
        }
    }
    
    @Override
    protected void setObject(int hash, XmlPullParser parser) {
        switch (hash) {
            case HASH_t2 :
                t2 = StaticDataReader.readElementFromXml(parser, "TestXml", TestXml.CREATOR);
                break;
            case HASH_t3 : {
                ArrayList<TestXml> l = StaticDataReader.readXmlList(parser, "TestXml",
                        TestXml.CREATOR);
                t3 = l == null ? null : l.toArray(new TestXml[l.size()]);
                break;
            }
            default :
                super.setObject(hash, parser);
                break;
        }
    }
    
    protected TestXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        super(parser);
    }
    public static final XmlRecreatableBase.CreatorBase<TestXml> CREATOR = new XmlRecreatableBase.CreatorBase<TestXml>() {
        @Override
        public TestXml[] newArray(int size) {
            return new TestXml[size];
        }
        @Override
        public TestXml createFromDeserializer(Deserializer deserializer) throws IOException {
            return new TestXml(deserializer);
        }
        @Override
        public TestXml createFromParser(XmlPullParser parser) throws XmlPullParserException,
                IOException {
            return new TestXml(parser);
        }
    };
    
    
}
