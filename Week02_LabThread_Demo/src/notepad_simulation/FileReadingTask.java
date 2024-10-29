package notepad_simulation;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;

public class FileReadingTask implements Callable<String>{
	private FileChannel channel;
	private long startLocation;
	private int size;
	
	public FileReadingTask(FileChannel channel, long startLocation, int size) {
		super();
		this.channel = channel;
		this.startLocation = startLocation;
		this.size = size;
	}



	@Override
	public String call() throws Exception {
		String stringChunk = "";
		
		System.out.println("Reading the channel: [start location: " + startLocation + ", size: " + size + "] - Current thread: " + Thread.currentThread().getName());
		
		// buffer memory allocation
		ByteBuffer buffer = ByteBuffer.allocate(size);
		
		// read data from channel to buffer
		channel.read(buffer, startLocation);
		
		// convert data chunk read into string
		stringChunk = new String(buffer.array(), Charset.forName("UTF-8"));
		
		System.out.println("Done reading the channel:  [start location: " + startLocation + ", size: " + size + "], data read: " + stringChunk);
		
		
		return stringChunk;
	}
	
}
	
	