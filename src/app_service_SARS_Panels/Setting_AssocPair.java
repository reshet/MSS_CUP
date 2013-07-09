package app_service_SARS_Panels;

public class  Setting_AssocPair<LeftType,RightType> {
	private LeftType left;
	private RightType right;
	public Setting_AssocPair(LeftType lft,RightType rht)
	{
		left = lft;
		right = rht;
	}
	public LeftType getLeft() {
		return left;
	}
	public RightType getRight() {
		return right;
	}
	public void setLeft(LeftType left) {
		this.left = left;
	}
	public void setRight(RightType right) {
		this.right = right;
	}
}
