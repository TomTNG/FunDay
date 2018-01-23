package innt.ffhs.ch.funday.model;


public class Resort {
    public String name;
    public String availability;
    public String price;


    public Resort (String name, String availability, String price){
        this.name = name;
        this.availability = availability;
        this.price = price;
    }

    public Resort(){

    }

    public String getName () {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }
    public String getAvailability () {
        return availability;
    }
    public void setAvailability (String availability) {
        this.availability = availability;
    }

    public String getPrice () {
        return price;
    }
    public void setPrice (String price) {
        this.price = price;
    }





}
