package Model;

public class Users {
    private String phone,name,pass;

    public Users() {
    }

    public Users(String phone, String name, String pass) {
        this.phone = phone;
        this.name = name;
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
