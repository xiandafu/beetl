package org.beetl.ow2.asm;

/**
 * Capture any exception raised during ASM operations
 */
public class AsmException extends RuntimeException {

    private AsmContext context;

    public AsmException(String fmt, Object ... msg) {
        super(String.format(fmt, msg));
        context = AsmContext.reset();
    }

    public AsmException(Throwable cause) {
        super("", cause);
        context = AsmContext.reset();
    }

    public AsmContext context() {
        return context;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    public static AsmException of(Throwable cause) {
        if (cause instanceof AsmException) {
            return (AsmException) cause;
        }
        return new AsmException(cause);
    }

    public static AsmException of(String fmt, Object ... args) {
        return new AsmException(fmt, args);
    }
}
