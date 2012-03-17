package com.omf.om.core.mapping.extractor;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.omf.om.api.exception.MappingException;
import com.omf.om.api.mapping.EntityMapping;
import com.omf.om.core.mapping.EntityWithPrimitiveProperties;
import com.omf.om.core.mapping.EntityWithoutProperties;

public class EntityMappingExtractorImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected = MappingException.class)
	public void testEntityWithoutAnnotation() throws Exception {
		final EntityMapping mapping = new EntityMappingExtractorImpl().extract(getClass());
	}

	@Test(expected = MappingException.class)
	public void testEntityWithoutIdProperty() {
		EntityMapping mapping = new EntityMappingExtractorImpl().extract(EntityWithoutProperties.class);
	}

	@Test
	public void testEntityWithBasicProperties() {
		final EntityMapping mapping = new EntityMappingExtractorImpl().extract(EntityWithPrimitiveProperties.class);

		assertThat(mapping, notNullValue());
		assertEquals(EntityWithPrimitiveProperties.class, mapping.getTypeClass());
		assertThat(mapping.getPropertyMappings().getSize(), is(EntityWithPrimitiveProperties.NUMBER_OF_FIELDS));
	}

}