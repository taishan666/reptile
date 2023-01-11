package com.tarzan.reptile.demo;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tarzan
 */
@Slf4j
public class HttpSocketClient {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 12001);
        SocketChannel socketChannel = SocketChannel.open(socketAddress);
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        while (true) {
            try {
                selector.select(1000);
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        try {
                            if (channel.read(byteBuffer) > 0) {
                                byte[] bytes = byteBuffer.array();
                                List<Byte> list = new ArrayList<>(1);
                                for (byte bt : bytes) {
                                    if (bt != 0) {
                                        list.add(bt);
                                    }
                                }
                                if (list.size() != 0) {
                                    byte[] newBytes = new byte[list.size()];
                                    for (int i = 0; i < list.size(); i++) {
                                        newBytes[i] = list.get(i);
                                    }
                                    System.out.println("收到来自服务器的消息：" + new String(newBytes));
                                }
                            }
                        } catch (IOException e) {
                            //    System.out.println("服务器异常....");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("服务器异常....");
                socketChannel.connect(socketAddress);
            }
        }
    }


}