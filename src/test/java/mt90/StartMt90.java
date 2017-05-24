package mt90;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author fyq
 */
public class StartMt90 {

	private String serverIp;

	private int serverPort;

	private IoConnector connector;

	private ConnectFuture future;

	private static IoSession session;

	public StartMt90(String serverIp, int serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	private void init() {
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000);
		connector.setHandler(new ClientHandler());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CodecFactory()));
	}

	public boolean startClient(String msg) {
		init();
		future = connector.connect(new InetSocketAddress(serverIp, serverPort));
		future.addListener(new IoFutureListener<ConnectFuture>() {
			@Override
			public void operationComplete(ConnectFuture future) {
				session = future.getSession();
				System.out.println("session info:" + session);
			}
		});
		return future.isConnected();
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public static void main(String[] args) throws Exception {
//		String connectIp = "192.168.5.66";
//		int connectPort = 60004;
//		StartMt90 client = new StartMt90(connectIp, connectPort);
//		client.startClient("fyq");
//
//		while (true) {
//			InputStreamReader input = new InputStreamReader(System.in);
//			BufferedReader reader = new BufferedReader(input);
//			String msg = reader.readLine();
//			if (msg.equals("quit")) {
//				reader.close();
//				session.close(true);
//				System.exit(0);
//				break;
//			} else {
//				session.write(msg);
//			}
//		}
//		
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hhmmss");
//		Date date = new Date();
//		date.setTime(1395395317000L);
//		System.out.println(df.format(date));
		
		String s ="#ssss";
		System.out.println(s.indexOf("a"));
	}
}
