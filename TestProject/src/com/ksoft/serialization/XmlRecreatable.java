package com.ksoft.serialization;


import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public interface XmlRecreatable {
    /**
     * Write this object out in a format that is compatible with
     * {@link Creator#createFromParser(XmlPullParser)}. The class should assume that the first
     * action to be made is {@code output.startTag()} of their own tag (eg that the document has
     * already been started). The implementing class needs to be sure that they close all tags they
     * start, therefore leaving the depth the same as it was when this method was called.
     * 
     * @param output
     */
    void writeToParser(XmlSerializer output) throws IOException;
    
    public interface Creator<T> {
        /**
         * Called when the start tag of the XML file is the same name as the implementing class. The
         * end location of the parser is expected to have the same depth and name as when the parser
         * was first passed in, but should be at the end tag rather than the start tag.
         * 
         * @param parser
         *            Parser from which data is read in.
         * @return An object recreated from the data starting at the specified location.
         */
        T createFromParser(XmlPullParser parser) throws XmlPullParserException, IOException;
    }
}
