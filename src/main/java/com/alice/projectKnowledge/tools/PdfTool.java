package com.alice.projectKnowledge.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfTool {
	
	/**
	 * PDF转HTML
	 * @param fileName 文件名
	 * @param sourcePath PDF文件路径
	 * @param targetPath HTML存放路径
	 * @param htmlName HTML文件名
	 * @throws InvalidPasswordException
	 * @throws IOException
	 */
	public static void toHtml(String fileName,String sourcePath,String targetPath,String htmlName) throws InvalidPasswordException, IOException {
		toHtml(new File(sourcePath + fileName),targetPath,htmlName);
	}

	/**
	 * PDF转HTML
	 * @param pdf PDF文件对象
	 * @param targetPath HTML存放路径
	 * @param htmlName HTML文件名
	 * @throws InvalidPasswordException
	 * @throws IOException
	 */
	public static void toHtml(File pdf,String targetPath,String htmlName) throws InvalidPasswordException, IOException{
		BufferedImage image;
		FileOutputStream out;
		FileOutputStream fos;
		StringBuffer buffer = new StringBuffer();
		PDDocument document = PDDocument.load(pdf);
		int pageNumber = document.getNumberOfPages();
		PDFRenderer reader = new PDFRenderer(document);
		buffer.append("<!doctype html>\r\n");
    	buffer.append("<head>\r\n");
    	buffer.append("<meta charset=\"UTF-8\">\r\n");
    	buffer.append("</head>\r\n");
    	buffer.append("<body style=\"background-color:gray;\">\r\n");
    	buffer.append("<style>\r\n");
    	buffer.append("img {background-color:#fff; text-align:center; width:100%; max-width:100%;margin-top:6px;}\r\n");
    	buffer.append("</style>\r\n");
		for(int i=0 ; i < pageNumber; i++){
	    	//image = new PDFRenderer(document).renderImageWithDPI(i,130,ImageType.RGB);
	    	image = reader.renderImage(i, 1.5f);
	    	//生成图片,保存位置
	    	out = new FileOutputStream(targetPath + htmlName + "_" + i + ".jpg");
	    	ImageIO.write(image, "png", out); //使用png的清晰度
	    	//将图片路径追加到网页文件里
	    	buffer.append("<img src=\"" + htmlName + "_"  + i + ".jpg\"/>\r\n");
	    	image = null; out.flush(); out.close(); 
	    	//ImageUtils.scaleSize(new File(filePath + htmlName + "_"  + i + ".jpg"));  //压缩图片大小
    	}
		reader = null;
    	document.close();
    	buffer.append("</body>\r\n");
    	buffer.append("</html>");
    	fos = new FileOutputStream(targetPath+htmlName+".html");
    	fos.write(buffer.toString().getBytes());
    	fos.flush(); 
    	fos.close();
    	buffer.setLength(0);
	}
}
