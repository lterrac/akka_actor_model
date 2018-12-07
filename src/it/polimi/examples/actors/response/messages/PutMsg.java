package it.polimi.examples.actors.response.messages;

import java.io.Serializable;

public class PutMsg implements Serializable {
	private static final long serialVersionUID = -2176685838574710317L;

	private final String key;
	private final String val;

	public PutMsg(String key, String val) {
		this.key = key;
		this.val = val;
	}

	public final String getKey() {
		return key;
	}

	public final String getVal() {
		return val;
	}

	@Override
	public String toString() {
		return "PutMsg[" + key + ", " + val + "]";
	}
}