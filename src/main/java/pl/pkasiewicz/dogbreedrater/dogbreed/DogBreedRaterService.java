package pl.pkasiewicz.dogbreedrater.dogbreed;

import org.springframework.stereotype.Service;
import pl.pkasiewicz.dogbreedrater.DogBreedIdPair;
import pl.pkasiewicz.dogbreedrater.rating.BreedRatingPair;
import pl.pkasiewicz.dogbreedrater.rating.EloRatingCalculator;
import pl.pkasiewicz.dogbreedrater.RandomDogBreedIdPairGenerator;
import pl.pkasiewicz.dogbreedrater.dto.DogBreedLeaderboard;
import pl.pkasiewicz.dogbreedrater.dto.QuestionResponse;

import java.util.*;

@Service
public class DogBreedRaterService {

    private final DogBreedRepository dogBreedRepository;
    private final RandomDogBreedIdPairGenerator RandomDogBreedIdPairGenerator;
    private final EloRatingCalculator eloRatingCalculator = new EloRatingCalculator();

    public DogBreedRaterService(DogBreedRepository dogBreedRepository, RandomDogBreedIdPairGenerator RandomDogBreedIdPairGenerator) {
        this.dogBreedRepository = dogBreedRepository;
        this.RandomDogBreedIdPairGenerator = RandomDogBreedIdPairGenerator;
    }

    public QuestionResponse getQuestionResponse() {
        DogBreedIdPair pairId = RandomDogBreedIdPairGenerator.generate();
        DogBreed firstBreed = dogBreedRepository.findById(Long.valueOf(pairId.fistBreed())).orElseThrow(() -> new RuntimeException("Id not found: " + pairId.fistBreed() + " " + dogBreedRepository.findAll().size()));
        DogBreed secondBreed = dogBreedRepository.findById(Long.valueOf(pairId.secondBreed())).orElseThrow(() -> new RuntimeException("Id not found: " + pairId.fistBreed()));
        String firstBreedName = nameFormatterToPrint(firstBreed.getBreed());
        String firstBreedImageUrl = firstBreed.getBreed() + ".jpg";
        String secondBreedName = nameFormatterToPrint(secondBreed.getBreed());
        String secondBreedImageUrl = secondBreed.getBreed() + ".jpg";
        return new QuestionResponse(firstBreedName, firstBreedImageUrl, secondBreedName, secondBreedImageUrl);
    }

//    private String breedImageUrlBuilder(DogBreed dogBreed) {
//        String breed = dogBreed.getBreed();
//        String url = "";
//        if (breed.contains("-")) {
//            String[] subBreed = breed.split("-");
//            url = String.format("https://dog.ceo/api/breed/%s/%s/images/random", subBreed[0], subBreed[1]);
//        } else {
//            url = String.format("https://dog.ceo/api/breed/%s/images/random", breed);
//        }
//        try {
//            return BreedImageUrlReader.getJson(new URL(url));
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public List<DogBreedLeaderboard> getAllBreeds() {
        List<DogBreed> allBreeds = dogBreedRepository.findAll();
        List<DogBreedLeaderboard> dogBreedLeaderboardsList = new ArrayList<>();
        allBreeds.forEach(b -> {
            String imgUrl = b.getBreed() + ".jpg";
            String breedName = nameFormatterToPrint(b.getBreed());
            dogBreedLeaderboardsList.add(new DogBreedLeaderboard(imgUrl, breedName, b.getRating()));
        });
        return dogBreedLeaderboardsList.stream().sorted(Comparator.comparingInt(DogBreedLeaderboard::rating).reversed()).toList();
    }

    private String nameFormatterToPrint(String breed) {
        if (breed.contains("-")) return breed.replace("-", " ");
        return breed;
    }

    private String nameFormatterToSave(String breed) {
        if (breed.contains(" ")) return breed.replace(" ", "-");
        return breed;
    }

    public void calculateRatings(String winnerBreed, String loserBreed) {
        DogBreed winner = dogBreedRepository.findByBreed(nameFormatterToSave(winnerBreed))
                .orElseThrow(() -> new RuntimeException("Breed not found: " + winnerBreed));
        DogBreed loser = dogBreedRepository.findByBreed(nameFormatterToSave(loserBreed))
                .orElseThrow(() -> new RuntimeException("Breed not found: " + loserBreed));
        BreedRatingPair breedRatingPair = eloRatingCalculator.EloRating(winner.getRating(), loser.getRating());
        dogBreedRepository.save(new DogBreed(winner.getId(), winner.getBreed(), breedRatingPair.winnerRating()));
        dogBreedRepository.save(new DogBreed(loser.getId(), loser.getBreed(), breedRatingPair.loserRating()));
    }
}
