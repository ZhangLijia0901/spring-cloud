package utils.file;

import java.io.File;
import java.util.List;

/**
 * 文件处理
 * 
 * @author gener
 * @date 2019/09/18
 */
public interface FileHandle {

    /** 读取文件内容 */
    List<?> readFile(String filePath);

    List<?> readFile(File file);

    /** 写入文件 */
    void writeFile(String filePath, List<?> content) throws Exception;

    /** 返回是否支持文件 */
    boolean supported(String filePath);

}
