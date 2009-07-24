/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package org.apache.openjpa.persistence.util;

import java.sql.Date;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.spi.LoadState;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.ProviderUtil;

import org.apache.openjpa.persistence.PersistenceProviderImpl;
import org.apache.openjpa.persistence.test.SingleEMFTestCase;

public class TestPersistenceUnitUtil extends SingleEMFTestCase{
    
    public void setUp() {
        setUp(CLEAR_TABLES, EagerEntity.class, LazyEmbed.class,
            LazyEntity.class, EagerEmbed.class);
    }

    /*
     * Verifies an entity and its persistent attributes are in the proper 
     * load state.
     */
    public void testIsLoadedEager() {        
        verifyIsLoadedEagerState(true);
    }

    /*
     * Verifies an entity and its persistent attributes are in the proper 
     * not loaded state.
     */
    public void testNotLoadedLazy() {
        verifyIsLoadedEagerState(false);       
    }

    /*
     * Verifies an entity and its persistent attributes are in the proper 
     * loaded state.
     */
    public void testIsLoadedLazy() {        
        verifyIsLoadedLazyState(true);
    }

    /*
     * Verifies an entity and its persistent attributes are in the proper 
     * NOT_LOADED state.
     */
    public void testNotLoadedEager() {
        verifyIsLoadedEagerState(false);       
    }

    
    private void verifyIsLoadedEagerState(boolean loaded) {
        PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();
        assertSame(emf, puu);
        EntityManager em = emf.createEntityManager();
        EagerEntity ee = createEagerEntity();
        
        // Vfy LoadState is false for the unmanaged entity
        assertEquals(false, puu.isLoaded(ee));
        assertEquals(false, puu.isLoaded(ee, 
            "id"));
        
        em.getTransaction().begin();
        em.persist(ee);
        em.getTransaction().commit();
        em.clear();
        
        if (loaded)
            ee = em.find(EagerEntity.class, ee.getId());
        else
            ee = em.getReference(EagerEntity.class, ee.getId());
        
        assertEquals(loaded, puu.isLoaded(ee));
        assertEquals(loaded, puu.isLoaded(ee, "id"));
        assertEquals(loaded, puu.isLoaded(ee, "name"));
        assertEquals(loaded, puu.isLoaded(ee, "eagerEmbed"));
        assertEquals(false, puu.isLoaded(ee, "transField"));
        
        em.close();
    }

    private void verifyIsLoadedLazyState(boolean loaded) {
        PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();
        assertSame(emf, puu);
        EntityManager em = emf.createEntityManager();
        LazyEntity le = createLazyEntity();
        
        // Vfy LoadState is false for the unmanaged entity
        assertEquals(false, puu.isLoaded(le));
        assertEquals(false, puu.isLoaded(le,"id"));
        
        em.getTransaction().begin();
        em.persist(le);
        em.getTransaction().commit();
        em.clear();
        
        // Use find or getReference based upon expected state
        if (loaded)
            le = em.find(LazyEntity.class, le.getId());
        else
            le = em.getReference(LazyEntity.class, le.getId());
        
        assertEquals(loaded, puu.isLoaded(le));
        assertEquals(loaded, puu.isLoaded(le, "id"));

        // Name is lazy fetch so it should not be loaded
        assertEquals(false, puu.isLoaded(le, "name"));
        assertEquals(loaded, puu.isLoaded(le, "lazyEmbed"));
        assertEquals(false, puu.isLoaded(le, "transField"));
        
        em.close();
    }

    /*
     * Verifies that an entity and attributes are considered loaded if they
     * are assigned by the application.
     */
    public void testIsApplicationLoaded() {
        PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();
        assertSame(emf, puu);
        EntityManager em = emf.createEntityManager();
        EagerEntity ee = createEagerEntity();
        
        em.getTransaction().begin();
        em.persist(ee);
        em.getTransaction().commit();
        em.clear();
        
        ee = em.getReference(EagerEntity.class, ee.getId());
        assertNotNull(ee);
        assertEagerLoadState(puu, ee, false);
        
        ee.setName("AppEagerName");
        EagerEmbed emb = createEagerEmbed();
        ee.setEagerEmbed(emb);
        // Assert fields are loaded via application loading
        assertEagerLoadState(puu, ee, true);
        // Vfy the set values are applied to the entity
        assertEquals("AppEagerName", ee.getName());
        assertEquals(emb, ee.getEagerEmbed());
        
        em.close();
    }
        
    private EagerEntity createEagerEntity() {
        EagerEntity ee = new EagerEntity();
        ee.setId(new Random().nextInt());
        ee.setName("EagerEntity");
        EagerEmbed emb = createEagerEmbed();
        ee.setEagerEmbed(emb);
        return ee;
    }

    private EagerEmbed createEagerEmbed() {
        EagerEmbed emb = new EagerEmbed();
        emb.setEndDate(new Date(System.currentTimeMillis()));
        emb.setStartDate(new Date(System.currentTimeMillis()));
        return emb;
    }

    private LazyEntity createLazyEntity() {
        LazyEntity le = new LazyEntity();
        le.setId(new Random().nextInt());
        le.setName("LazyEntity");
        LazyEmbed emb = new LazyEmbed();
        emb.setEndDate(new Date(System.currentTimeMillis()));
        emb.setStartDate(new Date(System.currentTimeMillis()));
        le.setLazyEmbed(emb);
        return le;
    }
    
    private void assertEagerLoadState(PersistenceUnitUtil pu, Object ent, 
        boolean state) {
        assertEquals(state, pu.isLoaded(ent));
        assertEquals(state, pu.isLoaded(ent, "id"));
        assertEquals(state, pu.isLoaded(ent, "name"));
        assertEquals(state, pu.isLoaded(ent, "eagerEmbed"));
        assertEquals(false, pu.isLoaded(ent, "transField"));
    }
}
