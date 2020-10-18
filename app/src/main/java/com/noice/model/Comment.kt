package com.noice.model

data class Comment(
    var id: Int? = null,
    var parentId: Int? = null,
    var comment: String? = null,
    var child_count: Int? = null,
    var like_count: Int? = null,
    var share_count: Int? = null,
    var episode: Episode? = null,
    var post_date: String? = null,
    var user: User? = null
)