package narpatpatel031999.com.newshandarrestaurant;

public class cartItem {

    String Food_Name, Image, Price, Id;
    public long quantity = 1;

    public cartItem() {

    }

    public cartItem(String food_Name, String image, String price, String id) {
        Food_Name = food_Name;
        Image = image;
        Price = price;
        Id = id;
        quantity = 1;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getFood_Name() {
        return Food_Name;
    }

    public String getImage() {
        return Image;
    }

    public String getPrice() {
        return Price;
    }

    public String getId() {
        return Id;
    }
}
