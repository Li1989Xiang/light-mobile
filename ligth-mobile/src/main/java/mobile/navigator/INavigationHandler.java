package mobile.navigator;

public interface INavigationHandler {

	void enter(ViewNode node);

	void back(ViewNode node);
	
	void reset();
}
