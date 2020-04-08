import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnector {
    static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    static String connect = "jdbc:derby:C:\\Apache\\db-derby-10.12.1.1-bin\\bin\\DigitalShop";

    public JDBCConnector(){
        System.setProperty("derby.system.home", "C:\\Apache\\db-derby-10.12.1.1-bin");
    }

    public List<String> displayDevices(){
        List<String> strings=new ArrayList<>();
        try {
            // Регистрируем драйвер JDBC
            Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" );
            // Подключаемся к БД
            Connection conn = DriverManager.getConnection(connect);
            // Выполняем запрос
            Statement st = conn.createStatement();
            ResultSet rec = st.executeQuery("SELECT * FROM devices order by devices.id");
            // Просматриваем и печатаем записи результирующей таблицы
            while (rec.next()) {
                int id=rec.getInt("id");
                String name = rec.getString("name");
                name=name.replaceAll(" ","_");
                String manu=rec.getString("manufacturer");
                strings.add(id+" " + name+" "+manu);
            }
            rec.close();
            st.close();
        } catch (Exception e) {
            System.err.println("Run-time error: " + e );
        }
        return strings;
    }

    public List<String> displayOrders(){
        List<String> result=new ArrayList<>();
        try {
            // Регистрируем драйвер JDBC
            Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" );
            // Подключаемся к БД
            Connection conn = DriverManager.getConnection(connect);
            // Выполняем запрос
            Statement st = conn.createStatement();
            ResultSet rec = st.executeQuery("SELECT orders.code, devices.name as Device, couriers.name as Courier" +
                    " FROM orders, devices, couriers where" +
                    " orders.idDevice=devices.id and " +
                    "orders.idCourier=couriers.id order by orders.code");
            // Просматриваем и печатаем записи результирующей таблицы
            while (rec.next()) {
                int code=rec.getInt("code");
                String courName=rec.getString("Courier");
                String dev = rec.getString("Device");
                dev=dev.replaceAll(" ","_");
                result.add(code+" " +dev+" " +
                        courName);
            }
            rec.close();
            st.close();
        } catch (Exception e) {
            System.err.println("Run-time error: " + e );
        }
        return result;
    }

    public List<String> displayCouriers(){
        List<String> result=new ArrayList<>();
        try {
            // Регистрируем драйвер JDBC
            Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" );
            // Подключаемся к БД
            Connection conn = DriverManager.getConnection(connect);
            // Выполняем запрос
            Statement st = conn.createStatement();
            ResultSet rec = st.executeQuery("SELECT * from couriers order by couriers.id");
            // Просматриваем и печатаем записи результирующей таблицы
            while (rec.next()) {
                int id=rec.getInt("id");
                String courName=rec.getString("name");
                result.add(id+" " +
                        courName);
            }
            rec.close();
            st.close();
        } catch (Exception e) {
            System.err.println("Run-time error: " + e );
        }
        return result;
    }

    public List<String> displayOrdersForCour(int id){
        List<String> result=new ArrayList<>();
        try {
            // Регистрируем драйвер JDBC
            Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" );
            // Подключаемся к БД
            Connection conn = DriverManager.getConnection(connect);
            PreparedStatement st = conn.prepareStatement(" select orders.code, " +
                    "devices.name as Device, couriers.name as Courier" +
                    " from orders, couriers, devices where orders.idDevice=devices.id and orders.idCourier = ? and " +
                    "orders.idCourier=couriers.id");
            st.setInt(1,id);
            ResultSet rec = st.executeQuery();
            while (rec.next()) {
                int code=rec.getInt("code");
                String courName=rec.getString("Courier");
                String dev = rec.getString("Device");
                result.add("Code:"+code+", Device:" +dev+", Name of courier:" +
                        courName);
                result.add("************************");
            }
            rec.close();
            st.close();
        } catch (Exception e) {
            System.err.println("Run-time error: " + e );
        }
        return result;
    }

    public List<String> displayDevsForManu(String name){
        List<String> result=new ArrayList<>();
        try {
            // Регистрируем драйвер JDBC
            Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" );
            // Подключаемся к БД
            Connection conn = DriverManager.getConnection(connect);
            // Выполняем запрос
            PreparedStatement st = conn.prepareStatement(" SELECT * FROM devices where devices.manufacturer=?");
            st.setString(1,name);
            ResultSet rec = st.executeQuery();
            while (rec.next()) {
                int id=rec.getInt("id");
                String nameDev = rec.getString("name");
                String manu=rec.getString("manufacturer");
                result.add("Id:"+id+" Name: " + nameDev+", Manufacturer: "+manu);
            }
            rec.close();
            st.close();
        } catch (Exception e) {
            System.err.println("Run-time error: " + e );
        }
        return result;
    }

    public void delOrder(int code){
        try {
            // Регистрируем драйвер JDBC
            Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" );
            Connection conn = DriverManager.getConnection(connect);
            PreparedStatement st = conn.prepareStatement("delete from orders where code=?");
            st.setInt(1,code);
            st.execute();
            st.close();
        } catch (Exception e) {
            System.err.println("Run-time error: " + e );
        }
    }

    public void insert(String value, int what){
        try {
            // Регистрируем драйвер JDBC
            Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" );
            Connection conn = DriverManager.getConnection(connect);
            String query;
            PreparedStatement st;
            String[] mas=value.split(" ");
            switch (what){
                case 1:
                    query="insert into devices values(?,?,?)";
                    st = conn.prepareStatement(query);
                    st.setInt(1,Integer.parseInt(mas[0]));
                    st.setString(2,mas[1]);
                    st.setString(3,mas[2]);
                    break;
                case 2:
                    query="insert into couriers values(?,?)";
                    st = conn.prepareStatement(query);
                    st.setInt(1,Integer.parseInt(mas[0]));
                    st.setString(2,mas[1]);
                    break;
                case 3:
                    query="insert into orders values(?,?,?)";
                    st = conn.prepareStatement(query);
                    st.setInt(1,Integer.parseInt(mas[0]));
                    st.setInt(2,Integer.parseInt(mas[1]));
                    st.setInt(3,Integer.parseInt(mas[2]));
                    break;
                    default: return;
            }
            st.execute();
            st.close();
        } catch (Exception e) {
            System.err.println("Run-time error: " + e );
        }
    }
}
