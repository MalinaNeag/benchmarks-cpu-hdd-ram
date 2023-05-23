package bench.ram;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class used to map a file into memory.
 */
public class MemoryMapper {

    private static AtomicInteger ider = new AtomicInteger();
    private List<ByteBuffer> chunks = new ArrayList<ByteBuffer>();
    private final static long TWOGIGS = Integer.MAX_VALUE;
    private long length;
    private File coreFile;
    private RandomAccessFile coreFileAccessor;

    /**
     * Generates a unique ID.
     *
     * @return The unique ID.
     */
    private static int getUniqueId() {
        return ider.incrementAndGet();
    }

    /**
     * Initializes a file that is mapped into virtual memory.
     *
     * @param prefix The prefix for the file name.
     * @param size   The size of the file.
     * @throws IOException if an I/O error occurs.
     */
    public MemoryMapper(String prefix, long size) throws IOException {
        coreFile = new File(prefix + getUniqueId() + ".mem");
        // This is a for testing - to avoid the disk filling up
        coreFile.deleteOnExit();
        // Now create the actual file
        coreFileAccessor = new RandomAccessFile(coreFile, "rw");
        FileChannel channelMapper = coreFileAccessor.getChannel();
        long nChunks = size / TWOGIGS;
        if (nChunks > Integer.MAX_VALUE)
            throw new ArithmeticException("Requested File Size Too Large");
        length = size;
        long countDown = size;
        long from = 0;
        while (countDown > 0) {
            long len = Math.min(TWOGIGS, countDown);
            ByteBuffer chunk = channelMapper.map(MapMode.READ_WRITE, from, len);
            chunks.add(chunk);
            from += len;
            countDown -= len;
        }
    }

    /**
     * Reads a byte array from memory at the given offset.
     *
     * @param offSet The offset from where to read the byte array.
     * @param size   The size of the byte array to read.
     * @return The byte array read from memory.
     * @throws IOException if an I/O error occurs.
     */
    public byte[] get(long offSet, int size) throws IOException {
        // Quick and dirty but will go wrong for massive numbers
        double a = offSet;
        double b = TWOGIGS;
        byte[] dst = new byte[size];
        long whichChunk = (long) Math.floor(a / b);
        long withinChunk = offSet - whichChunk * TWOGIGS;

        // Data does not straddle two chunks
        try {
            if (TWOGIGS - withinChunk > dst.length) {
                ByteBuffer chunk = chunks.get((int) whichChunk);
                // Allows free threading
                ByteBuffer readBuffer = chunk.asReadOnlyBuffer();
                readBuffer.position((int) withinChunk);
                readBuffer.get(dst, 0, dst.length);
            } else {
                int l1 = (int) (TWOGIGS - withinChunk);
                int l2 = (int) dst.length - l1;
                ByteBuffer chunk = chunks.get((int) whichChunk);
                // Allows free threading
                ByteBuffer readBuffer = chunk.asReadOnlyBuffer();
                readBuffer.position((int) withinChunk);
                readBuffer.get(dst, 0, l1);

                chunk = chunks.get((int) whichChunk + 1);
                readBuffer = chunk.asReadOnlyBuffer();
                readBuffer.position(0);
                try {
                    readBuffer.get(dst, l1, l2);
                } catch (java.nio.BufferUnderflowException e) {
                    throw e;
                }
            }
        } catch (IndexOutOfBoundsException i) {
            throw new IOException("Out of bounds");
        }
        return dst;
    }

    /**
     * Writes a byte array in memory at the given offset.
     *
     * @param offSet The offset at which to write the byte array.
     * @param src    The byte array to write.
     * @throws IOException if an I/O error occurs.
     */
    public void put(long offSet, byte[] src) throws IOException {
        // Quick and dirty but will go wrong for massive numbers
        double a = offSet;
        double b = TWOGIGS;
        long whichChunk = (long) Math.floor(a / b);
        long withinChunk = offSet - whichChunk * TWOGIGS;

        // Data does not straddle two chunks
        try {
            if (TWOGIGS - withinChunk > src.length) {
                ByteBuffer chunk = chunks.get((int) whichChunk);
                // Allows free threading
                ByteBuffer writeBuffer = chunk.duplicate();
                writeBuffer.position((int) withinChunk);
                writeBuffer.put(src, 0, src.length);
            } else {
                int l1 = (int) (TWOGIGS - withinChunk);
                int l2 = (int) src.length - l1;
                ByteBuffer chunk = chunks.get((int) whichChunk);
                // Allows free threading
                ByteBuffer writeBuffer = chunk.duplicate();
                writeBuffer.position((int) withinChunk);
                writeBuffer.put(src, 0, l1);

                chunk = chunks.get((int) whichChunk + 1);
                writeBuffer = chunk.duplicate();
                writeBuffer.position(0);
                writeBuffer.put(src, l1, l2);

            }
        } catch (IndexOutOfBoundsException i) {
            throw new IOException("Out of bounds");
        }
    }

    /**
     * Cleans memory by removing the temporary file.
     */
    public void purge() {
        if (coreFileAccessor != null) {
            try {
                coreFileAccessor.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                coreFile.delete();
            }
        }
    }

    /**
     * Retrieves the size of the memory.
     *
     * @return The size of the memory.
     */
    public long getSize() {
        return length;
    }
}
