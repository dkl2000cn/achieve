package com.easygoal.achieve;

public class UpdateInfo {
	private String version; // 版本号
	private String versionName;
	private String url; // 新版本存放url路径
	private String description; // 更新说明信息，比如新增什么功能特性等

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}