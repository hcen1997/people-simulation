package com.gistone.demomybatis.web;

import com.gistone.demomybatis.database.DeathRate;
import com.gistone.demomybatis.database.DeathRateDao;
import com.gistone.demomybatis.database.PopulationSexAge;
import com.gistone.demomybatis.database.PopulationSexAgeDao;
import com.gistone.demomybatis.web.vo.AgeSexPeople;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 按照算法模拟每年的出生人口,死亡人口, 并存入数据库
 */
@Service
@Slf4j
public class SimulationService {


    private final DeathRateDao deathRateDao;
    private final PopulationSexAgeDao populationSexAgeDao;

    //    * @param deathRateTable   每个年龄的死亡率
    DeathRateTable deathRateTable;

    public SimulationService(DeathRateDao deathRateDao, PopulationSexAgeDao populationSexAgeDao) {
        this.deathRateDao = deathRateDao;
        this.populationSexAgeDao = populationSexAgeDao;
        deathRateTable = new DeathRateTable(deathRateDao);
    }

    public void sim2020() {
        List<PopulationSexAge> simulation2010P = populationSexAgeDao.queryByYear(2010);
        simulation2010P.removeIf(pp -> pp.getToAge() - pp.getFromAge() > 5);
        List<AgeSexPeople> simulation2010 = initData(simulation2010P);

        Map<Integer, Long> yearBornMap = new HashMap<>();
        setYearBornMap(yearBornMap);

        Map<Integer, Double> yearManRateMap = new HashMap<>();
        setYearManRateMap(yearManRateMap);

        final int fromYear = 2010;
        final int toYear = 2040;

        List<AgeSexPeople> simulationThisYear = simulation2010;
        printInfo(simulationThisYear);
        for (int curYear = fromYear; curYear <= toYear; curYear++) {
            List<AgeSexPeople> simulationNextYear = simulation(simulationThisYear, yearBornMap.get(2010), yearManRateMap.get(2010));
            printInfo(simulationNextYear);

            simulationThisYear = simulationNextYear;
        }


        int i = 1;
    }

    private void setYearManRateMap(Map<Integer, Double> yearManRateMap) {
        yearManRateMap.put(2010, 117.9);
        yearManRateMap.put(2011, 116.9);
        yearManRateMap.put(2012, 115.9);
        yearManRateMap.put(2013, 114.9);
        yearManRateMap.put(2014, 113.9);
        yearManRateMap.put(2015, 113.5);
        yearManRateMap.put(2016, 113.4);
        yearManRateMap.put(2017, 111.9);
        yearManRateMap.put(2018, 110.9);
        yearManRateMap.put(2019, 109.9);
        yearManRateMap.put(2020, 109.9);
        yearManRateMap.put(2021, 109.9);
        yearManRateMap.put(2022, 109.8);
        yearManRateMap.put(2023, 109.7);
        yearManRateMap.put(2024, 109.6);
        yearManRateMap.put(2025, 109.5);
        yearManRateMap.put(2026, 109.4);
        yearManRateMap.put(2027, 109.3);
        yearManRateMap.put(2028, 109.2);
        yearManRateMap.put(2029, 109.1);
        yearManRateMap.put(2030, 109.0);
        yearManRateMap.put(2031, 108.9);
        yearManRateMap.put(2032, 108.8);
        yearManRateMap.put(2033, 108.7);
        yearManRateMap.put(2034, 108.6);
        yearManRateMap.put(2035, 108.5);
        yearManRateMap.put(2036, 108.4);
        yearManRateMap.put(2037, 108.3);
        yearManRateMap.put(2038, 108.2);
        yearManRateMap.put(2039, 108.0);
        yearManRateMap.put(2040, 108.0);
        yearManRateMap.put(2041, 108.0);
        yearManRateMap.put(2042, 108.0);
    }

