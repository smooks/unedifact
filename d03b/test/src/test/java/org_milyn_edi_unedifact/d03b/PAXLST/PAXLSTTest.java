package org_milyn_edi_unedifact.d03b.PAXLST;

import org.milyn.edi.test.InterchangeTestUtil;
import org.xml.sax.SAXException;
import org_milyn_edi_unedifact.d03b.AbstractTestCase;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class PAXLSTTest extends AbstractTestCase {

    public void test_read() throws IOException, SAXException {
        testXMLSerialize("message_01.edi", "expected_01.xml", false);
    }

    public void test_bind() throws IOException, SAXException {
        InterchangeTestUtil.testJavaBinding(factory, "/org_milyn_edi_unedifact/d03b/PAXLST/message_01.edi", false);
    }
}
