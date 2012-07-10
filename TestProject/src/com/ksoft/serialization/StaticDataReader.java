package com.ksoft.serialization;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public final class StaticDataReader {
    private StaticDataReader() {}
    
    public static <T extends XmlRecreatable> ArrayList<T> readXmlList(XmlPullParser parse,
            String targetName, XmlRecreatable.Creator<T> creator) {
        try {
            return readXmlListImpl(parse, targetName, creator);
        } catch (Exception e) {
            Log.e("ChooseConsumers", "Could not generate list from xml!", e);
            e.printStackTrace();
            
            return new ArrayList<T>();
        }
    }
    
    public static <T extends XmlRecreatable> T readElementFromXml(XmlPullParser parser,
            String targetName, XmlRecreatable.Creator<T> creator) {
        try {
            return readElementFromXmlImpl(parser, targetName, creator);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static <T extends XmlRecreatable> T readElementFromXmlImpl(XmlPullParser parse,
            String targetName, XmlRecreatable.Creator<T> creator) throws XmlPullParserException,
            IOException {
        
        int state = nextUntilImportantTag(parse);
        if (state != XmlPullParser.START_TAG || !parse.getName().equals(targetName)) {
            // No data to read!
            return null;
        }
        
        return creator.createFromParser(parse);
    }
    
    private static <T extends XmlRecreatable> ArrayList<T> readXmlListImpl(XmlPullParser parse,
            String targetName, XmlRecreatable.Creator<T> creator) throws XmlPullParserException,
            IOException {
        
        int state = nextUntilImportantTag(parse);
        if (state != XmlPullParser.START_TAG) {
            return null;
        }
        
        String startName = parse.getName();
        ArrayList<T> ret = new ArrayList<T>();
        while (!((state = nextUntilImportantTag(parse)) == XmlPullParser.END_TAG && parse.getName()
                .equals(startName))) {
            while (state != XmlPullParser.START_TAG || !parse.getName().equals(targetName)) {
                state = parse.next();
            }
            
            ret.add(creator.createFromParser(parse));
        }
        
        return ret;
    }
    
    private final static int nextUntilImportantTag(XmlPullParser parse)
            throws XmlPullParserException, IOException {
        int state;
        do {
            state = parse.next();
        } while (state != XmlPullParser.START_TAG && state != XmlPullParser.END_TAG);
        return state;
    }
    
    private static final String[] BLANK_STRING_ARRAY = new String[0];
    
    /**
     * I don't know why this doesn't exist in ArrayList itself. This is rather efficient because it
     * takes advantage of addAll of a collection that is actually just the dummy wrapping of the
     * array.
     * 
     * This will most likely fail if the implementation of {@link ArrayList#addAll(Collection)}
     * changes so that any method other then {@link Collection#toArray()} is called.
     * 
     * Note to future developers: I wrote this before I realized that
     * {@link Arrays#asList(Object...)} does this too. My version, though, prevents one less cloning
     * of the array given so I left this here because I already wrote it and it is less wasteful of
     * resources.
     * 
     * @param ts
     * @return
     */
    public static <T> ArrayList<T> arrToList(final T[] ts) {
        ArrayList<T> ret = new ArrayList<T>(ts.length);
        ret.addAll(new Collection<T>() {
            @Override
            public boolean add(T object) {
                return false;
            }
            @Override
            public boolean addAll(Collection<? extends T> collection) {
                return false;
            }
            @Override
            public void clear() {}
            @Override
            public boolean contains(Object object) {
                return false;
            }
            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }
            @Override
            public boolean isEmpty() {
                return false;
            }
            @Override
            public Iterator<T> iterator() {
                return null;
            }
            @Override
            public boolean remove(Object object) {
                return false;
            }
            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }
            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }
            @Override
            public int size() {
                return 0;
            }
            @Override
            public Object[] toArray() {
                return ts;
            }
            @Override
            public <E> E[] toArray(E[] array) {
                return null;
            }
        });
        return ret;
    }
    
    /**
     * Helper method to get the first character of a string
     * 
     * @param s
     * @return The first character of the string.
     */
    public static final char getChar(String s) {
        return s.charAt(0);
    }
    
    /**
     * Reads a comma separated list of integer values. Whitespace is stripped from the values before
     * parsing.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as integers.
     */
    public static int[] readIntArray(String csv) {
        String[] split = csv.split(",");
        if (split[0].length() == 0) {
            split = BLANK_STRING_ARRAY;
        }
        int len;
        int[] out = new int[len = split.length];
        for (; len > 0; /* done below */) {
            out[--len] = Integer.parseInt(split[len].trim());
        }
        return out;
    }
    
    /**
     * Reads a comma separated list of long values. Whitespace is stripped from the values before
     * parsing.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as longs.
     */
    public static long[] readLongArray(String csv) {
        String[] split = csv.split(",");
        if (split[0].length() == 0) {
            split = BLANK_STRING_ARRAY;
        }
        int len;
        long[] out = new long[len = split.length];
        for (; len > 0; /* done below */) {
            out[--len] = Long.parseLong(split[len].trim());
        }
        return out;
    }
    
    /**
     * Reads a comma separated list of short values. Whitespace is stripped from the values before
     * parsing.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as shorts.
     */
    public static short[] readShortArray(String csv) {
        String[] split = csv.split(",");
        if (split[0].length() == 0) {
            split = BLANK_STRING_ARRAY;
        }
        int len;
        short[] out = new short[len = split.length];
        for (; len > 0; /* done below */) {
            out[--len] = Short.parseShort(split[len].trim());
        }
        return out;
    }
    
    /**
     * Reads a comma separated list of boolean values. Whitespace is stripped from the values before
     * parsing. Note that only "true" (case insensitive) will evaluate to true, while everything
     * else will be false.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as booleans.
     */
    public static boolean[] readBooleanArray(String csv) {
        String[] split = csv.split(",");
        if (split[0].length() == 0) {
            split = BLANK_STRING_ARRAY;
        }
        int len;
        boolean[] out = new boolean[len = split.length];
        for (; len > 0; /* done below */) {
            out[--len] = Boolean.parseBoolean(split[len].trim());
        }
        return out;
    }
    
    /**
     * Reads a comma separated list of float values. Whitespace is stripped from the values before
     * parsing.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as floats.
     */
    public static float[] readFloatArray(String csv) {
        String[] split = csv.split(",");
        if (split[0].length() == 0) {
            split = BLANK_STRING_ARRAY;
        }
        int len;
        float[] out = new float[len = split.length];
        for (; len > 0; /* done below */) {
            out[--len] = Float.parseFloat(split[len].trim());
        }
        return out;
    }
    
    /**
     * Reads a comma separated list of double values. Whitespace is stripped from the values before
     * parsing.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as doubles.
     */
    public static double[] readDoubleArray(String csv) {
        String[] split = csv.split(",");
        if (split[0].length() == 0) {
            split = BLANK_STRING_ARRAY;
        }
        int len;
        double[] out = new double[len = split.length];
        for (; len > 0; /* done below */) {
            out[--len] = Double.parseDouble(split[len].trim());
        }
        return out;
    }
    
    /**
     * Reads a comma separated list of char values. Note that whitespace is NOT stripped.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as chars.
     */
    public static char[] readCharArray(String csv) {
        char[] split = csv.toCharArray();
        int inpos, outpos;
        char[] out = new char[outpos = (inpos = split.length + 1) / 2];
        for (; outpos > 0; /* done below */) {
            out[--outpos] = split[(inpos -= 2)];
        }
        return out;
    }
    
    /**
     * Reads a comma separated list of byte values. Whitespace is stripped from the values before
     * parsing.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as bytes.
     */
    public static byte[] readByteArray(String csv) {
        String[] split = csv.split(",");
        if (split[0].length() == 0) {
            split = BLANK_STRING_ARRAY;
        }
        int len;
        byte[] out = new byte[len = split.length];
        for (; len > 0; /* done below */) {
            out[--len] = Byte.parseByte(split[len].trim());
        }
        return out;
    }
    
    /**
     * Reads a comma separated list of String values. Commas cannot be escaped.
     * 
     * @param csv
     *            List of comma separated values
     * @return The list as bytes.
     */
    public static String[] readStringArray(String csv) {
        return csv.split(",");
    }
    
    // toString implementations for xml exporting. Currently not used anyway.
    
    public final static String toString(int i) {
        return Integer.toString(i);
    }
    public final static String toString(String s) {
        return s;
    }
    public final static String toString(float f) {
        return Float.toString(f);
    }
    public final static String toString(boolean b) {
        return Boolean.toString(b);
    }
    public final static String toString(double d) {
        return Double.toString(d);
    }
    public final static String toString(byte b) {
        return Byte.toString(b);
    }
    public final static String toString(short s) {
        return Short.toString(s);
    }
    public final static String toString(char c) {
        return Character.toString(c);
    }
    public final static String toString(long l) {
        return Long.toString(l);
    }
}
