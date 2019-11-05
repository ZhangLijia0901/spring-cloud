package utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractFileHandle implements FileHandle {

    private String parsePath(String path) {
        if (path == null)
            return FileFormatConst.DEFAULT_PATH;
        if (!path.contains(":"))
            return FileFormatConst.DEFAULT_PATH + path;
        return path;

    }

    protected File getFile(String filePath) {
        filePath = parsePath(filePath);
        File file = new File(filePath);
        return file;
    }

    protected InputStream getInputStream(String filePath) throws FileNotFoundException {
        File file = getFile(filePath);
        return getInputStream(file);
    }

    protected InputStream getInputStream(File file) throws FileNotFoundException {
        if (file.isDirectory())
            throw new FileNotFoundException(file.toString() + " 不是一个文件！");
        FileInputStream fis = new FileInputStream(file);
        return fis;
    }

    protected OutputStream getOutputStream(String filePath) throws FileNotFoundException {
        File file = getFile(filePath);
        return getOutputStream(file);
    }

    protected OutputStream getOutputStream(File file) throws FileNotFoundException {
        if (file.isDirectory())
            throw new FileNotFoundException(file.toString() + " 不是一个文件！");
        FileOutputStream fis = new FileOutputStream(file);
        return fis;
    }

    @Override
    public boolean supported(String filePath) {
        if (filePath.endsWith(supported()))
            return true;
        return false;
    }

    protected abstract String supported();

}
