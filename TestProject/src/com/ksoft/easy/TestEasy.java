package com.ksoft.easy;

import java.io.IOException;

import com.ksoft.serialization.Deserializer;
import com.ksoft.serialization.EasySerializableBase;
import com.ksoft.serialization.Serializer;

public class TestEasy extends EasySerializableBase {
    private final int t1;
    private final TestEasy t2;
    private final TestEasy[] t3;
    private final String s;
    
    public TestEasy(int a, TestEasy te, TestEasy[] tes, String ss) {
        t1 = a;
        t2 = te;
        t3 = tes;
        s = ss;
    }
    
    // ----------------------------------------
    // Begin automatically generated code
    // ----------------------------------------
    @Override
    public void writeToSerializer(Serializer serializer) throws IOException {
        super.writeToSerializer(serializer);
        serializer.writeInt(t1);
        serializer.writeString(s);
        serializer.writeObject(t2);
        serializer.writeObjectArray(t3);
    }
    public TestEasy(Deserializer deserializer) throws IOException {
        super(deserializer);
        t1 = deserializer.readInt();
        s = deserializer.readString();
        t2 = deserializer.readObject(TestEasy.CREATOR);
        t3 = deserializer.readObjectArray(TestEasy.CREATOR);
    }
    public int getT1() {
        return t1;
    }
    public String getS() {
        return s;
    }
    public TestEasy getT2() {
        return t2;
    }
    public TestEasy[] getT3() {
        return t3;
    }
    
    public static final EasySerializableBase.CreatorBase<TestEasy> CREATOR = new EasySerializableBase.CreatorBase<TestEasy>() {
        @Override
        public TestEasy[] newArray(int size) {
            return new TestEasy[size];
        }
        @Override
        public TestEasy createFromDeserializer(Deserializer deserializer) throws IOException {
            return new TestEasy(deserializer);
        }
    };
    
}
