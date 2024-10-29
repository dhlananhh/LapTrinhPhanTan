package ex1;


import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;


// Tính tổng các phần tử trong một mảng số nguyên
public class SumTask extends RecursiveTask<Integer> {
	// số lượng phần tử trong mảng số nguyên = 1000
    private static final int THRESHOLD = 1000;
    
    // mảng số nguyên
    private int[] array;
    
    // chỉ số bắt đầu
    private int start;
    
    // chỉ số kết thúc
    private int end;

    
    // constructor
    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    
    // hàm compute(): Tính tổng các phần tử trong mảng
    protected Integer compute() {
    	/*
    		nếu kích thước của mảng <= THRESHOLD (THRESHOLD = 1000)
    		thì sử dụng một vòng lặp để tính tổng các phần tử trong mảng
    	*/
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
        	// Chia mảng thành 2 phần
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);

            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask sumTask = new SumTask(array, 0, array.length);
        int result = forkJoinPool.invoke(sumTask);

        System.out.println("Sum: " + result);
    }
}
