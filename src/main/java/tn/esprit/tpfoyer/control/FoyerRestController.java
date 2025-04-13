package tn.esprit.tpfoyer.control;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/foyer")
public class FoyerRestController {

    IFoyerService foyerService;

    @GetMapping("/retrieve-all-foyers")
    @ResponseStatus(HttpStatus.OK)
    public List<Foyer> getFoyers() {
        return foyerService.retrieveAllFoyers();
    }

    @GetMapping("/retrieve-foyer/{foyer-id}")
    @ResponseStatus(HttpStatus.OK)
    public Foyer retrieveFoyer(@PathVariable("foyer-id") Long fId) {
        return foyerService.retrieveFoyer(fId);
    }

    @PostMapping("/add-foyer")
    @ResponseStatus(HttpStatus.CREATED)
    public Foyer addFoyer(@Valid @RequestBody Foyer f) {
        return foyerService.addFoyer(f);
    }

    @DeleteMapping("/remove-foyer/{foyer-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFoyer(@PathVariable("foyer-id") Long fId) {
        foyerService.removeFoyer(fId);
    }

    @PutMapping("/modify-foyer")
    @ResponseStatus(HttpStatus.OK)
    public Foyer modifyFoyer(@Valid @RequestBody Foyer f) {
        return foyerService.modifyFoyer(f);
    }
}
