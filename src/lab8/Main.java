package lab8;

import java.io.IOException;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        String path = "/path/to/lab8/examples/";
        AdminUnitList adminUnitList = new AdminUnitList();
        try {
            adminUnitList.read(path + "admin-units.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
