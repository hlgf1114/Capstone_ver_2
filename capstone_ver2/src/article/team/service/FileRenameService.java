package article.team.service;

import java.io.*;
import java.sql.Timestamp;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class FileRenameService implements FileRenamePolicy {

	private String strCode = ""; //분리자 code명

/**
* 기본 생성자
*/
	public FileRenameService()
	{
		this( new String(""));
	}

/**
* 생성자
* @param v : 분리자 (String 형) 
*/
	public FileRenameService(String v)
	{
		if ( v.length() < 1)
		{
			this.strCode = v;
		}else{
			this.strCode = v + "_";
		}

	}	

	public File rename(File f) {

		if (!f.exists()) {	
			return f;
		}

		String fileSystemName = ""; // 실제 시스템에 저장될 이름
		String name = null; // 시스템에 저장되어 있는 이름
		String ext = null; // 확장자
		String body = null; // 확장자 제외한 파일이름
		String tmpStr = "";
		Timestamp ts = null; 

		name = f.getName();

		int dot = name.lastIndexOf(".");
		if (dot != -1) 
		{
			ext = name.substring(dot); // includes "."
			body = name.substring(0, dot);
		}
		else 
		{
			body = name;
			ext = "";
		}

		ts = new java.sql.Timestamp(System.currentTimeMillis()) ;
		tmpStr = ts.toString();
		tmpStr = tmpStr. substring( 0, tmpStr.indexOf(" ") ).substring(0,4) +tmpStr.substring(0, tmpStr.indexOf(" ") ).substring(5,7)
				+tmpStr.substring( 0, tmpStr.indexOf(" ") ).substring(8)
				+ tmpStr.substring( (tmpStr.indexOf(" ")+1), 19 ).substring(0, 2)+ tmpStr.substring( (tmpStr.indexOf(" ")+1), 19 ).substring(3, 5)
				+ tmpStr.substring( (tmpStr.indexOf(" ")+1), 19 ).substring(6);

// 분리 코드명 + 이전파일이름 + "_"+ 등록 년월일 시분초 + 확장자
		fileSystemName = this.strCode + body + "_" + tmpStr + "."+ ext;
		f = new File(f.getParent(), fileSystemName);

		return f;
	}
}