package com.mcgj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.dsig.Transform;

import org.apache.commons.collections4.map.TransformedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel����
 * @author ad
 *
 */
public class ExcelUtil {
	
	/**
	 * @param fileName ·��
	 * @param sheet	sheet
	 * @return
	 */
	public static Map<String,ArrayList<String>> readData(String fileName,String sheetName){
		try{
			Workbook workbook = null;
			File file  = new File(fileName);
			if(!file.exists()){
				return null;//�ļ�������
			}
			InputStream fis = new FileInputStream(file);
			/** ���ļ��ĺϷ��Խ�����֤ */
			if (file.getName().matches("^.+\\.(?i)(xlsx)$")) {
				workbook = new XSSFWorkbook(fis);
			}
			/** ���ļ��ĺϷ��Խ�����֤ */
			if (file.getName().matches("^.+\\.(?i)(xls)$")) {
				workbook = new HSSFWorkbook(fis);
			}
			return transformMap(read(workbook,sheetName));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param fileName ·��
	 * @param sheet	sheetNum
	 * @return
	 */
	public static Map<String,ArrayList<String>> readData(String fileName,Integer sheetNum){
		try{
			Workbook workbook = null;
			File file  = new File(fileName);
			if(!file.exists()){
				return null;//�ļ�������
			}
			InputStream fis = new FileInputStream(file);
			/** ���ļ��ĺϷ��Խ�����֤ */
			if (file.getName().matches("^.+\\.(?i)(xlsx)$")) {
				workbook = new XSSFWorkbook(fis);
			}
			/** ���ļ��ĺϷ��Խ�����֤ */
			if (file.getName().matches("^.+\\.(?i)(xls)$")) {
				workbook = new HSSFWorkbook(fis);
			}
			List<ArrayList<String>> list = read(workbook,sheetNum);
			return transformMap(read(workbook,sheetNum));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	//��listתΪMap
	private static Map<String,ArrayList<String>> transformMap(List<ArrayList<String>> list){
		Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		for(int i=0;i<list.size();i++){
			//��������
			String key = "";//��¼��һ������,������Ϊkey
			ArrayList<String> value = new ArrayList<String>();//valueֵ
			for(int j = 0;j<list.get(i).size();j++){
				//��ȡ��һ��������Ϊmap��key
				if(j == 0){
					key = list.get(i).get(j);
					continue;
				}
				value.add(list.get(i).get(j));
				if(j == list.get(i).size()-1){//�������һ�����ݣ�������׷�ӵ�map��
					map.put(key, value);
				}
			}
		}
		return map;
	}
	//����sheetName��ȡ
	private static List<ArrayList<String>> read(Workbook wb, String sheetName) {

		/** ����sheet������ */
		int totalRows = 0;
		/** ����sheet������ */
		int totalCells = 0;

		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		/** �õ�sheet */
		sheet = wb.getSheet(sheetName);
		totalRows = sheet.getPhysicalNumberOfRows();
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		/** ѭ��Excel���У�ע���ӵڶ��п�ʼ��ȡ���ݣ���Ϊ�������ݱ�.xls�ͽ̲�Ŀ¼��.xlsģ�嶼�б��� */
		for (int r = 1; r < totalRows; r++) {
			row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			ArrayList<String> rowLst = new ArrayList<String>();
			/** ѭ��Excel���� */
			for (short c = 0; c < totalCells; c++) {
				cell = row.getCell(c);
				String cellValue = "";
				if (cell == null) {
					continue;
				}

				/** �����ֵ���� */
				if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
					continue;
				}
				/** �����ַ����� */
				else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
					cellValue = cell.getStringCellValue();
				}
				/** ������,�����ϼ����������� */
				else {
					cellValue = cell.toString() + "";
				}
				rowLst.add(cellValue);
			}
			dataLst.add(rowLst);
		}
		return dataLst;
	}
	
	/******************/
	//�����±��ȡ
	private static List<ArrayList<String>> read(Workbook wb, int sheetNum) {

		/** ����sheet������ */
		int totalRows = 0;
		/** ����sheet������ */
		int totalCells = 0;

		List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		/** �õ�sheet */
		sheet = wb.getSheetAt(sheetNum);
		totalRows = sheet.getPhysicalNumberOfRows();
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		Map<String,ArrayList<String>> maps = new HashMap<String, ArrayList<String>>();
		/** ѭ��Excel���У�ע���ӵڶ��п�ʼ��ȡ���ݣ���Ϊ�������ݱ�.xls�ͽ̲�Ŀ¼��.xlsģ�嶼�б��� */
		for (int r = 1; r < totalRows; r++) {
			row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			ArrayList<String> rowLst = new ArrayList<String>();
			/** ѭ��Excel���� */
			for (short c = 0; c < totalCells; c++) {
				cell = row.getCell(c);
				String cellValue = "";
				if (cell == null) {
					continue;
				}

				/** �����ֵ���� */
				if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
					continue;
				}
				/** �����ַ����� */
				else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
					cellValue = cell.getStringCellValue();
				}
				/** ������,�����ϼ����������� */
				else {
					cellValue = cell.toString() + "";
				}
				rowLst.add(cellValue);
			}
			
			dataLst.add(rowLst);
		}
		return dataLst;
	}
}
