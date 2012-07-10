package com.ksoft.serialization;


import android.os.Parcel;
import android.os.Parcelable;

public final class ParcelDeserializer extends Deserializer {
    private final Parcel _p;
    
    public ParcelDeserializer(Parcel p) {
        _p = p;
    }
    
    @Override
    public final boolean readBoolean() {
        return _p.readInt() != 0;
    }
    
    @Override
    public final byte readByte() {
        return (byte) _p.readInt();
    }
    
    @Override
    public final char readChar() {
        return (char) _p.readInt();
    }
    
    @Override
    public final double readDouble() {
        return _p.readDouble();
    }
    
    @Override
    public final float readFloat() {
        return _p.readFloat();
    }
    
    @Override
    public final int readInt() {
        return _p.readInt();
    }
    
    @Override
    public final long readLong() {
        return _p.readLong();
    }
    
    @Override
    public final short readShort() {
        return (short) _p.readInt();
    }
    
    @Override
    public final String readString() {
        return _p.readString();
    }
    
    @Override
    public final <T> T readObject(Parcelable.Creator<T> creator) {
        return creator.createFromParcel(_p);
    }
    
    @Override
    public final boolean[] readBooleanArray() {
        return _p.createBooleanArray();
    }
    @Override
    public final byte[] readByteArray() {
        return _p.createByteArray();
    }
    @Override
    public final char[] readCharArray() {
        return _p.createCharArray();
    }
    @Override
    public final double[] readDoubleArray() {
        return _p.createDoubleArray();
    }
    @Override
    public final float[] readFloatArray() {
        return _p.createFloatArray();
    }
    @Override
    public final int[] readIntArray() {
        return _p.createIntArray();
    }
    @Override
    public final long[] readLongArray() {
        return _p.createLongArray();
    }
    
    @Override
    public final String[] readStringArray() {
        return _p.createStringArray();
    }
    
    @Override
    public final <T> T[] readObjectArray(Parcelable.Creator<T> creator) {
        int N = readInt();
        if (N < 0) {
            return null;
        }
        T[] l = creator.newArray(N);
        for (int i = 0; i < N; i++) {
            if (readInt() != 0) {
                l[i] = creator.createFromParcel(_p);
            }
        }
        return l;
    }
}
