package com.interland.testcase.mcqquestion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name="ImageFiles")

public class FileEntity {
	
	@EmbeddedId
	private McqEmbedded primarykey;
	
	@Column(name="imagefile")
    private byte[] file;
	
	@Column(name="filename")
	private String fileName;

	public McqEmbedded getPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(McqEmbedded primarykey) {
		this.primarykey = primarykey;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileEntity(McqEmbedded primarykey, byte[] file, String fileName) {
		super();
		this.primarykey = primarykey;
		this.file = file;
		this.fileName = fileName;
	}

	public FileEntity() {
		super();
	}

	
}
