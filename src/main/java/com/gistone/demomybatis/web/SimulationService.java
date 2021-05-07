package com.gistone.demomybatis.web;

import com.gistone.demomybatis.database.DeathRate;
import com.gistone.demomybatis.database.DeathRateDao;
import com.gistone.demomybatis.database.PopulationSexAge;
import com.gistone.demomybatis.database.PopulationSexAgeDao;
import com.gistone.demomybatis.web.vo.AgeSexPeople;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 按照算法模拟每年的出生人口,死亡人口, 并存入数据库
 */
@Service
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

        List<AgeSexPeople> simulation2011 = simulation(simulation2010, (long) (1588 * 10000), 117.9);
        List<AgeSexPeople> simulation2012 = simulation(simulation2011, (long) (1588 * 10000), 117.9);
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
        return ageSexPeople;
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
