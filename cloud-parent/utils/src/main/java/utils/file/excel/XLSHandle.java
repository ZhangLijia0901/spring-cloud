package utils.file.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import utils.file.FileFormatConst;

/**
 * xls
 * 
 * @author gener
 * @date 2019/09/20
 */
public class XLSHandle extends AbstractExcelHandle {

    @Override
    protected String supported() {
        return FileFormatConst.EXCEL_XLS;
    }

    @Override
    protected Workbook getWorkbok(InputStream in) throws IOException {
        return in == null ? new HSSFWorkbook() : new HSSFWorkbook(in);
    }
}
