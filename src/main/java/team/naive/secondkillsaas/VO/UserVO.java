package team.naive.secondkillsaas.VO;

import team.naive.secondkillsaas.DO.UserDO;

/**
 * @author wangyuxiao
 * @date 2020/10/19
 */
public class UserVO {

    private Integer id;
    private String username;
    private String password;
    private int role;

    public UserVO(Integer id, String username, String password, int role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserVO(UserDO user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = null;
        this.role = user.getRole();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
