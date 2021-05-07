package com.gistone.demomybatis.web;

import com.gistone.demomybatis.database.PopulationSexAge;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 按照算法模拟每年的出生人口,死亡人口, 并存入数据库
 */
@Service
public class SimulationService {


    /**
     * 算法:
     * 1. 既有的每人大一岁
     * 2. 把新出生的放到数据盒子里, 年龄设置为0
     * 3. 对于每个年龄, 根据死亡率算人
     * 4. 返回
     *
     * @param population     年度人口分布
     * @param deathRateTable 每个年龄的死亡率
     * @param newBorn        当年新增人口
     * @return 下一年的人口分布
     */
    public List<PopulationSexAge> simulation(
            List<PopulationSexAge> population,
            DeathRateTable deathRateTable,
            Long newBorn) {
        return null
    }

}