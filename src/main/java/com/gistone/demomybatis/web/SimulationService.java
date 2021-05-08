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
        yearBornMap.put(2020, (long) (1300 * 10000));

        Map<Integer, Double> yearManRateMap = new HashMap<>();
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


        List<AgeSexPeople> simulation2011 = simulation(simulation2010, yearBornMap.get(2010), yearManRateMap.get(2010));
        List<AgeSexPeople> simulation2012 = simulation(simulation2011, yearBornMap.get(2011), yearManRateMap.get(2011));
        List<AgeSexPeople> simulation2013 = simulation(simulation2012, yearBornMap.get(2012), yearManRateMap.get(2012));
        List<AgeSexPeople> simulation2014 = simulation(simulation2013, yearBornMap.get(2013), yearManRateMap.get(2013));
        List<AgeSexPeople> simulation2015 = simulation(simulation2014, yearBornMap.get(2014), yearManRateMap.get(2014));
        List<AgeSexPeople> simulation2016 = simulation(simulation2015, yearBornMap.get(2015), yearManRateMap.get(2015));
        List<AgeSexPeople> simulation2017 = simulation(simulation2016, yearBornMap.get(2016), yearManRateMap.get(2016));
        List<AgeSexPeople> simulation2018 = simulation(simulation2017, yearBornMap.get(2017), yearManRateMap.get(2017));
        List<AgeSexPeople> simulation2019 = simulation(simulation2018, yearBornMap.get(2018), yearManRateMap.get(2018));
        List<AgeSexPeople> simulation2020 = simulation(simulation2019, yearBornMap.get(2019), yearManRateMap.get(2019));
        int i = 1;
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
        printInfo(ageSexPeople);
        return ageSexPeople;
    }

    private void printInfo(List<AgeSexPeople> ageSexPeople) {
        Integer year = ageSexPeople.get(0).getYear();
        Long sum = 0l;
        for (AgeSexPeople ageSexPerson : ageSexPeople) {
            sum = sum + ageSexPerson.getSum();
        }
        // 总人口
        log.debug(year + "年 总人口为 " + sum);
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
