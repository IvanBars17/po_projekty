import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ElectionTurn {
    private List<Candidate> candidates;
    private List<Vote> votes; // Krok 7

    public ElectionTurn(List<Candidate> candidates) {
        this.candidates = candidates;
        this.votes = new ArrayList<>();
    }

    public List<Vote> getVotes() { return votes; }
    public List<Candidate> getCandidates() { return candidates; }

    // Krok 7
    public void populate(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            boolean isFirstLine = true; // Флажок для першого рядка

            for (String line : lines) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Завжди пропускаємо перший рядок (шапку)
                }
                if (line.trim().isEmpty()) continue;

                votes.add(Vote.fromCsvLine(line, candidates));
            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku: " + filePath);
        }
    }

    // Krok 12
    public Candidate winner() throws NoWinnerException {
        Vote summary = summarize();
        for (Candidate c : candidates) {
            if (summary.percentage(c) > 50.0) {
                return c;
            }
        }
        throw new NoWinnerException("Żaden kandydat nie zdobył ponad 50% głosów.");
    }

    // Krok 14
    public List<Candidate> runoffCandidates() {
        Vote summary = summarize();
        return candidates.stream()
                .sorted((c1, c2) -> Integer.compare(summary.votes(c2), summary.votes(c1))) // Malejąco
                .limit(2)
                .collect(Collectors.toList());
    }

    // Krok 17
    public Vote summarize() {
        return Vote.summarize(this.votes);
    }

    public Vote summarize(List<String> location) {
        List<Vote> filtered = Vote.filterByLocation(this.votes, location);
        return Vote.summarize(filtered, location);
    }
}