package com.tower.service.rpc;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	private static HttpClient httpclient = HttpClientBuilder.create().build();

	public static HttpResponse httpGet(String url) {
		HttpResponse response = null;
		HttpGet get = new HttpGet(url);
		try {
			response = httpclient.execute(get);
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {

			}
		} catch (ClientProtocolException ex) {

		} catch (SocketTimeoutException ex) {
			String msg = ex.getMessage();
			if (msg != null
					&& msg.toLowerCase().indexOf("connect timed out") != -1) {

			} else if (msg != null
					&& msg.toLowerCase().indexOf("read timed out") != -1) {

			}
		} catch (ConnectTimeoutException ex) {
			
		} catch (IOException e) {

		}
		
		return response;
	}

	/**
	 * Unconditionally close a response.
	 * <p>
	 * Example Code:
	 * 
	 * <pre>
	 * HttpResponse httpResponse = null;
	 * try {
	 * 	httpResponse = httpClient.execute(httpGet);
	 * } catch (Exception e) {
	 * 	// error handling
	 * } finally {
	 * 	HttpClientUtils.closeQuietly(httpResponse);
	 * }
	 * </pre>
	 * 
	 * @param response
	 *            the HttpResponse to release resources, may be null or already
	 *            closed.
	 * 
	 * @since 4.2
	 */
	public static void closeQuietly(final HttpResponse response) {
		if (response != null) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					EntityUtils.consume(entity);
				} catch (final IOException ex) {
				}
			}
		}
	}

	/**
	 * Unconditionally close a httpClient. Shuts down the underlying connection
	 * manager and releases the resources.
	 * <p>
	 * Example Code:
	 * 
	 * <pre>
	 * HttpClient httpClient = null;
	 * try {
	 *   httpClient = new DefaultHttpClient(...);
	 * } catch (Exception e) {
	 *   // error handling
	 * } finally {
	 *   HttpClientUtils.closeQuietly(httpClient);
	 * }
	 * </pre>
	 * 
	 * @param httpClient
	 *            the HttpClient to close, may be null or already closed.
	 * @since 4.2
	 */
	public static void closeQuietly(final HttpClient httpClient) {
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

}