    private void setYearBornMap(Map<Integer, Long> yearBornMap) {
        yearBornMap.put(2010, (long) (1588 * 10000));
        yearBornMap.put(2011, (long) (1600 * 10000));
        yearBornMap.put(2012, (long) (1635 * 10000));
        yearBornMap.put(2013, (long) (1640 * 10000));
        yearBornMap.put(2014, (long) (1687 * 10000));
        yearBornMap.put(2015, (long) (1655 * 10000));
        yearBornMap.put(2016, (long) (1786 * 10000));
        yearBornMap.put(2017, (long) (1723 * 10000));
        yearBornMap.put(2018, (long) (1523 * 10000));
        yearBornMap.put(2019, (long) (1465 * 10000));
        yearBornMap.put(2020, (long) (1302 * 10000));
        yearBornMap.put(2021, (long) (1301 * 10000));
        yearBornMap.put(2022, (long) (1300 * 10000));
        yearBornMap.put(2023, (long) (1299 * 10000));
        yearBornMap.put(2024, (long) (1298 * 10000));
        yearBornMap.put(2025, (long) (1297 * 10000));
        yearBornMap.put(2026, (long) (1296 * 10000));
        yearBornMap.put(2027, (long) (1295 * 10000));
        yearBornMap.put(2028, (long) (1294 * 10000));
        yearBornMap.put(2029, (long) (1293 * 10000));
        yearBornMap.put(2030, (long) (1292 * 10000));
        yearBornMap.put(2031, (long) (1291 * 10000));
        yearBornMap.put(2032, (long) (1290 * 10000));
        yearBornMap.put(2033, (long) (1289 * 10000));
        yearBornMap.put(2034, (long) (1288 * 10000));
        yearBornMap.put(2035, (long) (1287 * 10000));
        yearBornMap.put(2036, (long) (1286 * 10000));
        yearBornMap.put(2037, (long) (1285 * 10000));
        yearBornMap.put(2038, (long) (1284 * 10000));
        yearBornMap.put(2039, (long) (1283 * 10000));
        yearBornMap.put(2040, (long) (1282 * 10000));
        yearBornMap.put(2041, (long) (1281 * 10000));
        yearBornMap.put(2042, (long) (1280 * 10000));
    }


    public void sim2010() {
        List<PopulationSexAge> simulation2010P = populationSexAgeDao.queryByYear(2010);
        simulation2010P.removeIf(pp -> pp.getToAge() - pp.getFromAge() > 5);
        List<AgeSexPeople> simulation2010 = initData(simulation2010P);

        List<AgeSexPeople> simulation = simulation(simulation2010, (long) (1588 * 10000), 117.9);
        int i = 1;
    }

    /**
     * 算法:
     * 1. 既有的每人大一岁
     * 2. 把新出生的放到数据盒子里, 年龄设置为0
     * 3. 对于每个年龄, 根据死亡率算人
     * 4. 返回
     *
     * @param ageSexPeople 年度人口分布
     * @param newBorn      当年新增人口
     * @param manWomanRate 当年新增人口的男女性别比例   108.2d
     * @return 下一年的人口分布
     */
    public List<AgeSexPeople> simulation(
            List<AgeSexPeople> ageSexPeople,
            Long newBorn, Double manWomanRate) {
//        1. 既有的每人大一岁
        increaseOne(ageSexPeople);
//        2. 把新出生的放到数据盒子里, 年龄设置为0
        addNewBorn(ageSexPeople, newBorn, manWomanRate);
//        3. 对于每个年龄, 根据死亡率算人
        dead(ageSexPeople, deathRateTable);
//        4. 返回
        ageSexPeople.sort(Comparator.comparingInt(AgeSexPeople::getAge));
        return ageSexPeople;
    }

    private void printInfo(List<AgeSexPeople> ageSexPeople) {
        Integer year = ageSexPeople.get(0).getYear();
        Long sum = 0l;
        for (AgeSexPeople person : ageSexPeople) {
            sum = sum + person.getSum();
        }
        // 总人口
        log.debug(year + "年 总人口为 " + sum);

        分年龄段计数(ageSexPeople, year, 0, 22);
        分年龄段计数(ageSexPeople, year, 23, 60);
        分年龄段计数(ageSexPeople, year, 61, 105);
    }

