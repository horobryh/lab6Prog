package client.serverManager;

import general.network.Request;
import org.apache.logging.log4j.core.util.JsonUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerManager {
    private static ServerManager instance = null;
    private InetAddress inetAddress;
    private Integer port;

    public ServerManager(InetAddress inetAddress, Integer port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public static ServerManager getInstance(InetAddress inetAddress, Integer port) {
        if (instance == null) {
            instance = new ServerManager(inetAddress, port);
        }
        return instance;
    }

    public static void setInstance(ServerManager instance) {
        ServerManager.instance = instance;
    }

    public Object sendRequestGetResponse(Request request, boolean responseNeeding) {
        SocketChannel socketChannel = getNewSocketChannel();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);

            byte[] objectBytes = baos.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(objectBytes);

            socketChannel.write(buffer);
        } catch (IOException e) {
            System.out.println("Произошла ошибка отправки Request");
        }

        if (responseNeeding) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Object response = null;
            try {
                int bytesRead = socketChannel.read(byteBuffer);
                while (bytesRead != -1) {
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        baos.write(byteBuffer.get());
                    }
                    byteBuffer.clear();
                    bytesRead = socketChannel.read(byteBuffer);
                }
                byte[] objectBytes = baos.toByteArray();
                ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                response = ois.readObject();
            } catch (IOException e) {
                System.out.println("Произошла ошибка чтения объекта");
            } catch (ClassNotFoundException e) {
                System.out.println("Произошла ошибка приведения класса");
            }
            return response;
        }
        return null;
    }

    public Socket getNewSocket() {
        try (Socket socket = new Socket(this.inetAddress, this.port);) {
            return socket;
        } catch (IOException e) {
            System.out.println("Произошла ошибка при получении сокета.");
            return null;
        }
    }

    public SocketChannel getNewSocketChannel() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(inetAddress, port));

            while (!socketChannel.finishConnect()) {}

            return socketChannel;

        } catch (IOException e) {
            System.out.println("Произошла ошибка получения socketChannel");
            return null;
        }
    }
}
