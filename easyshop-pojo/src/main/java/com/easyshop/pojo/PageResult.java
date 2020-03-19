package com.easyshop.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * @author 陶毅
 *
 */
public class PageResult<T> implements Serializable {

	private static final long serialVersionUID = 8553961652583931998L;
	
	private int totalCount;// ����
	private int pageIndex;// ҳ��
	private int pageSize;// ÿһҳ������
	private int totalPage;// �ܹ���ҳ��
	private List<T> list;// ��Ų�ѯ����
	private Object bean;// ���Ե�����ʵ����
	private int startPage;// ��ʼ��ҳ��,Ҫ��ʼ��ҳ��֮ǰ������ĸ�ҳ�棬֮����������ҳ��
	private int endPage;// ������ҳ��

	private List<Integer> numbers=new ArrayList<Integer>(); //Ҫѭ����ҳ��

	public PageResult() {
		super();
	}

	public PageResult(int totalCount, int pageIndex, int pageSize, List<T> list, Object bean) {
		this.totalCount = totalCount;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalPage = totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize + 1);
		this.list = list;
		this.bean = bean;
		
		
		if (totalPage <= 10) {
			this.startPage = 1;
			this.endPage = totalPage;
		} else {
			this.startPage = pageIndex - 4;
			this.endPage = pageIndex + 5;
			if (startPage < 1) {
				this.startPage = 1;
				this.endPage = 10;
			} else if (endPage > totalPage) {
				this.endPage = totalPage;
				this.startPage = totalPage - 9;
			}
		}
		//Ҫѭ����ҳ��  1-10
		for (int i = startPage; i <= endPage; i++) {
			numbers.add(i);
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

}
