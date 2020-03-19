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
@TableName("tb_content")
public class Content extends Model<Content> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 内容类目ID
	 */
	private Long categoryId;
	/**
	 * 内容标题
	 */
	private String title;
	/**
	 * 链接
	 */
	private String url;
	/**
	 * 图片绝对路径
	 */
	private String pic;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 排序
	 */
	private Integer sortOrder;
	private Integer del;

	// 扩展字段--此字段并非是数据库中的字段   (exist=false) 表示不在数据库中
	@TableField(exist=false)
	private String categoryName; // 扩展字段

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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

	@Override
	public String toString() {
		return "Content{" + ", id=" + id + ", categoryId=" + categoryId + ", title=" + title + ", url=" + url + ", pic="
				+ pic + ", status=" + status + ", sortOrder=" + sortOrder + ", del=" + del + "}";
	}
}
