package com.hand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonObject;

/**
 * Hello world!
 *
 */
public class Exam_3 {

	public static void main(String[] args) {
		new ReadByGet().start();
	}
}

class ReadByGet extends Thread {
	public void run() {
		try {
			URL url = new URL(" http://hq.sinajs.cn/list=sz300170");

			URLConnection connection = url.openConnection();

			InputStream is = connection.getInputStream();

			InputStreamReader isr = new InputStreamReader(is);

			BufferedReader br = new BufferedReader(isr);

			String line = null;
			StringBuffer sb = new StringBuffer();

			// ∫∫µ√–≈œ¢,13.910,14.550,13.500,14.040,13.270
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			String result = sb.toString();
			String[] str1 = result.split("\"");
			String[] str2 = str1[1].split(",");


			toXml(str2);
			toJson(str2);

			br.close();
			isr.close();
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void toJson(String[] str2) {

		JsonObject object = new JsonObject();
		
		object.addProperty("name", str2[0]);
		object.addProperty("open", Double.parseDouble(str2[1]));
		object.addProperty("close", Double.parseDouble(str2[2]));
		object.addProperty("current", Double.parseDouble(str2[3]));
		object.addProperty("high", Double.parseDouble(str2[4]));
		object.addProperty("low", Double.parseDouble(str2[5]));
		
		//System.out.println(object);
		
		try {
			FileOutputStream fos = new FileOutputStream(new File("test.json"));
			fos.write(object.toString().getBytes());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void toXml(String[] str2) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = document.createElement("xml");

			Element stock = document.createElement("stock");

			Element name = document.createElement("name");
			name.setTextContent(str2[0]);

			Element open = document.createElement("open");
			open.setTextContent(str2[1]);

			Element close = document.createElement("close");
			close.setTextContent(str2[2]);

			Element current = document.createElement("current");
			current.setTextContent(str2[3]);

			Element high = document.createElement("high");
			high.setTextContent(str2[4]);

			Element low = document.createElement("low");
			low.setTextContent(str2[5]);

			stock.appendChild(name);
			stock.appendChild(open);
			stock.appendChild(close);
			stock.appendChild(current);
			stock.appendChild(high);
			stock.appendChild(low);

			root.appendChild(stock);
			document.appendChild(root);

			TransformerFactory factory2 = TransformerFactory.newInstance();
			Transformer transformer = factory2.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(writer));

			transformer.transform(new DOMSource(document), new StreamResult(new File("test.xml")));

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}
