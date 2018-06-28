package com.jerry.o2o.dto;

import java.io.InputStream;

import lombok.Data;

@Data
public class ImageHolder {

	private InputStream image;
	private String imageName;

	public ImageHolder(InputStream image, String imageName) {
		this.image = image;
		this.imageName = imageName;
	}
}
