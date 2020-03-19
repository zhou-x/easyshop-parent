package com.easyshop.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陶毅
 */
@TableName("tb_brand")
public class Brand extends Model<Brand> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 品牌名称
	 */
	private String name;
	/**
	 * 品牌首字母
	 */
	private String firstChar;
	private Integer del;

	@TableField(exist=false)  //表示这个字段和数据库 没有半毛钱关系 额外加
	private boolean flag; // 是否被选中 额外加的状态

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstChar() {
		return firstChar;
	}

	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "Brand{" + ", id=" + id + ", name=" + name + ", firstChar=" + firstChar + ", del=" + del + "}";
	}
}
