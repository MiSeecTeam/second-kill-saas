package team.naive.secondkillsaas.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/25
 */
@Data
public class KillDTO implements Serializable {

    private static final long serialVersionUID = -2722873813306657206L;

    // 流水号保证幂等
    Long TransactionId;

    Long skuId;

    Long userId;
}
