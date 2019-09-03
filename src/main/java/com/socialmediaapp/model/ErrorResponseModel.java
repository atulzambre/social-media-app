package com.socialmediaapp.model;

import java.time.LocalDateTime;

public class ErrorResponseModel {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponseModel(ErrorResponseExceptionBuilder errorResponseExceptionBuilder) {
        this.error = errorResponseExceptionBuilder.error;
        this.message = errorResponseExceptionBuilder.message;
        this.status = errorResponseExceptionBuilder.status;
        this.timestamp = errorResponseExceptionBuilder.timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ErrorResponseExceptionBuilder {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;

        public ErrorResponseExceptionBuilder(int status) {
            this.status = status;
        }

        public ErrorResponseExceptionBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseExceptionBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ErrorResponseExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseModel build() {
            ErrorResponseModel errorResponseModel = new ErrorResponseModel(this);
            return errorResponseModel;
        }
    }
}
