import java.util.ArrayList;
import java.util.List;

public class WiTiProblem {
    public static int getMinWeightedSumOfDelays(Task[] tasks){
        final int sizeOfSearching = (int) Math.pow(2,tasks.length);
        int[] memory = new int[sizeOfSearching];
        for(int i = 1; i<memory.length; i++){

            memory[i] = Integer.MAX_VALUE;
            int sumPerformedTimes = 0;
            int permutationMask = 1;
            int indexOfCurrentTask = 0;
            int bitsConstraint = i;
            final int currentIteration = i;

            final List<Integer> taskIndexes = new ArrayList<>();
            final List<Integer> memoryOfPermutation = new ArrayList<>();
            
            while (bitsConstraint!=0) {
                if ((currentIteration & permutationMask) != 0) {
                    sumPerformedTimes += tasks[indexOfCurrentTask].getPerformedTime();
                    taskIndexes.add(indexOfCurrentTask);
                    memoryOfPermutation.add(currentIteration^permutationMask);
                }
                bitsConstraint >>= 1;
                permutationMask <<= 1;
                indexOfCurrentTask++;
            }

            for(int j = 0; j < taskIndexes.size(); j++){
                final int currentTask = taskIndexes.get(j);
                final int performedTimeInMemory = memoryOfPermutation.get(j);
                memory[i] = Math.min(memory[i], (Math.max(sumPerformedTimes-tasks[currentTask].getDeadline(),0) *
                        tasks[currentTask].getWeight()) + memory[performedTimeInMemory]);
            }
        }
        return memory[memory.length-1];
    }

}
