package pl.pkasiewicz.dogbreedrater;

import org.springframework.stereotype.Service;
import pl.pkasiewicz.dogbreedrater.dogbreed.DogBreedRepository;

import java.util.Random;

@Service
public class RandomDogBreedIdPairGenerator {
    private final DogBreedRepository dogBreedRepository;
    private final static Random random = new Random();

    public RandomDogBreedIdPairGenerator(DogBreedRepository dogBreedRepository) {
        this.dogBreedRepository = dogBreedRepository;
    }

    public DogBreedIdPair generate() {
        int firstId = randomDogBreedId();
        int secondId = generateExcept(firstId);
        return new DogBreedIdPair(firstId, secondId);
    }

    private int randomDogBreedId() {
        int sizeOfDogBreeds = dogBreedRepository.findAll().size();
        return random.nextInt(sizeOfDogBreeds) + 1;
    }

    private int generateExcept(int firstId) {
        int secondId = randomDogBreedId();
        if (secondId != firstId) {
            return secondId;
        }
        return generateExcept(firstId);
    }
}
