package com.gistone.demomybatis.web;

import com.gistone.demomybatis.database.BirthPeopleCount;
import com.gistone.demomybatis.database.BirthPeopleCountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataFileService {


    public static final String born_data_file = "bornData.cvs";
    @Autowired
    private BirthPeopleCountDao birthPeopleCountDao;

    public void insert2010PopulationDataToDb() {
        // 1. 加载文件
        String file = "ageLocalData2010.cvs";
        List<String> bornDataFile = getFileByLine("static", file);
        // 2. 每一行处理 放入数据库obj
        List<BirthPeopleCount> dbDataList = new ArrayList<>();
        String template = "(.*?)年：(.*?)万";
        for (String line : bornDataFile) {
            String[] strings = processCommentAndTemplate(line, template);
            if (strings != null) {
                BirthPeopleCount birthPeopleCount = new BirthPeopleCount(strings);
                dbDataList.add(birthPeopleCount);
            }
        }
    }

    public void insertBornDataToDb() {
        // 1. 加载文件
        List<String> bornDataFile = getFileByLine("static", born_data_file);
        // 2. 每一行处理 放入数据库obj
        List<BirthPeopleCount> dbDataList = new ArrayList<>();
        String template = "(.*?)年：(.*?)万";
        for (String line : bornDataFile) {
            String[] strings = processCommentAndTemplate(line, template);
            if (strings != null) {
                BirthPeopleCount birthPeopleCount = new BirthPeopleCount(strings);
                dbDataList.add(birthPeopleCount);
            }
        }
        // 3. 放入数据库
        for (BirthPeopleCount birthPeopleCount : dbDataList) {
            birthPeopleCountDao.insert(birthPeopleCount);
        }
    }


    private List<String> getFileByLine(String path, String filename) {

        List<String> lines = new ArrayList<>();
        ClassPathResource file = new ClassPathResource(path + "/" + filename);
        try {
            Scanner scanner = new Scanner(file.getInputStream());
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private String[] processCommentAndTemplate(String line, String template) {
        String slashLineStart = "#";
        if (line.startsWith(slashLineStart)) {
            return null;
        }
        Pattern pattern = Pattern.compile(template);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return new String[]{matcher.group(1), matcher.group(2)};
        }
        return null;
    }
}
