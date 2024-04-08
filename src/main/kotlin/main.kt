package ru.netology

class ChatNotFoundException(message: String) : Exception(message)

data class Chat(
    var chatId: Int = 0,
    val interlocutorFio: String = "",
    val interlocutorId: Int?,
    var messages: MutableList<Messages> = mutableListOf(),
    val readingStatus: Boolean = false // true - все сообщения прочитаны
)

data class Messages(
    var messageId: Int = 0,
    var messageСhatId: Int = 0,
    val authorMessageId: Int? = 0,
    val message: String = "",
    val readingStatus: Boolean = false, // true - сообщение прочтено
)

object ChatService {
    private var chats = mutableListOf<Chat>()
    private var messagesAll = mutableListOf<Messages>()

    // База существующих пользователей в социальном сервисе
    var hashFio: HashMap<String, Int> =
        hashMapOf("Abramov" to 1, "Batrakov" to 2, "Cylov" to 3, "Dodonov" to 4, "Malov" to 5)

    private var chId = 0
    private var mId = 0

    /* ************************************************
    Возможности ChatService для пользователя:
    1. (+)Видеть, сколько чатов не прочитано (например, service.getUnreadChatsCount). В каждом из таких чатов есть хотя бы одно непрочитанное сообщение.
    2. (+)Получить список чатов (например, service.getChats).
    3. (-)Получить список последних сообщений из чатов (можно в виде списка строк). Если сообщений в чате нет (все были удалены), то пишется «нет сообщений».
    4. (+)Получить список сообщений из чата, указав:
    - ID собеседника;
    - количество сообщений. После того как вызвана эта функция, все отданные сообщения автоматически считаются прочитанными.
    5. (+)Создать новое сообщение.
    6. (+)Удалить сообщение.
    7. (+)Создать чат. Чат создаётся, когда пользователю отправляется первое сообщение.
    8. (+)Удалить чат, т.е. целиком удалить всю переписку.
    * *************************************************/


    // Создание чата (п.7)
    fun add(userFio: String, interlocutorFio: String, message: String) {
        if (hashFio.contains(userFio) == true && hashFio.contains(interlocutorFio) == true) {
            val message =
                Messages(
                    messageId = mId++,
                    messageСhatId = chId,
                    authorMessageId = hashFio.get(userFio),
                    message = message
                )

            var messages = mutableListOf<Messages>()

            messages.add(message)
            messagesAll.add(message)

            val chat = Chat(
                chatId = chId,
                interlocutorFio = interlocutorFio,
                interlocutorId = hashFio.get(interlocutorFio),
                messages = messages
            )
            chats.add(chat)
        } else throw ChatNotFoundException("Пользователь не найден. Вам и/или Вашему собеседнику необходимо зарегистрироваться в социальном сервисе")
        chId++
    }

    // Вывод списка чатов (п.2)
    fun getChats(): List<Chat> {
        return chats
    }

    // Количество непрочитанных чатов (п.1)
    fun getUnreadChatsCount(): Int {
        return chats.count { it.readingStatus == false }
    }

    // Удаление сообщения (п.6)
    //fun deleteMessages(messageId: Int, chatId: Int) {
    //    messagesAll.removeAll { it.messageId == messageId && it.messageСhatId == chatId }
    //    val chat = chats.find { it.chatId == chatId }
    //    if (chat != null) {
    //        chat.messages.removeIf { it.messageId == messageId }
    //    }
    //}
    fun deleteMessages(messageId: Int, chatId: Int) {
        messagesAll.removeAll { it.messageId == messageId && it.messageСhatId == chatId }
        chats
            .find { it.chatId == chatId }
            ?.messages?.removeIf { it.messageId == messageId }
    }

    // Удаление чата (п.8)
    fun deleteChat(chatId: Int) {
        chats.removeIf { it.chatId == chatId }
        messagesAll.removeIf { it.messageСhatId == chatId }
    }

    // Создание нового сообщения (п.5)
    //fun addMessages(messageСhatId: Int, userFio: String, message: String) {
    //    val message = Messages(
    //        messageId = mId++,
    //        messageСhatId = messageСhatId,
    //        authorMessageId = hashFio.get(userFio),
    //        message = message
    //    )
    //    messagesAll.add(message)

