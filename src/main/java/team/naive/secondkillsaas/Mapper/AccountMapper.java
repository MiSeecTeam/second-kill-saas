package team.naive.secondkillsaas.Mapper;

import java.util.List;
import team.naive.secondkillsaas.DO.secondkill.UserDO;
import team.naive.secondkillsaas.DO.secondkill.UserDOExample;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    List<UserDO> selectByExample(UserDOExample example);

    UserDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}