package mockclient;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class HDecoder  extends CumulativeProtocolDecoder {
	private final Charset charset;

	public HDecoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	protected boolean doDecode(IoSession ioSession, IoBuffer message,
			ProtocolDecoderOutput output) throws Exception {
		CharsetDecoder cd = charset.newDecoder();
		String json = message.getString(cd);
		output.write(json);
		return true;
	}
}
