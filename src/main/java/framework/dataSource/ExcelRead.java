package framework.dataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {

	File file = null;
	FileInputStream fin = null;
	XSSFWorkbook workbook = null;
	XSSFSheet sheet = null;

	public HashMap<String, String> getData(String excelPath, int sheetNumber, String testCaseId) {

		HashMap<String, String> dict = new HashMap<String, String>();

		file = new File(excelPath);

		if (!file.exists()) {
			System.out.println("File Doesn't Exists!");
		}

		try {
			fin = new FileInputStream(file);
			workbook = new XSSFWorkbook(fin);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		sheet = workbook.getSheetAt(sheetNumber);
		DataFormatter dataFormatter = new DataFormatter();
		Row row = null;
		for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			row = sheet.getRow(rowIndex);
			if (row != null) {
				Cell first = row.getCell(0);
				if (first != null) {
					String firstCellValue = dataFormatter.formatCellValue(first);
					if (firstCellValue.equals(testCaseId)) {
						break;
					}
				}
			}
		}
		if (row != null) {
			for (int colIndex = 0; colIndex <= row.getLastCellNum(); colIndex++) {
				if (sheet.getRow(0).getCell(colIndex) == null || row.getCell(colIndex) == null) {
					break;
				}
				String key = dataFormatter.formatCellValue(sheet.getRow(0).getCell(colIndex));
				String value = dataFormatter.formatCellValue(row.getCell(colIndex));

				dict.put(key, value);

			}
		}

		try {
			workbook.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return dict;
	}
}
