package com.univision.radio.zap;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.zaproxy.zap.CustomProxyListener;
import org.zaproxy.zap.ProxyModelInfo;
import org.zaproxy.zap.ProxyRunner;

public class ProxyThreadRunner implements Runnable {
	public static ArrayList<String> Steps = new ArrayList<String>();
	public static ArrayList<JSONObject> Calls = new ArrayList<JSONObject>();
	private int var;
	
	private static int proxyPort;
	private static String proxyHost;

	public ProxyThreadRunner(String host, int port) {
		proxyHost = host;
		proxyPort = port;
	}

	public void run() {
		ProxyModelInfo.ip = proxyHost;
		ProxyModelInfo.port = proxyPort;
		ProxyModelInfo.configFile = System.getProperty("user.dir") + File.separator + "config.xml";
		ProxyModelInfo.rootCa = "_u3-7QAAAAIAAAABAAAAAQARb3dhc3BfemFwX3Jvb3RfY2EAAAFTo2yTCAAABQIwggT-MA4GCisGAQQBKgIRAQEFAASCBOrUnSbczT0pDjUUkMuAKl7dkM-gHVjBtJbJeoAnTKnGtERtVGaevXheQ5BfnUsPQ8cbB890wTL3i_YhAz3KcSdNnkhhYI3XVIoPA7J3lmR67_9dQWGBe7oqRHVk23c0I7k43sSFTKD5tai5W5vK2Bwb0xWRNgZqljRxQlSFMB0iAcNE3kM8m8fVPQFgE8AMaBb3I6vMGIonHxeb5siladRrQzZ2JQJvDZWCURNAkBt2txKREmNqBuzIn54luoyNLfqeiA01IHMANIlsX3HUKz59WQ5Y4BkCVRLpUjEIpgRTqgYlj5JxoSRhVK5c7j-mR1aUaN44TcbJC_-WiPVTXI-Hat81CyroCYsfOqw3EYCJUQu4orLcqBXP-cwkBipMpFQtW8cMO36Y5LNaENENbN8OfpqoPgM9cn7ckw58ZciPB7AfdJSvnQFHQ2zHEd-Kpdvno4E6Xo5ELsjJjO3V3oOz181lvMn9y0Q-2GozqLLDv2EIc--cr1mz3tOQruNetQfObQlCPtoZUZX8kgOJqgwqSUCdeeMbkSiHN8x5ggvCGSbXZIJ_I1eyZO-6aePPF0neEjwnU_wvtzUhy7t9uVtWAM9fzsyEF1fgPHcalwPKOXb3SRAHv5CzLJGR8lPum4_slYPEWjl7pFmj_fmmAQfLcYi6uoY9ZwiaspLzYjHC9uNvbuuiGIkqkljVa-TM2EX7Eo6iLELfkqwVbn3atL-GRCmYlk1MeZRrhP4cskZRddreXCSwlrkgqg8j-vWrJNbtZsirgTtj-jQWxSh58wDJC8OIUHh1eT_2KkCgVNGLMWl9Yz5WoZh0vEyzsbvtIhH3TxasJnXftOg_kUEfhBuqy_a7PsF8ME9scH7gXHTOGtkAJ0KyMR6Qdj_lH5_ql9WJ-r4du4LYbqgIugh0YMfYHPWublSQXcy5-FEbZtY9JumwPc62LYW7QDm59IcyeH9dL_5X2VKVq1VKcbd7WQHp42zf9ykGREmTJL_rrI46Qn19t62fpW2NdXfuzJoZonuszjEUas1kXEudhnVOLglAth7MnCeeuWHsJrPc2kM3AzAqpl59OwROapIKyirYj-UUntjE69sJCqiqKKRlrvpiPnHdV1PR3eyKtPjTPX6Uy3kStNoMl-r8fsn_08NtbjYQvp4MSPnGSCKGPjho_R70fsyx4Aeeon6Ww-LKFAPMCVS3_9flK9i1zY7QqnSXXb0JgvlmWQYsVe65bk_QhtMgEZFX994gNxYQAfxaOOf5-LUJJacQNuK9Yr9E45C4WJ4oG3WFm8u6LMx_hhKjaMjZHv3Wrf1lZwqIlGgBkKWLnRG2yjHTA_TKhKV2Qi9pAtXDqnoW9A8ADZQm70lqmtkhKwiDrSQdAZE1nm_GuMdHtRaUlXnU3HpKbdgLeYYiYB1wkXHNG-8oraFKpg2uUW5SzZWmROnUEg1KUjPjdDvwclaSZzSsigzwtoZVYnmPXL_cxML07dm_MaCLOodZMY8---PG94JoSjJuSQyfzul6rr8kz6IC9xm_l-4lROZvEyCZHmhzikDIlEA4j1fXju1TP_1625Q39wPpflsOLk9lO_jRlWBekReNouFehlE-Ll8s5vlKA6XnufqVXfqYcRjlpWlwmuA9C7ZKhbr6I4ucr2BLOCrbOWvsc_25CY7Rtbzb3kZRmPT7XXtjAAAAAQAFWC41MDkAAAUOMIIFCjCCA_KgAwIBAgIEFjGMYDANBgkqhkiG9w0BAQsFADCBhTEnMCUGA1UEAwweT1dBU1AgWmVkIEF0dGFjayBQcm94eSBSb290IENBMRkwFwYDVQQHDBBiYzdkZDE2M2JmMjM1MTE5MRYwFAYDVQQKDA1PV0FTUCBSb290IENBMRowGAYDVQQLDBFPV0FTUCBaQVAgUm9vdCBDQTELMAkGA1UEBhMCeHgwHhcNMTYwMzIzMTIyMTU0WhcNMTcwMzIzMTIyMTU0WjCBhTEnMCUGA1UEAwweT1dBU1AgWmVkIEF0dGFjayBQcm94eSBSb290IENBMRkwFwYDVQQHDBBiYzdkZDE2M2JmMjM1MTE5MRYwFAYDVQQKDA1PV0FTUCBSb290IENBMRowGAYDVQQLDBFPV0FTUCBaQVAgUm9vdCBDQTELMAkGA1UEBhMCeHgwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCcih_IjasOZtxhul7faVwaxDanSBowwgPv-GXcrGfHMn6r8B_XnB4sDZlFcmvaBj7CVznHMBp3LB-jx2wvtN8toYsdp9Xrys4OQlTCbQfaJ-dgQOzbPrziKZDvia8XPAjIWAqqW1f29eVM-qA67fPHG3xoOSAWiquCBPdtnkAZaTSFZ5Be_lPW4wxCKSpINwHC3Bmuvw0sf7Sw6VSKqx-VIzyNxVuFVkTIZZT2OYW92C4czd8Uws7GesYUZqCvExQW0NdlijhwqRFb57GxHIldfWeG036BVWG7_1iZLMxvR_3om42zrqgcSh6Psau6HOasLu8WTgpmjAlgs4XiTeGzAgMBAAGjggF-MIIBejCCATMGA1UdDgSCASoEggEmMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnIofyI2rDmbcYbpe32lcGsQ2p0gaMMID7_hl3KxnxzJ-q_Af15weLA2ZRXJr2gY-wlc5xzAadywfo8dsL7TfLaGLHafV68rODkJUwm0H2ifnYEDs2z684imQ74mvFzwIyFgKqltX9vXlTPqgOu3zxxt8aDkgFoqrggT3bZ5AGWk0hWeQXv5T1uMMQikqSDcBwtwZrr8NLH-0sOlUiqsflSM8jcVbhVZEyGWU9jmFvdguHM3fFMLOxnrGFGagrxMUFtDXZYo4cKkRW-exsRyJXX1nhtN-gVVhu_9YmSzMb0f96JuNs66oHEoej7GruhzmrC7vFk4KZowJYLOF4k3hswIDAQABMA8GA1UdEwEB_wQFMAMBAf8wCwYDVR0PBAQDAgG2MCMGA1UdJQQcMBoGCCsGAQUFBwMBBggrBgEFBQcDAgYEVR0lADANBgkqhkiG9w0BAQsFAAOCAQEAQwctCGYTmrTVlKoFnIKeN99v7K46wQY5QF98IHXdoKSYu5DjJOimcY_D8e331hsjqbxhee0zc8Rok6qL7vSfpZ6sWcom2IjeaZ0-8CTpStyEpyQiNo9Y-5HlOrXsI-IMYNRK-DSQpIxrMob-jBHcdus5pMfjCa8_b1zcLk1bCnB-uYeRsmxfVsCIZA3Rl_L7nE7kpPc6TmdJbQAk1TfTR4tUTdLcP4J2YeOJL2r7MKh3IVHhN2V-_gCda7EY8F7zCAefYSp6NsvqEhiEaLtDlRqFE1pVTP2fXvNsIi0Cu1uIKR2Zzqmj8WGHTNdqH9Kn-Buld7xoYjT2-YBFQrlwEmBn7-zG_F6dE7nKSNYN4R61rrL3";
		CustomProxyListener.filters = new String[] { "a" };
		//univision.sc.omtrdc
		try {
			ProxyRunner.main();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	
	public JSONObject getUrlVars(String url) {
		JSONObject UrlVars = new JSONObject();
		
		url = url.substring(url.indexOf("?") + 1);
	    String[] allParameter = url.split("&");
	    
	    for (int i = 0; i < allParameter.length; i++) {
	       String[] parameter = allParameter[i].split("=",2);
	       if(!(parameter.length == 1)){
	    	   try {
		    	   UrlVars.put(parameter[0],parameter[1]);
		    	   Steps.add("And the '"+parameter[0]+"' parameter should equal '"+parameter[1]+"'");
		       } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		       } 
	       }
		    
	    }
	    return UrlVars;
	}
	
	public void decodedURL() {
		String isoData = "";
		String decodedURL = "";
		
		int counter = 0;
			while (CustomProxyListener.allRequests.get(CustomProxyListener.allRequests.size() - 1).getRequestHeader().getURI().toString().length() < 500) {
				try {
					System.out.println(CustomProxyListener.allRequests.get(CustomProxyListener.allRequests.size() - 1).getRequestHeader().getURI().toString());
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				counter++;
				if(counter == 10){
					break;
				}
				
			}
			
			
			
			
			
		isoData = new String(CustomProxyListener.allRequests.get(CustomProxyListener.allRequests.size() - 1).getRequestHeader().getURI().toString());
		try {
			decodedURL = java.net.URLDecoder.decode(isoData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calls.clear();
		Calls.add(getUrlVars(decodedURL));
	}
	
	

}
