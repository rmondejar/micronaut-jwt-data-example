package mn.jwt.data.controller

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import mn.jwt.data.dtos.MessageDto
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class MessageControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())

    @Shared
    String validToken


    def "Login with an existing user correctly"() {

        given: 'User data'
        String username = 'user2'
        String password = 'password2'

        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password)
        HttpRequest request = HttpRequest.POST('/login', credentials)
        HttpResponse<BearerAccessRefreshToken> response = client.toBlocking().exchange(request, BearerAccessRefreshToken)
        validToken = response?.body()?.accessToken

        then: 'JWT token is returned'
        response.status == HttpStatus.OK
        response.body()
        response.body().username == username
        validToken
        JWTParser.parse(validToken) instanceof SignedJWT
    }

    def "Retrieve messages from one validate user correctly"() {

        given: 'User data'
        String username = 'user2'

        when: 'Message endpoint is called with the token'
        HttpRequest requestWithAuthorization = HttpRequest.GET('/messages').header(HttpHeaders.AUTHORIZATION, "Bearer $validToken")
        HttpResponse<List<MessageDto>> response = client.toBlocking().exchange(requestWithAuthorization, List)
        List<MessageDto> messages = response.body()

        then:
        noExceptionThrown()
        response.status == HttpStatus.OK
        messages
        messages.first()
        messages.first().user
        messages.first().user.username == username
    }
}
