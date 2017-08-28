package com.tower.service.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

/***
 * 导出数据到EXCEL 通过设置表头\列头\数据等信息,进行导出操作
 * 
 * @author shaobin
 */
public class ExcelUtil {
	
    private HSSFWorkbook wb = null;
    private HSSFSheet sheet = null;
    private String sheetName = ""; //工作区名称
    private String sheetTitle = ""; //设置列头上面的总标题
    private String[][] customInfoHeader;//设置列头名称
    public static int maxRow = 20000;
    private List<Map<String, Object>> colList;
    
    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetTitle() {
        return sheetTitle;
    }

    public void setSheetTitle(String sheetTitle) {
        this.sheetTitle = sheetTitle;
    }

    /***
     * @param worksheet
     *            工作区名称
     * @param worksheetTitle
     *            设置列头上面的总标题
     * @param customInfoHeader
     *            设置列头名称
     * @param modelHeader
     *            对应数据模型的属性名
     */
    public ExcelUtil(String sheetName, String sheetTitle, String[][] customInfoHeader) {
        this.sheetName = sheetName;
        this.sheetTitle = sheetTitle;
        this.customInfoHeader = customInfoHeader;
    }

    /***
     * @param customInfoHeader   设置列头名称
     * @param modelHeader
     *            对应数据模型的属性名
     */
    public ExcelUtil(String[][] customInfoHeader) {
        this.customInfoHeader = customInfoHeader;
    }

    public ExcelUtil() {
    }

    /***
     * 公共信息导出
     * 
     * @param worksheet
     * @param worksheetTitle
     * @param result
     * @return
     * @throws Exception
     */
    public HSSFWorkbook export(List<Map<String, Object>> list) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        int rowIndex = 0;
        this.wb = wb;
        HSSFSheet sheet;
        if (!"".equals(sheetName)) {
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.createSheet();
        }
        this.sheet = sheet;

        // 创建报表头部
        if (!"".equals(sheetTitle)) {
            createNormalHead(sheetTitle, customInfoHeader.length - 1);
            rowIndex++;
        }
        // 设置报表标题
        createColumHeader(customInfoHeader, rowIndex);
        rowIndex++;
        CellStyle style = cteateCellStyle(wb);
        
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow((short) rowIndex);
            Map<String, Object> dv = list.get(i);
            for (int j2 = 0; j2 < customInfoHeader.length; j2++) {
                if(dv.get(customInfoHeader[j2][0]) instanceof BigDecimal){
                    cteateDefaultCellNumber(wb, row, (short) j2, toString2(dv.get(customInfoHeader[j2][0])),style);
                }else{
                    cteateDefaultCell(wb, row, (short) j2, toString2(dv.get(customInfoHeader[j2][0])),style);
                }
            }
            rowIndex++;
            if(rowIndex >=this.maxRow){
                break;
            }
        }
        autoSizeColumn(sheet,customInfoHeader.length);
        return wb;
    }
     
    protected void autoSizeColumn(HSSFSheet sheet,int colNum){
    	for(int k=0;k<colNum; k++){
            sheet.autoSizeColumn((short)k,true);
        }
    }
    
    /***
     * 公共信息导出  导出多个sheet
     * 
     * @param worksheet
     * @param worksheetTitle
     * @param result
     * @return
     * @throws Exception
     */
    public HSSFWorkbook export(List<Map<String, Object>> list, HSSFWorkbook wb) throws Exception {
        int rowIndex = 0;
        this.wb = wb;
        HSSFSheet sheet;
        if (!"".equals(sheetName)) {
            sheet = wb.createSheet(sheetName);
        } else {
            sheet = wb.createSheet();
        }
        this.sheet = sheet;

        // 创建报表头部
        if (!"".equals(sheetTitle)) {
            createNormalHead(sheetTitle, customInfoHeader.length - 1);
            rowIndex++;
        }
        // 设置报表标题
        createColumHeader(customInfoHeader, rowIndex);
        rowIndex++;
        CellStyle style = cteateCellStyle(wb);
        
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow((short) rowIndex);
            Map<String, Object> dv = list.get(i);
            for (int j2 = 0; j2 < customInfoHeader.length; j2++) {
                
                if(dv.get(customInfoHeader[j2][0]) instanceof BigDecimal){
                    cteateDefaultCellNumber(wb, row, (short) j2, toString2(dv.get(customInfoHeader[j2][0])),style);
                }else{
                    cteateDefaultCell(wb, row, (short) j2, toString2(dv.get(customInfoHeader[j2][0])),style);
                }
            }
            rowIndex++;
            if(rowIndex >=this.maxRow){
                break;
            }
        }
        
        autoSizeColumn(sheet,customInfoHeader.length);
        
        return wb;
    }

