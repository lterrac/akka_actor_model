package it.polimi.examples.actors.response.messages;

import java.io.Serializable;

public class ReplyMsg implements Serializable {
	private static final long serialVersionUID = -5316980884988577991L;

	private final String content;

	public ReplyMsg(String content) {
		this.content = content;
	}

	public final String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "ReplyMsg[" + content + "]";
	}

}