package com.bt.creditappservices.container.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import com.bt.creditappservices.model.DivisionEntity;
import com.bt.creditappservices.repository.DivisionsRepository;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mvishwanath
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DivisionsRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private DivisionsRepository divisionRepository;

  private DivisionEntity firstEntity;
  private DivisionEntity secondEntity;

  @Before
  public void setUp() throws Exception{
    // given
    firstEntity = new DivisionEntity();
    firstEntity.setName("user1");
    firstEntity.setTenantId("10");
    firstEntity.setLastModifiedBy("user1");
    firstEntity.setDisplayName("XYZ company");
    firstEntity.setDescription("XYZ description");
    firstEntity.setCreatedBy("user1");
    entityManager.persist(firstEntity);
    entityManager.flush();

    secondEntity = new DivisionEntity();
    secondEntity.setName("user2");
    secondEntity.setTenantId("10");
    secondEntity.setLastModifiedBy("user2");
    secondEntity.setDisplayName("PBC");
    secondEntity.setDescription("PBC company description");
    secondEntity.setCreatedBy("user2");
    entityManager.persist(secondEntity);
    entityManager.flush();
  }

  @Test
  public void testFindAllDivisionsByTenantId() throws Exception {
    // when
    List<DivisionEntity> divisions = this.divisionRepository.findAllDivisionsByTenantId("10");

    //then
    assertThat(divisions.size()).isEqualTo(2);
    assertThat(divisions.get(0)).isEqualTo(firstEntity);
    assertThat(divisions.get(1)).isEqualTo(secondEntity);
  }

  @Test
  public void testExistsByTenantId() throws Exception {
    // when
    boolean divisions = this.divisionRepository.existsByTenantId("1");

    //then
    assertTrue(divisions);
  }

}
