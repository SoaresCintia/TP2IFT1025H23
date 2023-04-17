package server;

/**
 * Cette interface fonctionnelle
 */

@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}
