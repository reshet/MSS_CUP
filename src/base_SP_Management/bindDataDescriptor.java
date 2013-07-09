package base_SP_Management;

import java.util.Set;

public class bindDataDescriptor {
	private Set<String> set;
	private String fileName;
	public bindDataDescriptor(String fname,Set<String> set)
	{
		this.set = set;
		this.fileName = fname;
	}
	public Set<String> getSet() {
		return set;
	}
	public void setSet(Set<String> set) {
		this.set = set;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
