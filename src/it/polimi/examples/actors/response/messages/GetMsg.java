package it.polimi.examples.actors.response.messages;

import java.io.Serializable;

public class GetMsg implements Serializable {
	private static final long serialVersionUID = -6257513104356359295L;

	private final String key;

	public GetMsg(String key) {
		this.key = key;
	}

	public final String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "GetMsg[" + key + "]";
	}
}