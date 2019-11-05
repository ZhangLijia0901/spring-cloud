package utils.file.txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import utils.file.AbstractFileHandle;
import utils.file.FileFormatConst;

public abstract class AbstractTxtHandle extends AbstractFileHandle {

    @Override
    public List<?> readFile(String filePath) {
        return readFile(getFile(filePath));
    }

    @Override
    public List<?> readFile(File file) {
        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(new DataInputStream(new FileInputStream(file)), FileFormatConst.CHARSET))) {

            String line = null;
            List<Object> rows = new ArrayList<Object>();
            while ((line = br.readLine()) != null)
                rows.add(readEvent(line));
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void writeFile(String filePath, List<?> content) throws Exception {
        File file = getFile(filePath);

        try (BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(new DataOutputStream(new FileOutputStream(file)), FileFormatConst.CHARSET))) {
            content.forEach(o -> {
                try {
                    bw.append(writeEvent(o));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    protected abstract Object readEvent(String line);

    protected abstract String writeEvent(Object line);

}
