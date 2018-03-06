package com.palyrobotics.frc2018.vision.util;

import com.palyrobotics.frc2018.util.logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Controls managing client and server sockets.
 *
 * @author Quintin Dwight
 */
public abstract class AbstractVisionServer extends AbstractVisionThread {

	public enum ServerState {
		PRE_INIT, ATTEMPTING_CONNECTION, OPEN
	}

	protected int m_Port;
	protected boolean m_LogConnectionStatus;
	protected ServerSocket m_Server;
	protected Socket m_Client = new Socket();
	protected ServerState m_ServerState = ServerState.PRE_INIT;

	protected AbstractVisionServer(final String k_threadName) {
		super(k_threadName);
	}

	/**
	 * Starts the server thread.
	 *
	 * @param updateRate Update rate of the thread
	 * @param port The port to connect the server to
	 */
	public void start(final long updateRate, final int port, final boolean logConnectionStatus) {
		super.start(updateRate);
		m_Port = port;
		m_LogConnectionStatus = logConnectionStatus;
	}

	@Override
	protected void init() {
		if (m_ServerState != ServerState.PRE_INIT) {
			log(Level.FINEST, "Thread has already been initialized. Aborting...");
			return;
		}
		setServerState(ServerState.ATTEMPTING_CONNECTION);
		afterInit();
	}

	/**
	 * Check if the client socket is connected.
	 *
	 * @return New server state, depends on whether or not we should attempt conncetion
	 */
	protected ServerState checkConnection() {
		final boolean notConnected = !m_Client.isConnected(), closed = m_Client.isClosed(), shouldRetry = notConnected || closed;
		if (notConnected) log(m_LogConnectionStatus ? Level.INFO:Level.FINEST, String.format("Lost connection to port: %d",       m_Port));
		if (closed)       log(m_LogConnectionStatus ? Level.INFO:Level.FINEST, String.format("Connection was closed on port: %d", m_Port));
		if (shouldRetry) {
			try {
				Thread.sleep(200);
			} catch (final InterruptedException ie) {
				ie.printStackTrace();
			}
			return ServerState.ATTEMPTING_CONNECTION;
		} else {
			return ServerState.OPEN;
		}
	}

	/**
	 * Sets the state of the server.
	 *
	 * @param state State of the server
	 */
	protected void setServerState(ServerState state) {
		m_ServerState = state;
	}

	/**
	 * Pauses the thread until a connection is established.
	 *
	 * @return The state after execution
	 */
	private ServerState acceptConnection() {
		try {
			if (m_Server != null)
				m_Server.close();
			m_Server = new ServerSocket(m_Port);
			m_Server.setReuseAddress(true);
			// Pause thread until we accept from the client
			log(Level.INFO, String.format("Listening for connection on port %d", m_Port));
			m_Client = m_Server.accept();
			log(Level.INFO, String.format("Connected to client on port %d", m_Port));
			return ServerState.OPEN;
		} catch (final IOException ioe) {
			log(m_LogConnectionStatus ? Level.INFO:Level.FINEST, ioe.toString());
			return ServerState.ATTEMPTING_CONNECTION;
		}
	}

	protected void closeClient() {
		try {
			m_Client.close();
		} catch (final IOException ioe) {
			log(Level.FINEST, ioe.toString());
		}
	}

	@Override
	protected void update() {
		switch (m_ThreadState) {
			case RUNNING: {
				switch (m_ServerState) {
					case PRE_INIT: {
						log(Level.SEVERE, "Thread is not initialized while in update state!");
						break;
					}
					case ATTEMPTING_CONNECTION: {
						setServerState(acceptConnection());
						break;
					}
					case OPEN: {
						setServerState(checkConnection());
						break;
					}
				}
				break;
			}
		}
		afterUpdate();
	}

	protected abstract void afterInit();

	protected abstract void afterUpdate();

	@Override
	protected void onPause() {
		closeClient();
	}

	@Override
	protected void onStop() {
		closeClient();
	}

	@Override protected void onResume() { }
}