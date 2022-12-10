package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 検索結果アイテムモデルクラス
 */
public class ResultItemModel implements Serializable {

	private int id;
	private int result_id;
	private String comment;
	private int isDeleted;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	//ResultServlet内の処理でpublicにしないとエラーになる
	public int result1Prob;
	public int result1NameId;
	public int result2Prob;
	public int result2NameId;
	public int result3Prob;
	public int result3NameId;
	public int result4Prob;
	public int result4NameId;
	public int result5Prob;
	public int result5NameId;
	public int result6Prob;
	public int result6NameId;
	public int result7Prob;
	public int result7NameId;
	public int result8Prob;
	public int result8NameId;
	public int result9Prob;
	public int result9NameId;
	public int result10Prob;
	public int result10NameId;

	private UserModel userModel;
	private DiseaseItemModel diseaseItemModel;

	public long getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResultId() {
		return this.result_id;
	}

	public void setResultId(int result_id) {
		this.result_id = result_id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public int getResult1Prob() {
		return this.result1Prob;
	}

	public void setResult1Prob(int result1Prob) {
		this.result1Prob = result1Prob;
	}

	public int getResult1NameId() {
		return this.result1NameId;
	}

	public void setResult1NameId(int result1NameId) {
		this.result1NameId = result1NameId;
	}

	public int getResult2Prob() {
		return this.result2Prob;
	}

	public void setResult2Prob(int result2Prob) {
		this.result2Prob = result2Prob;
	}

	public int getResult2NameId() {
		return this.result2NameId;
	}

	public void setResult2NameId(int result2NameId) {
		this.result2NameId = result2NameId;
	}

	public int getResult3Prob() {
		return this.result3Prob;
	}

	public void setResult3Prob(int result3Prob) {
		this.result3Prob = result3Prob;
	}

	public int getResult3NameId() {
		return this.result3NameId;
	}

	public void setResult3NameId(int result3NameId) {
		this.result3NameId = result3NameId;
	}

	public int getResult4Prob() {
		return this.result4Prob;
	}

	public void setResult4Prob(int result4Prob) {
		this.result4Prob = result4Prob;
	}

	public int getResult4NameId() {
		return this.result4NameId;
	}

	public void setResult4NameId(int result4NameId) {
		this.result4NameId = result4NameId;
	}

	public int getResult5Prob() {
		return this.result5Prob;
	}

	public void setResult5Prob(int result5Prob) {
		this.result5Prob = result5Prob;
	}

	public int getResult5NameId() {
		return this.result5NameId;
	}

	public void setResult5NameId(int result5NameId) {
		this.result5NameId = result5NameId;
	}

	public int getResult6Prob() {
		return this.result6Prob;
	}

	public void setResult6Prob(int result6Prob) {
		this.result6Prob = result6Prob;
	}

	public int getResult6NameId() {
		return this.result6NameId;
	}

	public void setResult6NameId(int result6NameId) {
		this.result6NameId = result6NameId;
	}

	public int getResult7Prob() {
		return this.result7Prob;
	}

	public void setResult7Prob(int result7Prob) {
		this.result7Prob = result7Prob;
	}

	public int getResult7NameId() {
		return this.result7NameId;
	}

	public void setResult7NameId(int result7NameId) {
		this.result7NameId = result7NameId;
	}

	public int getResult8Prob() {
		return this.result8Prob;
	}

	public void setResult8Prob(int result8Prob) {
		this.result8Prob = result8Prob;
	}

	public int getResult8NameId() {
		return this.result8NameId;
	}

	public void setResult8NameId(int result8NameId) {
		this.result8NameId = result8NameId;
	}

	public int getResult9Prob() {
		return this.result9Prob;
	}

	public void setResult9Prob(int result9Prob) {
		this.result9Prob = result9Prob;
	}

	public int getResult9NameId() {
		return this.result9NameId;
	}

	public void setResult9NameId(int result9NameId) {
		this.result9NameId = result9NameId;
	}

	public int getResult10Prob() {
		return this.result10Prob;
	}

	public void setResult10Prob(int result10Prob) {
		this.result10Prob = result10Prob;
	}

	public int getResult10NameId() {
		return this.result10NameId;
	}

	public void setResult10NameId(int result10NameId) {
		this.result10NameId = result10NameId;
	}

	public UserModel getUserModel() {
		return this.userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public DiseaseItemModel getDiseaseItemModel() {
		return this.diseaseItemModel;
	}

	public void setDiseaseItemModel(DiseaseItemModel diseaseItemModel) {
		this.diseaseItemModel = diseaseItemModel;
	}
}
