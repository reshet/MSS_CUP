package app_service_quiz;

public interface ServerDispatcher {
	public void add(Object [] params);
	public void delete(Object [] params);
	public void edit(Object [] params);
	public void refresh(Object [] params);
	public void refreshHeirGroups(Object[] params);
	public void refreshGroupMembers(Object[] params);
}
