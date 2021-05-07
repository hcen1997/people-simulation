package com.gistone.demomybatis.web;

import com.gistone.demomybatis.database.BirthPeopleCount;
import com.gistone.demomybatis.database.BirthPeopleCountDao;
import com.gistone.demomybatis.database.PopulationSexAge;
import com.gistone.demomybatis.database.PopulationSexAgeDao;
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
    @Autowired
    private PopulationSexAgeDao populationSexAgeDao;

    public void insert2010PopulationDataToDb() {
        // 1. 加载文件
        String file = "ageLocalData2010.cvs";
        List<String> bornDataFile = getFileByLine("static", file);
        // 2. 每一行处理 放入数据库obj
        List<PopulationSexAge> dbDataList = new ArrayList<>();

        String template = "(\\d*)\\s*(\\d*)\\s*(\\d*)";
        for (int i = 0, bornDataFileSize = bornDataFile.size(), count = 0; i < bornDataFileSize; i++) {
            String line = bornDataFile.get(i);
            String[] strings = processCommentAndTemplate(line, template);
            if (strings != null) {
                PopulationSexAge populationSexAge = new PopulationSexAge(2010, strings);
                // 设置年份
                if (count == 0) {
                    populationSexAge.setFromAndTo(0, 105);
                } else if (count == 1) {
                    populationSexAge.setFromAndTo(0, 0);
                } else if (count == 2) {
                    populationSexAge.setFromAndTo(1, 4);
                } else {
                    int st = (count - 2) * 5;
                    int from = st;
                    int to = from + 4;
                    populationSexAge.setFromAndTo(from, to);
                }

                dbDataList.add(populationSexAge);
                count++;
            }
        }
        // 3. 放入数据库
        for (PopulationSexAge populationSexAge : dbDataList) {
            populationSexAgeDao.insert(populationSexAge);
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
        if (line.length() == 0 || null == line) {
            return null;
        }
        String slashLineStart = "#";
        if (line.startsWith(slashLineStart)) {
            return null;
        }
        Pattern pattern = Pattern.compile(template);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            int i = matcher.groupCount();
            String[] strings = new String[i];
            for (int i1 = 0; i1 < i; i1++) {
                strings[i1] = matcher.group(i1 + 1);
            }
            return strings;
        }
        return null;
    }
}
