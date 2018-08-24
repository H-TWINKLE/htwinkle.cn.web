package com.twinkle.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.handler.Handler;
import com.twinkle.jrebel.util.JrebelSign;
import com.twinkle.jrebel.util.rsasign;


public class JreBelHander extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

		try {

			if (target.equals("/jrebel/leases")) {
				isHandled[0] = true;
				jrebelLeasesHandler(target, request, response);
			} else if (target.equals("/jrebel/leases/1")) {
				isHandled[0] = true;
				jrebelLeases1Handler(target, request, response);
			} else if (target.equals("/agent/leases")) {
				isHandled[0] = true;
				jrebelLeasesHandler(target, request, response);
			} else if (target.equals("/agent/leases/1")) {
				isHandled[0] = true;
				jrebelLeases1Handler(target, request, response);
			} else if (target.equals("/jrebel/validate-connection")) {
				isHandled[0] = true;
				jrebelValidateHandler(target, request, response);
			} else if (target.equals("/rpc/ping.action")) {
				isHandled[0] = true;
				pingHandler(target, request, response);
			} else if (target.equals("/rpc/obtainTicket.action")) {
				isHandled[0] = true;
				obtainTicketHandler(target, request, response);
			} else if (target.equals("/rpc/releaseTicket.action")) {
				isHandled[0] = true;
				releaseTicketHandler(target, request, response);
			} else {
				next.handle(target, request, response, isHandled);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void jrebelValidateHandler(String target, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		String jsonStr = "{\n" + "    \"serverVersion\": \"3.2.4\",\n" + "    \"serverProtocolVersion\": \"1.1\",\n"
				+ "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n"
				+ "    \"groupType\": \"managed\",\n" + "    \"statusCode\": \"SUCCESS\",\n"
				+ "    \"company\": \"Administrator\",\n" + "    \"canGetLease\": true,\n" + "    \"licenseType\": 1,\n"
				+ "    \"evaluationLicense\": false,\n" + "    \"seatPoolType\": \"standalone\"\n" + "}\n";
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		String body = jsonObject.toJSONString();
		response.getWriter().print(body);
	}

	private void jrebelLeases1Handler(String target, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		String username = request.getParameter("username");
		String jsonStr = "{\n" + "    \"serverVersion\": \"3.2.4\",\n" + "    \"serverProtocolVersion\": \"1.1\",\n"
				+ "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n"
				+ "    \"groupType\": \"managed\",\n" + "    \"statusCode\": \"SUCCESS\",\n" + "    \"msg\": null,\n"
				+ "    \"statusMessage\": null\n" + "}\n";
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		if (username != null) {
			jsonObject.put("company", username);
		}
		String body = jsonObject.toJSONString();
		response.getWriter().print(body);

	}

	private void jrebelLeasesHandler(String target, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		String clientRandomness = request.getParameter("randomness");
		String username = request.getParameter("username");
		String guid = request.getParameter("guid");
		boolean offline = Boolean.parseBoolean(request.getParameter("offline"));
		String validFrom = "null";
		String validUntil = "null";
		if (offline) {
			String clientTime = request.getParameter("clientTime");
			//String offlineDays = request.getParameter("offlineDays");
			// long clinetTimeUntil = Long.parseLong(clientTime) +
			// Long.parseLong(offlineDays) * 24 * 60 * 60 * 1000;
			long clinetTimeUntil = Long.parseLong(clientTime) + 180L * 24 * 60 * 60 * 1000;
			validFrom = clientTime;
			validUntil = String.valueOf(clinetTimeUntil);
		}
		String jsonStr = "{\n" + "    \"serverVersion\": \"3.2.4\",\n" + "    \"serverProtocolVersion\": \"1.1\",\n"
				+ "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n"
				+ "    \"groupType\": \"managed\",\n" + "    \"id\": 1,\n" + "    \"licenseType\": 1,\n"
				+ "    \"evaluationLicense\": false,\n"
				+ "    \"signature\": \"OJE9wGg2xncSb+VgnYT+9HGCFaLOk28tneMFhCbpVMKoC/Iq4LuaDKPirBjG4o394/UjCDGgTBpIrzcXNPdVxVr8PnQzpy7ZSToGO8wv/KIWZT9/ba7bDbA8/RZ4B37YkCeXhjaixpmoyz/CIZMnei4q7oWR7DYUOlOcEWDQhiY=\",\n"
				+ "    \"serverRandomness\": \"H2ulzLlh7E0=\",\n" + "    \"seatPoolType\": \"standalone\",\n"
				+ "    \"statusCode\": \"SUCCESS\",\n" + "    \"offline\": " + String.valueOf(offline) + ",\n"
				+ "    \"validFrom\": " + validFrom + ",\n" + "    \"validUntil\": " + validUntil + ",\n"
				+ "    \"company\": \"Administrator\",\n" + "    \"orderId\": \"\",\n" + "    \"zeroIds\": [\n"
				+ "        \n" + "    ],\n" + "    \"licenseValidFrom\": 1490544001000,\n"
				+ "    \"licenseValidUntil\": 1691839999000\n" + "}";

		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		if (clientRandomness == null || username == null || guid == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			JrebelSign jrebelSign = new JrebelSign();
			jrebelSign.toLeaseCreateJson(clientRandomness, guid, offline, validFrom, validUntil);
			String signature = jrebelSign.getSignature();
			jsonObject.put("signature", signature);
			jsonObject.put("company", username);
			String body = jsonObject.toJSONString();
			response.getWriter().print(body);
		}
	}

	private void releaseTicketHandler(String target, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		String salt = request.getParameter("salt");
		if (salt == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			String xmlContent = "<ReleaseTicketResponse><message></message><responseCode>OK</responseCode><salt>" + salt
					+ "</salt></ReleaseTicketResponse>";
			String xmlSignature = rsasign.Sign(xmlContent);
			String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
			response.getWriter().print(body);
		}
	}

	private void obtainTicketHandler(String target, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		//SimpleDateFormat fm = new SimpleDateFormat("EEE,d MMM yyyy hh:mm:ss Z", Locale.ENGLISH);
		//String date = fm.format(new Date()) + " GMT";
		// response.setHeader("Date", date);
		// response.setHeader("Server", "fasthttp");
		String salt = request.getParameter("salt");
		String username = request.getParameter("userName");
		String prolongationPeriod = "607875500";
		if (salt == null || username == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			String xmlContent = "<ObtainTicketResponse><message></message><prolongationPeriod>" + prolongationPeriod
					+ "</prolongationPeriod><responseCode>OK</responseCode><salt>" + salt
					+ "</salt><ticketId>1</ticketId><ticketProperties>licensee=" + username
					+ "\tlicenseType=0\t</ticketProperties></ObtainTicketResponse>";
			String xmlSignature = rsasign.Sign(xmlContent);
			String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
			response.getWriter().print(body);
		}
	}

	private void pingHandler(String target, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		String salt = request.getParameter("salt");
		if (salt == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			String xmlContent = "<PingResponse><message></message><responseCode>OK</responseCode><salt>" + salt
					+ "</salt></PingResponse>";
			String xmlSignature = rsasign.Sign(xmlContent);
			String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
			response.getWriter().print(body);
		}

	}

	/*private void indexHandler(String target, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("<h1>Hello,This is a Jrebel & JetBrains License Server!</h1>");

	}*/

}
