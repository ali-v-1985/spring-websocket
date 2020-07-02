package me.valizadeh.practices.springwebsocket.ws.scheduleresponse;

import me.valizadeh.practices.springwebsocket.ws.handler.SocketHandler;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

public class ResponseScheduler {

    public static final int LEFT_LIMIT = 97;
    public static final int RIGHT_LIMIT = 122;
    public static final int TARGET_STRING_LENGTH = 10;
    public static final Random random = new Random();

    /**
     * Sending message periodically on topic which user is subscribed to.
     */
    @Scheduled(fixedRate = 5000)
    public void scheduleTaskWithFixedRate() {


        String generatedString = random.ints(/*letter 'a'*/LEFT_LIMIT,
                /*letter 'z'*/RIGHT_LIMIT + 1)
                .limit(TARGET_STRING_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(generatedString);
        SocketHandler.sendMessage(generatedString);
    }
}
