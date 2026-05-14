import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public class Main {
    public static void main(String[] args) {

/*

        //Działania na objektach

        Player p1 = new Player("Ivan", 26);
        System.out.println("Player " + p1.getName() + " have rating " + p1.getRating());

        University university = new University("UMCS", 20000);
        System.out.println("The name of university is " + university.getNameOfUniversity()
                + " with " + university.getNumberOfStudents() + " students");

*/

        //Listy

/*        List<Player> players= new ArrayList<>();
            players.add(new Player("Ivan", 1500));
            players.add(new Player("Natalka", 1550));
            players.add(new Player("Lena", 1525));
                for(Player p: players){
                    System.out.println("Player: " + p.getName());
                }
        int totalRanking = 0;
                for(Player p: players){
                    totalRanking += p.getRating();
                }
        System.out.println("Total ranking: " + totalRanking);

        List<University> universities = new ArrayList<>();
        universities.add(new University("UMCS", 20000));
        universities.add(new University("KUL", 15000));
        universities.add(new University("Politechnika", 17000));
        int totalCountOfStudents = 0;
        for(University u: universities){
            System.out.println("University: " + u.getNameOfUniversity());
        }
        for(University u: universities){
            totalCountOfStudents += u.getNumberOfStudents();
        }
        System.out.println("Number of students: " + totalCountOfStudents);
*/


        //Parsing files


      List<Player> players = new ArrayList<>();

        try{
            List<String> linesPlay = Files.readAllLines(Paths.get("players.csv"));
                boolean isFirstLine = true;
                for(String line: linesPlay){
                    if(isFirstLine){
                        isFirstLine = false;
                        continue;
                    }
                String[] parts = line.split(",");
                String name = parts[0];
                int rating = Integer.parseInt(parts[1]);
                int age = Integer.parseInt(parts[2]);

                players.add(new Player(name, rating, age));
                }

        } catch (IOException e) {
            System.out.println("No files founded");
            throw new RuntimeException(e);
        }
        System.out.println("Players founded: " + players.size());



        List<University> universities = new ArrayList<>();

        try{
            List<String> linesUni = Files.readAllLines(Paths.get("universities.csv"));
            boolean isFirstLine1 = true;
            for(String line: linesUni){
                if(isFirstLine1){
                    isFirstLine1 = false;
                    continue;
                }

            String[] parts1 = line.split(",");
            String name1 = parts1[0];
            int students = Integer.parseInt(parts1[1]);
            universities.add(new University(name1, students));
            }


        } catch (IOException e) {
            System.out.println("No files founded");
            throw new RuntimeException(e);
        }
       System.out.println("Number of universities: " + universities.size());



        //Map

        Map<String, Integer> counts = new HashMap<>();

        for (Player p: players){
            String name = p.getName();
            int current = counts.getOrDefault(name, 0);
            counts.put(name, current + 1);
        }
        System.out.println(players.toString());

        //Dziediczenie
        Son dziecko = new Son("Ivanio", 17);

        dziecko.toPrint();
    }
}
