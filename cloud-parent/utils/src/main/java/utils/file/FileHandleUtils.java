package utils.file;

import java.util.ArrayList;
import java.util.List;

import utils.file.excel.XLSHandle;
import utils.file.excel.XLSXHandle;
import utils.file.txt.CsvHandle;
import utils.file.txt.TxtHandle;

public class FileHandleUtils {
    static List<FileHandle> fileHandles = new ArrayList<>();

    static {
        fileHandles.add(new XLSHandle());
        fileHandles.add(new XLSXHandle());
        fileHandles.add(new TxtHandle());
        fileHandles.add(new CsvHandle());
    }

    public static List<?> readFile(String filePath) {
        for (FileHandle fileHandle : fileHandles) {
            if (fileHandle.supported(filePath))
                return fileHandle.readFile(filePath);
        }
        return new ArrayList<>();
    }

    public static void writeFile(String filePath, List<?> ls) throws Exception {
        for (FileHandle fileHandle : fileHandles) {
            if (fileHandle.supported(filePath))
                fileHandle.writeFile(filePath, ls);
        }
    }
}
