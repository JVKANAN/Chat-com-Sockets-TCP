import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiTCPServer {
    private static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try {
            int serverPort = 1337;
            ServerSocket serverSocket = new ServerSocket(serverPort);

            System.out.println("Aguardando a conexao no endereço: " + InetAddress.getLocalHost() + ":" + serverPort);

            // Thread para ler mensagens do servidor
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String message = scanner.nextLine();
                    broadcast("Servidor: " + message);
                }
            }).start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, clients);
                clients.add(handler);
                handler.start();
                System.out.println("Conexao feita com: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }

    public static void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}
