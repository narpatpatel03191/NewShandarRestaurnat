package narpatpatel031999.com.newshandarrestaurant;

public class Orders {

    String Address,AllOrders,DateAndTime,TotalPrice;

    public Orders(){

    }

    public Orders(String address, String allOrders, String dateAndTime, String totalPrice) {
        Address = address;
        AllOrders = allOrders;
        DateAndTime = dateAndTime;
        TotalPrice = totalPrice;
    }


    public String getAddress() {
        return Address;
    }

    public String getAllOrders() {
        return AllOrders;
    }

    public String getDateAndTime() {
        return DateAndTime;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }
}
