package com.example.petminder.models.feeds

object FeedManager: FeedStore {
    var feeds = ArrayList<FeedModel>()


    override fun findAll(): MutableList<FeedModel> {
        return feeds
    }

    override fun create(feed: FeedModel) {
//        feed.id = generateRandomFeedId()
        feeds.add(feed)
    }

    override fun findByPet(petId: String): List<FeedModel> {
        val list = java.util.ArrayList<FeedModel>()
        for (i in feeds) {
            if (i.petId == petId) {
                list.add(i)
            }
        }
        return list
    }

    fun findOne(id: String): FeedModel? {
        var foundFeed: FeedModel? = feeds.find { p -> p.uid == id }
        return foundFeed
    }

    override fun deleteOne(feedId: String) {
        val foundFeed = findOne(feedId)
        if (foundFeed != null) {
            feeds.remove(foundFeed)
        }
    }


    override fun update(feed: FeedModel) {
        var foundFeed: FeedModel? = feeds.find { p -> p.uid == feed.uid }
        if (foundFeed != null) {
            foundFeed.time = feed.time
            foundFeed.weigth = feed.weigth
        }
    }
}

