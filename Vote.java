import java.util.*;
import java.util.stream.Collectors;

public class Vote {
    private Map<Candidate, Integer> votesForCandidate;
    private List<String> location;

    // Krok 11: Zmienna do zapamiętania sumy, aby obliczać ją tylko raz
    private Integer cachedTotalVotes = null;

    public Vote() {
        this.votesForCandidate = new LinkedHashMap<>();
        this.location = new ArrayList<>();
    }

    public Map<Candidate, Integer> getVotesForCandidate() { return votesForCandidate; }
    public List<String> getLocation() { return location; }

    // Krok 6
    public static Vote fromCsvLine(String line, List<Candidate> candidates) {
        Vote vote = new Vote();
        // Regex "[,;]" pozwala na czytanie plików CSV niezależnie czy użyto przecinka czy średnika
        String[] parts = line.split("[,;]");

        if (parts.length >= 3) {
            vote.location.add(parts[0].trim());
            vote.location.add(parts[1].trim());
            vote.location.add(parts[2].trim());

            for (int i = 0; i < candidates.size(); i++) {
                if (i + 3 < parts.length) {
                    vote.votesForCandidate.put(candidates.get(i), Integer.parseInt(parts[i + 3].trim()));
                }
            }
        }
        return vote;
    }

    // Krok 8
    public static Vote summarize(List<Vote> votes) {
        return summarize(votes, new ArrayList<>());
    }

    // Krok 17
    public static Vote summarize(List<Vote> votes, List<String> targetLocation) {
        Vote sumVote = new Vote();
        sumVote.location = new ArrayList<>(targetLocation);

        if (votes.isEmpty()) return sumVote;

        for (Candidate c : votes.get(0).getVotesForCandidate().keySet()) {
            sumVote.votesForCandidate.put(c, 0);
        }

        for (Vote v : votes) {
            for (Map.Entry<Candidate, Integer> entry : v.getVotesForCandidate().entrySet()) {
                sumVote.votesForCandidate.put(
                        entry.getKey(),
                        sumVote.votesForCandidate.get(entry.getKey()) + entry.getValue()
                );
            }
        }
        return sumVote;
    }

    // Krok 16
    public static List<Vote> filterByLocation(List<Vote> votes, List<String> locFilter) {
        return votes.stream()
                .filter(v -> {
                    for (int i = 0; i < locFilter.size(); i++) {
                        if (v.getLocation().size() <= i || !v.getLocation().get(i).equalsIgnoreCase(locFilter.get(i))) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    // Krok 9
    public int votes(Candidate candidate) {
        return votesForCandidate.getOrDefault(candidate, 0);
    }

    // Krok 11
    public int getTotalVotes() {
        if (cachedTotalVotes == null) {
            cachedTotalVotes = votesForCandidate.values().stream().mapToInt(Integer::intValue).sum();
        }
        return cachedTotalVotes;
    }

    // Krok 9
    public double percentage(Candidate candidate) {
        int total = getTotalVotes();
        if (total == 0) return 0.0;
        return (double) votes(candidate) / total * 100.0;
    }

    // Krok 10
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!location.isEmpty()) {
            sb.append("Lokalizacja: ").append(String.join(", ", location)).append("\n");
        }
        for (Candidate c : votesForCandidate.keySet()) {
            sb.append(String.format("%s: %.2f%%\n", c.name(), percentage(c)));
        }
        return sb.toString();
    }
}