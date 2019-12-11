import java.util.ArrayList;

public class Screen {

	private int crowd;
	private ArrayList<Request> ads;
	private int capacity = 2;
	private int size = 0;

	public Screen() {
		this.crowd = (int) (Math.random() * 101 + 1);
		ads = new ArrayList<>(capacity);
	}

	public void addAd(Request ad) {
		ads.add(ad);
		size++;
	}

	public boolean isScreenFull() {
		return size == capacity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getCrowd() {
		return crowd;
	}

	public void setCrowd(int crowd) {
		this.crowd = crowd;
	}

	public ArrayList<Request> getAds() {
		return ads;
	}
}
