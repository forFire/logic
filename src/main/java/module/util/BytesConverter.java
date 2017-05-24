/**
 * 
 */
package module.util;

/**
 * @author fss
 * 
 */
public class BytesConverter {

	public BytesConverter() {
		// TODO Auto-generated constructor stub
	}

	public static int bytesToUshort(byte b[]) {
		return b[1] & 0xff | (b[0] & 0xff) << 8;
	}

	public static long bytesToUint(byte[] array) {
		return ((long) (array[3] & 0xff)) | ((long) (array[2] & 0xff)) << 8
				| ((long) (array[1] & 0xff)) << 16 | ((long) (array[0] & 0xff)) << 24;
	}
}
