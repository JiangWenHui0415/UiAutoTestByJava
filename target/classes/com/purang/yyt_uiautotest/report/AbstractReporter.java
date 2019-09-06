//=============================================================================
// Copyright 2006-2010 Daniel W. Dyer
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//=============================================================================
package com.purang.yyt_uiautotest.report;

import java.io.File;
import java.io.Writer;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ResourceBundle;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.testng.IReporter;
import com.purang.yyt_uiautotest.report.ReportMetadata;
import com.purang.yyt_uiautotest.report.ReportNGException;
import com.purang.yyt_uiautotest.report.ReportNGUtils;

/****************************************************************************************
 * Convenient base class for the ReportNG reporters. Provides commmon
 * functionality.
 * 
 * @author Daniel Dyer
 ****************************************************************************************/
public abstract class AbstractReporter implements IReporter {

	private static final String ENCODING = "UTF-8";
	private static final String META_KEY = "meta";
	protected static final String TEMPLATE_EXTENSION = ".vm";
	protected static final ReportMetadata META = new ReportMetadata();
	protected static final VelocityEngine ENGINE = new VelocityEngine();
	private static final String UTILS_KEY = "utils";
	private static final ReportNGUtils UTILS = new ReportNGUtils();
	private static final String MESSAGES_KEY = "messages";
	private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("com.purang.yyt_uiautotest.report.messages.report",
			META.getLocale());

	private final String classpathPrefix;

	/****************************************************************************************
	 * @param classpathPrefix
	 *            Where in the classpath to load templates from.
	 ****************************************************************************************/
	protected AbstractReporter(String classpathPrefix) {
		this.classpathPrefix = classpathPrefix;
		ENGINE.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
		ENGINE.setProperty("runtime.log.invalid.references", true);
		ENGINE.setProperty("resource.loader", "classpath");
		ENGINE.setProperty("classpath.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		try {
			ENGINE.init();
		} catch (Exception ex) {
			throw new ReportNGException("Failed to initialise Velocity Engine.", ex);
		}
	}

	/****************************************************************************************
	 * Helper method that creates a Velocity context and initialises it with a
	 * reference to the ReportNG utils, report metadata and localised messages.
	 * 
	 * @return An initialised Velocity context.
	 ****************************************************************************************/
	protected VelocityContext createContext() {
		VelocityContext context = new VelocityContext();
		context.put(META_KEY, META);
		context.put(UTILS_KEY, UTILS);
		context.put(MESSAGES_KEY, MESSAGES);
		return context;
	}

	/****************************************************************************************
	 * Generate the specified output file by merging the specified Velocity
	 * Engine template with the supplied context.
	 * 
	 * @throws Exception
	 ****************************************************************************************/
	protected void generateFile(File file, String templateName, VelocityContext context) throws Exception {
		OutputStream out = new FileOutputStream(file);
		Writer writer = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
		try {
			ENGINE.mergeTemplate(classpathPrefix + templateName, ENCODING, context, writer);
			writer.flush();
		} finally {
			writer.close();
		}
	}

	/****************************************************************************************
	 * Copy a single named resource from the classpath to the output directory.
	 * 
	 * @param outputDirectory
	 *            The destination directory for the copied resource.
	 * @param resourceName
	 *            The filename of the resource.
	 * @param targetFileName
	 *            The name of the file created in {@literal outputDirectory}.
	 * @throws IOException
	 *             If the resource cannot be copied.
	 ****************************************************************************************/
	protected void copyClasspathResource(File outputDirectory, String resourceName, String targetFileName)
			throws IOException {
		String resourcePath = classpathPrefix + resourceName;
		InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
		copyStream(outputDirectory, resourceStream, targetFileName);
	}

	/****************************************************************************************
	 * Copy a single named file to the output directory.
	 * 
	 * @param outputDirectory
	 *            The destination directory for the copied resource.
	 * @param sourceFile
	 *            The path of the file to copy.
	 * @param targetFileName
	 *            The name of the file created in {@literal outputDirectory}.
	 * @throws IOException
	 *             If the file cannot be copied.
	 ****************************************************************************************/
	protected void copyFile(File outputDirectory, File sourceFile, String targetFileName) throws IOException {
		InputStream fileStream = new FileInputStream(sourceFile);
		try {
			copyStream(outputDirectory, fileStream, targetFileName);
		} finally {
			fileStream.close();
		}
	}

	/****************************************************************************************
	 * Helper method to copy the contents of a stream to a file.
	 * 
	 * @param outputDirectory
	 *            The directory in which the new file is created.
	 * @param stream
	 *            The stream to copy.
	 * @param targetFileName
	 *            The file to write the stream contents to.
	 * @throws IOException
	 *             If the stream cannot be copied.
	 ****************************************************************************************/
	private void copyStream(File outputDirectory, InputStream stream, String targetFileName) throws IOException {
		File resourceFile = new File(outputDirectory, targetFileName);
		BufferedReader reader = null;
		Writer writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(stream, ENCODING));
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resourceFile), ENCODING));

			String line = reader.readLine();
			while (line != null) {
				writer.write(line);
				writer.write('\n');
				line = reader.readLine();
			}
			writer.flush();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	/****************************************************************************************
	 * Deletes any empty directories under the output directory. These
	 * directories are created by TestNG for its own reports regardless of
	 * whether those reports are generated. If you are using the default TestNG
	 * reports as well as ReportNG, these directories will not be empty and will
	 * be retained. Otherwise they will be removed.
	 * 
	 * @param outputDirectory
	 *            The directory to search for empty directories.
	 ****************************************************************************************/
	protected void removeEmptyDirectories(File outputDirectory) {
		if (outputDirectory.exists()) {
			for (File file : outputDirectory.listFiles(new EmptyDirectoryFilter())) {
				file.delete();
			}
		}
	}

	private static final class EmptyDirectoryFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.isDirectory() && file.listFiles().length == 0;
		}
	}
}
