package com.ksoft.serialization;


import android.os.Parcel;
import android.os.Parcelable;

public final class ParcelSerializer extends Serializer {
    private final Parcel _p;
    
    public ParcelSerializer(Parcel p) {
        _p = p;
    }
    
    @Override
    public final void writeBoolean(boolean val) {
        _p.writeInt(val ? 1 : 0);
    }
    
    @Override
    public final void writeByte(byte val) {
        _p.writeInt(val);
    }
    
    @Override
    public final void writeChar(char val) {
        _p.writeInt(val);
    }
    
    @Override
    public final void writeDouble(double val) {
        _p.writeDouble(val);
    }
    
    @Override
    public final void writeFloat(float val) {
        _p.writeFloat(val);
    }
    
    @Override
    public final void writeInt(int val) {
        _p.writeInt(val);
    }
    
    @Override
    public final void writeLong(long val) {
        _p.writeLong(val);
    }
    
    @Override
    public final void writeShort(short val) {
        _p.writeInt(val);
    }
    
    @Override
    public final void writeByteArray(byte[] buffer) {
        _p.writeByteArray(buffer);
    }
    
    @Override
    public final void writeByteArray(byte[] buffer, int offset, int count) {
        _p.writeByteArray(buffer, offset, count);
    }
    
    @Override
    public final void writeString(String obj) {
        _p.writeString(obj);
    }
    
    @Override
    public final void writeBooleanArray(boolean[] vals) {
        _p.writeBooleanArray(vals);
    }
    
    @Override
    public final void writeCharArray(char[] vals) {
        _p.writeCharArray(vals);
    }
    
    @Override
    public final void writeDoubleArray(double[] vals) {
        _p.writeDoubleArray(vals);
    }
    
    @Override
    public final void writeFloatArray(float[] vals) {
        _p.writeFloatArray(vals);
    }
    
    @Override
    public final void writeIntArray(int[] vals) {
        _p.writeIntArray(vals);
    }
    
    @Override
    public final void writeLongArray(long[] vals) {
        _p.writeLongArray(vals);
    }
    
    @Override
    public final void writeObject(Parcelable obj) {
        obj.writeToParcel(_p, 0);
    }
    
    @Override
    public final void writeStringArray(String[] vals) {
        _p.writeStringArray(vals);
    }
    
    @Override
    public final <T extends Parcelable> void writeObjectArray(T[] vals) {
        if (vals != null) {
            int N = vals.length;
            writeInt(N);
            for (int i = 0; i < N; i++) {
                T item = vals[i];
                if (item != null) {
                    writeInt(1);
                    item.writeToParcel(_p, 0);
                } else {
                    writeInt(0);
                }
            }
        } else {
            writeInt(-1);
        }
    }
}
