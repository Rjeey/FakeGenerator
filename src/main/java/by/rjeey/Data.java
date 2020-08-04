package by.rjeey;

public class Data {
    private String fullName;
    private String address;
    private String phoneNumber;

    public Data(String fullName, String address,  String phoneNumber) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void Replacement(Data d){
        this.fullName = d.getFullName();
        this.address = d.getAddress();
        this.phoneNumber = d.getPhoneNumber();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return fullName +
                "; " + address +
                "; " + phoneNumber;
    }
}