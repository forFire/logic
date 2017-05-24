package mt90;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 自定义编解码工厂
 * 
 * 实际上这个工厂类就是包装了编码器、解码器
 * 
 * 通过接口中的getEncoder()、getDecoder()方法向ProtocolCodecFilter过滤器返回编解码器实例
 * 
 * 以便在过滤器中对数据进行编解码
 */
public class CodecFactory implements ProtocolCodecFactory {
	private final Encoder encoder;
	private final Decoder decoder;

	public CodecFactory() {
		this(Charset.defaultCharset());
	}

	public CodecFactory(Charset charset) {
		this.encoder = new Encoder(charset);
		this.decoder = new Decoder(charset);
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}
}