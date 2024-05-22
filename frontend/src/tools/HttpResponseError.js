

export class HttpResponseError extends Error {


    constructor(statusCode, message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    getErrorCode() {
        return this.statusCode;
    }
}