import org.junit.Test

import org.junit.Assert.*
import ru.netology.ChatService

class ChatServiceTest {
    val chatService = ChatService

    @Test
    fun add1() {
        val result = chatService.add("Malov", "Batrakov", "Text1")
        // проверяем результат (используйте assertTrue или assertFalse)
        assertEquals(result, 1)
    }

    @Test
    fun add2() {
        val result = chatService.add("Malov", "Abramov", "Text2")
        // проверяем результат (используйте assertTrue или assertFalse)
        assertEquals(result, 1)
    }

    @Test
    fun getUnreadChatsCount1() {
        val result = chatService.getUnreadChatsCount()
        assertEquals(result, 2)
    }

    @Test
    fun getUnreadChatsCount2() {
        val result = chatService.getUnreadChatsCount()
        assertEquals(result, 1)
    }

    @Test
    fun deleteMessages1() {
        val result = chatService.deleteMessages(0, 0)
        assertEquals(result, 0)
    }

    @Test
    fun deleteMessages2() {
        val result = chatService.deleteMessages(1, 1)
        assertEquals(result, 5)
    }

    @Test
    fun deleteChat1() {
        val result = chatService.deleteChat(0)
        assertEquals(result, 0)
    }

    @Test
    fun deleteChat2() {
        val result = chatService.deleteChat(1)
        assertEquals(result, 5)
    }

    @Test
    fun addMessages1() {
        val result = chatService.add("Malov", "Batrakov", "Text1")
        // проверяем результат (используйте assertTrue или assertFalse)
        assertEquals(result, 1)
    }

    @Test
    fun addMessages2() {
        val result = chatService.add("Malov", "Abramov", "Text2")
        // проверяем результат (используйте assertTrue или assertFalse)
        assertEquals(result, 7)
    }

    @Test
    fun getMessageInfo() {
        val result = chatService.getMessageInfo(0, 5, 1)
        assertEquals(result, "")
    }

}