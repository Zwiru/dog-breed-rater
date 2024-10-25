package pl.pkasiewicz.dogbreedrater.rating;

public class EloRatingCalculator {

    private static final int K = 30;

    private double Probability(int rating1, int rating2) {
        return 1.0 / (1 + Math.pow(10, (rating1 - rating2) / 400.0));
    }

    public BreedRatingPair EloRating(int winnerRating, int loserRating) {
        double Pb = Probability(winnerRating, loserRating);

        double Pa = Probability(loserRating, winnerRating);

        winnerRating = (int) (winnerRating + K * (1 - Pa));
        loserRating = (int) (loserRating + K * ((-1) - Pb));

        return new BreedRatingPair(winnerRating, loserRating);
    }
}
