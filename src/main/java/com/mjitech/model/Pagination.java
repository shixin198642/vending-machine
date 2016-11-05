package com.mjitech.model;

import java.util.ArrayList;
import java.util.List;

import com.mjitech.constant.RequestConstants;

public class Pagination {

	private int total;
	private String baseUrl;
	private int begin;
	private int perpage;

	private int totalPage;
	private PaginationPage firstPage;
	private PaginationPage lastPage;
	private PaginationPage previousPage;
	private PaginationPage nextPage;
	private List<PaginationPage> previousPages = new ArrayList<PaginationPage>();
	private List<PaginationPage> nextPages = new ArrayList<PaginationPage>();
	private int currentPage = 1;
	private boolean showPreviousDot = false;
	private boolean showNextDot = false;

	private String buildUrl(String url, String[] paramNames,
			Object[] paramValues) {
		StringBuilder sb = new StringBuilder(url);
		if (url.indexOf("?") == -1) {
			sb.append("?");
		}
		for (int i = 0; i < paramNames.length; i++) {
			if (paramValues.length > i) {
				sb.append("&").append(paramNames[i]).append("=")
						.append(paramValues[i]);
			}
		}
		return sb.toString();
	}

	public Pagination(String baseUrl, int total, int begin, int perpage) {
		if (baseUrl.indexOf("?") == -1) {

			this.baseUrl = baseUrl + "?";
		}else{
			this.baseUrl = baseUrl;
		}
		this.total = total;
		this.begin = begin;
		this.perpage = perpage;
		if (this.perpage > 0) {
			this.totalPage = this.total / this.perpage;
			if (this.total % this.perpage > 0) {
				this.totalPage += 1;
			}
			this.currentPage = this.begin / this.perpage + 1;
		}

		if (currentPage == 1) {
			this.previousPage = null;
			this.firstPage = null;
		} else {
			this.previousPage = new PaginationPage();
			this.previousPage.setUrl(this.buildUrl(this.baseUrl, new String[] {
					RequestConstants.PARAMETER_NAME_BEGIN,
					RequestConstants.PARAMETER_NAME_PERPAGE }, new Object[] {
					((this.currentPage - 2) * this.perpage), this.perpage }));
			this.previousPage.setPage(this.currentPage - 1);
			this.firstPage = new PaginationPage();
			this.firstPage.setUrl(this.buildUrl(this.baseUrl, new String[] {
					RequestConstants.PARAMETER_NAME_BEGIN,
					RequestConstants.PARAMETER_NAME_PERPAGE }, new Object[] {
					0, this.perpage }));
			this.firstPage.setPage(1);
		}
		if (currentPage == totalPage) {
			this.nextPage = null;
			this.lastPage = null;
		} else {
			this.nextPage = new PaginationPage();
			this.nextPage.setUrl(this.buildUrl(this.baseUrl, new String[] {
					RequestConstants.PARAMETER_NAME_BEGIN,
					RequestConstants.PARAMETER_NAME_PERPAGE }, new Object[] {
					((this.currentPage) * this.perpage), this.perpage }));
			this.nextPage.setPage(this.currentPage + 1);
			this.lastPage = new PaginationPage();
			this.lastPage.setUrl(this.buildUrl(this.baseUrl, new String[] {
					RequestConstants.PARAMETER_NAME_BEGIN,
					RequestConstants.PARAMETER_NAME_PERPAGE }, new Object[] {
					((this.totalPage - 1) * this.perpage), this.perpage }));
			this.lastPage.setPage(this.totalPage);
		}
		if (currentPage - 1 > 3) {
			this.setShowPreviousDot(true);
		}
		if (totalPage - currentPage > 3) {
			this.setShowNextDot(true);
		}

		for (int i = this.currentPage - 2; i < this.currentPage; i++) {
			if (i > 1) {
				PaginationPage tmp = new PaginationPage();
				tmp.setPage(i);
				tmp.setUrl(this
						.buildUrl(this.baseUrl, new String[] {
								RequestConstants.PARAMETER_NAME_BEGIN,
								RequestConstants.PARAMETER_NAME_PERPAGE },
								new Object[] { ((i - 1) * this.perpage),
										this.perpage }));
				this.previousPages.add(tmp);
			}
		}
		for (int i = this.currentPage + 1; i <= this.currentPage + 2; i++) {
			if (i < this.totalPage) {
				PaginationPage tmp = new PaginationPage();
				tmp.setPage(i);
				tmp.setUrl(this
						.buildUrl(this.baseUrl, new String[] {
								RequestConstants.PARAMETER_NAME_BEGIN,
								RequestConstants.PARAMETER_NAME_PERPAGE },
								new Object[] { ((i - 1) * this.perpage),
										this.perpage }));
				this.nextPages.add(tmp);
			}
		}

	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getPerpage() {
		return perpage;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public boolean isShowPreviousDot() {
		return showPreviousDot;
	}

	public void setShowPreviousDot(boolean showPreviousDot) {
		this.showPreviousDot = showPreviousDot;
	}

	public boolean isShowNextDot() {
		return showNextDot;
	}

	public void setShowNextDot(boolean showNextDot) {
		this.showNextDot = showNextDot;
	}

	public PaginationPage getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(PaginationPage firstPage) {
		this.firstPage = firstPage;
	}

	public PaginationPage getLastPage() {
		return lastPage;
	}

	public void setLastPage(PaginationPage lastPage) {
		this.lastPage = lastPage;
	}

	public PaginationPage getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(PaginationPage previousPage) {
		this.previousPage = previousPage;
	}

	public PaginationPage getNextPage() {
		return nextPage;
	}

	public void setNextPage(PaginationPage nextPage) {
		this.nextPage = nextPage;
	}

	public List<PaginationPage> getPreviousPages() {
		return previousPages;
	}

	public void setPreviousPages(List<PaginationPage> previousPages) {
		this.previousPages = previousPages;
	}

	public List<PaginationPage> getNextPages() {
		return nextPages;
	}

	public void setNextPages(List<PaginationPage> nextPages) {
		this.nextPages = nextPages;
	}

}
