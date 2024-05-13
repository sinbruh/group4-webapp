

export class HttpResponseError implements Error {


    constructor(statusCode, message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    getErrorCode() {
        return this.statusCode;
    }
}