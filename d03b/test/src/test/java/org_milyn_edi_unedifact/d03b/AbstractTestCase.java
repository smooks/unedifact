package org_milyn_edi_unedifact.d03b;

import junit.framework.TestCase;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.milyn.Smooks;
import org.milyn.edi.unedifact.d03b.D03BInterchangeFactory;
import org.milyn.payload.StringResult;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.*;

public abstract class AbstractTestCase extends TestCase {

    public static D03BInterchangeFactory factory;
    static {
        try {
            factory = D03BInterchangeFactory.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testXMLSerialize(String messageInFile, String expectedResFile) throws IOException, SAXException {
        testXMLSerialize(messageInFile, expectedResFile, false);
    }

    public void testXMLSerialize(String messageInFile, String expectedResFile, boolean dumpResult) throws IOException, SAXException {
        Smooks smooks = new Smooks(AbstractTestCase.class.getResourceAsStream("smooks-config.xml"));
        StringResult result = new StringResult();

        smooks.filterSource(new StreamSource(getClass().getResourceAsStream(messageInFile)), result);

        if(dumpResult) {
            System.out.println(result);
        }

        XMLUnit.setIgnoreWhitespace(true);
        XMLAssert.assertXMLEqual(new InputStreamReader(getClass().getResourceAsStream(expectedResFile)), new StringReader(result.toString()));
    }
}