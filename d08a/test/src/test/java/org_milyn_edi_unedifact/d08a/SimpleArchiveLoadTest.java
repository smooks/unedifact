package org_milyn_edi_unedifact.d08a;

import junit.framework.TestCase;
import org.milyn.Smooks;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class SimpleArchiveLoadTest extends TestCase {

    public void test() throws IOException, SAXException {
        Smooks smooks = new Smooks(getClass().getResourceAsStream("smooks-config.xml"));

        smooks.createExecutionContext();
    }
}
