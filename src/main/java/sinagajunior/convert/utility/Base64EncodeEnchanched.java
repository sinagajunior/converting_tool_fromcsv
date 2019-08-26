package sinagajunior.convert.utility;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;

/**
 * @project convertingtool
 * @Author royantonius on 08/08/19 .
 */
public class Base64EncodeEnchanched {

    private static final int BUFFER_SIZE = 3 * 1024;

    public String encode(final InputStream input) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE)) {
            final Base64.Encoder encoder = Base64.getEncoder();
            final StringBuilder result = new StringBuilder();

            byte[] chunk = new byte[BUFFER_SIZE];
            ByteRingBuffer byteBuffer = new ByteRingBuffer(BUFFER_SIZE * 2);
            int len;
            while ((len = in.read(chunk)) != -1) {
                byteBuffer.put(Arrays.copyOf(chunk, len));

                if (byteBuffer.available() >= BUFFER_SIZE) {
                    final byte[] workingChunk = new byte[BUFFER_SIZE];
                    byteBuffer.get(workingChunk);

                    result.append(encoder.encodeToString(workingChunk));
                }
            }

            if (byteBuffer.available() > 0) {
                final byte[] workingChunk = new byte[byteBuffer.available()];
                byteBuffer.get(workingChunk);

                result.append(encoder.encodeToString(workingChunk));
            }


            return result.toString();
        }
    }


}
