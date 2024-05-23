'use client';
import React from "react";
import Link from "next/link";
import logo from "/public/temp-logo-low.webp";
import Image from "next/image";
import LoginModal from "@/components/modal/LoginModal.jsx";
import { isLoggedIn, useStore, deleteAuthorizationCookies } from "@/tools/authentication";
import userIcon from "@/img/icons/person.svg";
import { Button } from "@/components/ui/button";
import { DropDownMenu } from "@/components/ui/dropdownmenu";

export function Navigation() {


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
                <nav className="flex items-center justify-center grow gap-0 md:gap-x-10 m-10px">
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
                {/* User icon logic */}
                {isLoggedIn() ? <DropDownMenu /> : <LoginModal />}
            </div>
    )
}

export default Navigation;
