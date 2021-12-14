package com.project.couponsys.entities;

import javax.persistence.Entity;


public class Categories {

		private int categoriesid;
		private int categoriesname;
		public Categories(int categoriesid, int categoriesname) {
			this.categoriesid = categoriesid;
			this.categoriesname = categoriesname;
		}
		public int getcategoriesid() {
			return categoriesid;
		}
		public void setcategoriesid(int categoriesid) {
			this.categoriesid = categoriesid;
		}
		public int getcategoriesname() {
			return categoriesname;
		}
		public void setcategoriesname(int categoriesname) {
			this.categoriesname = categoriesname;
		}
		
}