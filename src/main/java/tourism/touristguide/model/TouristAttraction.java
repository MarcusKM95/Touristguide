package tourism.touristguide.model;

import java.util.List;

public class TouristAttraction {

    private String name;
    private String description;
    private String location;
    private List<String> tags;

    public TouristAttraction(String name, String description, String location, List<String> tags){
        this.name = name;
        this.description = description;
        this.location = location;
        this.tags = tags;
    }
    public TouristAttraction(String name, String description, String location){
        this.name = name;
        this.description = description;
        this.location = location;
    }


    public String getName() {
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getLocation(){
        return location;
    }
    public List<String> getTags(){
        return tags;
    }
}
