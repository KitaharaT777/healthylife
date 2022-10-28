package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 検索履歴アイテムモデルクラス
 */
public class SearchItemModel implements Serializable {

	private long id;
	private int userId;
	private int mark;
	private Date searchDate;
	private String word;
	private int isDeleted;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	private int result1Prob;
	private int result1NameId;
	private int result2Prob;
	private int result2NameId;
	private int result3Prob;
	private int result3NameId;
	
	private UserModel userModel;

	public long getId() {return this.id;}
	public void setId(long id) {this.id = id;}

	public int getUserId() {return this.userId;}
	public void setUserId(int userId) {this.userId = userId;}

	public int getMark() {return this.mark;}
	public void setMark(int mark) {this.mark = mark;}
	
	public Date getSearchDate() {return this.searchDate;}
	public void setSearchDate(Date searchDate) {this.searchDate = searchDate;}
	
	public String getWord() {return this.word;}
	public void setWord(String word) {this.word = word;}

	public int getIsDeleted() {return this.isDeleted;}
	public void setIsDeleted(int isDeleted) {this.isDeleted = isDeleted;}

	public Timestamp getCreatedAt() {return this.createdAt;}
	public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}
	public Timestamp getUpdatedAt() {return this.updatedAt;}
	public void setUpdatedAt(Timestamp updatedAt) {this.updatedAt = updatedAt;}

	public int getResult1Prob() {return this.result1Prob;}
	public void setResult1Prob(int result1Prob) {this.result1Prob = result1Prob;}
	public int getResult1NameId() {return this.result1NameId;}
	public void setResult1NameId(int result1NameId) {this.result1NameId = result1NameId;}
	
	public int getResult2Prob() {return this.result2Prob;}
	public void setResult2Prob(int result2Prob) {this.result2Prob = result2Prob;}
	public int getResult2NameId() {return this.result2NameId;}
	public void setResult2NameId(int result2NameId) {this.result2NameId = result2NameId;}
	
	public int getResult3Prob() {return this.result3Prob;}
	public void setResult3Prob(int result3Prob) {this.result3Prob = result3Prob;}
	public int getResult3NameId() {return this.result3NameId;}
	public void setResult3NameId(int result3NameId) {this.result3NameId = result3NameId;}
	
	public UserModel getUserModel() {return this.userModel;}
	public void setUserModel(UserModel userModel) {this.userModel = userModel;}
}
