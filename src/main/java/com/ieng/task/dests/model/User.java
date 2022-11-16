package com.ieng.task.dests.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="username", length = 40)
	String username;
	
	@Column(name="email", length = 45)
	String email;
	
	@JsonIgnore
	@Column(name="password")
	String password;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	Set<Destination> favouriteDestinations;
	
}
