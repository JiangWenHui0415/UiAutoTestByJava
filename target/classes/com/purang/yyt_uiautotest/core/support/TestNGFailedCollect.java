package com.purang.yyt_uiautotest.core.support;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.ArrayList;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.purang.yyt_uiautotest.utils.ReadXMLDocument;

public class TestNGFailedCollect {

	private static final String FAILS = "(failed)";
	private static final String ENCODE = "UTF-8";
	private static final String REPORT = "./report/";
	private static final String TASK = "./task/";

	public static void main(String[] args) throws Exception {
		modiXMLDocument(args[0]);
	}

	public static void modiXMLDocument(String taskName) throws Exception {
		if (!taskName.contains("/") && !taskName.contains("\\")) {
			taskName = TASK + taskName;
		}
		modiXMLDocument(taskName, REPORT + "testng-failed.xml", REPORT + "失败重跑.xml");
	}

	/**
	 * parse xml files to select failed test for rerun.
	 * 
	 * @param taskName
	 *            path and name of the testng task file to be parsed.
	 * @param failName
	 *            path and name of the testng-run-failed file to be parsed.
	 * @param outName
	 *            path and name of the new created xml file for rerun.
	 * @throws Exception
	 * 
	 * @throws RuntimeException
	 **/
	public static void modiXMLDocument(String taskName, String failName, String outName) throws Exception {
		if (!new File(taskName).exists()) {
			throw new RuntimeException("the TestNG task file 【 " + taskName + "】 does not exist!");
		}

		if (!new File(failName).exists()) {
			System.out.println("the TestNG run failed file 【 " + failName + "】 does not exist!");
			return;
		}

		final String xmlContent = readXMLDocument(taskName);
		final List<String> failList = readTestNGFailed(failName);
		writeXMLFile(failList, xmlContent, outName);
	}

	private static void writeXMLFile(List<String> failList, String xmlContent, String outName) throws Exception {
		org.dom4j.Document document = DocumentHelper.parseText(xmlContent);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(ENCODE);
		XMLWriter xml = new XMLWriter(new FileOutputStream(outName), format);
		Element root = document.getRootElement();
		List<?> list = (List<?>) root.elements("test");
		String testName = null;
		boolean equals = true;

		for (int i = 0; i < list.size(); i++) {
			Element element = (Element) list.get(i);
			testName = element.attributeValue("name").trim();
			equals = true;
			for (int j = 0; j < failList.size(); j++) {
				equals = (testName.equals(failList.get(j)));
				if (equals) {
					break;
				}
			}
			if (!equals) {
				root.remove(element);
			}
		}
		xml.write(document);
		xml.close();
	}

	/**
	 * read xml file content.
	 * 
	 * @param fileName
	 *            path and name of the file to be parsed.
	 * @return the file string text of your xml file.
	 * 
	 * @throws RuntimeException
	 **/
	private static String readXMLDocument(String fileName) throws Exception {
		File file = new File(fileName);
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		InputStream iStream;
		iStream = new FileInputStream(file);

		byte[] bytes = new byte[4096];
		int len = iStream.read(bytes);
		while (len > 0) {
			buff.write(bytes, 0, len);
			len = iStream.read(bytes);
		}
		iStream.close();
		return new String(buff.toByteArray(), "UTF-8");
	}

	/**
	 * read xml file, and find test nodes that failed.
	 * 
	 * @param fileName
	 *            path and name of the file to be read.
	 * @return the failed tests' name List.
	 * @throws Exception
	 **/
	private static List<String> readTestNGFailed(String fileName) throws Exception {
		Document document = new ReadXMLDocument().loadXMLDocument(fileName);
		NodeList nodeList = document.getElementsByTagName("test");
		String nodeValue = null;
		List<String> resList = new ArrayList<String>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeValue = nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue();
			if (nodeValue != null) {
				resList.add(nodeValue.replace(FAILS, "").trim());
			}
		}
		return resList;
	}
}
