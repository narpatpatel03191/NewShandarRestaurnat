package narpatpatel031999.com.newshandarrestaurant;

public class fooditem {
    String Food_Name, Image, Price, Description, Id;

    public fooditem() {

    }

    public fooditem(String Food_Name, String Image, String Price, String Description, String Id) {
        this.Price = Price;
        this.Description = Description;
        this.Food_Name = Food_Name;
        this.Image = Image;
        this.Id = Id;
    }

    public String getPrice() {
        return Price;
    }


    public String getDescription() {
        return Description;
    }


    public String getFood_Name() {
        return Food_Name;
    }


    public String getImage() {
        return Image;
    }

    public String getId() {
        return Id;
    }

}

