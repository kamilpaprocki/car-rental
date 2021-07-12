package car_rental.api.exceptions;

import java.util.Objects;

public class ExceptionBody {

    private String message;
    private String timestamp;
    private String path;
    private int status;

    private ExceptionBody(ExceptionBodyBuilder e){
        this.path = e.path;
        this.status = e.status;
        this.timestamp = e.timestamp;
        this.message = e.message;
    }

    public static ExceptionBodyBuilder builder() {
        return new ExceptionBodyBuilder();
    }

    public static final class ExceptionBodyBuilder {
        private String message;
        private String timestamp;
        private String path;
        private int status;

         public ExceptionBodyBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExceptionBodyBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ExceptionBodyBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ExceptionBodyBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ExceptionBody build() {
            return new ExceptionBody(this);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExceptionBody)) return false;
        ExceptionBody that = (ExceptionBody) o;
        return getStatus() == that.getStatus() && getMessage().equals(that.getMessage()) && getTimestamp().equals(that.getTimestamp()) && getPath().equals(that.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getTimestamp(), getPath(), getStatus());
    }

    @Override
    public String toString() {
        return "ExceptionBody{" +
                "message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", path='" + path + '\'' +
                ", status=" + status +
                '}';
    }
}
