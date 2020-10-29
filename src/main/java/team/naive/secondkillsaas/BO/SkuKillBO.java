package team.naive.secondkillsaas.BO;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import team.naive.secondkillsaas.DO.SkuQuantityDO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/10/27
 * @description: 该类包含一个sku用于秒杀的分桶信息
 */
@Data
public class SkuKillBO {

    //秒杀的skuId
    private Long skuId;
    //商品总数
    private Long amount;
    //秒杀开始时间
    private Date startTime;
    //秒杀结束时间
    private Date endTime;
    //分桶数
    private int bucketAmount;
    //键值对：桶编号-桶中库存量
    private Map<Integer, Long> bucketMap;

    public SkuKillBO(){
        this.bucketMap = new HashMap<>();
    }

    public SkuKillBO(SkuQuantityDO skuQuantityDO){
        BeanUtils.copyProperties(skuQuantityDO, this);
        this.bucketMap = new HashMap<>();
        this.divideIntoBucket();
    }

    /**
     * 进行分桶
     *
     * 分桶策略：
     * 1.当库存数量(amount)超过10000时
     * 使用100个桶，桶容量没有上限
     *
     * 1.当库存数量<=10000时
     * 桶数量为amount/100 + 1，每个桶容纳1-100个库存
     *
     * 2.库存平局分给每个桶
     *
     */
    private void divideIntoBucket(){
        if(this.amount>=10000){
            this.bucketAmount = 100;
        }
        else{
            this.bucketAmount = (int) (this.amount / 100 +1);
        }
        //对于每个桶来说
        int remain = (int) (this.amount % this.bucketAmount);
        for(int i=0;i<this.bucketAmount;i++){
            //桶中的库存量
            long bucketContent = this.amount / this.bucketAmount;
            if(i<remain){
                bucketContent++;
            }
            this.bucketMap.put(i, bucketContent);
        }
    }
}
