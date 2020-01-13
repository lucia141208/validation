package com.lh.validation.util;

import com.google.common.collect.Lists;
import com.lh.validation.annotation.ExcelColumn;
import com.lh.validation.entity.Dept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuhuanhuan on 2020/1/7.
 */
@Slf4j
public class ExcelUtils<T> {

    private String sheetName = "sheet";
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    /**
     * 导出文件
     *
     * @param fileNameSuffix 文件名称后缀
     * @param dataSet      数据集
     * @param out          输出流
     */
    public void export(String fileNameSuffix, Collection<T> dataSet,Class clazz, OutputStream out) throws Exception {
        switch (fileNameSuffix.toLowerCase()) {
            case ".xlsx":
                exportXlsx(dataSet, clazz, out);
                break;
            case ".xls":
                exportXls(dataSet, clazz, out);
                break;
            default:
                exportXlsx(dataSet, clazz, out);
                break;
        }
    }

    public void exportXlsx(Collection<T> dataSet, Class clazz,OutputStream fileOutputStream) throws Exception {
        ObjConfig objConfig = resolverConfig(clazz);
        if (Objects.isNull(objConfig.titleList) || objConfig.titleList.length<1){
            throw new Exception("需要设置Excel标头");
        }
        //获取属性
        String[] headers = objConfig.titleList;
        String[] fields = objConfig.fieldNameList;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        /**
         *创建Excel 第一行 HSSFRichTextString富文本字体
         */
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        Iterator<T> it = dataSet.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (int j = 0; j < fields.length; j++) {
                XSSFCell cell = row.createCell(j);
                try {
                    String strValue = getStringValue(sdf, getValue(t, fields[j]));

                    cell.setCellValue(strValue);

                } catch (NoSuchMethodException e) {

                    log.error(e.getMessage());
                } catch (IllegalAccessException e) {

                    log.error(e.getMessage());
                } catch (InvocationTargetException e) {

                    log.error(e.getMessage());
                } catch (NoSuchFieldException e) {
                    log.error(e.getMessage());
                }
            }
        }
        close(fileOutputStream,workbook);

    }

    public void exportXls(Collection<T> dataSet, Class clazz,OutputStream fileOutputStream) throws Exception {

        ObjConfig objConfig = resolverConfig(clazz);
        if (Objects.isNull(objConfig.titleList) || objConfig.titleList.length<1){
            throw new Exception("需要设置Excel标头");
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        //获取样式
        HSSFCellStyle style = createCellStyle(workbook);

        String[] headers = objConfig.titleList;
        String[] fields = objConfig.fieldNameList;
        /**
         *创建Excel 第一行 HSSFRichTextString富文本字体
         */
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置列宽

        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            sheet.setColumnWidth(i,5000);
            cell.setCellStyle(style);
        }
        Iterator<T> it = dataSet.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (int j = 0; j < fields.length; j++) {
                HSSFCell cell = row.createCell(j);
                try {
                    cell.setCellValue(getStringValue(sdf, getValue(t, fields[j])));

                } catch (NoSuchMethodException e) {

                    log.error(e.getMessage());
                } catch (IllegalAccessException e) {

                    log.error(e.getMessage());
                } catch (InvocationTargetException e) {

                    log.error(e.getMessage());
                } catch (NoSuchFieldException e) {
                    log.error(e.getMessage());
                }
            }
        }
        close(fileOutputStream, workbook);
    }

    /**
     * 设置表格样式
     * @param workbook
     * @return
     */
    private HSSFCellStyle createCellStyle(HSSFWorkbook workbook) {

        HSSFCellStyle style = workbook.createCellStyle();
        //设置上下左右四个边框宽度
        style.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
        style.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
        style.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
        style.setBorderRight(HSSFBorderFormatting.BORDER_THIN);

        //设置上下左右四个边框颜色
        style.setTopBorderColor(HSSFColor.RED.index);
        style.setBottomBorderColor(HSSFColor.RED.index);
        style.setLeftBorderColor(HSSFColor.RED.index);
        style.setRightBorderColor(HSSFColor.RED.index);

        //设置单元格背景色
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //设置字体格式
        HSSFFont font = workbook.createFont();
//        font.setFontName("幼圆");
        font.setFontHeightInPoints((short)9);
        font.setColor(HSSFColor.YELLOW.index);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        font.setItalic(true);
        font.setStrikeout(true);
        font.setUnderline((byte)1);
        style.setFont(font);//将字体格式设置到HSSFCellStyle上

        return style;
    }

    private void close(OutputStream out, Workbook workbook) {
        try {
            workbook.write(out);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                out.close();
                FieldCache.remove();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        }
    }

    private String getStringValue(SimpleDateFormat sdf, Object value) {
        String strValue;
        if (value instanceof Date) {
            strValue = sdf.format(value);
        } else {
            // 其它数据类型都当作字符串简单处理
            strValue = (value == null) ? "" : value.toString();
        }
        return strValue;
    }

    /**
     * 获取 对象值
     *
     * @param cla
     * @param fieldName
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    private Object getValue(Object cla, String fieldName) throws NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {


        Field field = FieldCache.getField(cla, fieldName);
        if(Objects.isNull(field)){
            throw new RuntimeException("字段["+fieldName+"]未找到");
        }
        field.setAccessible(true);

        return field.get(cla);
    }

    public ObjConfig resolverConfig(Class clazz){
        List<Integer> indexs = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<Type> dataTypes = new ArrayList<>();
        List<String> fieldNames = new ArrayList<>();
        Field[] fieldArr= clazz.getDeclaredFields();
        for (Field field: fieldArr ) {
            if (field.isAnnotationPresent(ExcelColumn.class)){
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                indexs.add(excelColumn.index());
                titles.add(excelColumn.title());
                dataTypes.add(field.getGenericType());
                fieldNames.add(field.getName());
            }
        }

        Integer[] indexList = new Integer[indexs.size()];
        String[] titleList = new String[indexs.size()];
        Type[] dataTypeList = new Type[indexs.size()];
        String[] fieldNameList = new String[indexs.size()];

        for (int index=0; index<indexs.size(); index++ ) {
            int i = indexs.contains(0) ?  indexs.get(index) : indexs.get(index)-1;
            indexList[i] = indexs.get(index);
            titleList[i] = titles.get(index);
            fieldNameList[i] = fieldNames.get(index);
            dataTypeList[i] = dataTypes.get(index);
        }

        ObjConfig objConfig = new ObjConfig();
        objConfig.setDataTypeList(dataTypeList);
        objConfig.setFieldNameList(fieldNameList);
        objConfig.setTitleList(titleList);
        objConfig.setIndexList(indexList);
        return objConfig;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class ObjConfig{
        Integer[] indexList;
        String[] titleList;
        Type[] dataTypeList;
        String[] fieldNameList;
    }


    public static void main(String[] args) throws Exception {
        Date startDate = new Date();
        Thread.sleep(10000);
        Date endDate = new Date();
        Dept dept = Dept.builder().id(1).nickname("测试").username("test").startDate(startDate).endDate(endDate).build();
        Dept dept1 = Dept.builder().id(2).nickname("测试111").username("test111").build();
        List<Dept> list = Lists.newArrayList();
        list.add(dept);
        list.add(dept1);
        String fileNameSuffix = ".xls";
        String fileName = new StringBuffer("C:\\Users\\Administrator\\Desktop\\导出部门测试")
                                .append(new Date().getTime())
                                .append(fileNameSuffix).toString();
        FileOutputStream outputStream = new FileOutputStream(fileName);

        ExcelUtils excelUtils = new ExcelUtils();
        //导出excle文件
        excelUtils.export(fileNameSuffix,list,Dept.class,outputStream);

        readAttributeValue(dept);

    }

    public static void readAttributeValue(Object object) throws Exception {
        Class clazz = object.getClass();
        Field nicknameField = clazz.getDeclaredField("nickname");
        nicknameField.setAccessible(true);
        Object title = nicknameField.get(object);
        System.out.println(nicknameField);
        System.out.println(title);
    }
}
