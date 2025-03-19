package io.github.yuanbaobaoo.dify.types;

public class DifyClientException extends RuntimeException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     */
    public DifyClientException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * @param message String
     */
    public DifyClientException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and  Throwable
     * @param message String
     * @param cause Throwable
     */
    public DifyClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the Throwable
     * @param cause Throwable
     */
    public DifyClientException(Throwable cause) {
        super(cause);
    }

}
