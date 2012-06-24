package org.om.core.impl.persistence.delegate;

import org.om.core.api.exception.ObjectMapperException;
import org.om.core.api.mapping.EntityMapping;
import org.om.core.api.mapping.PropertyMapping;
import org.om.core.api.persistence.PersistenceContext;
import org.om.core.api.persistence.PersistenceDelegate;

/**
 * @author tome
 * @author Jakob Külzer
 */
public class TestingPersistenceDelegate implements PersistenceDelegate {

	private final EntityMapping entityMapping;

	private final TestingPersistenceContext persistenceContext;

	public TestingPersistenceDelegate(EntityMapping entityMapping, PersistenceContext persistenceContext) {
		this.entityMapping = entityMapping;
		this.persistenceContext = (TestingPersistenceContext) (persistenceContext == null ? new TestingPersistenceContext() : persistenceContext);
	}

	public EntityMapping getEntityMapping() {
		return entityMapping;
	}

	public Object getProperty(PropertyMapping propertyMapping) throws ObjectMapperException {
		return persistenceContext.getProperty(propertyMapping);
	}

	public TestingPersistenceDelegate addProperty(String propertyName, Object value) throws ObjectMapperException {
		persistenceContext.addProperty(propertyName, value);
		return this;
	}

	public boolean hasProperty(PropertyMapping mapping) throws ObjectMapperException {
		return persistenceContext.hasProperty(mapping.getPropertyName());
	}

	public void setProperty(PropertyMapping propertyMapping, Object object) throws ObjectMapperException {
		persistenceContext.setProperty(propertyMapping, object);
	}

	public void delete() throws ObjectMapperException {
		// do nothing
	}
}