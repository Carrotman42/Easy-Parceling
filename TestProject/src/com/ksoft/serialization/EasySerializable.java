package com.ksoft.serialization;

import java.io.IOException;

import android.os.Parcel;
import android.os.Parcelable;


public interface EasySerializable extends Parcelable {
    /**
     * {@inheritDoc}<br>
     * <br>
     * 
     * Inherited from Parcelable (rarely used. Generally returning 0 is fine)
     */
    @Override
    int describeContents();
    
    /**
     * {@inheritDoc}<br>
     * <br>
     * 
     * Inherited from Parcelable. If you are using a standalone class you will most likely want to
     * simply call {@link #writeToSerializer(Serializer)} with the wrapping class
     * {@link ParcelSerializer}.
     */
    @Override
    void writeToParcel(Parcel dest, int flags);
    
    /**
     * Called when this object should be serialized either to a file or for parceling through use of
     * of the {@link #ParcelSerializer} class.
     * 
     * @param serializer
     *            Object to where this object's data should be written.
     */
    void writeToSerializer(Serializer serializer) throws IOException;
    
    /**
     * {@inheritDoc}<br>
     * <br>
     * 
     * Extends support for creating an object based off of a {@link #Deserializer} Object.
     * 
     * @param <T>
     *            The type of the implementing class such that calling these methods returns
     *            instances exactly the same type as the implementing class.
     */
    public interface Creator<T> extends Parcelable.Creator<T> {
        /**
         * {@inheritDoc}<br>
         * <br>
         * 
         * Inherited from Parcelable. If you are using a standalone class you will most likely want
         * to simply call {@link #createFromDeserializer(Deserializer)} with the wrapping class
         * {@link ParcelDeserializer}.
         */
        @Override
        T createFromParcel(Parcel source);
        
        /**
         * {@inheritDoc}<br>
         * <br>
         * 
         * Inherited from Parcelable. Simply return an array of the implementing class of size
         * 'size'.
         */
        @Override
        T[] newArray(int size);
        
        /**
         * Recreate this object from a {@link #Deserializer} object. If you want to support
         * deserializing final fields you will want to create your deserialization logic in a
         * constructor and simply pass in the Deserialzer object.
         * 
         * @param deserializer
         *            Object from which to get data for object recreation. Remember you must only
         *            read exactly what you wrote in {@link #writeToSerializer(Serializer)}.
         * @return A new object that was recreated from the previously serialized data.
         */
        T createFromDeserializer(Deserializer deserializer) throws IOException;
    }
}
