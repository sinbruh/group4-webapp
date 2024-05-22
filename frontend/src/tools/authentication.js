import { deleteCookie, getCookie, setCookie } from "./cookies";
import { asyncApiRequest } from "./request";
import { create } from "zustand";


export const useStore = create((set) => ({
    user: null,
    setUser: (user) => set({ user }),
    logout: () => set({ user: null }),
}));

export function getAuthenticatedUser() {
    let user = null;
    const email = getCookie("current_email");
    const commaSeparatedRoles = getCookie("current_user_roles");
    if (email && commaSeparatedRoles) {
        const roles = commaSeparatedRoles.split(",");
        user = {
            email: email,
            roles: roles,
        };
    }
    return user;
}

export function isAdmin() {
    const user = useStore.getState().user;
    const isAdmin = user && user.roles.includes("ROLE_ADMIN");

    return isAdmin;
}


export function isUser() {
    const user = useStore.getState().user;
    const isUser = user && user.roles.includes("ROLE_USER");

    return isUser;
}

export function isLoggedIn() {
    const user = useStore((state) => state.user);
    const setUser = useStore((state) => state.setUser);

    //attempt to get user from cookie if not logged in
    if (!user) {
        const authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser) {

            if (isJwtExpired()) {
                const reauthenticatedUser = reauthenticateUser();
                deleteAuthorizationCookies();
                if (reauthenticatedUser) {
                    setUser(reauthenticatedUser);
                }
            }
            setUser(authenticatedUser);
        }
    }

    return user != null;
}

export function isJwtExpired() {
    const jwt = getCookie("jwt");
    if (jwt) {
        const jwtData = parseJwt(jwt);
        if (jwtData) {
            const expirationDate = new Date(jwtData.exp * 1000);
            const currentDate = new Date();
            return expirationDate < currentDate;
        }
    }
    return true;
}

export function reauthenticateUser() {
    const email = getCookie("current_email");
    const jwt = getCookie("jwt");
    if (email && jwt) {
        sendAuthenticationRequest(email, jwt, (userData) => {
            console.log("Reauthenticated user: ", userData);
        }, (error) => {
            console.error("Error reauthenticating user: ", error);
        });
    }
}

export async function sendAuthenticationRequest(
    email,
    password,
    successCallBack,
    errorCallBack
) {
    const postData = {
        email: email,
        password: password,
    };
    try {
        const jwtResponse = await asyncApiRequest("POST", "/api/authenticate", postData);
        console.log("JWT response: ", jwtResponse);
        if (jwtResponse && jwtResponse.jwt) {
            const userData = parseJwtUser(jwtResponse.jwt);
            console.log("Parsed User data: ", userData)
            setCookie("jwt", jwtResponse.jwt);
            if (userData) {
                setCookie("current_email", userData.email);
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
            .map(function(c) {
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
            email: jwtObject.sub,
            roles: jwtObject.roles.map((r) => r.authority),
        };
    }
    return user;
}

export function deleteAuthorizationCookies() {
    deleteCookie("jwt");
    deleteCookie("current_email");
    deleteCookie("current_user_roles");
}





