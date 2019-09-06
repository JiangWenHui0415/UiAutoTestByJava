package com.purang.yyt_uiautotest.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class ThreadExecutor {
	private long readTimeOut = 30000;
	private long threadTimeOut = 90000;

	/**
	 * set process execute timeout
	 * 
	 * @param timeOut
	 *            timeout of milliseconds.
	 */
	public void setThreadTimeOut(long timeOut) {
		this.threadTimeOut = timeOut + this.readTimeOut;
	}

	/**
	 * Description:get the thread execute timeout setting.
	 * 
	 * @return timeout setting
	 */
	public long getThreadTimeOut() {
		return this.threadTimeOut;
	}

	/**
	 * set process buffer read timeout
	 * 
	 * @param timeOut
	 *            timeout of milliseconds.
	 */
	public void setReadTimeOut(long timeOut) {
		this.readTimeOut = timeOut;
	}

	/**
	 * Description:get the buffer read timeout setting.
	 * 
	 * @return timeout setting
	 */
	public long getReadTimeOut() {
		return this.readTimeOut;
	}

	/**
	 * execute exe/bat/shell string or file by java.
	 * 
	 * @param command
	 *            command to be executed
	 * @throws RuntimeException
	 * @throws TimeoutException
	 */
	public void executeCommands(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			ProcessListener listener = new ProcessListener(process);
			listener.start();

			try {
				listener.join(threadTimeOut);
			} catch (InterruptedException ex) {
				listener.interrupt();
				throw new RuntimeException(ex);
			} finally {
				process.destroy();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * execute vbs by java.
	 * 
	 * @param command
	 *            vbs file with params Object to be executed
	 * @throws RuntimeException
	 * @throws TimeoutException
	 */
	public void executeCommands(String[] command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			ProcessListener listener = new ProcessListener(process);
			listener.start();

			try {
				listener.join(threadTimeOut);
			} catch (InterruptedException ex) {
				listener.interrupt();
				throw new RuntimeException(ex);
			} finally {
				process.destroy();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private class ProcessListener extends Thread {
		private Process process = null;

		/**
		 * construct with parameter sets.
		 * 
		 * @param process
		 *            the process instance.
		 */
		public ProcessListener(Process process) {
			this.process = process;
		}

		/**
		 * read inputstream before process.waitfor(). keep threads never hangs
		 * up.
		 */
		@Override
		public void run() {
			try {
				StreamReader reader = new StreamReader(process.getInputStream());
				reader.start();
				reader.join(readTimeOut);
				process.waitFor();
			} catch (InterruptedException ignore) {
				return;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class StreamReader extends Thread {
		private InputStream input;

		/**
		 * construct with parameter sets.
		 * 
		 * @param input
		 *            the InputStream instance.
		 */
		public StreamReader(InputStream input) {
			this.input = input;
		}

		/**
		 * read inputstream and do buffer output write.
		 */
		@Override
		public void run() {
			try {
				InputStreamReader isReader = new InputStreamReader(input, "GBK");
				BufferedReader bfRader = new BufferedReader(isReader);
				while (bfRader.readLine() != null) {
					// do nothing.
				}
				bfRader.close();
				isReader.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}