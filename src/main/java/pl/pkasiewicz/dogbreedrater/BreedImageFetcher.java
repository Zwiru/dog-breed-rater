package pl.pkasiewicz.dogbreedrater;

import pl.pkasiewicz.dogbreedrater.dogbreed.DogBreed;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BreedImageFetcher {
    public void saveImage(DogBreed dogBreed, String url) throws IOException {
        InputStream in = new URL(url).openStream();
        String path = String.format("E:/Projekty/dog-breed-rater/src/main/resources/static/img/%s.jpg", dogBreed.getBreed());
        Files.copy(in, Paths.get(path));
    }
}

