package com.snail.iweibo.util;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.Reader;
import java.io.StringReader;

/**
 * Created by wang.weib on 2016/5/19.
 */
public class XmlUtils {
    public static void parse() throws Exception{
        String xml = "<message id='O1tz7-0'><subject>hello</subject><body>你好</body></message>";
        XmlPullParser pullParser = Xml.newPullParser();
        Reader reader = new StringReader(xml);
        pullParser.setInput(reader);
        int event = pullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if ("message".equals(pullParser.getName())) {
                        Log.d("XmlUtils","message");
                    }
                    if ("body".equals(pullParser.getName())) {
                        Log.d("XmlUtils","body text-->" + pullParser.nextText());
                    }
                    if ("subject".equals(pullParser.getName())) {
                        Log.d("XmlUtils","subject text-->" + pullParser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("message".equals(pullParser.getName())) {

                    }
                    break;
            }
            event = pullParser.next();
        }
    }

    public static void main(String[] args) throws Exception {
        parse();
    }


}
