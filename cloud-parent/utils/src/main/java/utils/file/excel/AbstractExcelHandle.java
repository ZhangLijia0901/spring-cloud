package utils.file.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import utils.file.AbstractFileHandle;

/**
 * excel
 * 
 * @author gener
 * @date 2019/09/20
 */
public abstract class AbstractExcelHandle extends AbstractFileHandle {

    @Override
    public List<?> readFile(String filePath) {
        return readFile(getFile(filePath));
    }

    @Override
    public List<List<Object>> readFile(File file) {
        InputStream is = null;
        Workbook wb = null;
        try {
            is = getInputStream(file);
            wb = getWorkbok(is);

            return readSheet(wb, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (wb != null)
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void writeFile(String filePath, List<?> contentParam) throws Exception {
        OutputStream os = null;
        Workbook wb = null;
        try {
            List<List<Object>> content = new ArrayList<>();
            if (contentParam != null) {
                contentParam.forEach(o -> {
                    if (o instanceof List)
                        content.add((List<Object>)o);
                });
            }
            wb = getWorkbok(null);
            writeSheet(wb, 0, content);
            os = getOutputStream(filePath);
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (wb != null)
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    private void writeSheet(Workbook wb, int index, List<List<Object>> content) {
        if (content == null)
            return;
        Sheet sheet = wb.createSheet();
        sheet.setDefaultColumnWidth((short)15);

        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(i);
            List<Object> tent = content.get(i);
            for (int j = 0; j < tent.size(); j++) {
                Cell cell = row.createCell(j);
                Object tobj = tent.get(j);
                cell.setCellValue(converstVal(tobj));
            }
        }
    }

    private String converstVal(Object obj) {
        // 判断object的类型
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String val = obj == null ? "" : obj.toString();

        if (obj == null)
            return val;
        if (obj instanceof Number) {
            // ((Number)obj).val = new String();
        } else if (obj instanceof Date)
            val = simpleDateFormat.format((Date)obj);
        else if (obj instanceof Calendar) {
            Calendar calendar = (Calendar)obj;
            val = simpleDateFormat.format(calendar.getTime());
        } else if (obj instanceof Boolean)
            val = String.valueOf(((Boolean)obj).booleanValue());
        return val;

    }

    /** 读取指定也内容 */
    public List<List<Object>> readSheet(Workbook wb, int index) {
        Sheet sheet = wb.getSheetAt(index);
        if (sheet == null)
            return new ArrayList<>();
        int rowNos = sheet.getLastRowNum();// 得到excel的总记录条数

        List<List<Object>> rowList = new ArrayList<>(rowNos);
        for (int i = 0; i <= rowNos; i++) {// 遍历行
            Row row = sheet.getRow(i);
            if (row == null)
                continue;
            int columNos = row.getLastCellNum();// 表头总共的列数
            List<Object> columList = new ArrayList<>(columNos);
            rowList.add(columList);
            for (int j = 0; j < columNos; j++) {
                Cell cell = row.getCell(j);
                String val = "";
                if (cell != null) {
                    cell.setCellType(CellType.STRING);
                    val = cell.getStringCellValue();
                }
                columList.add(val);
            }
        }
        return rowList;
    }

    protected abstract Workbook getWorkbok(InputStream in) throws IOException;

}
