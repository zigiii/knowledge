package com.alice.projectKnowledge.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextTool {

	public static String readWordStr(File txt) throws IOException {
		StringBuffer article = new StringBuffer();
		if(txt.getName().toLowerCase().endsWith(".txt")) {
			String line = new String();
			BufferedReader buffer_read = new BufferedReader(new InputStreamReader(new FileInputStream(txt),"UTF-8"));
			while((line = buffer_read.readLine()) != null){  
				line = line.trim();
				 if("".equals(line)){
					 continue;
				 }
				 article.append(line);
			}
			buffer_read.close();
		}
		return article.toString();
	}
}
