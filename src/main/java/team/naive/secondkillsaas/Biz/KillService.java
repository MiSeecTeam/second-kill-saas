package team.naive.secondkillsaas.Biz;


import team.naive.secondkillsaas.DTO.KillDTO;
import team.naive.secondkillsaas.VO.ResponseVO;

/**
 * @Description
 * @Author Toviel Xue
 * @Date 2020/9/22
 */
public interface KillService {

    ResponseVO killItem(KillDTO killDTO) throws Exception;

}
