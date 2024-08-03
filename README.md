# Explicação das Modificações

## ClientHandler:
Foi adicionado a capacidade de enviar mensagens para todos os clientes conectados usando um método broadcast.
Há uma lista compartilhada (CopyOnWriteArrayList) para manter a referência de todos os clientes conectados.

## MultiTCPServer:
Usa a lista clients para gerenciar múltiplos ClientHandler.
Adicionado uma thread para permitir que o servidor envie mensagens.
Há um método broadcast para enviar mensagens a todos os clientes conectados.

## SimpleTCPClient:
Adicionado uma thread para ler mensagens do servidor continuamente.
É possível que o cliente envie mensagens continuamente em um loop.
Com as modificações feitas, tanto o servidor quanto os clientes podem enviar e receber mensagens continuamente, e o servidor pode gerenciar múltiplos clientes simultaneamente.
