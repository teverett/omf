/*
 * Copyright 2012 Jakob Külzer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.om.core.impl.persistence.jcr;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.*;

import javax.inject.*;
import javax.jcr.*;

import org.junit.*;
import org.om.core.api.persistence.*;
import org.om.core.api.session.Session;
import org.om.core.api.session.factory.*;
import org.om.core.impl.entity.*;
import org.om.core.impl.persistence.jcr.test.*;

import com.google.guiceberry.junit4.*;

public class JcrPersistenceDelegate {
	@Rule
	public GuiceBerryRule guiceBerry = new GuiceBerryRule(TransientRepoTestEnv.class);
	@Inject
	private javax.jcr.Session jcrSession;
	@Inject
	private SessionFactory sessionFactory;
	private Node rootnode;

	@Before
	public void setUp() throws Exception {
		rootnode = jcrSession.getRootNode();
	}

	@Test
	public void testCollectionWithDifferingTargetAndImplTypes() throws Exception {
		final Node node = rootnode.addNode("collection");
		node.addNode("a").setProperty("value", "value a");
		node.addNode("b").setProperty("value", "value b");
		node.addNode("c").setProperty("value", "value c");
		final Session session = sessionFactory.getSession(new JcrPersistenceContext(jcrSession));
		final CollectionTestEntity entity = session.get(CollectionTestEntity.class, "collection");
		assertThat(entity, notNullValue());
		final List<MyInterface> list = entity.getListWithDifferingTargetAndImplType();
		assertThat(list, notNullValue());
		assertThat(list.size(), is(3));
		final MyInterface first = list.get(0);
		assertThat(first.getValue(), is("value a"));
		assertThat(first, instanceOf(MyInterfaceImpl.class));
	}

	@Test
	public void testCollectionWithStringValuesFromMultiValueProperty() throws Exception {
		final Node node = rootnode.addNode("collection");
		node.setProperty("listOfStrings", new String[] { "first value", "second value" });
		final Session session = sessionFactory.getSession(new JcrPersistenceContext(jcrSession));
		final CollectionTestEntity entity = session.get(CollectionTestEntity.class, "collection");
		assertThat(entity, notNullValue());
		final List<String> list = entity.getListOfStrings();
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		assertThat(list, containsInAnyOrder("first value", "second value"));
	}

	/**
	 * Testcase for retrieving {@link Map}s built of a node's properties.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMapWithStringValues() throws Exception {
		final Node node = rootnode.addNode("node").addNode("mapFromProperties");
		node.setProperty("foo", "bar");
		node.setProperty("chicken", "soup");
		final Session session = sessionFactory.getSession(new JcrPersistenceContext(jcrSession));
		final EntityWithMaps entityWithMaps = session.get(EntityWithMaps.class, "/node");
		final Map<String, String> map = entityWithMaps.getMap();
		assertThat(map, notNullValue());
		assertThat(map.size(), is(2));
		assertThat(map.containsKey("chicken"), is(true));
		assertThat(map.containsKey("foo"), is(true));
	}

	@Test
	public void testSimpleCollectionWithReferenceValues() throws Exception {
		final Node node = rootnode.addNode("collection");
		node.addNode("element1").setProperty("value", "first value");
		node.addNode("element2").setProperty("value", "second value");
		final Session session = sessionFactory.getSession(new JcrPersistenceContext(jcrSession));
		System.out.println("JcrPersistenceDelegateIT.test() Got session " + session);
		final CollectionTestEntity entity = session.get(CollectionTestEntity.class, "collection");
		assertThat(entity, notNullValue());
		final List<ChildEntity> list = entity.getList();
		assertThat(list, notNullValue());
		assertThat(list.size(), is(2));
		final ChildEntity childEntity = list.get(0);
		assertThat(childEntity, notNullValue());
		assertThat(childEntity.getValue(), is("first value"));
	}

	@Test
	public void testSimpleEntity() throws Exception {
		rootnode.addNode("entity");
		final PersistenceContext persistenceContext = new JcrPersistenceContext(jcrSession);
		final Session session = sessionFactory.getSession(persistenceContext);
		final TestEntity testEntity = session.get(TestEntity.class, "entity");
		assertThat(testEntity, notNullValue());
		// Disabled // ID does not map to a property any more //
		// assertThat(testEntity.getId(), is(""));
		assertThat(testEntity.getBlargh(), is(3131));
	}
}
