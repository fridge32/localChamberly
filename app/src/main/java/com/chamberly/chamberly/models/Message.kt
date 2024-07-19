package com.chamberly.chamberly.models

import com.google.firebase.firestore.FieldValue

data class Message @JvmOverloads constructor(
    var UID: String ="",
    var message_content: String = "",
    var message_type: String = "",
    var sender_name: String = "",
    var message_id: String = "",
    var game_content: String = "",
    var reactedWith: String = "",
    var replyingTo: String = "",
    var message_date: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Message) return false
        return message_id == other.message_id &&
                message_content == other.message_content &&
                message_type == other.message_type &&
                reactedWith == other.reactedWith &&
                replyingTo == other.replyingTo
    }
}

fun Message.toMap(): Map<String, Any> {
    val messageMap: HashMap<String, Any> = hashMapOf(
        "UID" to this.UID,
        "message_content" to this.message_content,
        "message_type" to this.message_type,
        "sender_name" to this.sender_name,
        "message_id" to this.message_id,
    )
    if(reactedWith.isNotBlank()) {
        messageMap["reactedWith"] = this.reactedWith
    }
    if(replyingTo.isNotBlank()) {
        messageMap["replyingTo"] = this.replyingTo
    }
    if(game_content.isNotBlank()) {
        messageMap["game_content"] = this.game_content
    }
    if(message_date != null) {
        messageMap["message_date"] = this.message_date!!
    }
    return messageMap
}

//fun Message.e