package hb0712.discovery.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.springframework.core.io.FileSystemResource;
import org.xml.sax.InputSource;

/*
 * 是一个XML文件，文件要有写入和读取
 */
public class SheetBean {
	// RootElement
	private Element element = new Element("data");
	Logger logger = Logger.getLogger(SheetBean.class);
	private String filePath;
	private String fileCode = "UTF-8";

	public SheetBean() {
	}

	public SheetBean(String filePath) {
		this.filePath = filePath;
		this.element = this.open(filePath);
	}
		
	public Element open(String filePath) {
		Reader is = null;
		try {
			InputStream input = new FileSystemResource(filePath).getInputStream();
			is = new InputStreamReader(input, "UTF-8");
			return new SAXBuilder().build(new InputSource(is)).getRootElement();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void save(Document doc, String filePath){
		XMLOutputter xmlopt = new XMLOutputter();
		try {
			File file = new FileSystemResource(filePath).getFile();
//			FileWriter writer = new FileWriter(file);
			OutputStream output = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(output, fileCode);
			Format fm = Format.getPrettyFormat();
			xmlopt.setFormat(fm);
			xmlopt.output(doc, writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(Document doc){
		save(doc, this.filePath);
	}

	public Element getRoot() {
		return element;
	}
	
	public String getName() {
		return getElement("account");
	}
	
	
	public String[] getjobs(){
		return getElements("article");
	}

	private String[] getElements(String xpathName){
		List nodes = null;
		try {
			nodes = XPath.selectNodes(element, xpathName);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		String[] names = null;
		if(nodes!=null && nodes.size()>0){
			names = new String[nodes.size()];
			for(int i=0;i<nodes.size();i++){
				names[i] = ((Element)nodes.get(i)).getText();
			}
		}
		
		return names;
	}
		
	private String getElement(String xpathName) {
		Element node = null;
		try {
			node = (Element) XPath.selectSingleNode(element, xpathName);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		String result = null;
		if (node != null) {
			result = node.getText();
		}
		return result;
	}
		
	public void setElement(String name, String value){
		Element phone = new Element(name);
		phone.setText(value);
		element.addContent(phone);
	}
	
	public String getNewId(String path){
		String lastid = null;
		try {
			List ids = XPath.selectNodes(element, path);
			
			if(ids!=null && ids.size()>0){
				lastid = ((Element)ids.get(ids.size()-1)).getText();
				Integer newId = Integer.valueOf(lastid) + 1;
				return newId.toString();
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return lastid;
	}

	public String toString() {
		XMLOutputter outputter = new XMLOutputter(Format.getRawFormat());
		return outputter.outputString(element);
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
