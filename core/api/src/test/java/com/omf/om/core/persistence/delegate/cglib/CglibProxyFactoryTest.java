package com.omf.om.core.persistence.delegate.cglib;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.omf.om.api.mapping.EntityMapping;
import com.omf.om.api.persistence.PersistenceContext;
import com.omf.om.api.persistence.PersistenceDelegateFactory;
import com.omf.om.core.mapping.EntityWithPlainProperties;
import com.omf.om.core.mapping.extractor.EntityMappingExtractorImpl;
import com.omf.om.core.persistence.delegate.TestingDelegateFactory;

@RunWith(JMock.class)
public class CglibProxyFactoryTest {

	private Mockery mockery = new JUnit4Mockery();

	@Test
	public void test() {
		final EntityMapping mapping = new EntityMappingExtractorImpl().extract(EntityWithPlainProperties.class);
		final PersistenceDelegateFactory delegateFactory = new TestingDelegateFactory();
		final PersistenceContext persistenceContext = null;
		final CglibProxyFactory proxyFactory = new CglibProxyFactory(delegateFactory);

		EntityWithPlainProperties proxy = (EntityWithPlainProperties) proxyFactory.create(null, persistenceContext, mapping);

		assertThat(proxy, notNullValue());
		String fieldWithDefaultSettings = proxy.getFieldWithDefaultSettings();

		assertThat(fieldWithDefaultSettings, notNullValue());
		assertThat(fieldWithDefaultSettings, is(""));

		fail("Not yet implemented");
	}
}