package com.lj.webflux;

import static org.assertj.core.api.Assertions.contentOf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.assertj.core.util.Arrays;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.lj.webflux.config.DatabaseConfig;
import com.lj.webflux.entity.CmParamProtocol;
import com.lj.webflux.mapper.CmParamProtocolMapper;

import utils.file.FileHandleUtils;

// import com.lj.webflux.controller.UserController;
// import com.spring4all.swagger.EnableSwagger2Doc;

@SpringBootApplication(scanBasePackageClasses = {DatabaseConfig.class
    // UserController.class
})
// @EnableSwagger2Doc
public class App implements ApplicationRunner {
    static String fileName = "GT协议\\\\NE12.xls";
    static String code = "NE12";
    static Integer unit = 16;

    @Resource
    CmParamProtocolMapper cmParamProtocolMapper;

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(App.class).web(WebApplicationType.NONE).run(args);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            exces();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static int no = 0;

    void exces() {
        List<List<Object>> fileMapping = new ArrayList<>();
        fileMapping.add(List.of("GT协议\\NE12.xls", "NE12", 16, "GT协议\\NE12-HEAD.xls"));
        // fileMapping.add(List.of("GT协议\\NE27.xls", "NE27", 8, "GT协议\\NE27-HEAD.xls"));
        // fileMapping.add(List.of("GT协议\\NE28.xls", "NE28", 8, "GT协议\\NE28-HEAD.xls"));
        // fileMapping.add(List.of("GT协议\\NE35.xls", "NE35", 16, "GT协议\\NE35-HEAD.xls"));
        // fileMapping.add(List.of("GT协议\\NE36.xls", "NE36", 16, "GT协议\\NE36-HEAD.xls"));
        // fileMapping.add(List.of("GT协议\\CRH6A2006.xls", "CRH6A", 8, "GT协议\\CRH6A2006-HEAD.xls"));

        for (List<Object> list : fileMapping) {
            fileName = (String)list.get(0);
            code = (String)list.get(1);
            unit = (Integer)list.get(2);

            cmParamProtocolMapper.delete(code);

            List<CmParamProtocol> cmParamProtocols = xlsToObj();
            cmParamProtocols.forEach(o -> {
                cmParamProtocolMapper.insert(o);
            });

            // Head
            no = cmParamProtocols.size() + 1;
            fileName = (String)list.get(3);
            cmParamProtocols = xlsHeadToObj();
            cmParamProtocols.forEach(o -> {
                cmParamProtocolMapper.insert(o);
            });

        }

    }

    List<CmParamProtocol> xlsToObj() {
        List<?> fs = FileHandleUtils.readFile(fileName);

        List<CmParamProtocol> cmParamProtocols = new ArrayList<>();
        for (int i = 0; i < fs.size(); i++) {
            CmParamProtocol cmParamProtocol = new CmParamProtocol();
            String paramCode = code;
            if (i < 9)
                paramCode += "00" + (i + 1);
            else if (i < 99)
                paramCode += "0" + (i + 1);
            else
                paramCode += (i + 1);
            cmParamProtocol.setCode(paramCode);
            cmParamProtocol.setTrainCate(code);
            if (fs.get(i) instanceof List) {
                List<Object> list = (List<Object>)fs.get(i);
                cmParamProtocol.setName((String)list.get(2));
                cmParamProtocol.setCar1((String)list.get(3));
                cmParamProtocol.setCar2((String)list.get(4));
                cmParamProtocol.setCar3((String)list.get(5));
                cmParamProtocol.setCar4((String)list.get(6));
                cmParamProtocol.setCar5((String)list.get(7));
                cmParamProtocol.setCar6((String)list.get(8));
                cmParamProtocol.setCar7((String)list.get(9));
                cmParamProtocol.setCar8((String)list.get(10));

                int temp = 11;
                if (unit == 16) {
                    cmParamProtocol.setCar9((String)list.get(11));
                    cmParamProtocol.setCar10((String)list.get(12));
                    cmParamProtocol.setCar11((String)list.get(13));
                    cmParamProtocol.setCar12((String)list.get(14));
                    cmParamProtocol.setCar13((String)list.get(15));
                    cmParamProtocol.setCar14((String)list.get(16));
                    cmParamProtocol.setCar15((String)list.get(17));
                    cmParamProtocol.setCar16((String)list.get(18));
                    temp = 19;
                }
                String kv = null;
                if (list.size() > temp)
                    kv = (String)list.get(temp);
                if (StringUtils.hasLength(kv) && !kv.equals("null")) {
                    if (!kv.startsWith("#")) {
                        while (list.size() > ++temp) {
                            String t = (String)list.get(temp);
                            if (StringUtils.hasLength(t) && !t.equals("null"))
                                kv += ";" + t;
                            else
                                break;
                        }
                    }
                    cmParamProtocol.setKvList(kv);
                }
            }
            cmParamProtocols.add(cmParamProtocol);
        }

        return cmParamProtocols;
    }

    List<CmParamProtocol> xlsHeadToObj() {
        List<?> fs = FileHandleUtils.readFile(fileName);
        List<CmParamProtocol> cmParamProtocols = new ArrayList<>();

        List<Object> temp = null;
        for (int i = 0; i < fs.size(); i++) {
            if (fs.get(i) instanceof List) {
                List<Object> list = (List<Object>)fs.get(i);
                if (list.get(0).toString().startsWith("TH")) {
                    temp = list;
                    continue;
                } else {
                    Integer codeNum = Integer.valueOf((String)list.get(1));
                    if (codeNum == 0)
                        continue;
                    String[] codes = ((String)list.get(2)).replaceAll("\\{", "").replaceAll("\\}", "")
                        .replaceAll("\\[", "").replaceAll("\\]", "").split("\\,");
                    if (codes.length != codeNum * 2) {
                        System.err.println(JSON.toJSONString(list));
                        continue;
                    }
                    String[] kvLists = ((String)list.get(3)).replaceAll("\\},\\{", ";").replaceAll("\\{", "")
                        .replaceAll("\\}", "").replaceAll("\\[", "").replaceAll("\\]", "").split("\\;");

                    Set<String> hashSet = new HashSet<>();
                    for (int j = 0; j < kvLists.length; j++) {
                        int b = kvLists[j].indexOf("(");
                        if (kvLists[j].startsWith("<0>="))
                            continue;

                        if (b != -1) {
                            int f = kvLists[j].indexOf(")");
                            hashSet.add((kvLists[j].substring(0, b) + kvLists[j].substring(f + 1)).replaceAll("\\<", "")
                                .replaceAll("\\>", ""));
                        } else {
                            hashSet.add(kvLists[j].replaceAll("\\<", "").replaceAll("\\>", ""));
                        }
                    }
                    String kvList = String.join(";", hashSet);

                    for (int j = 0; j < codes.length; j = j + 2) {
                        CmParamProtocol cm = new CmParamProtocol();
                        cm.setCode(code + (no++));
                        cm.setName((String)temp.get(2));
                        cm.setCar1(codes[j]);
                        cm.setCar8(codes[j]);
                        if (unit == 16) {
                            cm.setCar9(codes[j]);
                            cm.setCar16(codes[j]);
                        }
                        cm.setTrainCate(code);
                        cm.setParamType("0");
                        cm.setIsDerive(1);
                        cm.setKvList(kvList);
                        cmParamProtocols.add(cm);
                    }
                }
            }
        }

        return cmParamProtocols;
    }

}
