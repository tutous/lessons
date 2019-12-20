package de.tutous.spring.boot.common.log;

public interface HasLogger {

	default Logger logger() {
		return Logger.getLogger(getClass());
	}

}
