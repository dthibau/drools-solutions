package org.formation.helper;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;

public class ExcelCompilateur {

	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		
		String fileName="/org/formation/dtables/assurance.xls";
		if ( args.length  > 0 )
			fileName = args[0];
		SpreadsheetCompiler spc = new SpreadsheetCompiler();
		String drl = spc.compile(fileName, InputType.XLS);
		System.out.println("DRL\n"+drl);
	}

}
