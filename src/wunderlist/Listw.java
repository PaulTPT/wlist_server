package wunderlist;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlRootElement(name="list")
public class Listw {
	private int id=0;
	private String title="null";


	public Listw() {

	}
	
	

	public Listw(int id, String title) {
		super();
		this.id = id;
		this.title = title;
	}


	public Listw(String title) {
		this.title = title;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}