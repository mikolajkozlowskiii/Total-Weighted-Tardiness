import java.util.ArrayList;
import java.util.List;

public class WiTiProblem {
    public static int getMinWeightedSumOfDelays(Task[] tasks){
        final int sizeOfSearching = (int) Math.pow(2,tasks.length);
        int[] memory = new int[sizeOfSearching];
        for(int i = 1; i<memory.length; i++){
            memory[i] = Integer.MAX_VALUE;
            int sumPerformedTimeOfTasks = 0;
            int maskForPermutationInMemory = 1;
            int indexOfCurrentTask = 0;
            int loopConstraint = i;

            final List<Integer> indexesOfConsideredTasks = new ArrayList<>();
            final List<Integer> permutationInMemory = new ArrayList<>();
            
            while (loopConstraint!=0) {
                if ((i & maskForPermutationInMemory) != 0) {
                    sumPerformedTimeOfTasks += tasks[indexOfCurrentTask].getPerformedTime();
                    indexesOfConsideredTasks.add(indexOfCurrentTask);
                    permutationInMemory.add(i^maskForPermutationInMemory);
                }
                loopConstraint >>= 1;
                maskForPermutationInMemory <<= 1;
                indexOfCurrentTask++;
            }

            for(int j = 0; j < indexesOfConsideredTasks.size(); j++){
                final int currentTask = indexesOfConsideredTasks.get(j);
                final int performedTimeInMemory = permutationInMemory.get(j);
                memory[i] = Math.min(memory[i], (Math.max(sumPerformedTimeOfTasks-tasks[currentTask].getDeadline(),0) *
                        tasks[currentTask].getWeight()) + memory[performedTimeInMemory]);
            }
        }
        return memory[memory.length-1];
    }

}
