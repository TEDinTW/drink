package com.example.drinkorder.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.drinkorder.bean.jackson.AuthenticateRequest;
import com.example.drinkorder.bean.jackson.AuthenticateResponse;
import com.example.drinkorder.bean.jackson.DrinkData;
import com.example.drinkorder.bean.jackson.SubmitOrderRequest;
import com.example.drinkorder.bean.jackson.SubmitOrderResponse;

public class WebServiceGateway {

//	private static final String authURL = "http://192.168.11.4:8080/OrderDrinkWebService/rest/drink/authenticate";
	private static final String authURL = "http://192.168.0.188:8888/ted/jueseb/mobileTest";
	private static final String drinkDataURL = "http://192.168.11.4:8080/OrderDrinkWebService/rest/drink/getdrinkdata";
	private static final String submitOrderURL = "http://192.168.11.4:8080/OrderDrinkWebService/rest/drink/submitOrder";
	private static final int BUFFER_SIZE = 1024;

	public static AuthenticateResponse authenticate(AuthenticateRequest authReq) {
		try {
			String reqJsonStr = JsonManager.toJsonString(authReq);
			URL url = new URL(authURL);
			String respJsonStr = jsonPost(reqJsonStr, url);
			return (AuthenticateResponse) JsonManager.parseSubmitOrderResponse(respJsonStr, AuthenticateResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static SubmitOrderResponse submitOrder(SubmitOrderRequest request) {
		String reqJsonStr = JsonManager.toJsonString(request);

		try {
			URL url = new URL(submitOrderURL);
			String respJsonStr = jsonPost(reqJsonStr, url);
			return (SubmitOrderResponse) JsonManager.parseSubmitOrderResponse(respJsonStr, SubmitOrderResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getDrinkDataJsonString() {
		try {
			URL url = new URL(drinkDataURL);
			return jsonGet(url);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	public static DrinkData getDrinkData() {
		DrinkData drinkData = null;
		try {
			return (DrinkData) JsonManager.parseSubmitOrderResponse(getDrinkDataJsonString(), DrinkData.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return drinkData;
	}

	private static String jsonGet(URL url) throws Exception {
		InputStream is = null;

		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000 /* milliseconds */);
			conn.setConnectTimeout(5000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			conn.connect();

			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				is = conn.getInputStream();

				// Convert the InputStream into a string
				String contentAsString = inputStreamTOString(is);
				return contentAsString;
			}
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	private static String jsonPost(String reqJson, URL url) throws Exception {
		InputStream is = null;
		String respJsonStr = null;

		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000 /* milliseconds */);
			conn.setConnectTimeout(5000 /* milliseconds */);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			conn.connect();
			OutputStream out = new BufferedOutputStream(conn.getOutputStream());

			out.write(reqJson.getBytes());
			out.flush();
			out.close();

			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				is = conn.getInputStream();

				// Convert the InputStream into a string
				return inputStreamTOString(is);
			}
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return respJsonStr;
	}

	public static String inputStreamTOString(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}

}
