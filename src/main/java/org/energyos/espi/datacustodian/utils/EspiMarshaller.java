/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.datacustodian.utils;

import com.sun.syndication.io.FeedException;
import org.energyos.espi.datacustodian.domain.IdentifiedObject;

import javax.xml.bind.*;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

public class EspiMarshaller {

    private EspiMarshaller() {
    }

    private static JAXBContext jaxbContext;
    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;

    public static <T extends IdentifiedObject> JAXBElement<T> unmarshal(String xml) throws JAXBException {
        return (JAXBElement<T>) getUnmarshaller().unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }

    public static String marshal(IdentifiedObject entity) throws FeedException {
        StringWriter sw = new StringWriter();

        try {
            getMarshaller().marshal(entity, sw);
        } catch (JAXBException e) {
            throw new FeedException("Invalid " + entity.getClass().toString() + ". Could not serialize.");
        }
        return sw.toString();
    }

    private static Marshaller getMarshaller() throws JAXBException {
        if (marshaller == null) {
            JAXBContext jaxbContext = getJaxbContext();
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        }

        return marshaller;
    }

    private static Unmarshaller getUnmarshaller() throws JAXBException {
        if (unmarshaller == null) {
            JAXBContext jaxbContext = getJaxbContext();
            unmarshaller = jaxbContext.createUnmarshaller();
        }

        return unmarshaller;
    }

    private static JAXBContext getJaxbContext() throws JAXBException {
        if (jaxbContext == null) {
            jaxbContext = JAXBContext.newInstance("org.energyos.espi.datacustodian.domain");
        }

        return jaxbContext;
    }
}
