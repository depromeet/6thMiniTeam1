package com.conveniencestore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Setter
@Getter
@Entity
@ToString
@Table(name = "product")
public class Product implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(name = "name")
	private String name;

	@Column(name = "storename")
	private String storeName;

	@Column(name = "price")
	private int price;

	@Column(name = "event")
	private String event;

	@Column(name = "imageurl")
	private String imageUrl;

	@Column(name = "createtime")
	private Date createTime;
}
