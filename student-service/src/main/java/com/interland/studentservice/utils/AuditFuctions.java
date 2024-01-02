/****************************************************************************
 *                              COPYRIGHT NOTICE
*
*                      Copyright(@2006) by Interland Technology Services PVT. LTD **
*
*      This program is used to monitor the stream control and Stop/Start
*      the streams. The program and related materials are confidential and
*      proprietary of Interland Technology Services PVT. LTD and no part of these materials
*      should be reproduced, published in any forms without the written
*      approval of INTERLAND
*
** Project Name         : iPSH
** Program description  : AuditFunctions
** Version No           : 1.0.0
** Author               : Adarsh
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.interland.studentservice.utils;

public enum AuditFuctions {
	CREATE("C"), MODIFY("M"), DELETE("D"), VERIFY("V");

	public final String function;

	private AuditFuctions(String fucntion) {
		this.function = fucntion;
	}

	public String getFunction() {
		return function;
	}

}
