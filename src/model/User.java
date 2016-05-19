package model;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "user")
public class User {
	@XmlElement(name = "id")
	private long id;
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "email")
	private String email;
	@XmlElement(name = "password")
	private String password;
	@XmlElement(name = "img")
	private String img;

	public User(){
		img=null;
	}
	
	public User(int id){
		this.id=id;
		img=null;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean validate(Map<String, String> messages, String verpass) {
		if (name.trim().isEmpty() || name == null) {
			messages.put("error", "El nombre no puede estar vacío");
		} else if (!name.trim().matches("[A-Za-záéíóúÁÉÍÓÚñÑ][A-Za-záéíóúÁÉÍÓÚñÑ0-9 ]*")) {
			messages.put("error", "El nombre " + name.trim() + " no es válido");
		} else if (email.trim().isEmpty() || email == null) {
			messages.put("error", "El correo no puede estar vacío");
		} else if (!email.matches("[A-Za-z0-9-_.]{2,}[@][A-Za-z]{2,}[.][A-Za-z]{2,3}")) {
			messages.put("error", "El correo " + email.trim() + " no es válido");
		} else if (password.length() < 8 && (password == null || password.trim().isEmpty())) {
			messages.put("error", "Contraseña demasiado corta");
		} else if (!password.equals(verpass)) {
			messages.put("error", "Las contraseñas no coinciden");
		} else if (!password.trim().matches("^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z!-_]{8,}$")) {
			messages.put("error", "La contraseña debe contener letras y números");
		}
		if (messages.isEmpty())
			return true;
		else
			return false;
	}

	public boolean validate(Map<String, String> messages) {
		String regexIMG = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|/](.jpg|.gif|.jpeg|.png)";
		if (img == null || img.trim().isEmpty()) {
			messages.put("error", "Debes elegir una imagen");
		} else if (!img.matches(regexIMG) && !img.startsWith("/Trolleame")) {
			messages.put("error", "La URL de la imagen no es válida");
		}
		if (messages.isEmpty())
			return true;
		else
			return false;
	}
}
