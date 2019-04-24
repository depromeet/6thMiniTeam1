package com.conveniencestore.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

	@Column(name = "category")
	private String category;

	@Column(name = "event")
	private Integer event;

	@Column(name = "imageurl")
	private String imageUrl;

	@Column(name = "createtime")
	private LocalDate createTime;
}
