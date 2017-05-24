package mt90;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * @author fyq
 */
public class ClientHandler extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("messageReceived:" + message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("sessionCreated");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("sessionOpened：" + session.getRemoteAddress());
	}
}
