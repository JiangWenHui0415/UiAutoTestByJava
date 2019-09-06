package com.purang.yyt_uiautotest.testdata.fileio;

/**
 * 文本文件读取：
 * 1、将文本文件内容读取存入Map之后存入List；
 * 2、将文本文件内容List根据配置得到指定行数读出为Map供运行时使用；
 * 
 * @author 测试仔刘毅
 **/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.purang.yyt_uiautotest.utils.LoggerUtils;

public class TextParseUtils {

	private String fileName;
	public Map<String, String> paraMap;
	private String _mark = ";";
	private String charSet = "gbk";

	/**
	 * class construct with initialize parameters.
	 * 
	 * @param fName
	 *            the file name to be parsed
	 * @throws RuntimeException
	 **/
	public TextParseUtils(String fName) {
		this.fileName = fName;
	}

	/**
	 * Description: set file read encoding.
	 * 
	 * @param encoding
	 *            the charset of the file reading.
	 */
	public void setCharSet(String encoding) {
		this.charSet = encoding;
	}

	/**
	 * Description: the the char mark of the string split;
	 * 
	 * @param mark
	 *            the char mark char;
	 */
	public void setSplitChar(String mark) {
		this._mark = mark;
	}

	/**
	 * Description: get the txt file content.
	 * 
	 * @return the txt file content list.
	 */
	public List<String> readTXTToList() {
		String line = null;
		BufferedReader br = null;

		List<String> List = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
			while (br.ready() && (line = br.readLine()) != null) {
				List.add(line);
			}
			br.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		}
		return List;
	}

	/**
	 * Description: get the txt file rows.
	 * 
	 * @return the txt file row count number.
	 */
	public int txtRowCount() {
		return readTXTToList().size();
	}

	/**
	 * Description: get the specified row and column value of the txt file.
	 * 
	 * @param row
	 *            the row position.
	 * @param col
	 *            the column position.
	 * @return text value of the file specified position.
	 */
	public String getTxtValue(int row, int col) {
		BufferedReader br = null;
		int irow = 0;
		String[][] value = new String[1024][1024];

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
			String temp;
			while ((temp = br.readLine()) != null) {
				temp = temp.replaceAll("\\t", _mark);
				value[irow] = temp.split(_mark);
				irow++;
			}
			br.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		}
		return value[row - 1][col - 1];
	}

	/**
	 * Description: change value of the specified position.
	 * 
	 * @param row
	 *            the row position.
	 * @param col
	 *            the column position.
	 * @param input
	 *            the text value to input.
	 */
	public void inputTxtValue(int row, int col, String input) {
		List<String> original = readTXTToList();
		BufferedWriter bw = null;
		StringBuffer sb = new StringBuffer();
		String[] text = null;
		String temp = null;
		String txtMark = original.get(0).contains("\t") ? "\t" : _mark;

		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, false), charSet));

			for (int irow = 0; irow < original.size(); irow++) {
				if (irow != row - 1) {
					bw.write(original.get(irow));
				} else {
					temp = original.get(irow).replaceAll("\\t", _mark);
					text = temp.split(_mark);
					for (int icol = 0; icol < text.length; icol++) {
						if (icol != col - 1) {
							sb.append(text[icol]);
							sb.append(txtMark);
						} else {
							sb.append(input);
							sb.append(txtMark);
						}
					}
					bw.write(sb.toString());
				}
				if (irow != original.size() - 1) {
					bw.newLine();
				}
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * add map to arraylist.
	 * 
	 * @throws RuntimeException
	 **/
	public List<Map<String, String>> configList() {
		List<Map<String, String>> paraList = new ArrayList<Map<String, String>>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
			String strKey = br.readLine();
			String[] keys = null;
			if (null == strKey) {
				br.close();
				return null;
			} else {
				keys = strKey.replaceAll("\\t", _mark).split(_mark);
			}
			String strParm = null;
			while ((strParm = br.readLine()) != null && strParm.length() > 0) {
				String[] parms = strParm.replaceAll("\\t", _mark).split(_mark);
				paraList.add(creatParaMap(keys, parms));
			}
			br.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException("execute extern file failed:" + e.getMessage());
		}
		return paraList;
	}

	/**
	 * put data into map.
	 * 
	 * @param keys
	 *            the keyname array to put into map
	 * @param parms
	 *            the keyvalue array to put into map
	 * @throws RuntimeException
	 **/
	public Map<String, String> creatParaMap(String[] keys, String[] parms) {
		Map<String, String> paraMap = new HashMap<String, String>();
		for (int i = 0; i < keys.length; i++) {
			paraMap.put(String.valueOf(keys[i]), String.valueOf(parms[i]));
		}
		return paraMap;
	}

	/**
	 * get datamap from datalist using configed line number.
	 * 
	 * @param index
	 *            the line number of your file to read
	 * @throws RuntimeException
	 **/
	public Map<String, String> getConfigedParaMap(String index) {
		Iterator<Map<String, String>> it = configList().iterator();
		if (index == null) {
			index = "1";
		}
		for (int i = 0; i < Integer.valueOf(index); i++) {
			if (it.hasNext()) {
				paraMap = (Map<String, String>) it.next();
				System.out.println(paraMap.toString());
			}
		}
		return paraMap;
	}

	/**
	 * override getConfigedParaMap using default line number 1.
	 * 
	 * @throws RuntimeException
	 **/
	public Map<String, String> getConfigedParaMap() {
		return getConfigedParaMap(null);
	}
}