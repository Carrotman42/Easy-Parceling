package com.ksoft.serialization;


import java.io.IOException;

import android.os.Parcelable;


public abstract class Serializer {
    public abstract void writeBoolean(boolean val) throws IOException;
    public abstract void writeByte(byte val) throws IOException;
    public abstract void writeChar(char val) throws IOException;
    public abstract void writeDouble(double val) throws IOException;
    public abstract void writeFloat(float val) throws IOException;
    public abstract void writeInt(int val) throws IOException;
    public abstract void writeLong(long val) throws IOException;
    public abstract void writeShort(short val) throws IOException;
    
    public final void writeObject(EasySerializable obj) throws IOException {
        if (obj == null) {
            writeInt(0);
        } else {
            writeInt(1);
            obj.writeToSerializer(this);
        }
    }
    /**
     * Implemented in ParcelSerializer but in no other subclasses. Useful for Parceling up classes
     * that don't support EasySerialization. Therefore these classes will not be able to be save
     * persistently but we can still easily transfer them between Activities.
     * 
     * @param obj
     */
    public abstract void writeObject(Parcelable obj);
    
    public abstract void writeString(String obj) throws IOException;
    
    public abstract void writeByteArray(byte[] vals) throws IOException;
    public abstract void writeByteArray(byte[] vals, int offset, int count) throws IOException;
    
    // Array writing section
    
    // The following were taken from the Android SDK's implementations in Parcel for the
    // same functions.
    
    public abstract void writeBooleanArray(boolean[] vals) throws IOException;
    public abstract void writeCharArray(char[] vals) throws IOException;
    public abstract void writeDoubleArray(double[] vals) throws IOException;
    public abstract void writeFloatArray(float[] vals) throws IOException;
    public abstract void writeIntArray(int[] vals) throws IOException;
    public abstract void writeLongArray(long[] vals) throws IOException;
    
    public abstract void writeStringArray(String[] vals) throws IOException;
    
    // From the android sdk's parcel class
    public final <T extends EasySerializable> void writeObjectArray(T[] vals) throws IOException {
        if (vals != null) {
            int N = vals.length;
            writeInt(N);
            for (int i = 0; i < N; i++) {
                T item = vals[i];
                if (item != null) {
                    writeInt(1);
                    item.writeToSerializer(this);
                } else {
                    writeInt(0);
                }
            }
        } else {
            writeInt(-1);
        }
    }
    
    /**
     * See {@link #writeObject(Parcelable)} for info.
     * 
     * @param vals
     */
    public abstract <T extends Parcelable> void writeObjectArray(T[] vals);
    
    
    
    
    
    
    
    
    
    
    
    
}
