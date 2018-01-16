package com.alice.projectKnowledge.tools;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ImageTool {

	public static void scaleSize(File file) throws IOException {
		Thumbnails.of(file).
        scale(1f).
        outputQuality(0.3f).
        toFile(file.getAbsoluteFile());
	}
}
