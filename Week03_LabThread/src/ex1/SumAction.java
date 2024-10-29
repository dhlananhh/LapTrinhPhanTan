package ex1;


import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;


public class SumAction extends RecursiveAction {
	// kích thước tối đa của mảng
    private static final int THRESHOLD = 1000;
    private int[] array;
    private int start;
    private int end;
    private int sum;

    
    // constructor
    public SumAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
        } else {
            int mid = (start + end) / 2;
            SumAction leftAction = new SumAction(array, start, mid);
            SumAction rightAction = new SumAction(array, mid, end);

            leftAction.fork();
            rightAction.compute();
            leftAction.join();

            sum = leftAction.sum + rightAction.sum;
        }
    }

    
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumAction sumAction = new SumAction(array, 0, array.length);
        forkJoinPool.invoke(sumAction);

        System.out.println("Sum: " + sumAction.sum);
    }
}