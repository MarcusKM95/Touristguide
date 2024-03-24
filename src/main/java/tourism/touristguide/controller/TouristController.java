package tourism.touristguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.touristguide.model.TouristAttraction;
import tourism.touristguide.service.TouristService;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.NoSuchElementException;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("attractions")
public class TouristController {

    private final TouristService touristService;

    @Autowired
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("/attractions")
    public String showAttractionList(Model model) {
        List<Map<String, Object>> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionsList";
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        if (attraction != null) {
            return ResponseEntity.ok(attraction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addAttraction(@RequestBody TouristAttraction attraction) {
        touristService.addAttraction(attraction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update")

    public ResponseEntity<Void> updateAttraction(@RequestBody TouristAttraction updatedAttraction) {
        touristService.updateAttraction(updatedAttraction);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteAttraciton(@PathVariable String name) {
        touristService.deleteAttraction(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/add")
    public String showAddAttractionForm(Model model) {
        model.addAttribute("cities", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        return "addAttraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@RequestParam String name, @RequestParam String description, @RequestParam String location, @RequestParam String tags) {
        List<String> tagList = Arrays.asList(tags.split(","));
        TouristAttraction newAttraction = new TouristAttraction(name, description, location, tagList);
        touristService.addAttraction(newAttraction);
        return "redirect:/attractions/attractions";
    }

    @GetMapping("/tags")
    public String showTags(Model model) {
        List<String> tags = touristService.getTags();
        model.addAttribute("tags", tags);
        return "tags";
    }


    /*@GetMapping("/tags/{attractionName}")
    public String showTagsForAttraction(@PathVariable String attractionName, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(attractionName);
        if (attraction != null) {
            model.addAttribute("tags", attraction.getTags());
            return "tags";
        } else {
            return "error"; // return error view if attraction not found
        }
    }*/
    /*@GetMapping("/tags/{attractionName}")
    public String showTagsForAttraction(@PathVariable String attractionName, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(attractionName);
        if (attraction != null) {
            model.addAttribute("tags", attraction.getTags());
            return "tags";
        } else {
            return "attractionsList"; // return attractionsList view if attraction not found
        }
    }*/
    @GetMapping("/tags/{attractionName}")
    public String showTagsForAttraction(@PathVariable String attractionName, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(attractionName);
        if (attraction != null) {
            model.addAttribute("attraction", attraction); // Add the attraction to the model
            model.addAttribute("tags", attraction.getTags());
            return "tags";
        } else {
            return "attractionsList"; // return attractionsList view if attraction not found
        }
    }

    @PostMapping("/updateForm")
    public String updateAttraction(@RequestParam String name, @RequestParam String description, @RequestParam String location, @RequestParam String tags) {
        List<String> tagList = Arrays.asList(tags.split(","));
        TouristAttraction updatedAttraction = new TouristAttraction(name, description, location, tagList);
        touristService.updateAttraction(updatedAttraction);
        return "redirect:/attractions";
    }
    @GetMapping("/{name}/edit")
    public String showEditAttractionForm(@PathVariable("name") String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        if (attraction != null) {
            model.addAttribute("attraction", attraction);
            model.addAttribute("cities", touristService.getCities());
            model.addAttribute("tags", touristService.getTags());
            return "editAttraction";
        } else {
            return "redirect:/attractions";
        }
    }
    /*@GetMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable("name") String name) {
        touristService.deleteAttraction(name);
        return "redirect:/attractions";
    }*/
    @GetMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable("name") String name) {
        try {
            touristService.deleteAttraction(name);
            return "redirect:/attractions/attractions";
        } catch (NoSuchElementException e) {
            return "redirect:/attractions/attractions";
        }
    }
}