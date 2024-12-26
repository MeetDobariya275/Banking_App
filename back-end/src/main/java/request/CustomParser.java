package request;

public class CustomParser {

  public static ParsedRequest parse(String request) {
    if (request == null || request.isEmpty()) {
      System.out.println("Error: Raw HTTP request is null or empty");
      throw new IllegalArgumentException("Invalid HTTP request");
    }

    String[] lines = request.split("(\r\n|\r|\n)");
    String requestLine = lines[0];
    String[] requestParts = requestLine.split(" ");
    var result = new ParsedRequest();

    try {
      // Debugging request line
      System.out.println("Request Line: " + requestLine);

      result.setMethod(requestParts[0]);
      var parts = requestParts[1].split("\\?");
      result.setPath(parts[0]);

      // Debugging path and query parameters
      System.out.println("Parsed Path: " + parts[0]);

      if (parts.length == 2) {
        System.out.println("Query Parameters: " + parts[1]);
        String[] queryParts = parts[1].split("&");
        for (int i = 0; i < queryParts.length; i++) {
          String[] pair = queryParts[i].split("=");
          result.setQueryParam(pair[0], pair.length > 1 ? pair[1] : "");
        }
      }

      boolean isBody = false;
      StringBuilder bodyBuilder = new StringBuilder();
      for (int i = 1; i < lines.length; i++) {
        String line = lines[i];
        if (line.isEmpty()) {
          isBody = true; // Marks the start of the body
          continue;
        }

        if (isBody) {
          bodyBuilder.append(line).append("\n");
        } else if (line.contains(":")) {
          String[] headerParts = line.split(": ", 2);
          String key = headerParts[0].trim();
          String value = headerParts.length > 1 ? headerParts[1].trim() : "";

          // Debugging headers
          System.out.println("Header: " + key + " -> " + value);

          if (!key.isEmpty() && !value.isEmpty() && !key.equalsIgnoreCase("cookie")) {
            result.setHeaderValue(key, value);
          } else if (key.equalsIgnoreCase("cookie") && value.contains("=")) {
            String[] cookieParts = value.split(";");
            for (String cookie : cookieParts) {
              String[] pair = cookie.trim().split("=", 2);
              if (pair.length == 2) {
                result.setCookieValue(pair[0].trim(), pair[1].trim());
              }
            }
          }
        }
      }

      String body = bodyBuilder.toString().trim();
      System.out.println("Extracted Body: " + body); // Debugging
      result.setBody(body);

    } catch (Exception e) {
      System.out.println("Error parsing HTTP request: " + e.getMessage());
      e.printStackTrace();
      throw e;
    }

    return result;
  }
}
