package utils.file.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.file.FileFormatConst;

/**
 * xlsx
 * 
 * @author gener
 * @date 2019/09/20
 */
public class XLSXHandle extends AbstractExcelHandle {

    @Override
    protected String supported() {
        return FileFormatConst.EXCEL_XLSX;
    }

    @Override
    protected Workbook getWorkbok(InputStream in) throws IOException {
        return in == null ? new XSSFWorkbook() : new XSSFWorkbook(in);
    }
}
