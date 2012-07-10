package com.ksoft.serialization;


import java.io.IOException;

import android.os.Parcelable;


public abstract class Deserializer {
    public abstract boolean readBoolean() throws IOException;
    public abstract byte readByte() throws IOException;
    public abstract char readChar() throws IOException;
    public abstract short readShort() throws IOException;
    public abstract float readFloat() throws IOException;
    public abstract int readInt() throws IOException;
    public abstract long readLong() throws IOException;
    public abstract double readDouble() throws IOException;
    
    public abstract String readString() throws IOException;
    
    public final <T> T readObject(EasySerializable.Creator<T> creator) throws IOException {
        if (readInt() == 0) {
            return null;
        } else {
            return creator.createFromDeserializer(this);
        }
    }
    
    /**
     * See {@link Serializer#writeObject(EasySerializable)} for info on why this method exists.
     * 
     * @param creator
     * @return
     */
    public abstract <T> T readObject(Parcelable.Creator<T> creator);
    
    // Array reading section
    public abstract boolean[] readBooleanArray() throws IOException;
    public abstract byte[] readByteArray() throws IOException;
    public abstract char[] readCharArray() throws IOException;
    public abstract float[] readFloatArray() throws IOException;
    public abstract int[] readIntArray() throws IOException;
    public abstract long[] readLongArray() throws IOException;
    public abstract double[] readDoubleArray() throws IOException;
    
    public abstract String[] readStringArray() throws IOException;
    
    public final <T extends EasySerializable> T[] readObjectArray(EasySerializable.Creator<T> c)
            throws IOException {
        int N = readInt();
        if (N < 0) {
            return null;
        }
        T[] l = c.newArray(N);
        for (int i = 0; i < N; i++) {
            if (readInt() != 0) {
                l[i] = c.createFromDeserializer(this);
            }
        }
        return l;
    }
    
    /**
     * See {@link Serializer#writeObject(EasySerializable)} for info on why this method exists.
     * 
     * @param creator
     * @return
     */
    public abstract <T> T[] readObjectArray(Parcelable.Creator<T> creator);
}
