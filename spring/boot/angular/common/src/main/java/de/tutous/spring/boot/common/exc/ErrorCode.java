package de.tutous.spring.boot.common.exc;

import java.util.Objects;

import org.springframework.http.HttpStatus;

public interface ErrorCode<ID> {

	public ID getId();

	public String getMessage(String[] args);

	public HttpStatus getHttpStatus();

	public default Object[] toSaveArgs(String[] args) {
		String[] saveArgs = new String[] { //
				"arg0", "arg1", "arg2", "arg3", "arg4", //
				"arg5", "arg6", "arg7", "arg8", "arg9" };
		if (Objects.nonNull(args) && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				saveArgs[i] = args[i];
			}
		}
		return saveArgs;
	}

}
