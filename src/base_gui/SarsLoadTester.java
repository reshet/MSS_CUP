package base_gui;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.lowagie.text.pdf.codec.Base64;

import base_connectivity.MSS_RQ_Request;

public class SarsLoadTester {

	/**
	 * @param args
	 */
	public static String URL = "http://54.225.84.44/MSS/msmartgate2.php?param=";
	public static void main(String[] args) throws UnsupportedEncodingException {
		//send_anket(1,1);
		final int threads = 1;
		final int loads_per_thread = 10;
		final int int_per_packet = 20;
		
		for(int i = 0;i < threads;i++){
			final int ii = i; 
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < loads_per_thread;j++){
						try {
							send_anket(ii, j,int_per_packet);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}	
					}
				}
			}).start();
		}
	}
	public static void send_anket(int thread,int seq,int int_in_packet) throws UnsupportedEncodingException{
		//System.out.println(new Date(System.currentTimeMillis())+" Thr:"+thread+", Seq:"+seq+" : "+"anket generation started");
		//gepatoprotectors example interview
		String interv = "<RQ><interview END_TIME=\"25.04.2013 12:16:27\" START_TIME=\"25.04.2013 12:05:52\" ID=\"1\" DEVICE_ID=\"201\" S1=\"1\" S1EXT=\"\" S2_1=\"33\" S2_2=\"4\" S2_2EXT=\"\" S3=\"1\" S3EXT=\"\" S4_7=\"1\" S4EXT=\"\" S5=\"2\" S5EXT=\"\" S6=\"1\" S6EXT=\"\" S7_1=\"4\" S7_1EXT=\"\" S7_2_5=\"1\" S7_2_7=\"1\" S7_2_9=\"1\" S7_2EXT=\"\" S8_3=\"1\" S8_6=\"1\" S8_10=\"1\" S8EXT=\"\" S9_4=\"1\" S9_5=\"1\" S9EXT=\"\" S10=\"9\" S10EXT=\"\" S11=\"1\" S11EXT=\"\" S12=\"2\" S12EXT=\"\" A1_1=\"4\" A1_1EXT=\"\" A1_2=\"3\" A1_2EXT=\"\" A1_3=\"1\" A1_3EXT=\"\" A1_4=\"3\" A1_4EXT=\"\" A1_5=\"2\" A1_5EXT=\"\" A1_6=\"1\" A1_6EXT=\"\" A1_7=\"3\" A1_7EXT=\"\" A1_8=\"2\" A1_8EXT=\"\" A1_9=\"5\" A1_9EXT=\"\" A1_10=\"4\" A1_10EXT=\"\" A1_11=\"1\" A1_11EXT=\"\" A1_12=\"4\" A1_12EXT=\"\" A2_4=\"1\" A2_7=\"1\" A2_9=\"1\" A2EXT=\"\" " +
				"B1=\"2\" B1EXT=\"\" B2=\"1\" B2EXT=\"\" B3=\"5\" B3EXT=\"\" B4_2=\"1\" B4_5=\"1\" B4EXT=\"\" C1=\"2\" C1EXT=\"\" C2_1=\"22\" " +
				"C2_2=\"Демотекст\" C3_1=\"Демотекст2\" C3_2=\"Демотекст\" C4_1=\"Демотекст\" C4_2=\"Демотекст\" C5=\"6\" C5EXT=\"\" C6_1=\"22\" C6_2=\"3\" C6_2EXT=\"\" D5_D1_1=\"3\" D5_D1_1EXT=\"\" D7_D1_1=\"3\" D7_D1_1EXT=\"\" D9_D1_1=\"5\" D9_D1_1EXT=\"\" " +
				"D5_D1_2=\"3\" D5_D1_2EXT=\"\" D7_D1_2=\"3\" D7_D1_2EXT=\"\" D9_D1_2=\"2\" D9_D1_2EXT=\"\" D5_D1_3=\"4\" D5_D1_3EXT=\"\" D7_D1_3=\"5\" D7_D1_3EXT=\"\" D9_D1_3=\"2\" D9_D1_3EXT=\"\" D5_D1_4=\"4\" D5_D1_4EXT=\"\" D7_D1_4=\"3\" D7_D1_4EXT=\"\" D9_D1_4=\"2\" D9_D1_4EXT=\"\" " +
				"D5_D1_5=\"4\" D5_D1_5EXT=\"\" D7_D1_5=\"3\" D7_D1_5EXT=\"\" D9_D1_5=\"2\" D9_D1_5EXT=\"\" D5_D1_6=\"4\" D5_D1_6EXT=\"\" D7_D1_6=\"3\" D7_D1_6EXT=\"\" D9_D1_6=\"5\" D9_D1_6EXT=\"\" D5_D1_7=\"4\" D5_D1_7EXT=\"\" D7_D1_7=\"3\" D7_D1_7EXT=\"\" D9_D1_7=\"3\" D9_D1_7EXT=\"\" D5_D1_8=\"4\" D5_D1_8EXT=\"\" D7_D1_8=\"3\" D7_D1_8EXT=\"\" D9_D1_8=\"4\"" +
				" D9_D1_8EXT=\"\" D5_D1_9=\"3\" D5_D1_9EXT=\"\" D7_D1_9=\"4\" D7_D1_9EXT=\"\" D9_D1_9=\"5\" D9_D1_9EXT=\"\" D5_D1_10=\"2\" D5_D1_10EXT=\"\" D7_D1_10=\"4\" D7_D1_10EXT=\"\" D9_D1_10=\"4\" D9_D1_10EXT=\"\" D5_D1_11=\"3\" D5_D1_11EXT=\"\" D7_D1_11=\"3\" D7_D1_11EXT=\"\" D9_D1_11=\"4\" D9_D1_11EXT=\"\" D5_D1_12=\"3\" D5_D1_12EXT=\"\" D7_D1_12=\"2\"" +
				" D7_D1_12EXT=\"\" D9_D1_12=\"4\" D9_D1_12EXT=\"\" D5_D1_13=\"3\" D5_D1_13EXT=\"\" D7_D1_13=\"4\" D7_D1_13EXT=\"\" D9_D1_13=\"2\" D9_D1_13EXT=\"\" D5_D1_14=\"4\" D5_D1_14EXT=\"\" D7_D1_14=\"3\" D7_D1_14EXT=\"\" D9_D1_14=\"4\" D9_D1_14EXT=\"\" D2_1=\"7\" D2_1EXT=\"\" D2_2=\"7\" D2_2EXT=\"\" D2_3=\"5\" D2_3EXT=\"\" D2_4=\"7\" D2_4EXT=\"\" D2_5=\"7\" " +
				"D2_5EXT=\"\" D2_6=\"5\" D2_6EXT=\"\" D2_7=\"7\" D2_7EXT=\"\" D2_8=\"7\" D2_8EXT=\"\" D2_9=\"5\" D2_9EXT=\"\" D2_10=\"7\" D2_10EXT=\"\" D2_11=\"7\" D2_11EXT=\"\" D2_12=\"5\" D2_12EXT=\"\" D2_13=\"5\" D2_13EXT=\"\" D2_14=\"7\" D2_14EXT=\"\" D5_D3=\"2\" D5_D3EXT=\"\" D7_D3=\"4\" D7_D3EXT=\"\" D9_D3=\"3\" D9_D3EXT=\"\" E1_3=\"1\" E1_5=\"1\" E1_7=\"1\" " +
				"E1EXT=\"\" E2_5=\"1\" E2_7=\"1\" E2EXT=\"\" E3_2=\"1\" E3_4=\"1\" E3_5=\"1\" E3_7=\"1\" E3EXT=\"\" E6_1=\"5\" E6_1EXT=\"\" E6_2_6=\"1\" E6_2_7=\"1\" E6_2EXT=\"\" E7_4=\"1\" E7_9=\"1\" E7EXT=\"\" D4_E8_4=\"1\" D4_E8_6=\"1\" D4_E8EXT=\"\" D9_E8_3=\"1\" D9_E8_7=\"1\" D9_E8EXT=\"\" F1_5=\"1\" F1EXT=\"\" D1_F03=\"4\" D1_F03EXT=\"\" D1_F2=\"0\" D1_F2EXT=\"\"" +
				" D1_F3_1=\"2\" D1_F3_1EXT=\"\" D1_F3_2=\"3\" D1_F3_2EXT=\"\" D1_F3_3=\"3\" D1_F3_3EXT=\"\" D2_F03=\"3\" D2_F03EXT=\"\" D2_F2=\"0\" D2_F2EXT=\"\" D2_F3_1=\"3\" D2_F3_1EXT=\"\" D2_F3_2=\"3\" D2_F3_2EXT=\"\" D2_F3_3=\"4\" D2_F3_3EXT=\"\" D3_F03=\"5\" D3_F03EXT=\"\" D3_F2=\"0\" D3_F2EXT=\"\" D3_F3_1=\"3\" D3_F3_1EXT=\"\" D3_F3_2=\"4\" D3_F3_2EXT=\"\" D3_F3_3=\"3\" D3_F3_3EXT=\"\"" +
				" D4_F03=\"5\" D4_F03EXT=\"\" D4_F2=\"0\" D4_F2EXT=\"\" D4_F3_1=\"3\" D4_F3_1EXT=\"\" D4_F3_2=\"4\" D4_F3_2EXT=\"\" D4_F3_3=\"3\" D4_F3_3EXT=\"\" D5_F03=\"5\" D5_F03EXT=\"\" D5_F2=\"0\" D5_F2EXT=\"\" D5_F3_1=\"3\" D5_F3_1EXT=\"\" D5_F3_2=\"3\" D5_F3_2EXT=\"\" D5_F3_3=\"3\" D5_F3_3EXT=\"\" D6_F03=\"4\" D6_F03EXT=\"\" D6_F2=\"0\" D6_F2EXT=\"\" D6_F3_1=\"3\" D6_F3_1EXT=\"\" D6_F3_2=\"3\" D6_F3_2EXT=\"\" D6_F3_3=\"4\" D6_F3_3EXT=\"\"" +
				" F4=\"4\" F4EXT=\"\" G1a=\"222\" G1b=\"22\" G1c=\"12\" G1d=\"12234\" G1e=\"234\" G2a=\"234\" G2b=\"256\" G2c=\"23\" G2d=\"23\" G2e=\"12\" G3=\"4\" G3EXT=\"\" G5=\"2\" G5EXT=\"\" H1=\"3\" H1EXT=\"\" H2=\"2\" H2EXT=\"\" H3=\"4\" H3EXT=\"\" H5=\"2\" H5EXT=\"\" H6=\"3\" H6EXT=\"\" H7=\"2\" H7EXT=\"\" H8=\"4\" H8EXT=\"\" REMOTE_IP=\"Tool\" />"
		+"</RQ>";
		StringBuilder anket = new StringBuilder();
		
		anket.append("<MRQ>");
		for(int i = 0; i < int_in_packet;i++)anket.append(interv);
		anket.append("</MRQ>");
		String enc_anket = Base64.encodeBytes(anket.toString().getBytes("CP1251"));
		String sitTask = "<RQ SCREEN=\"240\" ME=\"YS11A\"><send_interviews></send_interviews><vertex DATA=\"" +
	    enc_anket
		+"\" PASSWORD=\"default\" SERVICE=\"sys_s_ars\" LOGIN=\"1000136\" UID=\"455\">"
		+"</vertex><SERVICE NAME=\"tile://sars_send/service.m\"></SERVICE><PROGRESS#REPORTING></PROGRESS#REPORTING></RQ> ";
		String login = "NAXVOURTRVCN";
		String password = "bfcmd34fpr89";
		
		String sitTask2 = xorek_pswd(sitTask, password);
		//System.out.println(sitTask2);
		System.out.println(new Date(System.currentTimeMillis())+" Thr:"+thread+", Seq:"+seq+" : "+"anket generated and send");
		String xmlans = MSS_RQ_Request.http_request(login+sitTask2,URL);
		System.out.println(new Date(System.currentTimeMillis())+" Thr:"+thread+", Seq:"+seq+" : "+"answer.achieved");
		
		//System.out.println(xmlans);
		String xmlans_dec = xorek_pswd(xmlans, password);
		//System.out.println(new Date(System.currentTimeMillis())+" Thr:"+thread+", Seq:"+seq+" : "+"answer.decoded");
		System.out.println(xmlans_dec);
			
	}
	public static synchronized String  xorek_pswd(String str,String pswd)
	{
		int lng = str.length();
		int lng_p = pswd.length();
		StringBuilder str_enc = new StringBuilder();
		int pswd_i = 0;
		for (int pos = 0;pos < lng;pos++)
		{
			if (pswd_i == lng_p) pswd_i = 0;
			char key = pswd.charAt(pswd_i);
			int ansi_key = (int)key;
			char encr_byte = str.charAt(pos);
			int asci_byte_encr = (int)encr_byte;
			int xored_byte = asci_byte_encr ^ ansi_key;
			char encrypted_byte = (char)xored_byte;
			str_enc.append(encrypted_byte);
			pswd_i++;
		}
		return str_enc.toString();
	}	
}
