package team.naive.secondkillsaas.BO;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */

@Data
public class ItemDetailBO {

    private Date gmtCreated;

    private Date gmtModified;

    private Long itemId;

    private Integer total;

    private Date startTime;

    private Date endTime;

    private Byte isActive;

}