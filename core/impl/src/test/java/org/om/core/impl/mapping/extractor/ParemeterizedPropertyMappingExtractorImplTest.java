package org.om.core.impl.mapping.extractor;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.om.core.api.annotation.PropertyMissingStrategy;
import org.om.core.api.exception.PropertyMissingException;
import org.om.core.api.mapping.PropertyMapping;
import org.om.core.impl.mapping.EntityWithPrimitiveProperties;

@RunWith(Parameterized.class)
public class ParemeterizedPropertyMappingExtractorImplTest {

	private final Class<?> type;
	private final String fieldName;
	private final String propertyName;
	private final String defaultValue;
	private final PropertyMissingStrategy missingStrategy;
	private final Class<? extends Exception> missingException;
	private final Class<?> propertyType;

	@Parameters
	public static List<Object[]> getParameters() {
		List<Object[]> list = new ArrayList<Object[]>();

		list.add(new Object[] { EntityWithPrimitiveProperties.class, "fieldWithDefaultSettings", String.class, "fieldWithDefaultSettings", "",
				PropertyMissingStrategy.ReturnNull, PropertyMissingException.class });
		list.add(new Object[] { EntityWithPrimitiveProperties.class, "fieldWithDefaultValue", String.class, "fieldWithDefaultValue", "1234",
				PropertyMissingStrategy.ReturnNull, PropertyMissingException.class });
		list.add(new Object[] { EntityWithPrimitiveProperties.class, "fieldWithCustomName", String.class, "customName", "", PropertyMissingStrategy.ReturnNull,
				PropertyMissingException.class });
		list.add(new Object[] { EntityWithPrimitiveProperties.class, "fieldWithMissingStrategy", String.class, "fieldWithMissingStrategy", "default value",
				PropertyMissingStrategy.DefaultValue, PropertyMissingException.class });
		list.add(new Object[] { EntityWithPrimitiveProperties.class, "fieldWithAllSettings", String.class, "differentCustomName", "custom default value",
				PropertyMissingStrategy.ThrowException, PropertyMissingException.class });
		list.add(new Object[] { EntityWithPrimitiveProperties.class, "primitiveInt", int.class, "primitiveInt", "", PropertyMissingStrategy.ReturnNull,
				PropertyMissingException.class });
		list.add(new Object[] { EntityWithPrimitiveProperties.class, "complexFloat", Float.class, "complexFloat", "", PropertyMissingStrategy.ReturnNull,
				PropertyMissingException.class });

		return list;
	}

	public ParemeterizedPropertyMappingExtractorImplTest(Class<?> type, String fieldName, Class<?> propertyType, String propertyName, String defaultValue,
			PropertyMissingStrategy missingStrategy, Class<? extends Exception> missingException) {
		this.type = type;
		this.fieldName = fieldName;
		this.propertyType = propertyType;
		this.propertyName = propertyName;
		this.defaultValue = defaultValue;
		this.missingStrategy = missingStrategy;
		this.missingException = missingException;
	}

	@Test
	public void test() throws Exception {
		Field field = type.getDeclaredField(fieldName);

		PropertyMapping mapping = new PropertyMappingExtractorImpl().fromField(field);

		assertThat(mapping, notNullValue());
		assertThat(mapping.getFieldname(), is(fieldName));
		assertThat(mapping.getPropertyName(), is(propertyName));
		assertThat(mapping.getDefaultValue(), is(defaultValue));
		assertThat(mapping.getMissingStrategy(), is(missingStrategy));
		assertThat(mapping.isPrimitiveOrWrappedType(), is(true));
		assertEquals(propertyType, mapping.getPropertyType());
		assertEquals(missingException, mapping.getMissingException());
	}
}