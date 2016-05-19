package model;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import resources.adapter.DateAdapter;
import resources.adapter.TimeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "news")
public class News {
	@XmlElement(name = "id")
	private long id;
	
	@XmlElement(name = "owner")
	private long owner;
	
	@XmlElement(name = "dateStamp")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dateStamp;
	
	@XmlElement(name = "timeStamp")
	@XmlJavaTypeAdapter(TimeAdapter.class)
	private Time timeStamp;
	
	@XmlElement(name = "title")
	private String title;
	
	@XmlElement(name = "text")
	private String text;
	
	@XmlElement(name = "url")
	private String url;
	
	@XmlElement(name = "category")
	private String category;
	
	@XmlElement(name = "likes")
	private int likes;
	
	@XmlElement(name = "hits")
	private int hits;
	
	@XmlElement(name = "img")
	private String img;
	public News(){
		Calendar calendar = new GregorianCalendar();
		dateStamp = calendar.getTime();
		timeStamp = new Time(calendar.getTimeInMillis());
	
	}
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	public Time getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Time timeStamp) {
		this.timeStamp = timeStamp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public boolean validate(Map<String, String> messages) {
		String regexURL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		String regexIMG = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|/](.jpg|.gif|.jpeg|.png)";
		if (title.trim().isEmpty() || title == null) {
			messages.put("error", "El título no puede estar vacío");
		}	else if(title.length()>120){
			messages.put("error", "El título no puede exceder 120 caracteres");
		} else if (!title.trim().matches("[A-Za-záéíóúÁÉÍÓÚñÑ0-9., ()-:]+")) {
			messages.put("error", "El título " + title.trim() + " no es válido");
		} else if (url.trim().isEmpty() || url == null) {
			messages.put("error", "La url no puede estar vacía");
		}else if(!url.matches(regexURL)){
			messages.put("error", "La url " + url.trim() + " no es válido");
		}else if (text.trim().isEmpty() || text == null) {
			messages.put("error", "El texto no puede estar vacío");
		}else if(category==null){
			messages.put("error", "Elige una categoría");
		}else if(!category.equals("actualidad") && category.equals("ocio") && category.equals("deportes")){
			messages.put("error", "La categoría introducida es desconocida");
		}else if(img == null || img.trim().isEmpty()){
			messages.put("error", "Debes elegir una imagen");
		}else if(!img.matches(regexIMG) && !img.startsWith("/Trolleame")){
			messages.put("error", "La URL de la imagen no es válida");
		}
		if (messages.isEmpty())
			return true;
		else
			return false;
	}
}
