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

package org.energyos.espi.datacustodian.atom;


import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.io.FeedException;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.datacustodian.domain.ReadingType;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.energyos.espi.datacustodian.Asserts.assertXpathValue;
import static org.energyos.espi.datacustodian.utils.factories.EspiFactory.newReadingType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReadingTypeEntryTests extends XMLTest {

    @Test
    public void constructsReadingTypeEntry() throws FeedException, SAXException, IOException, XpathException {
        ReadingType readingType = newReadingType();
        readingType.setId(96L);
        ReadingTypeEntry entry = new ReadingTypeEntry(readingType);
        assertNotNull("entry was null", entry);

        assertEquals("ReadingType/96", entry.getSelfLink().getHref());
        assertEquals("ReadingType", entry.getUpLink().getHref());

        Content content = (Content)entry.getContents().get(0);
        String xmlContent = content.getValue();

        assertXpathExists("ReadingType", xmlContent);
        assertXpathValue("accumulationBehaviour", "ReadingType/accumulationBehaviour", xmlContent);
        assertXpathValue("commodity", "ReadingType/commodity", xmlContent);
        assertXpathValue("dataQualifier", "ReadingType/dataQualifier", xmlContent);
        assertXpathValue("10", "ReadingType/intervalLength", xmlContent);
        assertXpathValue("kind", "ReadingType/kind", xmlContent);
        assertXpathValue("phase", "ReadingType/phase", xmlContent);
        assertXpathValue("multiplier", "ReadingType/powerOfTenMultiplier", xmlContent);
        assertXpathValue("uom", "ReadingType/uom", xmlContent);
        assertXpathValue("currency", "ReadingType/currency", xmlContent);
        assertXpathValue("tou", "ReadingType/tou", xmlContent);
        assertXpathValue("aggregate", "ReadingType/aggregate", xmlContent);
        assertXpathValue("1", "ReadingType/argument/numerator", xmlContent);
        assertXpathValue("3", "ReadingType/argument/denominator", xmlContent);
        assertXpathValue("1", "ReadingType/interharmonic/numerator", xmlContent);
        assertXpathValue("6", "ReadingType/interharmonic/denominator", xmlContent);
    }
}