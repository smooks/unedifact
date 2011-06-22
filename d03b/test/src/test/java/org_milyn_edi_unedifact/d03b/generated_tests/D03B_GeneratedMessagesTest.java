/*
 * Milyn - Copyright (C) 2006 - 2010
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (version 2.1) as published by the Free Software
 * Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl.txt
 */

package org_milyn_edi_unedifact.d03b.generated_tests;

import org.milyn.edi.test.InterchangeTestUtil;
import org.milyn.edi.unedifact.d03b.APERAK.Aperak;
import org.milyn.edi.unedifact.d03b.AUTHOR.Author;
import org.milyn.edi.unedifact.d03b.BALANC.Balanc;
import org.milyn.edi.unedifact.d03b.BANSTA.Bansta;
import org.milyn.edi.unedifact.d03b.INVOIC.Invoic;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;
import org_milyn_edi_unedifact.d03b.AbstractTestCase;

import java.io.IOException;
import java.util.List;

/**
 * Finding UN/EDIFACT sample messages has proved to be a PITA, .
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class D03B_GeneratedMessagesTest extends AbstractTestCase {

    public void test_APERAK() throws IOException {
        InterchangeTestUtil.test_Interchange(factory, false, Aperak.class);
    }

    public void test_APERAK_comma_decimal() throws IOException {
        InterchangeTestUtil.test_Interchange_Comma_Decimal(factory, false, Aperak.class);
    }

    public void test_AUTHOR() throws IOException {
        InterchangeTestUtil.test_Interchange(factory, false, Author.class);
    }

    public void test_AUTHOR_comma_decimal() throws IOException {
        InterchangeTestUtil.test_Interchange_Comma_Decimal(factory, true, Author.class);
    }

    public void test_BALANC() throws IOException {
        InterchangeTestUtil.test_Interchange(factory, false, Balanc.class);
    }

    public void test_BANSTA() throws IOException {
        InterchangeTestUtil.test_Interchange(factory, false, Bansta.class);
    }

    public void test_INVOIC() throws IOException {
        InterchangeTestUtil.test_Interchange(factory, false, Invoic.class);
    }

    public void test_APERAK_AUTHOR_INVOIC_1_per_group() throws IOException {

        InterchangeTestUtil.test_Interchange(factory, false, Aperak.class, Author.class, Invoic.class);
    }

    public void test_APERAK_AUTHOR_INVOIC_all_in_1_group() throws IOException {
        UNEdifactInterchange41 interchange41 = InterchangeTestUtil.buildInterchange(Aperak.class, Author.class, Invoic.class);

        // Set the 2nd and 3rd to have the same group header as the first...
        List<UNEdifactMessage41> messages = interchange41.getMessages();
        messages.get(1).setGroupHeader(messages.get(0).getGroupHeader());
        messages.get(2).setGroupHeader(messages.get(0).getGroupHeader());

        InterchangeTestUtil.test_Interchange(factory, false, interchange41);
    }
}
