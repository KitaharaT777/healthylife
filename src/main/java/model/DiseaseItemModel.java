package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 病名アイテムモデルクラス
 */
public class DiseaseItemModel implements Serializable {

	//ExportServlet内の処理でpublicにしないとエラーになるので、privateからpublicに変更
	public int id;
	public int nameId;
	public String nameOfDisease;
	public String info;
	public String link;
	public int isDeleted;
	public Timestamp createdAt;
	public Timestamp updatedAt;

	private UserModel userModel;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNameId() {
		return this.nameId;
	}

	public void setNameId(int nameId) {
		this.nameId = nameId;
	}

	public String getNameOfDisease() {
		return this.nameOfDisease;
	}

	public void setNameOfDisease(String nameOfDisease) {
		this.nameOfDisease = nameOfDisease;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserModel getUserModel() {
		return this.userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
}
