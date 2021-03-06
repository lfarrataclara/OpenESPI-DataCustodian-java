package org.energyos.espi.datacustodian.repositories.jpa;

import org.energyos.espi.datacustodian.domain.ThirdParty;
import org.energyos.espi.datacustodian.repositories.ThirdPartyRepository;
import org.energyos.espi.datacustodian.utils.factories.EspiFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class ThirdPartyRepositoryImplTest {

    @Autowired
    private ThirdPartyRepository repository;

    @Test
    public void persist() throws Exception {
        ThirdParty thirdParty = EspiFactory.newThirdParty();
        repository.persist(thirdParty);

        assertNotNull(thirdParty.getId());
    }
    @Test
    public void findById() throws Exception {
        ThirdParty thirdParty = EspiFactory.newThirdParty();
        repository.persist(thirdParty);

        assertEquals(thirdParty.getId(), repository.findById(thirdParty.getId()).getId());
    }

    @Test
    public void findAll_returnsAllThirdParties() throws Exception {
        assertTrue("Repository has no data", repository.findAll().size() > 0);
    }
}
