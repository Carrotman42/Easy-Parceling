package com.ksoft.serialization;

import java.io.IOException;

import android.os.Parcel;
import android.util.Log;


public abstract class EasySerializableBase implements EasySerializable {
    /**
     * {@inheritDoc}<br>
     * <br>
     * 
     * Not marked final because someone might find a use for this. In this implementation it simply
     * returns 0 always.
     */
    @Override
    public int describeContents() {
        return 0;
    }
    
    /**
     * {@inheritDoc}<br>
     * <br>
     * 
     * Marked as final in this base class for easy inlining. This simply calls
     * {@link #writeToSerializer(Serializer)} with a {@link #ParcelSerializer} wrapping of the
     * parameter 'dest'.
     */
    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        try {
            writeToSerializer(new ParcelSerializer(dest));
        } catch (IOException ex) {
            Log.e("EasySerializableBase", "Writing to Parcel failed: ", ex);
        }
    }
    
    @Override
    public void writeToSerializer(Serializer serializer) throws IOException {
        // Base class does nothing!
    }
    
    /**
     * Used as a marker to remind subclasses that in order to deserialize final fields,
     * deserializing in a constructor with this method signature is required. This base
     * implementation doesn't do anything.
     * 
     * @param deserializer
     */
    public EasySerializableBase(Deserializer deserializer) throws IOException {
        // Base class does nothing!
    }
    
    /**
     * Empty constructor that does nothing.
     */
    public EasySerializableBase() {}
    
    protected abstract static class CreatorBase<T> implements EasySerializable.Creator<T> {
        @Override
        public final T createFromParcel(Parcel source) {
            try {
                return createFromDeserializer(new ParcelDeserializer(source));
            } catch (IOException ex) {
                Log.e("EasySerializableBase", "Reading from Parcel failed: ", ex);
                return null;
            }
        }
        
        // Still need to implement these in subclass!
        @Override
        public abstract T[] newArray(int size);
        
        @Override
        public abstract T createFromDeserializer(Deserializer deserializer) throws IOException;
        
    }
}
