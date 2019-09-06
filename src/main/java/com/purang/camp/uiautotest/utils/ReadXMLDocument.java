package com.purang.camp.uiautotest.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class ReadXMLDocument {
    private String charSet = "UTF-8";

    /**
     * set the java file reading encoding.
     *
     * @param encode
     *            the charset of the java files.
     */
    public void setReadCharSet(String encode) {
        this.charSet = encode;
    }

    /**
     * get the java file reading encoding.
     *
     * @return the charset of the java files.
     */
    public String getReadCharSet() {
        return this.charSet;
    }

    /**
     * read xml file content.
     *
     * @param fileName
     *            path and name of the file to be parsed.
     * @return the file string text of your xml file.
     * @throws Exception
     **/
    public String readXMLDocument(String fileName) throws Exception {
        File file = new File(fileName);
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        InputStream iStream;
        iStream = new FileInputStream(file);

        byte[] bytes = new byte[4096];
        int len = iStream.read(bytes);
        while (len > 0) {
            buff.write(bytes, 0, len);
            len = iStream.read(bytes);
        }
        iStream.close();
        return new String(buff.toByteArray(), charSet);
    }

    /**
     * load your xml files, parse and return document object.
     *
     * @param fileName
     *            path and name of the file to be parsed.
     * @return the document of your xml file, loaded as domfactory
     * @throws Exception
     **/
    public Document loadXMLDocument(String fileName) throws Exception {
        if (!new File(fileName).exists()) {
            return null;
        }
        String context = readXMLDocument(fileName);
        if (null == context || "".equals(context)) {
            return null;
        }
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setValidating(false);
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        return builder.parse(fileName);
    }
}

