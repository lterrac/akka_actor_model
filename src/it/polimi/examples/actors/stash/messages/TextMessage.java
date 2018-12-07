package it.polimi.examples.actors.stash.messages;

import java.io.Serializable;

public class TextMessage implements Serializable {
	private static final long serialVersionUID = -7391447825693538359L;

	private final String content;
	private final boolean reply;

	public TextMessage(String content) {
		super();
		this.content = content;
		this.reply = false;
	}

	public TextMessage(TextMessage msg) {
		super();
		this.content = msg.content;
		this.reply = true;
	}

	public final String getContent() {
		return content;
	}

	public final boolean reply() {
		return reply;
	}

	@Override
	public String toString() {
		return "TextMessage: " + content;
	}

}