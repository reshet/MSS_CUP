package base_data_exchanger;

import java.io.File;
import java.io.IOException;

import com.pmstation.spss.SPSSWriter;

public class SPSS_Hack extends SPSSWriter{

	public SPSS_Hack(File arg0, String arg1) throws NullPointerException,
			IOException {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void addDataSection() throws IOException {
		// TODO Auto-generated method stub
		//super.addFinishSection();
	}
	@Override
	public void addFinishSection() throws IOException {
		// TODO Auto-generated method stub
		//super.addFinishSection();
	}
}
