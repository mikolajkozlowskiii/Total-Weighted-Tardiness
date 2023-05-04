
public class Task {
    private final int id;
    private final int performedTime;
    private final int weight;
    private final int deadline;

    public Task(int id, int performedTime, int weight, int deadline) {
        this.id = id;
        this.performedTime = performedTime;
        this.weight = weight;
        this.deadline = deadline;
    }

    public int getPerformedTime() {
        return performedTime;
    }

    public int getWeight() {
        return weight;
    }

    public int getDeadline() {
        return deadline;
    }

}