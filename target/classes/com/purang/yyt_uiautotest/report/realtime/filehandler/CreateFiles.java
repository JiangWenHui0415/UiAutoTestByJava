package com.purang.yyt_uiautotest.report.realtime.filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.testng.ISuite;

import com.google.common.io.Files;

import com.purang.yyt_uiautotest.report.realtime.datahandler.DataMap;
import com.purang.yyt_uiautotest.report.realtime.datahandler.DataPreparator;
import com.purang.yyt_uiautotest.report.realtime.datahandler.DataSuite;

public class CreateFiles {

	synchronized public static void createRequiredFolders(ISuite iSuite) {
		/*
		 * STEP 1: If RootResult folder, i.e - 'RealtimeReport', does not exist,
		 * create it
		 */
		if (!new File(FileNameConstants.ROOT_FOLDER).exists()) {
			new File(FileNameConstants.ROOT_FOLDER).mkdir();
		}
		/*
		 * STEP 2: create css, fonts, image, js folders under RootResult folder,
		 * i.e - 'RealtimeReport'
		 */
		createFolderUnder(FileNameConstants.CSS_FOLDER, FileNameConstants.ROOT_FOLDER);
		createFolderUnder(FileNameConstants.FONT_FOLDER, FileNameConstants.ROOT_FOLDER);
		createFolderUnder(FileNameConstants.IMAGE_FOLDER, FileNameConstants.ROOT_FOLDER);
		createFolderUnder(FileNameConstants.JS_FOLDER, FileNameConstants.ROOT_FOLDER);

		/*
		 * STEP 3: get all the resource files from 'html-rsc' folder
		 */
		File[] cssFiles = getFilesUnder(FileNameConstants.RESOURCE_FOLDER + "/" + FileNameConstants.CSS_FOLDER);
		File[] fontFiles = getFilesUnder(FileNameConstants.RESOURCE_FOLDER + "/" + FileNameConstants.FONT_FOLDER);
		File[] imgFiles = getFilesUnder(FileNameConstants.RESOURCE_FOLDER + "/" + FileNameConstants.IMAGE_FOLDER);
		File[] jsFiles = getFilesUnder(FileNameConstants.RESOURCE_FOLDER + "/" + FileNameConstants.JS_FOLDER);

		/*
		 * STEP 4: put all the resource files to the respective folders under
		 * RootResult folder, i.e - 'RealtimeReport' which is already created at
		 * step 2.
		 */
		copyFilesToDestination(cssFiles, FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.CSS_FOLDER);
		copyFilesToDestination(fontFiles, FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.FONT_FOLDER);
		copyFilesToDestination(imgFiles, FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.IMAGE_FOLDER);
		copyFilesToDestination(jsFiles, FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.JS_FOLDER);

		/*
		 * STEP 5: Put values related to current suite at a set, 'suiteSet'
		 */
		if (DataMap.suiteMap.containsKey(iSuite)) {
			int suiteIndex = DataMap.suiteMap.get(iSuite);
			String suiteName = DataPreparator.prepareSuiteName(iSuite);
			DataSuite ds = new DataSuite(suiteIndex, suiteName, FileNameConstants.DASHBOARD_HTML + "-" + suiteIndex + ".html");
			DataMap.suiteSet.add(ds);
		}

		/*
		 * STEP 6: Finally create index.html page
		 */
		createIndexPage();

	}

	synchronized private static void copyFilesToDestination(File[] files, String parentFolderPath) {
		if (files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				try {
					Files.copy(files[i], new File(parentFolderPath + "/" + files[i].getName()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	synchronized private static void createFolderUnder(String newFolderName, String parentFolderPath) {
		if (!new File(parentFolderPath + "/" + newFolderName).exists()) {
			new File(parentFolderPath + "/" + newFolderName).mkdir();
		}
	}

	synchronized private static File[] getFilesUnder(String parentDirectoryPath) {
		File[] files = null;
		if (new File(parentDirectoryPath).exists()) {
			files = new File(parentDirectoryPath).listFiles();
		}
		return files;
	}

	synchronized private static void createIndexPage() {
		if (new File(FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.INDEX_HTML).exists()) {
			new File(FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.INDEX_HTML).delete();
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File(FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.INDEX_HTML), false));
		} catch (FileNotFoundException e) {
		}
		if (pw != null) {
			pw.write(FileNameConstants.INDEX_HEADER);
			pw.write(FileNameConstants.INDEX_BODY_PRE);
			for (DataSuite ds : DataMap.suiteSet) {
				pw.write("<a class='btn btn-link' href='" + ds.getSuiteHTMLPath() + "' style='font-size:24px;'><i class='fa fa-dashboard'></i> "
						+ ds.getSuiteName() + "</a><br/>");
			}
			pw.write(FileNameConstants.INDEX_BODY_POST);
			pw.flush();
			pw.close();
		}
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof ISuite)) {
			return false;
		}
		
		return false;
	}
}
