package at.elias.misc;

public class CustomLogger {
    private boolean isError;
    private boolean outline;
    private String header;
    private String contentMessage;

    public CustomLogger(String header, String contentMessage) {
        setHeader(header);
        setContentMessage(contentMessage);
        setError(false);
        setHasOutline(false);
    }

    public CustomLogger setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
        return this;
    }

    public CustomLogger setError(boolean error) {
        this.isError = error;
        return this;
    }

    public CustomLogger setHasOutline(boolean outline) {
        this.outline = outline;
        return this;
    }

    public CustomLogger setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getContentMessage() {
        return this.contentMessage;
    }

    public String getHeader() {
        return this.header;
    }

    public boolean isError() {
        return this.isError;
    }

    public boolean hasOutline() {
        return this.outline;
    }

    public void send() {
        String content;
        content = getTime() + ": " + getHeader() + " | " + getContentMessage();
        if (this.outline) {
            String tempContent = content;
            String line = "";
            for (int i = 0; i < content.length(); i++)
                line += "=";
            content = line + "\n" + tempContent + "\n" + line;
        }
        if (this.isError)
            System.err.println(content);
        else
            System.out.println(content);

    }

    private String getTime() {
        return "1337";
    }
}
