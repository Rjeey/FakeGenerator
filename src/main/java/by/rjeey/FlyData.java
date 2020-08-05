package by.rjeey;

public class FlyData {

    private FlyData() {
    }

    public static String getFullName(int obnum) {
        return ExternalizeData.fullName[obnum];
    }

    public static void setFullName(String fullName, int obnum) {
        ExternalizeData.fullName[obnum] = fullName;
    }

    public static String getAddress(int obnum) {
        return ExternalizeData.address[obnum];
    }

    public static void setAddress(String address, int obnum) {
        ExternalizeData.address[obnum] = address;
    }

    public static String getPhoneNumber(int obnum) {
        return ExternalizeData.phoneNumber[obnum];
    }

    public static void setPhoneNumber(String phoneNumber, int obnum) {
        ExternalizeData.phoneNumber[obnum] = phoneNumber;
    }

    public static String str(int obnum) {

        return ExternalizeData.fullName[obnum] +
                "; " + ExternalizeData.address[obnum] +
                "; " + ExternalizeData.phoneNumber[obnum];
    }
}
