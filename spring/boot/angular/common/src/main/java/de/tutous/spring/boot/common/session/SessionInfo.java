package de.tutous.spring.boot.common.session;

import java.util.UUID;

public class SessionInfo {

	private UUID uuid;
	private String currentRequestURI;
	private String currentRequestMethod;

	public SessionInfo(String currentRequestURI, String currentRequestMethod) {
		this.uuid = UUID.randomUUID();
		this.currentRequestURI = currentRequestURI;
		this.currentRequestMethod = currentRequestMethod;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setCurrentRequestURI(String currentRequestURI) {
		this.currentRequestURI = currentRequestURI;
	}

	public String getCurrentRequestURI() {
		return currentRequestURI;
	}

	public String getCurrentRequestMethod() {
		return currentRequestMethod;
	}

	public void setCurrentRequestMethod(String currentRequestMethod) {
		this.currentRequestMethod = currentRequestMethod;
	}

}
