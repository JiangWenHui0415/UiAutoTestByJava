package com.purang.yyt_uiautotest.report;

/**
 * Excel文件读写操作：
 * 1、使用poi组建，基于poi-3.7.jar；
 * 2、实现对单元格的读写；
 * 3、读取整个sheet页的数据存入List和Map，供测试运行时使用；
 * 4、所有单元格的类型全部强制转换为字符串类型。
 * 5、如果要使用xlsx格式，则必须使用dom4j.jar。
 * 
 * @author 测试仔刘毅
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.purang.yyt_uiautotest.utils.LoggerUtils;
import com.purang.yyt_uiautotest.utils.ParseProperties;

public class ExcelParseUtils {

	private String fileName;
	private Sheet xlSheet = null;
	private Row xlRow = null;
	private Cell xlCell = null;
	private final ParseProperties PROPERTY = new ParseProperties("config/config.properties");

	/**
	 * class construct with initlize to set filename for excel file operations.
	 * 
	 * @param fileName
	 *            the excel file name with whole path
	 */
	public ExcelParseUtils(String filePath, String fileName) {
		this.fileName = filePath + "\\" + fileName;
	}

	/**
	 * class construct with initlize to set filename for excel file operations.
	 * 
	 * @param subFolder
	 *            the excel file path under public file path, named testdata.xls
	 */
	public ExcelParseUtils(String subFolder) {
		subFolder = subFolder.endsWith("\\") ? subFolder : subFolder + "\\";
		this.fileName = PROPERTY.get("datapath") + subFolder + "testdata.xls";
	}

	private Workbook getWorkBookStyle(String fileFormat) {
		if (fileFormat.equals("xls")) {
			return new HSSFWorkbook();
		} else {
			return new XSSFWorkbook();
		}
	}

	private void readFileOnNotExist(Boolean readOnly) {
		if (readOnly) {
			LoggerUtils.error("file [" + fileName + "] does not exist!");
			throw new RuntimeException("file [" + fileName + "] does not exist!");
		}
	}

	private Workbook getWorkBookStyle(FileInputStream fso) throws Exception {
		if (null != fso) {
			return WorkbookFactory.create(fso);
		} else {
			throw new IllegalArgumentException("file input stream can not be null!");
		}
	}

	private Workbook getWorkBook(FileInputStream fso, Boolean readOnly) {
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
		try {
			if (new File(fileName).exists()) {
				return getWorkBookStyle(fso);
			} else {
				readFileOnNotExist(readOnly);
				return getWorkBookStyle(fileExt);
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * write excel sheet, specified value to specified cell.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @param row
	 *            row index which to be changed
	 * @param col
	 *            column index which to be changed
	 * @param value
	 *            value to be put into cell
	 * @throws RuntimeException
	 */
	public void setExcelValue(String sheetName, int row, int col, String value) {
		Workbook workBook = null;
		try {
			if (new File(fileName).exists()) {
				workBook = getWorkBook(new FileInputStream(fileName), false);
			} else {
				workBook = getWorkBook(null, false);
			}
			xlSheet = workBook.getSheet(sheetName);
			if (xlSheet == null) {
				xlSheet = workBook.createSheet(sheetName);
			}
			xlRow = xlSheet.getRow(row - 1);
			if (xlRow == null) {
				xlRow = xlSheet.createRow((short) row - 1);
			}
			xlCell = xlRow.getCell(col - 1);
			if (xlCell == null) {
				xlCell = xlRow.createCell(col - 1);
			}
			xlCell.setCellType(1);// set cell type as string
			xlCell.setCellValue(value);
			FileOutputStream fileOut = new FileOutputStream(fileName);
			workBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException("set excel value failed:" + e.getMessage());
		}
	}

	/**
	 * put dataList to excel sheets.
	 * 
	 * @param sheetName
	 *            excel sheet name.
	 * @param dataList
	 *            data list to be parsed and put into excel sheets.
	 * @param rowNum
	 *            row count of the sheet to be modified.
	 * @param ignoreRows
	 *            rows to skip when put value.
	 * @param ignoreColumns
	 *            columns to skip when put value.
	 * 
	 * @throws RuntimeException
	 * @throws IllegalArgumentException
	 */
	public void putListToExcelWithFullIgnore(String sheetName, List<String> dataList, int rowNum, int ignoreRows,
			int ignoreColumns) {
		if (dataList.size() % rowNum != 0) {
			LoggerUtils.error("dataList has wrong element count for excel!");
			throw new IllegalArgumentException("dataList has wrong element count for excel!");
		}

		String value = null;
		int index = 0;
		final int colCount = dataList.size() / rowNum;
		Workbook workBook = null;

		try {
			if (new File(fileName).exists()) {
				workBook = getWorkBook(new FileInputStream(fileName), false);
			} else {
				workBook = getWorkBook(null, false);
			}
			xlSheet = workBook.getSheet(sheetName);
			if (xlSheet == null) {
				xlSheet = workBook.createSheet(sheetName);
			}

			for (int j = ignoreRows; j < ignoreRows + rowNum; j++) {
				xlRow = xlSheet.getRow(j);
				if (xlRow == null) {
					xlRow = xlSheet.createRow(j);
				}
				for (int i = ignoreColumns; i < ignoreColumns + colCount; i++) {
					value = dataList.get(index);
					xlCell = xlRow.getCell(i);
					if (xlCell == null) {
						xlCell = xlRow.createCell(i);
					}

					xlCell.setCellType(1);
					if (value != null) {
						xlCell.setCellValue(value);
					}
					index++;
				}
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			workBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException("set excel value failed:" + e.getMessage());
		}
	}

	/**
	 * put dataList to excel sheets.
	 * 
	 * @param sheetName
	 *            excel sheet name.
	 * @param dataList
	 *            data list to be parsed and put into excel sheets.
	 * @param rowNum
	 *            row count of the sheet to be modified.
	 * @param ignoreRows
	 *            rows to skip when put value.
	 * 
	 * @throws RuntimeException
	 * @throws IllegalArgumentException
	 */
	public void putListToExcelWithNoColumnIgnore(String sheetName, List<String> dataList, int rowNum, int ignoreRows) {
		putListToExcelWithFullIgnore(sheetName, dataList, rowNum, ignoreRows, 0);
	}

	/**
	 * put dataList to excel sheets.
	 * 
	 * @param sheetName
	 *            excel sheet name.
	 * @param dataList
	 *            data list to be parsed and put into excel sheets.
	 * @param rowNum
	 *            row count of the sheet to be modified.
	 * 
	 * @throws RuntimeException
	 * @throws IllegalArgumentException
	 */
	public void putListToExcelWithNoIgnore(String sheetName, List<String> dataList, int rowNum) {
		putListToExcelWithFullIgnore(sheetName, dataList, rowNum, 0, 0);
	}

	/**
	 * get excel cell value of specified cell.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @param row
	 *            row index which to be changed
	 * @param col
	 *            column index which to be changed
	 * @return excel cell value string
	 * 
	 * @throws RuntimeException
	 */
	public String getExcelValue(String sheetName, int row, int col) {
		String text = null;
		FileInputStream fso = null;
		try {
			fso = new FileInputStream(fileName);
			Workbook workBook = getWorkBook(fso, true);
			xlSheet = workBook.getSheet(sheetName);
			if (xlSheet == null) {
				LoggerUtils.error("sheet [" + sheetName + "] does not exist!");
				throw new RuntimeException("sheet [" + sheetName + "] does not exist!");
			}
			xlRow = xlSheet.getRow(row - 1);
			xlCell = (xlRow == null) ? null : xlRow.getCell(col - 1);
			text = (xlCell == null) ? "" : xlCell.toString();
			fso.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException("read excel failed:" + e.getMessage());
		}
		return text;
	}

	/**
	 * put data into map.
	 * 
	 * @param keys
	 *            map key names
	 * @param parms
	 *            map key values
	 * 
	 * @throws RuntimeException
	 */
	public Map<String, String> creatMap(List<String> keys, List<String> parms) {
		if (keys.size() != parms.size()) {
			LoggerUtils.error("incorrect parameters, the size of list not equals!");
			throw new RuntimeException("incorrect parameters, the size of list not equals!");
		}
		Map<String, String> paraMap = new HashMap<String, String>();
		for (int i = 0; i < keys.size(); i++) {
			paraMap.put(keys.get(i), parms.get(i));
		}
		return paraMap;
	}

	/**
	 * get the specified excel sheet and put its value string to list.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @param ignoreRows
	 *            first several rows not to read.
	 * @param ignoreCols
	 *            first several cols not to read.
	 * @param readRows
	 *            specified row count to read.
	 * @param readColumns
	 *            specified column count to read.
	 * @throws RuntimeException
	 */
	public List<String> excelToList(String sheetName, int ignoreRows, int ignoreCols, int readRows, int readColumns) {
		FileInputStream fso = null;
		List<String> paraList = new ArrayList<String>();

		try {
			fso = new FileInputStream(fileName);
			Workbook workBook = getWorkBook(fso, true);
			xlSheet = workBook.getSheet(sheetName);
			if (xlSheet == null) {
				LoggerUtils.error("sheet [" + sheetName + "] does not exist!");
				throw new RuntimeException("sheet [" + sheetName + "] does not exist!");
			}
			readRows = (readRows == 0) ? xlSheet.getPhysicalNumberOfRows() : readRows;
			for (int i = ignoreRows; i < ignoreRows + readRows; i++) {
				xlRow = xlSheet.getRow(i);
				readColumns = (readColumns == 0) ? xlRow.getPhysicalNumberOfCells() : readColumns;
				if (xlRow != null) {
					for (int j = ignoreCols; j < ignoreCols + readColumns; j++) {
						xlCell = xlRow.getCell(j);
						if (xlCell == null) {
							paraList.add("");
						} else {
							paraList.add(xlCell.toString());
						}
					}
				}
			}
			fso.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException("read excel failed:" + e.getMessage());
		}
		return paraList;
	}

	/**
	 * get the specified excel sheet and put its value string to list, with no
	 * ignores.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @param readRows
	 *            specified row count to read.
	 * @param readColumns
	 *            specified column count to read.
	 * @throws RuntimeException
	 */
	public List<String> excelToListHasNoIgnore(String sheetName, int readRows, int readColumns) {
		return excelToList(sheetName, 0, 0, readRows, readColumns);
	}

	/**
	 * get the specified excel sheet and put its value string to list.</BR> with
	 * no ignores rows and columns, read all the sheet area.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @throws RuntimeException
	 */
	public List<String> excelToListWithAllArea(String sheetName) {
		return excelToList(sheetName, 0, 0, 0, 0);
	}

	/**
	 * get the specified excel sheet and put its value string to list.</BR>
	 * ignores several rows and columns, then read all the rest sheet area.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @param ignoreRows
	 *            first several rows not to read.
	 * @param ignoreCols
	 *            first several cols not to read.
	 * @throws RuntimeException
	 */
	public List<String> excelToListAllAreaAfterIgnore(String sheetName, int ignoreRows, int ignoreCols) {
		return excelToList(sheetName, ignoreRows, ignoreCols, 0, 0);
	}

	/**
	 * get the specified excel sheet and put its value string to list.</BR>
	 * ignores several rows and no columns, then read all the rest sheet area.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @param ignoreRows
	 *            first several rows not to read.
	 * @throws RuntimeException
	 */
	public List<String> excelToListAllAreaAfterIgnore(String sheetName, int ignoreRows) {
		return excelToList(sheetName, ignoreRows, 0, 0, 0);
	}

	/**
	 * read excel Xls and add the result into arraylist.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @throws RuntimeException
	 */
	public List<Map<String, String>> excelToList(String sheetName) {
		Row firstxlRow = null;
		FileInputStream fso = null;
		List<Map<String, String>> paraList = new ArrayList<Map<String, String>>();

		try {
			fso = new FileInputStream(fileName);
			Workbook workBook = getWorkBook(fso, true);
			xlSheet = workBook.getSheet(sheetName);
			if (xlSheet == null) {
				LoggerUtils.error("sheet [" + sheetName + "] does not exist!");
				return null;
			}
			firstxlRow = xlSheet.getRow(xlSheet.getFirstRowNum());
			int firstCell = firstxlRow.getFirstCellNum();
			int lastCell = firstxlRow.getLastCellNum();
			List<String> keyList = new ArrayList<String>();

			for (int cNum = firstCell; cNum < lastCell; cNum++) {
				if (firstxlRow.getCell(cNum).toString() == null) {
					break;
				}
				keyList.add(firstxlRow.getCell(cNum).toString());
			}

			for (int i = xlSheet.getFirstRowNum() + 1; i < xlSheet.getPhysicalNumberOfRows(); i++) {
				xlRow = xlSheet.getRow(i);
				List<String> valueList = new ArrayList<String>();
				if (xlRow == null) {
					break;
				}
				for (int j = firstCell; j < lastCell; j++) {
					xlCell = xlRow.getCell(j);
					if (xlCell == null) {
						valueList.add(null);
						continue;
					} else {
						valueList.add(xlCell.toString());
					}
				}
				paraList.add(creatMap(keyList, valueList));
			}
			fso.close();
		} catch (Exception e) {
			LoggerUtils.error(e);
			throw new RuntimeException("read excel failed:" + e.getMessage());
		}
		return paraList;
	}

	/**
	 * get the specified excel Xls sheet with specified row data into map.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @param index
	 *            index of the row you want to use
	 * @throws RuntimeException
	 */
	public Map<String, String> excelDataMap(String sheetName, int index) {
		Iterator<Map<String, String>> it = excelToList(sheetName).iterator();
		Map<String, String> paraMap = null;

		for (int i = 0; i < index; i++) {
			if (it.hasNext()) {
				paraMap = (Map<String, String>) it.next();
			}
		}
		return paraMap;
	}

	/**
	 * override the excelDataMapXls method, using default rownum 1.
	 * 
	 * @param sheetName
	 *            excel sheet name
	 * @throws RuntimeException
	 */
	public Map<String, String> excelDataMap(String sheetName) {
		return excelDataMap(sheetName, 1);
	}
}