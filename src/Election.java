import java.util.*;

public class Election {
    private PriorityQueue<Candidate> maxHeap;
    private Map<String, Candidate> votes;
    private int totalVotes;
    private int p;

    public Election(int p) {
        this.maxHeap = new PriorityQueue<>((a, b) -> b.votes - a.votes);
        this.votes = new HashMap<>();
        this.totalVotes = 0;
        this.p = p;
    }

    public void initializeCandidates(LinkedList<String> candidates) {
        for (String candidate : candidates) {
            Candidate candidateName = new Candidate(candidate);
            votes.put(candidate, candidateName);
            maxHeap.offer(candidateName);
        }
    }

    public void castVote(String candidate) {
        if (votes.containsKey(candidate)) {
            Candidate candidateName = votes.get(candidate);
            maxHeap.remove(candidateName);
            candidateName.votes++;
            maxHeap.offer(candidateName);
            totalVotes++;
        }
    }

    public void castRandomVote() {
        Random random = new Random();
        List<String> candidates = new ArrayList<>(votes.keySet());
        String randomCandidate = candidates.get(random.nextInt(candidates.size()));
        castVote(randomCandidate);
    }

    public void rigElection(String candidate) {
        Candidate candidateName = votes.get(candidate);
        if (candidateName != null) {
            //Calculate how many votes needed to make this candidate win
            int votesNeeded = p - candidateName.votes - 1; //Subtract 1 to ensure the candidate wins by 1 vote

            //Create a list of other candidates to take votes from
            List<Candidate> otherCandidates = new ArrayList<>(maxHeap);
            otherCandidates.remove(candidateName); //Remove the rigged candidate from the list
            Collections.shuffle(otherCandidates); //Shuffle the list for random distribution

            //Redistribute votes from other candidates randomly
            for (Candidate otherCandidate : otherCandidates) {
                if (votesNeeded > 0 && otherCandidate.votes > 1) {
                    //Randomly decide the number of votes to transfer, ensuring we don't take all votes
                    int transferVotes = Math.min(new Random().nextInt(otherCandidate.votes - 1) + 1, votesNeeded);
                    otherCandidate.votes -= transferVotes;
                    candidateName.votes += transferVotes;
                    votesNeeded -= transferVotes;
                }
            }

            // Rebuild the max heap after redistributing votes
            maxHeap.clear();
            for (Candidate c : votes.values()) {
                maxHeap.offer(c);
            }
        }
    }

    public List<String> getTopKCandidates(int k) {
        List<String> topCandidates = new ArrayList<>();
        PriorityQueue<Candidate> tmp = new PriorityQueue<>(maxHeap);

        while (!tmp.isEmpty() && k > 0) {
            topCandidates.add(tmp.poll().name);
            k--;
        }
        return topCandidates;
    }

    public void auditElection() {
        PriorityQueue<Candidate> tmp = new PriorityQueue<>(maxHeap);
        while (!tmp.isEmpty()) {
            Candidate candidate = tmp.poll();
            System.out.println(candidate.name + " - " + candidate.votes);
        }
    }

    private class Candidate {
        String name;
        int votes;

        Candidate(String name) {
            this.name = name;
            this.votes = 0;
        }
    }
}