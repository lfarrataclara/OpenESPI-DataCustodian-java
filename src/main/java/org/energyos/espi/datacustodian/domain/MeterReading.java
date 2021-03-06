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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2013.08.27 at 01:43:57 PM EDT
//


package org.energyos.espi.datacustodian.domain;

import org.energyos.espi.datacustodian.models.atom.adapters.GenericAdapter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * Set of values obtained from the meter.
 * <p/>
 * <p>Java class for MeterReading complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="MeterReading">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}IdentifiedObject">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement(name="MeterReading")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeterReading")
@Entity
@Table(name = "meter_readings")
@XmlJavaTypeAdapter(GenericAdapter.class)
public class MeterReading
    extends IdentifiedObject
{
    @OneToMany(mappedBy = "meterReading", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @XmlTransient
    private List<IntervalBlock> intervalBlocks = new ArrayList<>();

    public List<IntervalBlock> getIntervalBlocks() {
        return intervalBlocks;
    }

    public void setIntervalBlocks(List<IntervalBlock> intervalBlocks) {
        this.intervalBlocks = intervalBlocks;
    }

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "usage_point_id")
    private UsagePoint usagePoint;

    @XmlTransient
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reading_type_id")
    private ReadingType readingType;

    public UsagePoint getUsagePoint() {
        return usagePoint;
    }

    public void setUsagePoint(UsagePoint usagePoint) {
        this.usagePoint = usagePoint;
    }

    public void addIntervalBlock(IntervalBlock intervalBlock) {
        intervalBlock.setMeterReading(this);
        intervalBlocks.add(intervalBlock);
    }

    public ReadingType getReadingType() {
        return readingType;
    }

    public void setReadingType(ReadingType readingType) {
        this.readingType = readingType;
    }
}
