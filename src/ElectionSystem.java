import java.util.*;

public class ElectionSystem {
    private static final String[] POSSIBLE_CANDIDATE_NAMES = {
            "Dominic Santiago", "Damon Baird", "Cole Train", "Anya Stroud", "Marcus Fenix",
            "Augustus Cole", "Victor Hoffman", "Adam Fenix", "Minh Young Kim", "Benjamin Carmine"
    };
    public static void main(String[] args) {
        Random random = new Random();
        int numberOfCandidates = random.nextInt(8) + 3;
        LinkedList<String> candidates = new LinkedList<>();
        List<String> usedNames = new ArrayList<>();

        while (candidates.size() < numberOfCandidates) {
            String candidateName = POSSIBLE_CANDIDATE_NAMES[random.nextInt(POSSIBLE_CANDIDATE_NAMES.length)];

            if (!usedNames.contains(candidateName)) {
                usedNames.add(candidateName);
                candidates.add(candidateName);
            }
        }

        int p = random.nextInt(9001) + 1000;
        Election election = new Election(p);
        election.initializeCandidates(candidates);


        for (int i = 0; i < p; i++) {
            election.castRandomVote();
        }


        /* Manual Voting
        election.castVote("Cole Train");
        election.castVote("Cole Train");
        election.castVote("Marcus Fenix");
        election.castVote("Anya Stroud");
        election.castVote("Anya Stroud");
        */

        System.out.println("Top 3 candidates after " + p + " votes: " + election.getTopKCandidates(3));

        /* Rig Election
        election.rigElection("Marcus Fenix");

        System.out.println("Top 3 candidates after rigging the election: " + election.getTopKCandidates(3));
        */

        System.out.println("\nAudit of Election:");
        election.auditElection();
    }
}