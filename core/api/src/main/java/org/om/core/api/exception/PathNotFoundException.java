package org.om.core.api.exception;

import org.om.core.api.path.Path;

public class PathNotFoundException extends ObjectMapperException {

	private static final long serialVersionUID = -8842387827650563782L;

	private final Path path;

	public PathNotFoundException(Path path) {
		super(path.toString() + " not found.");
		this.path = path;
	}

	public PathNotFoundException(Path path, String message) {
		super(message);
		this.path = path;
	}

	public PathNotFoundException(Path path, String message, Throwable cause) {
		super(message, cause);
		this.path = path;
	}

	public PathNotFoundException(Path path, Throwable cause) {
		super(cause);
		this.path = path;
	}

	public Path getPath() {
		return path;
	}
}
