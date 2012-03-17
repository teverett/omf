package org.om.core.impl.session;

import org.om.core.api.mapping.EntityMapping;
import org.om.core.api.mapping.registry.MappingRegistry;
import org.om.core.api.persistence.PersistenceContext;
import org.om.core.api.persistence.PersistenceDelegate;
import org.om.core.api.persistence.PersistenceDelegateFactory;
import org.om.core.api.session.Session;
import org.om.core.api.session.proxy.ProxyFactory;

public class ImmutableSessionImpl implements Session {

	private final MappingRegistry mappingRegistry;

	private final ProxyFactory proxyFactory;

	private final PersistenceContext persistenceContext;

	private final PersistenceDelegateFactory persistenceDelegateFactory;

	public ImmutableSessionImpl(PersistenceContext persistenceContext, PersistenceDelegateFactory persistenceDelegateFactory, MappingRegistry mappingRegistry,
			ProxyFactory proxyFactory) {
		this.persistenceContext = persistenceContext;
		this.persistenceDelegateFactory = persistenceDelegateFactory;
		this.mappingRegistry = mappingRegistry;
		this.proxyFactory = proxyFactory;
	}

	public <T> T get(Class<T> clazz, Object id) {
		if (clazz == null)
			throw new NullPointerException("Class is null.");

		final EntityMapping entityMapping = mappingRegistry.getMapping(clazz);
		final PersistenceDelegate persistenceDelegate = persistenceDelegateFactory.create(this, id, entityMapping, persistenceContext);
		return (T) proxyFactory.create(this, entityMapping, persistenceDelegate);
	}
}