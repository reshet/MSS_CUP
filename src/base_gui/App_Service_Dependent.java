package base_gui;

public interface App_Service_Dependent {
	/*
	 * This interface used for post-initializing service when parameters from others 
	 * needed.
	 */
	public void requireInitialization(Object[] params);
}
