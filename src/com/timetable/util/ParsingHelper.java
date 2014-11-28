package com.timetable.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParsingHelper {

	// URLHTTPConnection
	private static void faid() {

		{
			final String username = "admin";
			final String password = "admin123!";
			Authenticator.setDefault(new Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					PasswordAuthentication pa = new PasswordAuthentication(username, password
							.toCharArray());
					return pa;
				}
			});
		}
		try {
			URL urlString = new URL("http://10.224.23.66");
			HttpURLConnection conn = (HttpURLConnection) urlString.openConnection();
			System.out.println(urlString);
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");

			int code = conn.getResponseCode();// deployThreadPool(conn);
			System.out.println(code);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output = "", in;
			while ((in = br.readLine()) != null) {

				output = output.concat(in);
			}
			System.out.println(output);

		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// simulating the wating process
	public static void load() {
		// Create the connection to
		ExecutorService pool = Executors.newFixedThreadPool(1);

		Thread th = new Thread() {
			public void run() {
				int i = 0;
				while (i++ < 10) {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
		};
		Future fut = pool.submit(th);
		int i = 0;
		while (true) {
			if (fut.isDone())
				break;
			try {
				Thread.sleep(300);
				if (i++ > 20) {
					i = 0;
					System.out.println();
				}
				System.out.print(".");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		pool.shutdown();
	}

}
