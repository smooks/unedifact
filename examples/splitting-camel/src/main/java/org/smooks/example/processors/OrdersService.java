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

package org.smooks.example.processors;

import com.thoughtworks.xstream.XStream;
import org.milyn.edi.unedifact.d93a.ORDERS.Orders;
import org.milyn.edi.unedifact.d93a.ORDRSP.Ordrsp;
import org.milyn.edi.unedifact.d93a.common.BGMBeginningOfMessage;
import org.milyn.edi.unedifact.d93a.common.PAIPaymentInstructions;
import org.milyn.edi.unedifact.d93a.common.field.C002DocumentMessageName;
import org.milyn.edi.unedifact.d93a.common.field.C534PaymentInstructionDetails;

/**
 * Orders Processing Service.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class OrdersService {

    public Ordrsp processOrder(Orders order) {

        System.out.println("================ Orders Message ================");
        System.out.println(new XStream().toXML(order.getBGMBeginningOfMessage()));
        System.out.println(new XStream().toXML(order.getDTMDateTimePeriod()));

        // Now lets create a Purchase Order Response and return it...
        Ordrsp orderResponse = new Ordrsp();

        orderResponse.setBGMBeginningOfMessage(
                new BGMBeginningOfMessage().
                        setC002DocumentMessageName(new C002DocumentMessageName().setE1000DocumentMessageName("ORDRSP")).
                        setE1004DocumentMessageNumber(order.getBGMBeginningOfMessage().getE1004DocumentMessageNumber())
        );
        orderResponse.setDTMDateTimePeriod(order.getDTMDateTimePeriod());
        orderResponse.setPAIPaymentInstructions(
                new PAIPaymentInstructions().
                        setC534PaymentInstructionDetails(new C534PaymentInstructionDetails().
                                setE4435PaymentChannelCoded("2")) // Automatic clearing house debit
        );

        return orderResponse;
    }
}