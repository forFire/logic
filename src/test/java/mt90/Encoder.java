package mt90;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 可实现ProtocolEncoder 其中有encode() dispose()两个要实现
 * 销毁我们并不关心，故直接继承ProtocolEncoderAdapter
 */
public class Encoder extends ProtocolEncoderAdapter {

	private final Charset charset;

	public Encoder(Charset charset) {
		this.charset = charset;
	}

	/**
	 * 按协议拼字符串到IoBuffer缓冲区，然后输出字节流
	 */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		if ("".equals(message)) {
			System.out.println("不能发空白");
			return;
		}
		CharsetEncoder ce = charset.newEncoder();
		IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);

		buffer.putString(message.toString() + "\r\n", ce);

		buffer.flip();

		out.write(buffer);
	}
}