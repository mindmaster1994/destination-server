package com.ieng.task.dests.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Entity
@Data
@Table(name = "destination")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Destination {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="title")
	String title;
	
	@Column(name="locationName")
	String locationName;
	
	@Column(name="image")
	String image;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	Instant createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	Instant updatedAt;
	
}
