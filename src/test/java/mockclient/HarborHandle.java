package mockclient;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class HarborHandle extends IoHandlerAdapter {

	public void sessionOpened(IoSession session) throws Exception {
		String json = "<body>{id:helloworld}</body>";
		session.write(json);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String json = message.toString();
		System.out.println("Server Say: " + json);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		session.close(true);
	}
}
