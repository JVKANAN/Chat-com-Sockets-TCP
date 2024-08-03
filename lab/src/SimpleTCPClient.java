import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleTCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public void start(String serverIp, int serverPort) throws IOException {
        System.out.println("[C1] Conectando com servidor " + serverIp + ":" + serverPort);
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        System.out.println("[C2] Conexão estabelecida, eu sou o cliente: " + socket.getLocalSocketAddress());

        // Thread para ler mensagens do servidor
        new Thread(() -> {
            try {
                while (true) {
                    String message = input.readUTF();
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler mensagem do servidor: " + e.getMessage());
            }
        }).start();

        // Loop para enviar mensagens para o servidor
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            output.writeUTF(message);
        }
    }

    public void stop() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String serverIp = "10.220.0.16"; // Coloque o IP aqui
        int serverPort = 1337;
        try {
            SimpleTCPClient client = new SimpleTCPClient();
            client.start(serverIp, serverPort);
            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
