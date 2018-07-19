package com.duchong.thread.callback;

/**
 * @author DUCHONG
 * @since 2018-07-19 10:51
 **/
public class CallBackTest {

    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client(server);

        client.sendMsg("Server,Hello~");
    }

}
