package notepad_simulation;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

public class FileReadingSW extends SwingWorker<StringBuilder, String>{
	private File file;
	private StringBuilder text = new StringBuilder();
	
	private NotepadGUI view;
	
	public FileReadingSW(File file, NotepadGUI view) {
		super();
		this.file = file;
		this.view = view;
	}

	@Override
	protected StringBuilder doInBackground() throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileChannel channel = fis.getChannel();
		
		// We want to make it 8 threads, so dividing the whole file by 8 will produce the size for each chunk read by each thread
		long chunkSize = channel.size() / 8;
		
		// Initially, the remaining size is indeed the total
		long remainingSize = channel.size();
		
		// Start location initialization
		long startLocation = 0L;
		
		ArrayList<Future<String>> results = new ArrayList<>();
		
		ExecutorService service = Executors.newFixedThreadPool(8);
		
		while(remainingSize >= chunkSize) {
			results.add(service.submit(new FileReadingTask(channel, startLocation, Math.toIntExact(chunkSize))));
			startLocation += chunkSize;
			remainingSize -= chunkSize;
		}
		
		// Load the remaining data
		results.add(service.submit(new FileReadingTask(channel, startLocation, Math.toIntExact(remainingSize))));
		
		service.shutdown();
		
		// Wait for an hour if all threads haven't finished their tasks yet
	    try {
	        service.awaitTermination(1, TimeUnit.HOURS);
	    } catch (InterruptedException e) {
	        // Handle interruption if needed
	        e.printStackTrace();
	    }
	    
	    System.out.println("All threads have finished !");
	    fis.close();
	    
	    for (Future<String> result : results) {
			text.append(result.get());
		}
		
		return text;
	}

	@Override
	protected void done() {
		try {
			text = get();
			view.getTextArea().setText(text.toString());
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
