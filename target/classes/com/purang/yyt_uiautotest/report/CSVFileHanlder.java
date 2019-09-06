package com.purang.yyt_uiautotest.report;

import java.io.File;
import java.util.List;

import com.purang.yyt_uiautotest.utils.LoggerUtils;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CSVFileHanlder {

	private File file;
	private BufferedReader reader;
	private BufferedWriter writer;
	private String charSet = "ISO-8859-1";
	private String separator = ",";

	/**
	 * class construct with initlize to set filename for csv file operations.
	 * 
	 * @param fileName
	 *            the file name to be read or write
	 * @param fileEncode
	 *            the file charset
	 */
	public CSVFileHanlder(String fileName, String fileEncode) {
		this.file = new File(fileName);
		this.charSet = fileEncode;
	}

	/**
	 * class construct with initlize to set filename for csv file operations.
	 * 
	 * @param fileName
	 *            the file name to be read or write
	 */
	public CSVFileHanlder(String fileName) {
		this.file = new File(fileName);
	}

	/**
	 * set the separator of the csv file, defaul value is ",".
	 * 
	 * @param spacer
	 *            the separator of the csv file in each line, such as ",".
	 */
	public void setSeparator(String spacer) {
		this.separator = spacer;
	}

	/**
	 * set the charset of the csv file, defaul value is "ISO-8859-1". used only
	 * when user did not set it by class construct.
	 * 
	 * @param fileEncode
	 *            the charset of the csv file.
	 */
	public void setCharSet(String fileEncode) {
		this.charSet = fileEncode;
	}

	/**
	 * read specified row and column value of csv file
	 * 
	 * @param row
	 *            the row index of the file
	 * @param col
	 *            the column index of the file
	 * 
	 * @throws RuntimeException
	 */
	public String readCSVCellValue(int row, int col) {
		String line = null;
		String[] chars = null;

		try {
			int i = 1;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
			reader.read();
			while (reader.ready() && (line = reader.readLine()) != null) {
				chars = line.split(separator);
				if (row == i) {
					return chars[col - 1];
				}
				i++;
			}
			reader.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		} finally {
			System.gc();
		}
		return null;
	}

	/**
	 * read specified line value of csv file
	 * 
	 * @param row
	 *            the row index of the file
	 * 
	 * @throws RuntimeException
	 */
	public String readCSVLineValue(int row) {
		String line = null;

		try {
			int i = 1;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
			while (reader.ready() && (line = reader.readLine()) != null) {
				if (row == i) {
					return line.toString();
				}
				i++;
			}
			reader.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		} finally {
			System.gc();
		}

		return null;
	}

	/**
	 * read the whole content to list of the csv file name.
	 * 
	 * @throws RuntimeException
	 */
	public List<String> readCSVToList() {
		String line = null;

		List<String> csvList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
			while (reader.ready() && (line = reader.readLine()) != null) {
				csvList.add(line.toString());
			}
			reader.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		} finally {
			System.gc();
		}

		return csvList;
	}

	/**
	 * read the whole content to string of the csv file name.
	 */
	public String readCSVText() {
		return readCSVToList().toString();
	}

	/**
	 * get the row count of the csv file name.
	 */
	public int csvRowCount() {
		return readCSVToList().size();
	}

	/**
	 * get the column count of the csv file name.
	 */
	public int csvColumnCount() {
		List<String> content = readCSVToList();

		if (content.toString().equals("[]")) {
			return 0;
		} else {
			if (content.get(0).toString().contains(separator)) {
				return content.get(0).toString().split(separator).length;
			} else if (content.get(0).toString().trim().length() != 0) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * parse the data list and put it to the csv file, for no append.
	 * 
	 * @param dataList
	 *            data list to be put into the csv file
	 * 
	 * @throws RuntimeException
	 */
	public void putListToCSV(List<String> dataList) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), charSet));
			for (int i = 0; i < dataList.size(); i++) {
				writer.write(dataList.get(i));
				if (i != dataList.size() - 1) {
					writer.newLine();
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		} finally {
			System.gc();
		}
	}

	/**
	 * parse the data list and put it to the csv file, for append.
	 * 
	 * @param value
	 *            the string text to be put into the csv file
	 * @param row
	 *            line number of the csv file to be modified
	 * 
	 * @throws RuntimeException
	 */
	public void putLineToCSV(String value, int row) {
		List<String> original = readCSVToList();

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), charSet));
			for (int i = 0; i < original.size(); i++) {
				if (i != row - 1) {
					writer.write(original.get(i));
				} else {
					writer.write(new String(value.getBytes(), charSet));
				}
				if (i != original.size() - 1) {
					writer.newLine();
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		} finally {
			System.gc();
		}
	}

	/**
	 * parse the data list and put it to the csv file, for append.
	 * 
	 * @param cellValue
	 *            the string text to be put into the csv file
	 * @param row
	 *            line number of the csv file
	 * @param col
	 *            column number of the csv file
	 * 
	 * @throws RuntimeException
	 */
	public void putValueToCSV(String cellValue, int row, int col) {
		List<String> original = readCSVToList();
		StringBuffer sb = new StringBuffer();
		String[] text = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), charSet));
			for (int i = 0; i < original.size(); i++) {
				if (i != row - 1) {
					writer.write(original.get(i));
				} else {
					text = original.get(i).split(separator);
					for (int j = 0; j < text.length; j++) {
						if (j != col - 1) {
							if (j == text.length - 1) {
								sb.append(text[j]);
							} else {
								sb.append(text[j]);
								sb.append(separator);
							}
						} else {
							if (j == text.length - 1) {
								sb.append(cellValue.toString());
							} else {
								sb.append(cellValue.toString());
								sb.append(separator);
							}
						}
					}
					writer.write(sb.toString());
				}
				if (i != original.size() - 1) {
					writer.newLine();
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		} finally {
			System.gc();
		}
	}

	/**
	 * append text to csv file, on the end of the file.
	 * 
	 * @param text
	 *            text to append to the csv file
	 * 
	 * @throws RuntimeException
	 */
	public void appendLineToCSV(String text) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), charSet));
			writer.newLine();
			writer.write(text);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		} finally {
			System.gc();
		}
	}

	/**
	 * parse the data list and put it to the csv file, for append.
	 * 
	 * @param dataList
	 *            data list to be put into the csv file
	 * 
	 * @throws RuntimeException
	 */
	public void appendListToCSV(List<String> dataList) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), charSet));
			for (int i = 0; i < dataList.size(); i++) {
				writer.newLine();
				writer.write(dataList.get(i));
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		} finally {
			System.gc();
		}
	}
}