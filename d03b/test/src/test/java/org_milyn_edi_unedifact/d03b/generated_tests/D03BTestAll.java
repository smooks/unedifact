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
import org_milyn_edi_unedifact.d03b.AbstractTestCase;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class D03BTestAll {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // Set VM args:
        //     -Xms128m -Xmx1024m -XX:MaxPermSize=512m
        while(true) {
            InterchangeTestUtil.test_loads(AbstractTestCase.factory, false, "/org/milyn/edi/unedifact/d03b/ejc-classes.lst", 50);
        }
    }
}
