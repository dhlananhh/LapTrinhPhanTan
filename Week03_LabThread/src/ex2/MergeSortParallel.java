package ex2;


import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


/*
	sử dụng Java Fork/Join Framework 
	để thực hiện thuật toán sắp xếp mảng số nguyên theo Merge Sort Parallel
*/
public class MergeSortParallel extends RecursiveAction {
	// số lượng phần tử trong mảng số nguyên = 1000
	private static final int THRESHOLD = 1000;
	
	// mảng số nguyên
	private int[] array;
	
	// chỉ số bắt đầu
	private int start;
	
	// chỉ số kết thúc
	private int end;
	
	
	// constructor
	public MergeSortParallel(int[] array, int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}


	// hàm compute(): sắp xếp mảng 
	@Override
	protected void compute() {
		if (end - start <= THRESHOLD) {
			// Sắp xếp trực tiếp nếu mảng nhỏ hơn THRESHOLD
			Arrays.sort(array, start, end);
		} else {
			// Chia mảng thành 2 phần
			int mid = (start + end) / 2;
			MergeSortParallel leftTask = new MergeSortParallel(array, start, mid);
			MergeSortParallel rightTask = new MergeSortParallel(array, mid, end);
			
			// Thực hiện đệ quy song song
			invokeAll(leftTask, rightTask);
			
			// Gộp hai mảng đã sắp xếp
			merge(start, mid, end);
		}
	}

	
	private void merge (int start, int mid, int end ) {
		int[] merged = new int[end - start];
		int i = start;
		int j = mid;
		int k = 0;
		
		while (i < mid && j < end) {
			if (array[i] <= array[j]) {
				merged[k++] = array[i++];
			} else {
				merged[k++] = array[j++];
			}
		}
		
		while (i < mid) {
			merged[k++] = array[i++];
		}
	
		while (j < end) {
			merged[k++] = array[j++];
		}
		
		System.arraycopy(merged, 0, array, start, merged.length);
	}
	
	
	public static void main(String[] args) {
		int[] array = {9, 5, 7, 1, 3, 6, 8, 2, 4, 0};
		
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		MergeSortParallel mergeSort = new MergeSortParallel(array, 0, array.length);
		forkJoinPool.invoke(mergeSort);
		
		System.out.println("Sorted array: " + Arrays.toString(array));
	}
}
