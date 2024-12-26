package server;

import handler.BaseHandler;
import handler.HandlerFactory;
import handler.StatusCodes;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import request.CustomParser;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.HttpResponseBuilder;

public class Server {

    public static void main(String[] args) {
        Calendar.getInstance();
        ServerSocket serverSocket;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Opened socket " + 8080);
            while (true) {
                // keeps listening for new clients, one at a time
                try {
                    socket = serverSocket.accept(); // waits for client here
                } catch (IOException e) {
                    System.out.println("Error opening socket");
                    System.exit(1);
                }

                InputStream stream = socket.getInputStream();
                byte[] b = new byte[1024 * 20];
                int bytesRead = stream.read(b);
                if (bytesRead == -1) {
                    System.out.println("No data received from the client.");
                    continue; // Skip processing this request
                }
                String input = new String(b, 0, bytesRead).trim();
                System.out.println("Raw HTTP Request: " + input); // Debugging

                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                PrintWriter writer = new PrintWriter(out, true); // char output to the client

                // HTTP Response
                if (!input.isEmpty()) {
                    writer.println(processRequest(input));
                } else {
                    writer.println(new HttpResponseBuilder()
                            .setStatus("400 Bad Request")
                            .setBody("Empty or invalid request.")
                            .build()
                            .toString());
                }

                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error opening socket");
            System.exit(1);
        }
    }

    // Assume the HTTP server feeds the entire raw HTTP request here
    // Response is a raw HTTP response string
    public static String processRequest(String requestString) {
        // Handle empty or invalid request
        if (requestString == null || requestString.isBlank()) {
            System.out.println("Received an empty or invalid request.");
            return new HttpResponseBuilder()
                    .setStatus("400 Bad Request")
                    .setBody("Request is empty or invalid.")
                    .build()
                    .toString();
        }

        try {
            ParsedRequest request = CustomParser.parse(requestString);
            System.out.println("Parsed Request Path: " + request.getPath());
            System.out.println("Parsed Request Body: " + request.getBody());

            BaseHandler handler = HandlerFactory.getHandler(request);
            var builder = handler.handleRequest(request);
            builder.setHeader("Content-Type", "application/json");
            builder.setHeader("Access-Control-Allow-Origin", "*");
            var httpRes = builder.build();
            System.out.println("Generated Response: " + httpRes);
            return httpRes.toString();
        } catch (Exception e) {
            System.out.println("Error processing request: " + e.getMessage());
            e.printStackTrace();
            return new HttpResponseBuilder()
                    .setStatus(StatusCodes.SERVER_ERROR)
                    .setBody("Internal Server Error: " + e.toString())
                    .build()
                    .toString();
        }
    }
}
