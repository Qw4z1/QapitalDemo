package se.devies.qapitaldemo.data

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

@Suppress("IllegalIdentifier")
class DemoRepoTest {

    lateinit var repo: DemoRepo

    @Mock lateinit var api: DemoApi
    @Mock lateinit var store: DemoStore

    @Before
    fun setUp() {
        initMocks(this)
        repo = DemoRepo(api, store)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun observeFeed() {
        val subject = PublishSubject.create<List<Feed>>()
        val feedWrapper = FeedWrapper(listOf(mock(Feed::class.java)))

        `when`(store.observeFeed(anyInt())).thenReturn(subject)
        `when`(api.getFeed(1)).thenReturn(Single.just(feedWrapper))
        `when`(store.insertFeed(ArgumentMatchers.anyList())).thenReturn(Completable.complete())

        repo.observeFeed(1).test()
        verify(store).insertFeed(ArgumentMatchers.anyList())
    }
}