    private void 分年龄段计数(List<AgeSexPeople> ageSexPeople, Integer year, int min, int max) {
        Long sum1 = 0l;
        for (AgeSexPeople person : ageSexPeople) {
            if (person.getAge() >= min
                    && person.getAge() <= max) {
                sum1 = sum1 + person.getSum();
            }
        }
        log.debug(String.format("    %d年 年龄段为  %3d -%3d 总计为 %12d", year, min, max, sum1));
    }

    private void dead(List<AgeSexPeople> ageSexPeople, DeathRateTable deathRateTable) {
        for (AgeSexPeople people : ageSexPeople) {
            Integer age = people.getAge();
            BigDecimal manDeathRage = deathRateTable.getManDeathRage(age);
            BigDecimal womanDeathRage = deathRateTable.getWomanDeathRage(age);
            // die die die
            people.setMan(die(manDeathRage, people.getMan()));
            people.setWoman(die(womanDeathRage, people.getWoman()));
        }
        // 如果都死了, 比如超过了105岁, 就删除
        ageSexPeople.removeIf(pp ->
                pp.getMan().equals(0L) &&
                        pp.getMan().equals(pp.getWoman())
        );
    }

    private long die(BigDecimal womanDeathRage, Long woman) {
        BigDecimal all = BigDecimal.valueOf(woman);
        BigDecimal left = all.multiply(BigDecimal.ONE.subtract(womanDeathRage));
        long longValue = left.longValue();
        return longValue;
    }

    private void addNewBorn(List<AgeSexPeople> ageSexPeople, Long newBorn, Double manWomanRate) {
        Integer year = ageSexPeople.get(0).getYear();
        Long newMan = Math.round(newBorn * (manWomanRate / (manWomanRate + 100)));
        Long newWoman = newBorn - newMan;
        AgeSexPeople ageSexPerson = new AgeSexPeople(
                year,
                0,
                newMan,
                newWoman
        );
        ageSexPeople.add(ageSexPerson);
    }

    private void increaseOne(List<AgeSexPeople> ageSexPeople) {
        for (AgeSexPeople ageSexPerson : ageSexPeople) {
            ageSexPerson.increaseOneYear();
        }
    }

    private List<AgeSexPeople> initData(List<PopulationSexAge> populationInYear) {
        List<AgeSexPeople> result = new ArrayList<>();
        for (PopulationSexAge pp : populationInYear) {
            AgeSexPeople aa = new AgeSexPeople();
            aa.setYear(pp.getDataYear());
            aa.setMan(pp.getMan());
            aa.setWoman(pp.getWoman());
            // handle age range
            {
                Integer fromAge = pp.getFromAge();
                Integer toAge = pp.getToAge();
                if (Objects.equals(fromAge, toAge)) {
                    aa.setAge(fromAge);
                    result.add(aa);
                } else {
                    int totalCount = toAge - fromAge + 1;
                    aa.setMan(aa.getMan() / totalCount);
                    aa.setWoman(aa.getWoman() / totalCount);
                    for (int age = fromAge; age <= toAge; age++) {
                        aa.setAge(age);
                        result.add(aa.copy());
                    }
                }
            }
        }
        return result;
    }

    private class DeathRateTable {
        HashMap<Integer, DeathRate> integerDeathRateHashMap;

        public DeathRateTable(DeathRateDao deathRateDao) {
            List<DeathRate> deathRates = deathRateDao.selectAll();
            integerDeathRateHashMap = new HashMap<>();
            for (DeathRate deathRate : deathRates) {
                Integer age = deathRate.getAge();
                integerDeathRateHashMap.put(age, deathRate);
            }
        }

        public BigDecimal getManDeathRage(int age) {
            DeathRate deathRate = integerDeathRateHashMap.get(age);
            String rate = deathRate.getRateManNotOld1();
            return new BigDecimal(rate);
        }

        public BigDecimal getWomanDeathRage(int age) {
            DeathRate deathRate = integerDeathRateHashMap.get(age);
            String rate = deathRate.getRateWomanNotOld1();
            return new BigDecimal(rate);
        }
    }
}
