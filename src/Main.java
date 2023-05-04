import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File dataFolder = new File("data");
        File[] files = dataFolder.listFiles();
        List<String> fileNames = Arrays.stream(files)
                .map(File::getName)
                .map(s -> "data/" + s)
                .toList();

        for (String pathname : fileNames) {
            Task[] tasks = getTasksFromFile(pathname);

            final long start = System.currentTimeMillis();
            final int result = WiTiProblem.getMinWeightedSumOfDelays(tasks);
            final long stop = System.currentTimeMillis();

            System.out.println("Result: " + result);
            System.out.println("Time: " + (stop-start) + "ms");
        }
    }

    public static Task[] getTasksFromFile(String pathname){
        try{
            Scanner reader = new Scanner(new File(pathname));
            String firstLine = reader.nextLine();
            Task[] tasks = new Task[Integer.parseInt(firstLine)];
            int i = 0;
            while(reader.hasNextLine()){
                String[] task = reader.nextLine().split(" ");
                tasks[i] = new Task(i, Integer.parseInt(task[0]), Integer.parseInt(task[1]), Integer.parseInt(task[2]));
                i++;
            }
            return tasks;
        } catch (IOException ex){
            throw new NoSuchElementException("Pathname: " + pathname + " didn't found.");
        }
    }
}