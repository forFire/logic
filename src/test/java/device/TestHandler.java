package device;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@Component
public class TestHandler implements IoHandler {

	// --------------------------------------------------------------

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if(message instanceof String) {
			Device.displayMsg((String)message);
			System.out.println("收到数据："+(String)message);
		}	
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		if(status.equals(IdleStatus.BOTH_IDLE))
			session.close(true);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
//		((SocketSessionConfig) session.getConfig()).setKeepAlive(true);
//		((SocketSessionConfig) session.getConfig()).setBothIdleTime(300);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

}