    //    val chat = chats.find { it.chatId == messageСhatId }
    //    if (chat != null) {
    //        chat.messages.add(message)
    //    }
    //}
    fun addMessages(messageСhatId: Int, userFio: String, message: String) {
        val message = Messages(
            messageId = mId++,
            messageСhatId = messageСhatId,
            authorMessageId = hashFio.get(userFio),
            message = message
        )
        messagesAll.add(message)

        chats
            .find { it.chatId == messageСhatId }
            ?.messages?.add(message)
    }

    //Получить список сообщений из чата, указав(п.4):
    //- ID собеседника;
    //- количество сообщений. После того как вызвана эта функция, все отданные сообщения автоматически считаются прочитанными.
    // Вывод списка сообщений
    //fun getMessageInfo(chatId: Int, authorMessageId: Int, count: Int): List<Messages> {//
    //    val chat = chats.find { it.chatId == chatId } ?: throw ChatNotFoundException("Чат не найден")
    //    val message = chat.messages.filter { it.authorMessageId == authorMessageId }.take(count)
    //    message.forEach { it.readingStatus == true }
    //    return message
    //}
    fun getMessageInfo(chatId: Int, authorMessageId: Int, count: Int): List<Messages> {
        val chat = chats
            .find { it.chatId == chatId } ?: throw ChatNotFoundException("Чат не найден")
        val message = chat.messages
            .filter { it.authorMessageId == authorMessageId }
            .take(count)

        message.forEach { it.readingStatus == true }
        return message
    }


    // Вывод списка последних сообщений из чатов (можно в виде списка строк). Если сообщений в чате нет (все были удалены), то пишется «нет сообщений» (п.3)
    //fun lastMessages(): List<String> = chats.map {
    //    if (it.messages.last().message == "") {
    //        it.messages.add(it.messages.last().copy(message = "Нет сообщений"))
    //    }
    //    it.messages.last().message
    //}
    fun lastMessages(): List<String> = chats.map {
        if (it.messages.last().message.isEmpty()) it.messages.add(it.messages.last().copy(message = "Нет сообщений"))
        it.messages.last().message
    }


    // Регистрация в социальном сервисе
    //fun registration(userFio: String) {
    //    if (hashFio.contains(userFio) == false) {
    //        hashFio.put(userFio, (hashFio.size + 1))
    //    }
    //}
    fun registration(userFio: String) {
        if (hashFio.contains(userFio) == false) hashFio.put(userFio, (hashFio.size + 1))
    }

    // Вывод списка сообщений
    fun getMessages(): List<Messages> {
        return messagesAll
    }

    fun printChats() {
        for (chat in chats) {
            println(chat)
        }
    }

    fun printMessage() {
        for (message in messagesAll) {
            println(message)
        }
    }

}


fun main() {
    val chatService = ChatService

    println("Создение чата")
    chatService.add("Malov", "Batrakov", "Text1")
    chatService.add("Malov", "Abramov", "Text2")
    println(chatService.getChats())

    println("\nВозвращение списка чатов")
    chatService.getChats()
    println(chatService.getChats())

    println("\nКоличество непрочитанных чатов")
    println(chatService.getUnreadChatsCount())

    println("\nУдаление сообщения")
    println(chatService.getChats())
    chatService.deleteMessages(0, 0)
    println(chatService.getChats())

    println("\nУдаление чата")
    chatService.deleteChat(0)
    println(chatService.getChats())


    println("\nСоздание нового сообщения")
    println(chatService.getMessages())
    chatService.addMessages(1, "Batrakov", "Текст4")
    chatService.addMessages(1, "Malov", "Текст5")
    println(chatService.getMessages())
    println(chatService.getChats())

    chatService.registration("Petrov")

    println("\nВывод списка последних сообщений из чатов")
    println(chatService.lastMessages())

    println("\nВывод списка сообщений из чатов")
    println(chatService.getMessageInfo(1, 5, 1))
    println(chatService.getMessageInfo(1, 5, 2))
}