package tourism.touristguide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourism.touristguide.model.TouristAttraction;
import tourism.touristguide.repository.TouristRepository;

import java.util.List;


@Service
public class TouristService {

    private final TouristRepository touristRepository;

    @Autowired
    public TouristService (TouristRepository touristRepository){
        this.touristRepository = touristRepository;
    }

    public void addAttraction(TouristAttraction attraction){
        touristRepository.addAttraction(attraction);
    }

    public List<TouristAttraction> getAllAttractions(){
        return touristRepository.getAllAttractions();
    }

    public TouristAttraction getAttractionByName(String name){
        return touristRepository.getAttractionByName(name);
    }

    public void updateAttraction(TouristAttraction updatedAttraction){
        touristRepository.updateAttraction(updatedAttraction);
    }

    public void deleteAttraction(String name){
        touristRepository.deleteAttraction(name);
    }

    public List<String> getCities() {
        return touristRepository.getCities();
    }

    public List<String> getTags() {
        return touristRepository.getTags();
    }
}