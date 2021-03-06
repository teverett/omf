package org.om.core.impl.persistence.interceptor.handler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.om.core.api.mapping.*;
import org.om.core.api.persistence.*;
import org.om.core.impl.mapping.extractor.*;
import org.om.core.impl.test.*;

public class PrimitivePropertyHandlerTest {
	private final PrimitiveHandler handler = new PrimitiveHandler();

	@Test(expected = NumberFormatException.class)
	public void testIntegerFieldWithInvalidStringInput() {
		final EntityMapping entityMapping = new EntityMappingExtractorImpl().extract(EntityWithPrimitiveProperties.class);
		final MappedField field = entityMapping.getByFieldName("primitiveInt");
		final PersistenceAdapter delegate = new TestingPassThroughPersistenceAdapter("BAM");
		final Integer retrieve = (Integer) handler.retrieve(field, delegate);
		assertThat(retrieve, notNullValue());
		assertThat(retrieve, is(1234));
	}

	@Test
	public void testIntegerFieldWithStringInput() {
		final EntityMapping entityMapping = new EntityMappingExtractorImpl().extract(EntityWithPrimitiveProperties.class);
		final MappedField field = entityMapping.getByFieldName("primitiveInt");
		final PersistenceAdapter delegate = new TestingPassThroughPersistenceAdapter("1234");
		final Integer retrieve = (Integer) handler.retrieve(field, delegate);
		assertThat(retrieve, notNullValue());
		assertThat(retrieve, is(1234));
	}

	@Test
	public void testNullInput() {
		final EntityMapping entityMapping = new EntityMappingExtractorImpl().extract(EntityWithPrimitiveProperties.class);
		final MappedField field = entityMapping.getByFieldName("fieldWithDefaultSettings");
		assertThat(field, notNullValue());
		final PersistenceAdapter delegate = new TestingPassThroughPersistenceAdapter(null);
		final Object retrieve = handler.retrieve(field, delegate);
		assertThat(retrieve, nullValue());
	}

	@Test
	public void testWithStringInput() {
		final EntityMapping entityMapping = new EntityMappingExtractorImpl().extract(EntityWithPrimitiveProperties.class);
		final MappedField field = entityMapping.getByFieldName("fieldWithDefaultSettings");
		final PersistenceAdapter delegate = new TestingPassThroughPersistenceAdapter("I'm a String!");
		final String retrieve = (String) handler.retrieve(field, delegate);
		assertThat(retrieve, notNullValue());
		assertThat(retrieve, is("I'm a String!"));
	}
}
