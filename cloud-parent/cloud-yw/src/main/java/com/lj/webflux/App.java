package com.lj.webflux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.lj.webflux.config.DatabaseConfig;
import com.lj.webflux.entity.CmParamHeadProtocol;
import com.lj.webflux.entity.CmParamProtocol;
import com.lj.webflux.mapper.CmParamHeadProtocolMapper;
import com.lj.webflux.mapper.CmParamProtocolMapper;

import utils.file.FileHandleUtils;

// import com.lj.webflux.controller.UserController;
// import com.spring4all.swagger.EnableSwagger2Doc;

@SpringBootApplication(scanBasePackageClasses = {DatabaseConfig.class
    // UserController.class
})
// @EnableSwagger2Doc
public class App implements ApplicationRunner {
    static String fileName = "";
    static String code = "";
    static Integer unit = 0;
    static int no = 0;
    boolean insertParam = true;
    boolean insertHead = true;

    @Resource
    CmParamProtocolMapper cmParamProtocolMapper;

    @Resource
    CmParamHeadProtocolMapper cmParamHeadProtocolMapper;

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(App.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            exces();

            // cmParamHeadProtocolMapper.selectAll().forEach(o -> {
            // });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Map<String, Set<String>> filter = Map.of("E12", Set.of("速度")); // 车型参数名过滤

    List<CmParamHeadProtocol> mergin(List<CmParamProtocol> cmParamProtocols) {
        List<CmParamHeadProtocol> cmParamHeadProtocols = new ArrayList<>();
        Map<String, List<CmParamProtocol>> maps = new LinkedHashMap<>();
        for (CmParamProtocol cm : cmParamProtocols) {
            if (filter.get(cm.getTrainCate()) != null && filter.get(cm.getTrainCate()).contains(cm.getName()))
                continue;
            if (mergins.contains(cm.getName())) {
                if (kvListss.contains(cm.getKvList())
                    && (cm.getName().equals("控制指令CI") || cm.getName().equals("牵引信息"))) {
                    continue;
                }

                List<CmParamProtocol> list = maps.get(cm.getName());
                if (list == null) {
                    list = new ArrayList<>();
                    maps.put(cm.getName(), list);
                }
                list.add(cm);
            }
        }

        int tempNo = 999;
        for (Entry<String, List<CmParamProtocol>> entity : maps.entrySet()) {
            List<CmParamProtocol> cms = entity.getValue();

            CmParamHeadProtocol headProtocol = new CmParamHeadProtocol();
            headProtocol.setTrainCate(cms.get(0).getTrainCate());
            headProtocol.setCode(headProtocol.getTrainCate() + tempNo);
            headProtocol.setName(cms.get(0).getName());
            headProtocol.setType("024");
            switch (entity.getKey()) {
                case "速度":
                    headProtocol.setUnit("km/h");
                case "里程": {
                    if (headProtocol.getUnit() == null)
                        headProtocol.setUnit("km");
                    if (cms.size() != 2)
                        throw new RuntimeException("速度里程、参数数量错误...");
                    headProtocol.setParamCodes(cms.get(0).getCode() + "," + cms.get(1).getCode());
                    headProtocol.setParamkv("1,*");
                    headProtocol.setDict(cms.get(1).getCode());
                    headProtocol.setType("005");
                    break;
                }
                case "运行方向": {
                    headProtocol.setParamkv("1,0|1,1|0,1|0,0");
                    headProtocol.setDict("前向|无效|后向|无方向");
                }
                case "档位":
                case "制动信息": {
                    String paramCodes = "";
                    for (CmParamProtocol cm : cms)
                        paramCodes += cm.getCode() + ",";
                    paramCodes = paramCodes.substring(0, paramCodes.length() - 1);
                    headProtocol.setParamCodes(paramCodes);
                    if (headProtocol.getParamkv() == null && headProtocol.getName().equals("制动信息")) {
                        headProtocol.setParamkv("[" + paramCodes.replaceAll("\\,", "],[") + "]");
                        headProtocol.setDict("*");
                    }

                    if (headProtocol.getParamkv() == null && headProtocol.getName().equals("档位")) {
                        headProtocol.setParamkv("1,1,1,31|0,0,0,*|1,*,*,*|0,1,[" + cms.get(3).getCode() + "],*|0,0,1,["
                            + cms.get(3).getCode() + "]");
                        headProtocol.setDict("无效|无档位|快速|?|?");
                    }
                    break;
                }
                case "控制指令制动": {
                    String paramCodes = "";
                    String kv = "0,";
                    for (CmParamProtocol cm : cms) {
                        paramCodes += cm.getCode() + ",";
                        kv += "*,";
                    }
                    paramCodes = paramCodes.substring(0, paramCodes.length() - 1);
                    kv = kv.substring(0, kv.length() - 3);
                    kv += "|1,[" + paramCodes.replaceAll("\\,", "],[") + "]";

                    headProtocol.setParamCodes(paramCodes);
                    headProtocol.setParamkv(kv.replaceAll("\\[" + cms.get(0).getCode() + "],", ""));
                    headProtocol.setDict("指令有效|*");
                    break;
                }
                case "控制指令CI":
                case "牵引信息": {
                    CmParamProtocol youxiao = null;
                    CmParamProtocol qianyin = null;
                    CmParamProtocol jioudang = null;
                    List<CmParamProtocol> qita = new ArrayList<>();
                    for (CmParamProtocol cm : cms) {
                        if (cm.getKvList().contains("=指令有效"))
                            youxiao = cm;
                        else if (cm.getKvList().contains("=牵引"))
                            qianyin = cm;
                        else if (cm.getKvList().contains("=偶数档") || cm.getKvList().contains("=偶数挡")
                            || cm.getKvList().contains("=偶数"))
                            jioudang = cm;
                        else
                            qita.add(cm);
                    }
                    String paramCodes = "";
                    if (youxiao != null)
                        paramCodes += youxiao.getCode() + ",";
                    if (qianyin != null)
                        paramCodes += qianyin.getCode() + ",";
                    if (jioudang != null)
                        paramCodes += jioudang.getCode() + ",";
                    for (CmParamProtocol qt : qita)
                        paramCodes += qt.getCode() + ",";
                    paramCodes = paramCodes.substring(0, paramCodes.length() - 1);

                    String[] pcs = paramCodes.split("\\,");

                    String kv = "";
                    String dict = "";

                    String yx = "";
                    if (youxiao != null) {
                        String temp = "";
                        for (String pc : pcs)
                            if (youxiao.getCode().equals(pc))
                                temp += "0,";
                            else
                                temp += "*,";
                        dict += "指令无效|";
                        kv += temp.substring(0, temp.length() - 1) + "|";
                        yx += "1,";
                    }
                    if (qianyin != null) {
                        String temp = "";
                        for (String pc : pcs)
                            if (qianyin.getCode().equals(pc))
                                temp += "0,";
                            else
                                temp += "*,";
                        dict += "非牵引|";
                        kv += temp.substring(0, temp.length() - 1) + "|";
                        yx += "1,";
                    }

                    String qc = "";
                    for (CmParamProtocol qt : qita)
                        qc += qt.getCode() + ",";
                    qc = qc.substring(0, qc.length() - 1);

                    if (jioudang != null) {
                        String dictkv = yx + "0,[" + qc.replaceAll("\\,", "],[") + "]";
                        kv += dictkv + "|";

                        dictkv = yx + "1,[" + qc.replaceAll("\\,", "+1],[") + "+1]";
                        kv += dictkv + "|";

                        dict += "?|?|";
                    } else {
                        String dictkv = yx + "[" + qc.replaceAll("\\,", "],[") + "]";
                        kv += dictkv + "|";
                        dict += "?|";
                    }

                    kv = kv.substring(0, kv.length() - 1);
                    dict = dict.substring(0, dict.length() - 1);

                    headProtocol.setDict(dict);
                    headProtocol.setParamkv(kv);
                    headProtocol.setParamCodes(paramCodes);
                    headProtocol.setRemark("牵引%s档");
                    break;
                }
                case "制动级位":
                case "停放制动信息": {
                    String paramCodes = "";
                    for (CmParamProtocol cm : cms)
                        paramCodes += cm.getCode() + ",";

                    String[] kvLists = cms.get(0).getKvList().split("\\;");
                    String kv = "";
                    String dict = "";

                    for (String kvList : kvLists) {
                        String[] temp = kvList.split("\\=");
                        kv += temp[0] + "|";
                        dict += temp[1] + "|";
                    }
                    kv = kv.substring(0, kv.length() - 1);
                    dict = dict.substring(0, dict.length() - 1);

                    headProtocol.setDict(dict);
                    headProtocol.setParamkv(kv);
                    headProtocol.setParamCodes(paramCodes);
                    break;
                }
            }
            tempNo--;
            cmParamHeadProtocols.add(headProtocol);
        }
        return cmParamHeadProtocols;
    }

    Set<String> mergins = Set.of("速度", "里程", "运行方向", "档位", "制动信息", "控制指令制动", "控制指令CI", "牵引信息", "制动级位", "停放制动信息");

    void exces() {
        List<List<Object>> fileMapping = new ArrayList<>();
         fileMapping.add(List.of("GT协议\\NE12.xls", "NE12", 16, "GT协议\\NE12-HEAD.xls")); // ok
         fileMapping.add(List.of("GT协议\\NE27.xls", "NE27", 8, "GT协议\\NE27-HEAD.xls")); // ok
         fileMapping.add(List.of("GT协议\\NE28.xls", "NE28", 8, "GT协议\\NE28-HEAD.xls")); // ok
         fileMapping.add(List.of("GT协议\\NE35.xls", "NE35", 16, "GT协议\\NE35-HEAD.xls")); // ok
         fileMapping.add(List.of("GT协议\\NE36.xls", "NE36", 16, "GT协议\\NE36-HEAD.xls")); // ok
         fileMapping.add(List.of("GT协议\\CRH6A2006.xls", "CRH6A", 8, "GT协议\\CRH6A2006-HEAD.xls")); // ok
        fileMapping.add(List.of("GT协议\\E12.xls", "E12", 8, "GT协议\\E12-HEAD.xls"));// ok

       // fileMapping.add(List.of("GT协议\\CRH6F-A.xls", "E21", 4));// ok

        for (List<Object> list : fileMapping) {
            fileName = (String)list.get(0);
            code = (String)list.get(1);
            unit = (Integer)list.get(2);

            if (insertParam)
                cmParamProtocolMapper.delete(code);
            List<CmParamProtocol> cmParamProtocols = xlsToObj();
            if (insertParam)
                cmParamProtocols.forEach(cmParamProtocolMapper::insert);

            if (list.size() <= 3)
                continue;
            // Head
            no = cmParamProtocols.size() + 1;
            fileName = (String)list.get(3);
            cmParamProtocols = xlsHeadToObj();
            List<CmParamHeadProtocol> cmParamHeadProtocols = mergin(cmParamProtocols);// 合并整车参数

            cmParamProtocols.forEach(o -> {
                if (o.getType().equals("005"))
                    o.setKvList("");
            });
            if (insertParam)
                cmParamProtocols.forEach(cmParamProtocolMapper::insert);

            if (insertHead) {
                cmParamHeadProtocolMapper.delete(code);
                cmParamHeadProtocols.forEach(cmParamHeadProtocolMapper::insert);
            }

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
                if (!StringUtils.hasLength(cmParamProtocol.getName()))
                    continue;
                cmParamProtocol.setCar1((String)list.get(3));
                cmParamProtocol.setCar2((String)list.get(4));
                cmParamProtocol.setCar3((String)list.get(5));
                cmParamProtocol.setCar4((String)list.get(6));
                int temp = 7;
                if (unit != 4) {
                    cmParamProtocol.setCar5((String)list.get(7));
                    cmParamProtocol.setCar6((String)list.get(8));
                    cmParamProtocol.setCar7((String)list.get(9));
                    cmParamProtocol.setCar8((String)list.get(10));
                    temp = 11;
                }

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
                cmParamProtocol.setType("005");
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
                    if (kv.indexOf("=") != -1)
                        cmParamProtocol.setType("024");
                    // kv = kv.replaceAll("\\#", "");
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

                    String oneTrainCode = null;
                    for (int j = 0; j < codes.length; j = j + 2) {
                        CmParamProtocol cm = new CmParamProtocol();
                        cm.setCode(code + (no < 100 ? "0" + no : no));
                        no++;
                        cm.setName((String)temp.get(2));
                        cm.setCar1(codes[j]);
                        cm.setCar8(codes[j]);
                        if (unit == 16) {
                            cm.setCar9(codes[j]);
                            cm.setCar16(codes[j]);
                        }
                        cm.setTrainCate(code);
                        cm.setParamType("0");

                        if (!names.contains(cm.getName()))
                            cm.setIsDerive(1);
                        if (filter.get(code) != null && filter.get(code).contains(cm.getName()))
                            cm.setIsDerive(0);
                        if (kvListss.contains(kvList)
                            && (cm.getName().equals("控制指令CI") || cm.getName().equals("牵引信息"))) {
                            cm.setName(kvList.replaceAll("1=", ""));
                        }
                        if (kvList.contains("1=") && !kvList.contains("0=") && !kvList.contains(","))
                            kvList += ";0= ";
                        cm.setType("005");
                        if (kvList.indexOf("=") != -1)
                            cm.setType("024");
                        cm.setKvList(kvList);
                        if (kvList.indexOf(",") != -1) {
                            cm.setType("005");
                            // cm.setKvList("");
                        }
                        if (cm.getName().equals("主控")) {
                            if (oneTrainCode == null) {
                                oneTrainCode = codes[j];
                                continue;
                            } else {
                                cm.setCar1(oneTrainCode);
                                cm.setCar9(null);
                                oneTrainCode = null;
                                if (unit == 16)
                                    cm.setCar8(null);
                                else
                                    cm.setCar16(null);
                                cm.setIsDerive(0);
                                cm.setType("024");
                                cm.setKvList("0=非主控端;1=主控端");
                            }
                        }

                        switch (cm.getName()) {
                            case "牵引信息":
                            case "控制指令CI": {
                                if (kvList.contains("偶数档")) {
                                    cm.setType("005");
                                } else if (kvList.contains("档") || kvList.contains("挡")) {
                                    cm.setType("024");
                                    kvList = kvList.replaceAll("牵引", "").replaceAll("档", "").replaceAll("挡", "");
                                    cm.setKvList(kvList);
                                } else {
                                    cm.setType("005");
                                }
                                break;
                            }
                            case "控制指令制动": {
                                if (kvList.contains("指令有效")) {
                                    cm.setType("005");
                                }
                                break;
                            }
                        }
                        cmParamProtocols.add(cm);
                    }
                }
            }
        }

        return cmParamProtocols;
    }

    Set<String> names = Set.of("加减速度", "线区代码", "外温", "恒速", "救援", "WTD设备", "联挂状态");
    Set<String> kvListss = Set.of("1=前进", "1=后退", "1=复位", "1=电制动", "1=高速加速", "1=恒速", "1=空档");

}
