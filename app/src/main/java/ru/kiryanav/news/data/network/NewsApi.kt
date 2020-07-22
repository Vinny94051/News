package ru.kiryanav.news.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kiryanav.news.data.dto.news.response.NewsResponse
import ru.kiryanav.news.data.dto.source.SourceResponse

interface NewsApi {

    /**
     * This endpoint returns the subset of news publishers that top headlines
     * (/v2/top-headlines) are available from. It's mainly a convenience endpoint
     * that you can use to keep track of the publishers available on the API,
     * and you can pipe it straight through to your users.
     *
     * @param category
     * Find sources that display news of this category.
     * Possible options:
     * business
     * entertainment
     * general
     * health
     * science
     * sports
     * technology.
     * Default: all categories.
     *
     * @param language
     * Find sources that display news in a specific language.
     * Possible options: ar de en es fr he it nl no pt ru se ud zh.
     * Default: all languages.
     *
     * @param country
     * Find sources that display news in a specific country.
     * Possible options: ae ar at au be bg br ca ch cn co cu cz de eg
     * fr gb gr hk hu id ie il in it jp kr lt lv ma mx my ng nl no nz
     * ph pl pt ro rs ru sa se sg si sk th tr tw ua us ve za .
     * Default: all countries.
     *
     * @param apiKey
     * Your API key.
     */

    @GET("sources")
    fun getSource(
        @Query("categoty") category: String,
        @Query("language") language: String,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = RetrofitClient.BASE_API_KEY
    ): Single<SourceResponse>

    /**
     * This endpoint provides live top and breaking headlines for a country,
     * specific category in a country, single source, or multiple sources. You can also search with keywords.
     * Articles are sorted by the earliest date published first.
     *This endpoint is great for retrieving headlines for display on news tickers or similar.
     *
     * @param category you want to get headlines for.
     * Possible options: business entertainment general health science sports technology .
     * Note: you can't mix this param with the sources param.
     *
     * @param query
     * Keywords or a phrase to search for.
     *
     * @param pageSize The number of results to return per page (request).
     * 20 is the default, 100 is the maximum.
     */


    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("q") query: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = RetrofitClient.BASE_API_KEY
    ): Single<NewsResponse>


    /**
     * Search through millions of articles from over 50,000 large and small news sources and blogs.
     * This includes breaking news as well as lesser articles.
     * This endpoint suits article discovery and analysis,
     * but can be used to retrieve articles for display, too.
     *
     * @param query
     * Keywords or a phrase to search for
     *
     * @param fromDate A date and optional time for the oldest article allowed.
     * This should be in ISO 8601 format (e.g. 2020-07-20 or 2020-07-20T06:24:14)
     * Default: the oldest according to your plan.
     *
     * @param toDate A date and optional time for the newest article allowed.
     * This should be in ISO 8601 format (e.g. 2020-07-20 or 2020-07-20T06:24:14)
     * Default: the newest according to your plan.
     *
     * @param language The 2-letter ISO-639-1 code of the language you want to get headlines for.
     * Possible options: ar de en es fr he it nl no pt ru se ud zh.
     * Default: all languages returned.
     *
     * @param sortBy The order to sort the articles in.
     * Possible options:
     * relevancy, popularity, publishedAt.
     * relevancy = articles more closely related to q come first.
     * popularity = articles from popular sources and publishers come first.
     * publishedAt = newest articles come first.
     * Default: publishedAt
     *
     * @param pageNumber - Use this to page through the results.
     *
     * @param pageSize - The number of results to return per page. 20 is the default, 100 is the maximum.
     */

    @GET("everything")
    fun getEverything(
        @Query("q") query: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("page") pageNumber : Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = RetrofitClient.BASE_API_KEY
    ): Single<NewsResponse>


    /**
     * Search through millions of articles from over 50,000 large and small news sources and blogs.
     * This includes breaking news as well as lesser articles.
     * This endpoint suits article discovery and analysis,
     * but can be used to retrieve articles for display, too.
     *
     * @param qInTitle
     * Keywords or phrases to search for in the article title only.
     *
     * @param fromDate A date and optional time for the oldest article allowed.
     * This should be in ISO 8601 format (e.g. 2020-07-20 or 2020-07-20T06:24:14)
     * Default: the oldest according to your plan.
     *
     * @param toDate A date and optional time for the newest article allowed.
     * This should be in ISO 8601 format (e.g. 2020-07-20 or 2020-07-20T06:24:14)
     * Default: the newest according to your plan.
     *
     * @param language The 2-letter ISO-639-1 code of the language you want to get headlines for.
     * Possible options: ar de en es fr he it nl no pt ru se ud zh.
     * Default: all languages returned.
     *
     * @param sortBy The order to sort the articles in.
     * Possible options:
     * relevancy, popularity, publishedAt.
     * relevancy = articles more closely related to q come first.
     * popularity = articles from popular sources and publishers come first.
     * publishedAt = newest articles come first.
     * Default: publishedAt
     */
    @GET("everything")
    fun getEverythingByTitle(
        @Query("qInTitle") qInTitle: String,
        @Query("sources") sources: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String = RetrofitClient.BASE_API_KEY
    ) : Single<NewsResponse>


}