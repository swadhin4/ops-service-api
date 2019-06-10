package com.ops.api.util;

public class OracleTestConnection {

	public static void main(String[] args) {
		try{  
			String jobName1 = "SDI_SBI_BDW_AMPS_1000_EXTRACTION_SCHEDULE_ID_4";
			String jobName2 = "SDI_SBI_ROBOTM_DEMANDLINE_SUPPLIER_DATA_2000_INGESTION_SCHEDULED_ID_1";
			String jobName3 = "EP_SUPPLIER_OTD_SEPM";
			String jobName4 = "SDI_SBI_SCCOST2_700_SCHEDULE_ID_3";
			
			if(jobName4.startsWith("SDI_SBI_")){
				String result1 = jobName4.substring(jobName4.indexOf("SDI_SBI_")+8);
				System.out.println(result1);
				if(result1.contains("EXTRACTION")){
					String result2=result1.substring(0,result1.indexOf("_EXTRACTION"));
					System.out.println(result2);
					System.out.println(result2.substring(0, result2.lastIndexOf("_")));
				}
				else if(result1.contains("INGESTION")){
					String result2=result1.substring(0,result1.indexOf("_INGESTION"));
					System.out.println(result2);
					System.out.println(result2.substring(0, result2.lastIndexOf("_")));
				}
				else if(result1.contains("SCHEDULE")){
					String result2=result1.substring(0,result1.indexOf("_SCHEDULE"));
					System.out.println(result2);
					System.out.println(result2.substring(0, result2.lastIndexOf("_")));
				}
				
			 }
			  
			}catch(Exception e){ e.printStackTrace();}  
			  
		  }  

}
