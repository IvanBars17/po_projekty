import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Election {
    private List<Candidate> candidates; // Krok 2
    private ElectionTurn firstTurn;     // Krok 4
    private ElectionTurn secondTurn;    // Krok 4
    private Candidate winner;           // Krok 13

    public Election() {
        this.candidates = new ArrayList<>();
    }

    // Krok 2
    public List<Candidate> getCandidates() {
        return new ArrayList<>(this.candidates);
    }

    public ElectionTurn getFirstTurn() { return firstTurn; }
    public ElectionTurn getSecondTurn() { return secondTurn; }
    public Candidate getWinner() { return winner; }

    // Krok 3
    public void populateCandidates(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    candidates.add(new Candidate(line.trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku kandydatów: " + path);
        }
    }

    // Krok 3, 7, 13, 15
    public void populate() {
        populateCandidates("kandydaci.txt");

        firstTurn = new ElectionTurn(getCandidates());
        secondTurn = null;

        firstTurn.populate("1.csv");

        try {
            winner = firstTurn.winner();
            System.out.println("Zwycięzca wyłoniony w I turze: " + winner.name());
        } catch (NoWinnerException e) {
            System.out.println("Brak zwycięzcy w I turze. Przejście do II tury...");

            List<Candidate> runoff = firstTurn.runoffCandidates();
            secondTurn = new ElectionTurn(runoff);
            secondTurn.populate("2.csv");

            try {
                winner = secondTurn.winner();
                System.out.println("Zwycięzca wyłoniony w II turze: " + winner.name());
            } catch (NoWinnerException ex) {
                System.out.println("Nierozstrzygnięta II tura.");
            }
        }
    }
}