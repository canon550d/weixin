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

public class SheetBean {
	// RootElement
		Element element = new Element("data");
		Logger logger = Logger.getLogger(SheetBean.class);
		String configName;

		public SheetBean() {
		}

		public SheetBean(String configName) {
			this.configName = configName;
			Reader is = null;
			try {
				InputStream input = new FileSystemResource(configName).getInputStream();
				is = new InputStreamReader(input, "UTF-8");
				element = new SAXBuilder().build(new InputSource(is)).getRootElement();
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
		}
		
		public void save(Document doc){
			XMLOutputter xmlopt = new XMLOutputter();
			try {
				File file = new FileSystemResource(configName).getFile();
//				FileWriter writer = new FileWriter(file);
				OutputStream output = new FileOutputStream(file);
				Writer writer = new OutputStreamWriter(output, "UTF-8");
				Format fm = Format.getPrettyFormat();
				xmlopt.setFormat(fm);
				xmlopt.output(doc, writer);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

		public String toString() {
			XMLOutputter outputter = new XMLOutputter(Format.getRawFormat());
			return outputter.outputString(element);
		}
}
