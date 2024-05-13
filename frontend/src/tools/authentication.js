import {deleteCookie, getCookie, setCookie} from "./cookies";
import {asyncApiRequest} from "./request";

export function getAuthenticatedUser() {
    let user = null;
    const username = getCookie("current_username");
    const commaSeparatedRoles = getCookie("current_user_roles");
    if (username && commaSeparatedRoles) {
        const roles = commaSeparatedRoles.split(",");
        user = {
            username: username,
            roles: roles,
        };
    }
    return user;
}

export function isAdmin(user) {
    return user && user.roles && user.roles.includes("admin");
}

export async function sendAuthenticationRequest(
    username,
    password,
    successCallBack,
    errorCallBack
){
    const postData = {
        username: username,
        password: password,
    };
    try {
        const jwtResponse = await asyncApiRequest("POST", "/authenticate", postData);
        if (jwtResponse && jwtResponse.jwt) {
            setCookie("jwt", jwtResponse.jwt);
            if (userData) {
                setCookie("current_username", userData.username);
                setCookie("current_user_roles", userData.roles.join(","));
                successCallBack(userData);
            }
        }
    } catch (httpError) {
        errorCallBack(httpError);
    }
}

function parseJwt(token) {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const jsonPayload = decodeURIComponent(
        atob(base64)
            .split("")
            .map(function (c) {
                return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
            })
            .join("")
    );
    return JSON.parse(jsonPayload);
}

function parseJwtUser(jwtString) {
    let user = null;
    const jwtObject = parseJwt(jwtString);
    if (jwtObject) {
        user = {
            username: jwtObject.sub,
            roles: jwtObject.roles.map((r) => r.authority),
        };
    }
    return user;
}

export function deleteAuthorizationCookies() {
    deleteCookie("jwt");
    deleteCookie("current_username");
    deleteCookie("current_user_roles");
}





