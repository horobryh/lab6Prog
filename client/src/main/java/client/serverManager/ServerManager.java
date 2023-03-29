package client.serverManager;

import general.network.Request;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class ServerManager {
    private static ServerManager instance = null;
    private InetAddress inetAddress;
    private Integer host;

    public ServerManager(InetAddress inetAddress, Integer host) {
        this.inetAddress = inetAddress;
        this.host = host;
    }

    public static ServerManager getInstance() {
        return instance;
    }

    public static void setInstance(ServerManager instance) {
        ServerManager.instance = instance;
    }

    public Object sendRequestGetResponse(Request request, boolean responseNeeding) {
        SocketChannel socketChannel = getNewSocketChannel();
        try {
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            System.out.println("Произошла ошибка " + e);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream())) {
            oos.writeObject(request);
        } catch (IOException e) {
            System.out.println("Произошла ошибка отправки Request");
        }

        if (responseNeeding) {
            Object response = null;
            try (ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream())) {
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
        try (Socket socket = new Socket(this.inetAddress, this.host);) {
            return socket;
        } catch (IOException e) {
            System.out.println("Произошла ошибка при получении сокета.");
            return null;
        }
    }

    public SocketChannel getNewSocketChannel() {
        Socket socket = this.getNewSocket();
        try (SocketChannel socketChannel = SocketChannel.open(socket.getRemoteSocketAddress())) {
            return socketChannel;
        } catch (IOException e) {
            System.out.println("Произошла ошибка получения socketChannel");
            return null;
        }
    }
}
