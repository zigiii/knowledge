package com.alice.projectKnowledge.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

public class LuceneTool {
	//停用词
	private String[] stopWords = new String[]{"和","的","了","呢","是","吧","吗","，","。","！","：","、","及","等","一","以上","台","个","；","对","则","有",","};
	
	/**
	 * 使用SmartChineseAnalyzer分词器，暂不支持其他分词器
	 */
	public LuceneTool() {
		charArraySet = new CharArraySet(0, true);
		for(int i = 0, size = stopWords.length; i < size; i++){
			charArraySet.add(stopWords[i]);
		}
		analyzer = new SmartChineseAnalyzer(charArraySet);
	}
	
	public static void createIndex(File[] files,File indexDir) throws IOException, XmlException, OpenXML4JException {
		for(File file : files) {
			createFileIndex(file, indexDir);
		}
	}
	
	/**
	 * 查询内容
	 * @param strList 查询词语
	 * @param field 索引属性
	 * @param indexDir 索引目录对象
	 * @param number 查询条数
	 * @param isAccurate 是否精确查询
	 * @param isRed 是否关键词加红
	 */
	public Map<String, Object> searcher(String[] strList,String field,File indexDir,int number,boolean isAccurate,boolean isRed) throws IOException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		IndexSearcher searcher = createIndexSearcher(indexDir);
		Query query = null;
		Document docment = null;
		String fileName = null;
		String content = null;
		List<Pattern> patList = new ArrayList<Pattern>();
		Pattern p = null;
		Matcher m = null;
		String groupContent = "";
		QueryParser queryParser = new QueryParser(field, analyzer);
		if(isAccurate){ //精确查询，利用短语查询拼接
			query = queryParser.parse("\"" + strList[0] + "\"");
			p = Pattern.compile(strList[0],Pattern.CASE_INSENSITIVE);
		}else{ //分词查询
			//要查找的字符串数组
			//String [] stringQuery={"根据","施工"};
			//待查找字符串对应的字段
			String[] fields = new String[strList.length];
			//Occur.MUST表示对应字段必须有查询值， Occur.MUST_NOT 表示对应字段必须没有查询值，Occur.SHOULD表示可有可无
			Occur[] occ = new Occur[strList.length];
			for(int i = 0,size = strList.length; i < size; i++){
				fields[i] = field;
				occ[i] = Occur.MUST;
			}
			query=MultiFieldQueryParser.parse(strList,fields,occ,analyzer);//MultiFieldQueryParser为多词同时查询
			for(int i = 0, size = strList.length; i < size; i++){
				//保存忽略大小写匹配表达式
				patList.add(Pattern.compile(strList[i],Pattern.CASE_INSENSITIVE));
			}
		}
		TopDocs docs = searcher.search(query,number);
		map.put("totalHits", docs.totalHits); //保存命中次数
		for(ScoreDoc doc : docs.scoreDocs){
			docment = searcher.doc(doc.doc);
			fileName = docment.get("fileName");
			content = docment.get("content");
			content = truncationWord(content,strList); //截取段落
			//=================
			if(isRed) {
				if(isAccurate){
					m = p.matcher(content);
					while(m.find()){
						groupContent = m.group();
						content = content.replaceAll(groupContent, "<span class=\"keyword\">" + groupContent +  "</span>");
					}
				}else{
					for(int j = 0, size = patList.size(); j < size; j++){
						//为关键字标红
						m = patList.get(j).matcher(content);
						while(m.find()){
							groupContent = m.group();
							content = content.replaceAll(groupContent, "<span class=\"keyword\">" + groupContent +  "</span>");
						}
					}
				}
			}
			//=======================
			Map<String, String> result = new HashMap<String, String>();
			result.put("fileName", fileName);
			result.put("content", content);
			resultList.add(result);
		}
		map.put("resultList", resultList);
		return map;
	}
	
	private static CharArraySet charArraySet = null;
	private static Analyzer analyzer = null;
	
	/**
	 * 创建索引
	 * @param file 文件对象
	 * @param indexDir 索引目录对象
	 * @param uuid 唯一编码，与T_S_SNAPSHOT表ID一致
	 */
	private static void createFileIndex(File file,File indexDir) throws IOException, XmlException, OpenXML4JException{
		IndexWriter writer = createIndexWriter(indexDir);
		List<Document> documents = getDocumentByFile(file);
		writer.addDocuments(documents);
		writer.commit();
		writer.close();
	}
	
	/**
	 * 创建索引写入器
	 * @param indexDir 索引目录对象
	 */
	private static IndexWriter createIndexWriter(File indexDir) throws IOException{
		IndexWriter writer = null;
		IndexWriterConfig conf = new IndexWriterConfig(analyzer);
		writer = new IndexWriter(FSDirectory.open(indexDir.toPath()), conf);
		return writer;
	}
	
	/**
	 * 获取所有文档内容对象
	 * @param file 文件对象
	 * @param uuid 唯一编码，与T_S_SNAPSHOT表ID一致
	 * PS:这里均是按整篇文章作为一个文档节点内容做索引
	 */
	private static List<Document> getDocumentByFile(File file) throws IOException, XmlException, OpenXML4JException{
		List<Document> documents = new ArrayList<Document>();
		String fileName = file.getName();
		boolean isTxt = fileName.toLowerCase().endsWith(".txt");
		boolean isWord2003 = fileName.toLowerCase().endsWith(".doc");
		boolean isWord2007 = fileName.toLowerCase().endsWith(".docx");
		String str = null;
		if(isTxt){
			str = TextTool.readWordStr(file);
		}
		if(isWord2003 || isWord2007){
			str = WordTool.readWordStr(file);
		}
		if(null != str && (isWord2003 || isWord2007)){
			Document document = new Document();
			Field contentField =  new Field("content",str,TextField.TYPE_STORED);
			Field nameField =  new Field("fileName",fileName,TextField.TYPE_STORED);
			document.add(contentField);
			document.add(nameField);
			documents.add(document);
		}
		return documents;
	}
	
	/**
	 * 创建索引搜索器
	 * @param indexDir 索引目录对象
	 */
	private IndexSearcher createIndexSearcher(File indexDir) throws IOException{
		IndexSearcher searcher = null;
		Directory indexDirectory = FSDirectory.open(indexDir.toPath());
		IndexReader reader = DirectoryReader.open(indexDirectory);
		searcher = new IndexSearcher(reader);
		return searcher;
	}
	
	/**
	 * 截取包含关键字的段落
	 * @param content 内容
	 * @param keyWords 关键字数组
	 */
	private String truncationWord(String content, String[] keyWords){
		StringBuffer recontent = new StringBuffer();
		Map<String, Integer> map = new HashMap<String, Integer>();
		int index = -1;
		//获取关键字下标
		for(int i = 0,size = keyWords.length; i < size; i++){
			index = content.indexOf(keyWords[i]);
			if(index != -1){
				map.put(keyWords[i], index);
			}
		}
		//获取包含关键字的内容
		int beginRange = 100; //截取关键字前范围字数
		int endRange = 200; //截取关键字后范围字数
		String key = null;
		int keyIndex = -1;
		int beginIndex = -1;
		int endIndex = -1;
		Map<String, String> sectionMap = new HashMap<String, String>();
		Iterator<String> it = map.keySet().iterator();
		List<String> keyList = new LinkedList<String>();
		while(it.hasNext()){
			key = it.next();
			keyIndex = map.get(key);
			if(keyIndex - beginRange < 0){ //获取截取范围的开始下标
				beginIndex = 0;
			}else{
				beginIndex = keyIndex - beginRange;
			}
			if(keyIndex + endRange > content.length()){
				endIndex = content.length();
			}else{
				endIndex = keyIndex + endRange;
			}
			sectionMap.put(key, content.substring(beginIndex, endIndex));
			keyList.add(key);
		}
		//把段落合并成一句完整的内容。
		//规则：1.关键词的后面内容以句号结束或多于150字，
		//2.关键词前面内容以制表符或句号+换行符，如果超过150字都没出现，则以100字以内的首个逗号，顿号，感叹号，分号之后的字开始
		String section = null;
		int middleIndex = -1;
		String after = null;
		String before = null;
		int finishIndex = -1;
		int startIndex = -1;
		int outIndex = -1;
		int inIndex = -1;
		List<String> removeIndex = null;
		for(int i = 0, size = keyList.size(); i < size; i++,size = keyList.size()){
			key = keyList.get(i);
			section = sectionMap.get(key);
			middleIndex = section.indexOf(key);
			after = section.substring(0, middleIndex);
			before = section.substring(middleIndex + key.length());
			finishIndex = before.lastIndexOf("。");
			if(finishIndex != -1){
				before = before.substring(0, finishIndex+1); //+1是为了把句号加进来 
			}
			Integer[] itg = getSymbolIndex(after);
			startIndex = itg[0];
			if(startIndex != -1){
				//保持原样或者扩大范围
				after = after.substring(startIndex + itg[1], after.length());
			}
			section = after + key + before;
			//去重，如果一句话中包含多个关键字，随机去掉一句话
			removeIndex = new ArrayList<String>();
			for(int j = i+1, jsize = keyList.size(); j < jsize; j++){
				String inKey = keyList.get(j);
				//比较下标
				outIndex = map.get(key);
				inIndex = map.get(inKey);
				if(inIndex >= (outIndex-beginRange) && inIndex <= (outIndex + endRange)){ //关键字出现在同一句话中
					removeIndex.add(inKey);
//						keyList.remove(j);
				}
			}
			for(int k = 0, ksize = removeIndex.size(); k < ksize; k++){
				keyList.remove(removeIndex.get(k));
			}
			if((i + 1) < keyList.size()){ //由于可能会删除keyList，所以要动态获取大小,for循环也是这原因
				recontent.append(section).append("......");
			}else{
				recontent.append(section);
			}
			
		}
		return recontent.toString();
	}
	
	/**
	 * 获取符号下标及偏移位
	 * @param content
	 * @return 下标0为index，下标1为偏移位 
	 */
	private Integer[] getSymbolIndex(String content){
		Integer[] itg = new Integer[2];
		int index = -1;
		itg[0] = index;
		itg[1] = 0;
		index = content.lastIndexOf("\t");
		if(index != -1){
			itg[0] = index;
			itg[1] = 1;
			return itg;
		}
		index = content.lastIndexOf("。\r\n");
		if(index != -1){
			itg[0] = index;
			itg[1] = 3;
			return itg;
		}
		index = content.lastIndexOf("\r\n");
		if(index != -1){
			itg[0] = index;
			itg[1] = 2;
			return itg;
		}
		index = content.lastIndexOf("，");
		if(index != -1){
			itg[0] = index;
			itg[1] = 1;
			return itg;
		}
		index = content.lastIndexOf(",");
		if(index != -1){
			itg[0] = index;
			itg[1] = 1;
			return itg;
		}
		index = content.lastIndexOf("、");
		if(index != -1){
			itg[0] = index;
			itg[1] = 1;
			return itg;
		}
		index = content.lastIndexOf("!");
		if(index != -1){
			itg[0] = index;
			itg[1] = 1;
			return itg;
		}
		index = content.lastIndexOf(";");
		if(index != -1){
			itg[0] = index;
			itg[1] = 1;
			return itg;
		}
		return itg;
	}
	
	/**
	 * 去重，同文件
	 * @param snapshotList
	 * @return
	 */
	public List<Map<String,String>> deduplicate(List<Map<String,String>> resultList){
		for (int i = 0; i < resultList.size() - 1; i++) {
            for (int j = resultList.size() - 1; j > i; j--) {
                if (resultList.get(j).get("fileName").equals(resultList.get(i).get("fileName"))) {
                	resultList.remove(j);
                }
            }
        }
        return resultList;
	}

	//分词
	public List<String> participle(String content) throws IOException{
		List<String> strList = new LinkedList<String>();
        
        TokenStream ts = analyzer.tokenStream("field", content);
        CharTermAttribute ch = ts.addAttribute(CharTermAttribute.class);

        ts.reset();
        while (ts.incrementToken()) {
        	strList.add(ch.toString());
        }
        ts.end();
        ts.close();
        if(strList.size() > 3){
        	return strList.subList((strList.size()-3), strList.size());
        }else{
        	return strList;
        }
	}
	
	//字符串组合
	public List<List<String>> combiantion(String chs[]){
		List<List<String>> listList=new ArrayList<List<String>>();
		List<String> list=new ArrayList<String>();
		if(chs==null||chs.length==0){
			return listList;
		}
		for(int i=chs.length;i>=1;i--){
			combine(chs,0,i,list,listList);
		}
		return listList;
	}
	//从字符数组中第begin个字符开始挑选number个字符加入list中
	private void combine(String []cs,int begin,int number,List<String> list,List<List<String>> listList){
		if(number==0){
			listList.add(new ArrayList<>(list));
			return;
		}
		if(begin==cs.length){
			return;
		}
		list.add(cs[begin]);
		combine(cs,begin+1,number-1,list,listList);
		list.remove(cs[begin]);
		combine(cs,begin+1,number,list,listList);
	}
	
	public static void main(String[] args) throws Exception {
		
	}
}
