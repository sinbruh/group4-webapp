

export class HttpResponseError extends Error {


    constructor(statusCode, message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    getErrorCode() {
        return this.statusCode;
    }
}