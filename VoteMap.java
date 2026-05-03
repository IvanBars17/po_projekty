import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteMap extends VoivodeshipMap {
    private Map<String, Vote> results;
    private Map<Candidate, String> candidateColors;

    // Paleta kolorów do przydzielenia kandydatom
    private static final String[] PALETTE = {"#0044CC", "#FFA500", "#228B22", "#8B0000", "#800080"};

    public VoteMap(Map<String, Vote> results, List<Candidate> candidates) {
        this.results = results;
        this.candidateColors = new HashMap<>();

        // Krok 21: Automatyczne powiązanie kandydatów z kolorami
        for (int i = 0; i < candidates.size(); i++) {
            candidateColors.put(candidates.get(i), PALETTE[i % PALETTE.length]);
        }
    }

    @Override
    protected String getColor(String voivodeship) {
        Vote vote = results.get(voivodeship);
        if (vote != null) {
            Candidate winnerInVoivodeship = null;
            int maxVotes = -1;

            // Wyszukiwanie zwycięzcy w tym konkretnym województwie
            for (Map.Entry<Candidate, Integer> entry : vote.getVotesForCandidate().entrySet()) {
                if (entry.getValue() > maxVotes) {
                    maxVotes = entry.getValue();
                    winnerInVoivodeship = entry.getKey();
                }
            }

            if (winnerInVoivodeship != null && candidateColors.containsKey(winnerInVoivodeship)) {
                return candidateColors.get(winnerInVoivodeship);
            }
        }
        return super.getColor(voivodeship);
    }
}
