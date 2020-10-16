package team.naive.secondkillsaas.Form;

import lombok.Data;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/10/16
 */
@Data
public class KillForm {

    // 流水号保证幂等
    Long transactionId;

    Long skuId;

    Long userId;
}
