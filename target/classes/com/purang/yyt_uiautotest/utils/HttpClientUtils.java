package com.purang.yyt_uiautotest.utils;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONArray;

public class HttpClientUtils {

	public static String ENCODING = "UTF-8";
	public static String REQ_HEADER = "application/json";
	public static String CONTENT_TYPE = "application/json;charset=UTF-8";

	private static HttpClientUtils httpRequst = null;

	private HttpClientUtils() {
	}

	/**
	 * @return
	 */
	public static HttpClientUtils getInstance() {
		if (httpRequst == null) {
			synchronized (HttpClientUtils.class) {
				if (httpRequst == null) {
					httpRequst = new HttpClientUtils();
				}
			}
		}
		return httpRequst;
	}

	/**
	 * @param url
	 * @param jsonParam
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static JSONArray doPut(String url, String jsonParam) {
		JSONArray jsonArr = null;
		HttpClient httpClient = new HttpClient();
		PutMethod putMethod = new PutMethod(url);
		putMethod.addRequestHeader("Content-Type", CONTENT_TYPE);
		putMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODING);

		try {
			putMethod.setRequestBody(jsonParam);
			int statusCode = httpClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + putMethod.getStatusLine());
			}
			byte[] responseBody = putMethod.getResponseBody();
			String ab = "[" + new String(responseBody, ENCODING) + "]";
			jsonArr = JSONArray.parseArray(ab);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			putMethod.releaseConnection();
		}
		return jsonArr;
	}

	/**
	 * @param url
	 * @param jsonParam
	 * @return
	 */
	public JSONArray doPost(String url, String jsonParam) {
		JSONArray jsonArr = null;
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-Type", CONTENT_TYPE);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODING);

		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			byte[] responseBody = postMethod.getResponseBody();
			String ab = "[" + new String(responseBody, ENCODING) + "]";
			jsonArr = JSONArray.parseArray(ab);

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return jsonArr;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public JSONArray doGet(String url) {
		JSONArray jsonArr = null;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			String ab = "[" + new String(responseBody, ENCODING) + "]";
			jsonArr = JSONArray.parseArray(ab);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return jsonArr;
	}
}
