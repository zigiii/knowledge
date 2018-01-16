package com.alice.projectKnowledge.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.w3c.dom.Document;

import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.core.FileURIResolver;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;

public class WordTool {

	/**
	 * WORD转HTML
	 * @param wordName word文件名
	 * @param sourcePath word文件路径
	 * @param targetPath HTML存放路径
	 * @param htmlName HTML文件名
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void wordToHtml(String wordName,String sourcePath,String targetPath,String htmlName) throws IOException, ParserConfigurationException, TransformerException {
		wordToHtml(new File(sourcePath + wordName), targetPath, htmlName);
	}
	
	/**
	 * 
	 * @param word word文件对象
	 * @param targetPath HTML存放路径
	 * @param htmlName HTML文件名
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void wordToHtml(File word,String targetPath,String htmlName) throws IOException, ParserConfigurationException, TransformerException {
		  String fileName = word.getName();
		  if(fileName.toLowerCase().endsWith(".doc")) { //2003
			  word2003ToHtml(word, targetPath, htmlName);
		  }else if(fileName.toLowerCase().endsWith(".docx")) {
			  word2007ToHtml(word, targetPath, htmlName); //2007
		  }
	}
	
	/**
	 * 读取word内容
	 * @param word 文档对象
	 * @param type doc或者docx
	 */
	public static String readWordStr(File word) throws FileNotFoundException, IOException, XmlException, OpenXML4JException{
		String fileName = word.getName();
		String wordText = "";
		if(fileName.toLowerCase().endsWith(".doc")){
			WordExtractor wordExtrator = new WordExtractor(new FileInputStream(word));
			wordText = wordExtrator.getText();
			wordExtrator.close();
		}else if(fileName.toLowerCase().endsWith(".docx")){
			OPCPackage opcPackage = POIXMLDocument.openPackage(word.getAbsolutePath());  
	        POIXMLTextExtractor wordExtrator = new XWPFWordExtractor(opcPackage);
	        wordText = wordExtrator.getText();
	        wordExtrator.close();
		}
		wordText = wordText.replaceAll(" +", " ");
		return wordText;
	}
	
	@SuppressWarnings("deprecation")
	private static void word2007ToHtml(File word,String targetPath,String htmlName) throws IOException{
		// 1) 加载word文档生成 XWPFDocument对象  
        InputStream in = new FileInputStream(word);  
        XWPFDocument document = new XWPFDocument(in);  

        // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)  
        File imageFolderFile = new File(targetPath+"/images/");  
        if(!imageFolderFile.exists()){//图片目录不存在则创建
        	imageFolderFile.mkdirs();
        }
        XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File(targetPath + "/images/")));  
        options.setExtractor(new FileImageExtractor(imageFolderFile));  
        options.setIgnoreStylesIfUnused(false);  
        options.setFragment(true);  
          
        // 3) 将 XWPFDocument转换成XHTML  
        OutputStream out = new FileOutputStream(new File(targetPath + htmlName));  
        XHTMLConverter.getInstance().convert(document, out, options);
	}
	
	private static void word2003ToHtml(File word,String filePath,String htmlName) throws IOException, ParserConfigurationException, TransformerException {
        InputStream input = new FileInputStream(word);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
//                File imgPath = new File(imagepath);
//                if(!imgPath.exists()){//图片目录不存在则创建
//                    imgPath.mkdirs();
//                }
                File file = new File(filePath + "/images/" + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return suggestedName;
            }
        });
        
        //解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        
        File htmlFile = new File(filePath + htmlName);
        OutputStream outStream = new FileOutputStream(htmlFile);
        
        //也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
//        OutputStream outStream = new BufferedOutputStream(baos);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        
        serializer.transform(domSource, streamResult);

        outStream.close();
        System.out.println("转换完成");
	}
}
