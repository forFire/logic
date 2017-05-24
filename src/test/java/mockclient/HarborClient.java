package mockclient;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class HarborClient{
	private static final int bindPort=60004;
	
	public static void main(String[] args){
		// 创建一个socket连接      
		NioSocketConnector connector=new NioSocketConnector();
		// 获取过滤器链        
		DefaultIoFilterChainBuilder chain=connector.getFilterChain();
		
		ProtocolCodecFilter filter= new ProtocolCodecFilter(new HCoderFactory(Charset.forName("UTF-8"))); 
		// 添加编码过滤器 处理乱码、编码问题  
		chain.addLast("objectFilter",filter);
		// 消息核心处理器     
		connector.setHandler(new HarborHandle());
		// 设置链接超时时间     
		connector.setConnectTimeoutCheckInterval(30);
		// 连接服务器，知道端口、地址    
		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",bindPort));
		// 等待连接创建完成    
		cf.awaitUninterruptibly();
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}

}
