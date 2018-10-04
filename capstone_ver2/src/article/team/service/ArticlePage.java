package article.team.service;

import java.util.List;

import article.team.model.TeamArticle;

public class ArticlePage {

	private int total;
	private int currentPage;
	private List<TeamArticle> content;	//코드 재사용이 안됨.... 공지사항과 따로??
	private List<String> stuName;
	private int totalPages;
	private int startPage;
	private int endPage;

	public ArticlePage(int total, int currentPage, int size, List<TeamArticle> content, List<String> stuName) {
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		this.stuName = stuName;
		if (total == 0) {
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else {
			totalPages = total / size;
			if (total % size > 0) {
				totalPages++;
			}
			int modVal = currentPage % 5;
			startPage = currentPage / 5 * 5 + 1;
			if (modVal == 0) startPage -= 5;
			
			endPage = startPage + 4;
			if (endPage > totalPages) endPage = totalPages;
		}
	}

	public int getTotal() {
		return total;
	}

	public boolean hasNoArticles() {
		return total == 0;
	}

	public boolean hasArticles() {
		return total > 0;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<TeamArticle> getContent() {
		return content;
	}
	
	public List<String> getStuName() {
		return stuName;
	}

	public int getStartPage() {
		return startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
}
