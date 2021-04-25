package narpatpatel031999.com.newshandarrestaurant;

public class fooditem {
    String Food_Name,Image;
    public fooditem(){

    }

    public  fooditem(String Food_Name,String Image){
        this.Food_Name=Food_Name;
        this.Image=Image;
    }
    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String food_Name) {
        Food_Name = food_Name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

