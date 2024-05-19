'use client';
import React, {useEffect, useState} from "react";
import Link from "next/link";
import logo from "/public/temp-logo-low.webp";
import Image from "next/image";
import LoginModal from "@/components/modal/LoginModal.jsx";
import {deleteAuthorizationCookies, isAdmin, isUser} from "@/tools/authentication";
import {getCookie} from "@/tools/cookies";

export function Navigation() {
    const [isUser, setIsUser] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false)

    useEffect(() => {
        const currentUserRoles = getCookie("current_user_roles");
        setIsUser(currentUserRoles.includes("ROLE_USER"));
        setIsAdmin(currentUserRoles.includes("ROLE_ADMIN"));
    }, []);

    const handleLogout = () => {
        deleteAuthorizationCookies();
        setIsUser(false);
        setIsAdmin(false)
        location.reload();
    };

    return (
        <div id="nav-container" className=" p-2 bg-white min-h-16 flex items-center justify-between">
            <header className="flex items-center">
                <Link href="/">
                    <Image
                        src={logo}
                        alt="Temporary logo"
                        width={32}
                        height={32}
                    />
                </Link>
                <Link href="/">
                    <h1 className="px-2 font-bold">Rental Roulette</h1>
                </Link>
            </header>
            <nav className="flex items-center justify-center grow gap-x-10 m-10px">
                <p>
                    <Link href="/" className="p-2 rounded hover:bg-[#F1F5F9] font-bold">Home</Link>
                </p>
                <p>
                    <Link href="about" className="p-2 rounded hover:bg-[#F1F5F9] font-bold">About</Link>
                </p>
                <p>
                    <Link href="contact" className="p-2 rounded hover:bg-[#F1F5F9] font-bold">Contact</Link>
                </p>
            </nav>
            {/* User icon */}
            {isUser && <button onClick={handleLogout}>Logout</button>}
            {!(isUser || isAdmin) && <LoginModal/>}
        </div>
    )
}

export default Navigation;
