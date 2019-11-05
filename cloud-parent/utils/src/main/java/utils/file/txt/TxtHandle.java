package utils.file.txt;

import java.util.List;

import utils.file.FileFormatConst;

public class TxtHandle extends AbstractTxtHandle {

    @Override
    protected Object readEvent(String line) {
        return line;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected String writeEvent(Object line) {

        StringBuilder builder = new StringBuilder();

        if (line instanceof List) {
            StringBuilder tmp = new StringBuilder();
            ((List)line).forEach(o -> tmp.append(o).append(","));
            builder.append(tmp.substring(0, tmp.length() - 1));
        } else
            builder.append(line.toString());
        builder.append("\r\n");

        return builder.toString();
    }

    @Override
    protected String supported() {
        return FileFormatConst.TXT;
    }

}
