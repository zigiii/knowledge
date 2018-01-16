package com.alice.projectKnowledge.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class LoadTool {

	/**
	 * 写入上传文件
	 * @param files 文件
	 * @param targetPath 目标路径
	 * @throws IOException
	 */
	public static void upload(File[] files,String targetPath) throws IOException{
		String fileName = null;
		for(File file : files){
			fileName = file.getName();
	        //拿到输出流，同时重命名上传的文件
	        FileOutputStream os = new FileOutputStream(targetPath + fileName);
	        //拿到上传文件的输入流
	        FileInputStream in = new FileInputStream(file);
	        //以写字节的方式写文件
	        int b = 0;
	        while ((b = in.read()) != -1) {
	            os.write(b);
	        }
	        os.flush();
	        os.close();
	        in.close();
		}
	}
	
	/**
	 * 写入上传文件
	 * @param files 文件
	 * @param targetPath 目标路径
	 * @throws IOException
	 */
	public static void upload(MultipartFile[] files,String targetPath) throws IOException{
		String fileName = null;
		for(MultipartFile file : files){
			fileName = file.getName();
	        //拿到输出流，同时重命名上传的文件
	        FileOutputStream os = new FileOutputStream(targetPath + fileName);
	        //拿到上传文件的输入流
	        FileInputStream in = (FileInputStream) file.getInputStream();
	        //以写字节的方式写文件
	        int b = 0;
	        while ((b = in.read()) != -1) {
	            os.write(b);
	        }
	        os.flush();
	        os.close();
	        in.close();
		}
	}
}
