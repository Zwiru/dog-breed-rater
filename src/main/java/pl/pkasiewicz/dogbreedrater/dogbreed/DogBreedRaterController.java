package pl.pkasiewicz.dogbreedrater.dogbreed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pkasiewicz.dogbreedrater.dto.DogBreedLeaderboard;
import pl.pkasiewicz.dogbreedrater.dto.QuestionResponse;

import java.util.List;

@Controller
public class DogBreedRaterController {

    private final DogBreedRaterService dogBreedRaterService;

    public DogBreedRaterController(DogBreedRaterService dogBreedRaterService) {
        this.dogBreedRaterService = dogBreedRaterService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/vote";
    }

    @GetMapping("/vote")
    public String getVote(Model model) {
        QuestionResponse questionResponse = dogBreedRaterService.getQuestionResponse();
        model.addAttribute("questionResponse", questionResponse);
        return "vote";
    }

    @PostMapping("/vote")
    public String postVote(@RequestParam String breed, @ModelAttribute QuestionResponse questionResponse) {
        if (breed.equals("firstBreed")) {
            dogBreedRaterService.calculateRatings(questionResponse.firstDogBreed(), questionResponse.secondDogBreed());
        } else if (breed.equals("secondBreed")) {
            dogBreedRaterService.calculateRatings(questionResponse.secondDogBreed(), questionResponse.firstDogBreed());
        }
        return "redirect:/vote";
    }

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        List<DogBreedLeaderboard> allBreeds = dogBreedRaterService.getAllBreeds();
        model.addAttribute("allBreeds", allBreeds);
        return "leaderboard";
    }
}
