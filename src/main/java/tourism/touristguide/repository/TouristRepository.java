package tourism.touristguide.repository;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tourism.touristguide.model.TouristAttraction;
import tourism.touristguide.service.TouristService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class TouristRepository {


    private List<TouristAttraction> touristAttractions;

    public TouristRepository(){
        this.touristAttractions = new ArrayList<>();
        //temp attractions
        touristAttractions.add(new TouristAttraction("Kongens Have", "Public park in the center of Copenhagen", "Copenhagen", List.of("Park", "Nature")));
        touristAttractions.add(new TouristAttraction("Runde tårn", "Very famous tower in Copenhagen, made by King Christian 4.", "Copenhagen", List.of("tower", "landmark")));
    }

    //CREATE
    public void addAttraction(TouristAttraction attraction){
        touristAttractions.add(attraction);
    }

    //READ

    public List<TouristAttraction> getAllAttractions(){
        return touristAttractions;
    }

    public TouristAttraction getAttractionByName(String name){
        for (TouristAttraction attraction : touristAttractions){
            if(attraction.getName().equalsIgnoreCase(name)){
                return attraction;
            }
        }
        return null;
    }

    //UPDATE

    public void updateAttraction(TouristAttraction updatedAttraction){
        for (int i = 0; i < touristAttractions.size(); i++){
            TouristAttraction attraction = touristAttractions.get(i);
            if (attraction.getName().equals(updatedAttraction.getName())){
                touristAttractions.set(i, updatedAttraction);
                return;
            }
        }
    }
    public void deleteAttraction(String name) {
        TouristAttraction attraction = touristAttractions.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Attraction not found: " + name));

        touristAttractions.remove(attraction);
    }

    //DELETE

   /* public void deleteAttraction(String name){
        Iterator<TouristAttraction> iterator = touristAttractions.iterator();
        while (iterator.hasNext()){
            TouristAttraction attraction = iterator.next();
            if (attraction.getName().equals(name)){
                iterator.remove();
            }
        }*/


    public List<String> getCities() {
        // Replace with your actual list of cities
        return List.of("Copenhagen", "Aarhus", "Odense", "Aalborg");
    }

    public List<String> getTags() {
        // Replace with your actual list of tags
        return List.of("Park", "Nature", "Museum", "Landmark");
    }

}