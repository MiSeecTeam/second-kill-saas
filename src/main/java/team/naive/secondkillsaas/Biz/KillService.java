package team.naive.secondkillsaas.Biz;


import team.naive.secondkillsaas.DTO.KillDTO;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
public interface KillService {

    Boolean killItem(KillDTO killDTO) throws Exception;

}
