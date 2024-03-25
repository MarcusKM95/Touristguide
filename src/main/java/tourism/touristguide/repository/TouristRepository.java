package tourism.touristguide.repository;

import org.springframework.core.metrics.StartupStep;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tourism.touristguide.database.DatabaseService;
import tourism.touristguide.model.TouristAttraction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TouristRepository {

    private DatabaseService db;
    private final JdbcTemplate jdbcTemplate;

    private List<TouristAttraction> touristAttractions;

    public TouristRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.touristAttractions = new ArrayList<>();
        //temp attractions
        touristAttractions.add(new TouristAttraction("Kongens Have", "Public park in the center of Copenhagen", "Copenhagen", List.of("Park", "Nature")));
        touristAttractions.add(new TouristAttraction("Runde tårn", "Very famous tower in Copenhagen, made by King Christian 4.", "Copenhagen", List.of("tower", "landmark")));
    }
    public static class AttractionRowMapper implements RowMapper <TouristAttraction> {
        @Override
        public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {
          //  List<TouristAttraction> attractions = new ArrayList<>();

            // Assuming that the ResultSet contains multiple rows
            //while (rs.next()) {
                int attractionId = rs.getInt("attractionID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int locationId = rs.getInt("locationid");
                /*List<String> tags = new ArrayList<>();
                tags.add(String.valueOf(attractionId));
                System.out.println(attractionId + name + description + locationId);
                // Create a new TouristAttraction for each row
                TouristAttraction attraction = new TouristAttraction(name, description, String.valueOf(locationId));
                attractions.add(attraction);*/
            //}

            return new TouristAttraction(name, description, String.valueOf(locationId));

        }
    }
    //CREATE
    public void addAttraction(TouristAttraction attraction){
        String sql = "select locationsID from locations where name = ?";
        String sqlInsert = "INSERT INTO attractions (name, description, locationID) VALUES (?, ?, ?)";
        //try {
            int locationID = jdbcTemplate.queryForObject(sql, Integer.class, attraction.getLocation());
            jdbcTemplate.update(sqlInsert, attraction.getName(), attraction.getDescription(), locationID);
        //jdbcTemplate.execute("INSERT INTO tourist_attractions (name, description, city) VALUES ('" + attraction.getName() + "', '" + attraction.getDescription() + "', '" + attraction.getLocation() + "')";

        // touristAttractions.add(attraction);
        System.out.println(locationID);
    }

    //READ

    /*public List<Map<String, Object>> getAllAttractions(){
        String sql = "SELECT * FROM attractions";
        List<Map<String, Object>> attractions = jdbcTemplate.queryForList(sql, new AttractionRowMapper());
        System.out.println(attractions.size());
        return attractions;
       // return touristAttractions;
    }*/
    public List<TouristAttraction> getAllAttractions(){
        String sql = "SELECT * FROM attractions";
        List<TouristAttraction> attractions = jdbcTemplate.query(sql, new AttractionRowMapper());
        System.out.println(attractions.size());
        return attractions;
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