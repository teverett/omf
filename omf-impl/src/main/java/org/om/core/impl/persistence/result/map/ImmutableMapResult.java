package org.om.core.impl.persistence.result.map;

import java.util.*;

import org.om.core.api.persistence.result.*;

public class ImmutableMapResult implements MapResult {
	private final Map<?, ?> result;

	public ImmutableMapResult(Map<?, ?> result) {
		this.result = result;
	}

	@Override
	public Map<?, ?> getValue() {
		return result;
	}

	@Override
	public boolean hasResult() {
		return true;
	}
}
