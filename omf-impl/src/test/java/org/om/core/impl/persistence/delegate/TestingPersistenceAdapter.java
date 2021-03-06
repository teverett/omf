package org.om.core.impl.persistence.delegate;

import org.om.core.api.exception.*;
import org.om.core.api.mapping.*;
import org.om.core.api.mapping.field.*;
import org.om.core.api.persistence.*;
import org.om.core.api.persistence.request.*;
import org.om.core.api.persistence.result.*;
import org.om.core.impl.persistence.result.*;

/**
 * @author tome
 * @author Jakob Külzer
 */
public class TestingPersistenceAdapter implements PersistenceAdapter {
	private final EntityMapping entityMapping;
	private final TestingPersistenceContext persistenceContext;

	public TestingPersistenceAdapter(EntityMapping entityMapping, PersistenceContext persistenceContext) {
		this.entityMapping = entityMapping;
		this.persistenceContext = (TestingPersistenceContext) (persistenceContext == null ? new TestingPersistenceContext() : persistenceContext);
	}

	public TestingPersistenceAdapter addProperty(String propertyName, Object value) {
		persistenceContext.addProperty(propertyName, value);
		return this;
	}

	public boolean canProvide(Mapping mapping) {
		return false; // persistenceContext.hasProperty(mapping.get());
	}

	@Override
	public void delete() throws ObjectMapperException {
		// do nothing
	}

	@Override
	public CollectionResult getCollection(CollectionMapping collectionMapping) {
		// TODO Auto-generated method stub
		return null;
	}

	public EntityMapping getEntityMapping() {
		return entityMapping;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public MapResult getMapResult(CollectionMapping collectionMapping) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceResult getProperty(PersistenceRequest request) {
		if (!persistenceContext.hasProperty(request.getPath())) {
			return new NoValuePersistenceResult();
		}
		return new ImmutablePersistenceResult(persistenceContext.getProperty(request.getPath()));
	}

	@Override
	public PersistenceResult getProperty(PropertyMapping propertyMapping) {
		if (!persistenceContext.hasProperty(propertyMapping.getPropertyName())) {
			final MappedField mappedField = entityMapping.getMappedFields().getFieldByMapping(propertyMapping);
			return MissingPersistenceResult.createMissing(mappedField);
		}
		return new ImmutablePersistenceResult(persistenceContext.getProperty(propertyMapping));
	}

	@Override
	public Object resolve(String id) {
		return id;
	}

	@Override
	public void setProperty(PropertyMapping propertyMapping, Object object) throws ObjectMapperException {
		persistenceContext.setProperty(propertyMapping, object);
	}
}
