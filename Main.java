import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Start Symulacji Wyborów ---");
        Election election = new Election();

        // Zapełnia dane (Kroki 3, 7, 13, 15)
        election.populate();

        // Test Kroku 17 (Przeciążone summarize)
        if (election.getFirstTurn() != null) {
            System.out.println("\n--- Ogólnopolskie wyniki I Tury ---");
            System.out.println(election.getFirstTurn().summarize());

            System.out.println("\n--- Wyniki I Tury (Tylko mazowieckie) ---");
            System.out.println(election.getFirstTurn().summarize(List.of("mazowieckie")));
        }

        // Krok 19 i 21: Przygotowanie danych do wygenerowania map
        if (election.getSecondTurn() != null) {
            System.out.println("\n--- Generowanie Map (Kroki 19-21) ---");

            VoivodeshipMap baseMap = new VoivodeshipMap();
            List<String> voivodeships = baseMap.getVoivodeshipNames();
            Map<String, Vote> secondTurnResultsMap = new HashMap<>();

            for (String vName : voivodeships) {
                // Filtruje głosy z drugiej tury tylko dla konkretnego województwa
                Vote vVote = election.getSecondTurn().summarize(List.of(vName));
                secondTurnResultsMap.put(vName, vVote);
            }

            // Krok 21: Test VoteMap
            VoteMap voteMap = new VoteMap(secondTurnResultsMap, election.getSecondTurn().getCandidates());
            voteMap.saveToSvg("wyniki_2_tura.svg");
        }

        // Krok 20: Test SelectableMap
        SelectableMap selectableMap = new SelectableMap();
        selectableMap.select("śląskie");
        selectableMap.saveToSvg("wybrane_slaskie.svg");

        System.out.println("Zakończono. Pliki SVG powinny być gotowe w folderze projektu.");
    }
}
