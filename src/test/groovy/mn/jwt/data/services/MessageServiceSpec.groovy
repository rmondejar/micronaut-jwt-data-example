package mn.jwt.data.services

import io.micronaut.test.annotation.MicronautTest
import mn.jwt.data.domain.Message
import mn.jwt.data.domain.User
import mn.jwt.data.dtos.MessageDto
import mn.jwt.data.repositories.MessageRepository
import mn.jwt.data.repositories.UserRepository
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@MicronautTest
class MessageServiceSpec extends Specification {

    @Inject
    MessageRepository messageRepository

    @Inject
    UserRepository userRepository

    @Inject
    MessageService messageService

    @Unroll
    def "Checking last message #content from user #username"() {

        when:
        List<MessageDto> messages = messageService.findAllByUsername(username)

        then:
        !messages.empty
        messages.size() == 1
        messages.last()
        username == messages.last().user.username
        content == messages.last().content

        where:
        username    | content
        "user1"     | "My name is user1"
        "user2"     | "My name is user2"
        "user3"     | "My name is user3"
    }

    @Unroll
    def "Writing message #content for user #username"() {

        when:
        Optional<User> user = userRepository.findByUsername(username)

        then:
        user
        user.isPresent()

        when:
        Message message = Message.builder().content(content).user(user.get()).build()
        message = messageRepository.save(message)

        then:
        message
        message.id
        username == message.user.username
        content == message.content

        where:
        username    | content
        "user1"     | "new message for user1"
        "user1"     | "other message for user1"
        "user2"     | "important message of user2"
        "user2"     | "different message of user2"
        "user3"     | "hello lorep itsum user3"
        "user3"     | "world lorep itsum user3"
        "user3"     | "padding lorep itsum user3"
        "user3"     | "content lorep itsum user3"
    }
}