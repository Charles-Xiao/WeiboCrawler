package com.xy.weibocrawler.db;

public class Weibo {
	/** 微博id */
	private String mId;
	/** 微博用户昵称 */
	private String mUserName;
	/** 微博用户是否为大V */
	private boolean mIsUserVip;
	/** 微博内容 */
	private String mContent;
	/** 微博发布时间 */
	private String mTime;
	/** 微博果蔬类关键词数目 */
	private int mFruitNum;
	/** 微博酒类关键词数目 */
	private int mWineNum;
	/** 微博乳制品类关键词数目 */
	private int mMilkNum;
	/** 微博食品安全关键词数目 */
	private int mSafetyNum;
	/** 微博所属种类：fruit/wine/milk */
	private String mCategory;

	public Weibo(String mId, String mUserName, boolean mIsUserVip,
			String mContent, String mTime, int mFruitNum, int mWineNum,
			int mMilkNum, int mSafetyNum, String mCategory) {
		this.mId = mId;
		this.mUserName = mUserName;
		this.mIsUserVip = mIsUserVip;
		this.mContent = mContent;
		this.mTime = mTime;
		this.mFruitNum = mFruitNum;
		this.mWineNum = mWineNum;
		this.mMilkNum = mMilkNum;
		this.mSafetyNum = mSafetyNum;
		this.mCategory = mCategory;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmUserName() {
		return mUserName;
	}

	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public boolean ismIsUserVip() {
		return mIsUserVip;
	}

	public void setmIsUserVip(boolean mIsUserVip) {
		this.mIsUserVip = mIsUserVip;
	}

	public String getmContent() {
		return mContent;
	}

	public void setmContent(String mContent) {
		this.mContent = mContent;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public int getmFruitNum() {
		return mFruitNum;
	}

	public void setmFruitNum(int mFruitNum) {
		this.mFruitNum = mFruitNum;
	}

	public int getmWineNum() {
		return mWineNum;
	}

	public void setmWineNum(int mWineNum) {
		this.mWineNum = mWineNum;
	}

	public int getmMilkNum() {
		return mMilkNum;
	}

	public void setmMilkNum(int mMilkNum) {
		this.mMilkNum = mMilkNum;
	}

	public int getmSafetyNum() {
		return mSafetyNum;
	}

	public void setmSafetyNum(int mSafetyNum) {
		this.mSafetyNum = mSafetyNum;
	}

	public String getmCategory() {
		return mCategory;
	}

	public void setmCategory(String mCategory) {
		this.mCategory = mCategory;
	}

	@Override
	public String toString() {
		return "Weibo [mId=" + mId + ", mUserName=" + mUserName
				+ ", mIsUserVip=" + mIsUserVip + ", mContent=" + mContent
				+ ", mTime=" + mTime + ", mFruitNum=" + mFruitNum
				+ ", mWineNum=" + mWineNum + ", mMilkNum=" + mMilkNum
				+ ", mSafetyNum=" + mSafetyNum + ", mCategory=" + mCategory
				+ "]";
	}

}