//将null字符串转换为空
    public  static String toString2(Object object){
        if(object ==null) {
            return "";
        }
        return object+"";
    }
        
    /**
     * 创建通用EXCEL头部
     * 
     * @param headString
     *            头部显示的字符
     * @param colSum
     *            该报表的列数
     */
    @SuppressWarnings("deprecation")
    public void createNormalHead(String headString, int colSum) {
        
        String[] array = headString.split(",");
        
        int colFrom = Integer.valueOf(array[1]);
        HSSFRow row = sheet.createRow(0);
        // 设置第一行
        HSSFCell cell = row.createCell((short) 0);
        row.setHeight((short) 300);
        // 定义单元格为字符串类型
        cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理
        cell.setCellValue(new HSSFRichTextString(array[0]));
        // 指定合并区域
        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colFrom));
        // 定义单元格格式，添加单元格表样式，并添加到工作簿
        HSSFCellStyle cellStyle = setCellStyle();
        HSSFFont font = setCellFont();
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        
        HSSFCell cell12 = row.createCell(colFrom+1);
        cell12.setCellValue(new HSSFRichTextString(array[2]));
        cell12.setCellStyle(cellStyle);
        sheet.addMergedRegion(new Region(0, (short) (colFrom+1), 0, (short) colSum));
    }

    /**
     * 设置报表标题
     * 
     * @param columHeader
     *            标题字符串数组
     */
    public void createColumHeader(String[][] columHeader, int rowIndex) {
        // 设置列头 在第二行
        HSSFRow row1 = sheet.createRow(rowIndex);
        // 指定行高
        row1.setHeight((short) 300);
        
        HSSFCellStyle cellStyle = setCellStyle();

        // 单元格字体
        HSSFFont font = setCellFont();
        cellStyle.setFont(font);
        // 设置边框
        setBorder(cellStyle);

        // 设置单元格背景色
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCell cell = null;
        for (int i = 0; i < columHeader.length; i++) {
            cell = row1.createCell((short) i);
            cell.setCellType(HSSFCell.ENCODING_UTF_16);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(new HSSFRichTextString(columHeader[i][1]));
        }
    }

    /**
     * 创建内容单元格
     * 
     * @param wb
     *            HSSFWorkbook
     * @param row
     *            HSSFRow
     * @param col
     *            short型的列索引
     * @param val
     *            列值
     */
    public void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, String val) {
        HSSFCell cell = row.createCell((short) col);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(new HSSFRichTextString(val));
        HSSFCellStyle cellStyle = setCellStyle();
        HSSFFont font = setCellFont();
        cellStyle.setFont(font);
        setBorder(cellStyle);
        cell.setCellStyle(cellStyle);
    }

    public CellStyle cteateCellStyle(HSSFWorkbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();   
        cellStyle.setDataFormat(format.getFormat("@"));   
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        //cellStyle.setWrapText(true);// 指定单元格自动换行
        setBorder(cellStyle);
        return cellStyle;
    }
    public CellStyle cteateCellStyleRight(HSSFWorkbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();   
        cellStyle.setDataFormat(format.getFormat("@"));   
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        //cellStyle.setWrapText(true);// 指定单元格自动换行
        setBorder(cellStyle);
        return cellStyle;
    }
    public void cteateDefaultCell(Workbook wb, Row row, int col, String val,CellStyle cellStyle) {
        Cell cell = row.createCell((short) col);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue((val));
        cell.setCellStyle(cellStyle);
    }
    
    public void cteateDefaultCellNumber(Workbook wb, Row row, int col, String val,CellStyle cellStyle) {
        Cell cell = row.createCell((short) col);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellValue(Double.valueOf(val));
        cell.setCellStyle(cellStyle);
    }

    private void setBorder(CellStyle cellStyle) {
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
    }

    private HSSFFont setCellFont() {
        // 设置单元格字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        return font;
    }

    private HSSFCellStyle setCellStyle() {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格水平对齐类型
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        //cellStyle.setWrapText(true);// 指定单元格自动换行
        return cellStyle;
    }

    /**
     * 输入EXCEL文件
     * 
     * @param fileName
     *            文件名
     */
    public void outputExcel(String fileName, Workbook wb) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(fileName));
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /***
     * 设置列头名称
     * 
     * @return
     */
    public String[][] getCustomInfoHeader() {
        return customInfoHeader;
    }

    /***
     * 设置列头名称
     * 
     * @param customInfoHeader
     */
    public void setCustomInfoHeader(String[][] customInfoHeader) {
        this.customInfoHeader = customInfoHeader;
    }

    public List<Map<String, Object>> getColList() {
        return colList;
    }

    public void setColList(List<Map<String, Object>> colList) {
        this.colList = colList;
    }
    
    public static String getValue(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return null;
        } 
        xssfCell.setCellType(Cell.CELL_TYPE_STRING);
        return String.valueOf(xssfCell.getStringCellValue());
    }  
    
    public static String getValue(HSSFCell cell) {
        if (cell == null) {
            return null;
        } 
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return String.valueOf(cell.getStringCellValue());
    }  
    
    public static void main(String[] args) {
        HSSFWorkbook hs = null;
         
        String worksheetTitle = "乘务员返奖记录";
        String[][] userHeader = { { "crewNo", "ID" }, { "crewName", "姓名" } };
        ExcelUtil exp = new ExcelUtil();
        exp.setSheetName(worksheetTitle);
        exp.setCustomInfoHeader(userHeader);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("crewNo", "1111");
        map.put("crewName", "2222");
        
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("crewNo", "3333");
        map2.put("crewName", "4444");
        
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("crewNo", "3333");
        map3.put("crewName", "4444");
        
        list.add(map);
        list.add(map2);
        list.add(map3);
        try {
            hs = exp.export(list);
        } catch (Exception e) {
        }

        OutputStream os;
        try {
            os = new FileOutputStream("E:/TEXT.xlsx");
            hs.write(os);
        } catch (Exception e) {
        } 
    }
}
