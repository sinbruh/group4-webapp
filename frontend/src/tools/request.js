import {getCookie} from "@/tools/cookies";
import {HttpResponseError} from "@/tools/HttpResponseError";

const API_BASE_URL = 'http://localhost:8080';

export function asyncApiRequest(method, url, requestBody = null, returnPlainText = false) {
    const fullUrl = API_BASE_URL + url;
    let body = null;
    let headers = {};
    if (method.toLowerCase() !== "get" && requestBody) {
        headers["Content-Type"] = "application/json";
        body = JSON.stringify(requestBody);
    }
    const jwtToken = getCookie("jwt");
    if (jwtToken) {
        headers["Authorization"] = "Bearer " + jwtToken;
    }

    return fetch(fullUrl, {
        method: method,
        mode: "cors",
        headers: headers,
        body: body,
    })
        .then(handleErrors)
        .then((response) => returnPlainText ? response : response.json());
}

async function handleErrors(response) {
    if (!response.ok) {
        const responseText = await response.text();
        throw new HttpResponseError(response.status, responseText);
    }
    return response;
}