package com.alice.projectKnowledge.tools;

import java.util.UUID;

public class CommonTool {

	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
