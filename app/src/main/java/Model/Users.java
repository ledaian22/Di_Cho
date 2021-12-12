package Model;

public class Users {
    private String phone,name,pass,address,email,image;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Users(String phone, String name, String pass, String address, String email, String image) {
        this.phone = phone;
        this.name = name;
        this.pass = pass;
        this.address = address;
        this.email = email;
        this.image = image;
    }

    public Users() {
    }
}
