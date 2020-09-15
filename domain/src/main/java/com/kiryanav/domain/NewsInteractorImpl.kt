package com.kiryanav.domain

import com.kiryanav.domain.model.Article
import com.kiryanav.domain.model.ArticleSource
import com.kiryanav.domain.model.News
import com.kiryanav.domain.repoApi.LocalNewsRepository
import com.kiryanav.domain.repoApi.RemoteNewsRepository


class NewsInteractorImpl(
    private val newsRepo: RemoteNewsRepository,
    private val articleRepository: LocalNewsRepository
) : NewsInteractor {

    override suspend fun getNews(
        query: String?,
        from: String?,
        to: String?,
        sources: List<ArticleSource>,
        language: String?,
        pageNumber: Int
    ) =
        compareAndChooseArticles(
            newsRepo.getNews(query, from, to, sources, language, pageNumber),
            articleRepository.getAllSavedArticles()
        )


    override suspend fun saveArticle(article: Article) {
        articleRepository.saveArticle(article)
    }

    override suspend fun getSavedArticles(isLocalSavedFlagNeedToBeTrue : Boolean): List<Article> =
        articleRepository.getAllSavedArticles(isLocalSavedFlagNeedToBeTrue)

    override suspend fun getSourcesByLanguage(language: String): List<ArticleSource> =
        newsRepo.getSourcesByLanguage(language)

    override suspend fun getSavedSources(): List<ArticleSource> =
        articleRepository.getAllSources()


    override suspend fun saveSources(sources: List<ArticleSource>) =
        articleRepository.insertSources(sources)

    override suspend fun deleteSource(source: ArticleSource) {
        articleRepository.deleteSource(source)
    }

    override suspend fun getSources(): List<ArticleSource> {
        return compareAndChooseArticleSources(
            newsRepo.getSourcesByLanguage("ru"), //Stub
            articleRepository.getAllSources()
        )
    }

    override suspend fun deleteArticle(article: Article) {
        articleRepository.deleteArticle(article)
    }

    private fun compareAndChooseArticleSources(
        list1: List<ArticleSource>,
        list2: List<ArticleSource>
    ): List<ArticleSource> {

        val tmpList = mutableListOf<ArticleSource>()
        list1.forEach { item1 ->
            val item = list2.find { item2 ->
                 item1.name == item2.name
             }?.copy(isLocalSaved = true)
             //isLocalSaved = true
             ?: item1
            if (item.name?.contains(" ") == false) { // if source name contains " "-symbol NewsApi not found it
                tmpList.add(item)
            }
        }
        return tmpList
    }

    private fun compareAndChooseArticles(
        news: News,
        list2: List<Article>
    ): News {

        val tmpList = mutableListOf<Article>()

        news.articles.forEach { item1 ->
            val item = list2.find { item2 ->
                item1.title == item2.title
            }?.apply {
                isLocalSaved = true
            } ?: item1
            tmpList.add(item)
        }
        return News(news.resultNumber, tmpList)
    }

}