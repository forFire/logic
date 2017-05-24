package mt90;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class Decoder extends CumulativeProtocolDecoder {

	private final Charset charset;

	public Decoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
		byte[] bytes = new byte[in.limit()];
		in.get(bytes);
		String rs = new String(bytes, charset);
		out.write(rs);
		return false;
	}

}
