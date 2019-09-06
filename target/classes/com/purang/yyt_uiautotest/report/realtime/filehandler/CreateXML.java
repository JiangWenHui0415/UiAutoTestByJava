package com.purang.yyt_uiautotest.report.realtime.filehandler;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.testng.ISuite;
import org.testng.ITestResult;

import com.purang.yyt_uiautotest.report.realtime.datahandler.DataMap;
import com.purang.yyt_uiautotest.report.realtime.result.ConsolidatedResult;

public class CreateXML {

	synchronized public static void writeXML(ISuite iSuite, ConsolidatedResult result) {
		if (!new File(FileNameConstants.ROOT_FOLDER).exists()) {
			new File(FileNameConstants.ROOT_FOLDER).mkdir();
		}
		int suiteIndex = 0;
		if (DataMap.suiteMap.containsKey(iSuite)) {
			suiteIndex = DataMap.suiteMap.get(iSuite);
		}
		if (new File(FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.XML_FILE_NAME + "-" + suiteIndex
				+ ".xml").exists()) {
			new File(FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.XML_FILE_NAME + "-" + suiteIndex
					+ ".xml").delete();
		}

		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ConsolidatedResult.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(result, new File(FileNameConstants.ROOT_FOLDER + "/"
					+ FileNameConstants.XML_FILE_NAME + "-" + suiteIndex + ".xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	synchronized public static void writeXML(ITestResult tr, ConsolidatedResult result) {
		ISuite iSuite = tr.getTestContext().getSuite();
		writeXML(iSuite, result);
	}
}
