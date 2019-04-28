package org.fartpig.dbunit_extractor_web.util;

public class ResolverException extends RuntimeException {

	public ResolverException(String stage, String message, Throwable cause) {
		super(message, cause);
		this.stage = stage;
	}

	public ResolverException(String stage, String message) {
		super(message);
		this.stage = stage;
	}

	private String stage;

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

}
