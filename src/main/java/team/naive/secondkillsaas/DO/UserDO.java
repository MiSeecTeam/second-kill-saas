package team.naive.secondkillsaas.DO;

public class UserDO {
    private Integer id;

    private String username;

    private String password;

    private String name;

    private Integer role;

    private Integer lastProject;

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
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getLastProject() {
        return lastProject;
    }

    public void setLastProject(Integer lastProject) {
        this.lastProject = lastProject;
    }
}